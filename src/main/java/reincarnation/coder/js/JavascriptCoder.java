/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import kiss.I;
import kiss.Variable;
import kiss.Ⅱ;
import kiss.Ⅲ;
import js.lang.Native;
import reincarnation.Debuggable;
import reincarnation.Operand;
import reincarnation.OperandConstructorCall;
import reincarnation.PatternCase;
import reincarnation.Reincarnation;
import reincarnation.coder.AnnotationLike;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.CodingOption;
import reincarnation.coder.DelegatableCoder;
import reincarnation.coder.Join;
import reincarnation.coder.Naming;
import reincarnation.coder.VariableNaming;
import reincarnation.operator.AccessMode;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;
import reincarnation.structure.Fragment;
import reincarnation.util.Classes;
import reincarnation.util.GeneratedCodes;
import reincarnation.util.MultiMap;

public class JavascriptCoder extends Coder<JavascriptCodingOption> {

    /** The current type. (maybe null in debug context) */
    private final Variable<Class> current = Variable.empty();

    /** The variable holder. */
    private final VariableNaming vars = new VariableNaming();

    /** The placeholder of local classes. */
    private final MultiMap<Executable, Class> placeholders = new MultiMap(false);

    /** The class name allocator. It is shared by every class written by this coder. */
    private NameAllocator names;

    /** The classes which are defined by this coder. */
    private final Set<Class> defined = new HashSet();

    /** Whether the standard runtime (collections) is required or not. */
    private boolean runtime;

    /** The decompiled abstract code. */
    private Reincarnation reincarnation;

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Reincarnation reincarnation) {
        // push decompiler info
        Reincarnation old = this.reincarnation;
        this.reincarnation = reincarnation;

        // search placeholder method or constructor for local classes
        reincarnation.locals.forEach(e -> {
            Constructor con = e.getEnclosingConstructor();
            if (con != null) placeholders.put(con, e);

            Method method = e.getEnclosingMethod();
            if (method != null) placeholders.put(method, e);
        });

        // Seed the class names in a stable order. The root class keeps its preferred name even if
        // a dependency declares the same simple name.
        seed(reincarnation);

        writeOne(reincarnation);

        // pop decompiler info
        this.reincarnation = old;
    }

    /**
     * Obtain the class name allocator of this coder.
     * 
     * @return A class name allocator.
     */
    private NameAllocator names() {
        if (names == null) {
            names = new NameAllocator();
        }
        return names;
    }

    /**
     * Pre-allocate the class names in a stable order.
     * 
     * @param reincarnation A target class.
     */
    private void seed(Reincarnation reincarnation) {
        NameAllocator allocator = names();

        defined.add(reincarnation.clazz);
        defined.addAll(reincarnation.anonymous);
        defined.addAll(reincarnation.locals);

        allocator.seed(reincarnation.clazz);
        reincarnation.classes.forEach(type -> {
            if (!Classlib.isNative(type)) {
                allocator.seed(Classlib.replace(type));
            }
        });
        reincarnation.locals.forEach(allocator::seed);
        reincarnation.anonymous.forEach(allocator::seed);

        // Allocate the member names before writing the class so that the declaration and every
        // reference share the same name.
        allocator.seedMembers(reincarnation.clazz);
    }

    private void writeOne(Reincarnation reincarnation) {
        // push decompiler info
        Reincarnation old = this.reincarnation;
        this.reincarnation = reincarnation;

        writeType(reincarnation.clazz, () -> {
            reincarnation.staticFields.forEach(this::writeStaticField);
            reincarnation.staticInitializer.forEach(this::writeStaticInitializer);
            reincarnation.fields.forEach(this::writeField);
            reincarnation.initializer.forEach(this::writeInitializer);
            reincarnation.constructors.forEach(this::writeConstructor);
            reincarnation.methods.forEach(this::writeMethod);
        });

        // In JavaScript, a class cannot be declared inside another class body, so the anonymous
        // classes are written as independent top-level classes after the enclosing class. In the
        // bundle mode, every class is written separately by the driver.
        if (!options.bundle) {
            for (Class clazz : reincarnation.anonymous) {
                Class superclass = clazz.getSuperclass();
                if (superclass == null || !superclass.isEnum()) {
                    writeOne(Reincarnation.exhume(clazz));
                }
            }
        }

        // pop decompiler info
        this.reincarnation = old;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String code = super.toString();

        // A bundle embeds the runtime once at its header.
        if (!options.embedRuntime) {
            return code;
        }

        StringBuilder builder = new StringBuilder(JavascriptRuntime.Core).append(EoL).append(EoL);
        builder.append(JavascriptRuntime.Intrinsics).append(EoL).append(EoL);

        if (runtime) {
            builder.append(JavascriptRuntime.Standard).append(EoL).append(EoL);
        }
        return builder.append(code).toString();
    }

    /**
     * Share the class names and the defined classes with the other coders of the same bundle.
     * 
     * @param names A shared name allocator.
     * @param defined The classes which are defined by the bundle.
     */
    void share(NameAllocator names, Set<Class> defined) {
        this.names = names;
        this.defined.addAll(defined);
    }

    /**
     * Check whether the standard runtime is required.
     * 
     * @return A result.
     */
    boolean requiresRuntime() {
        return runtime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePackage(Package info) {
        // JavaScript has no package concept.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
        Class prev = current.set(type);
        defined.add(type);

        // JavaScript has no interface, enum, record and annotation, so every type is written as a
        // class declaration. The superclass of the enum and the record is also dropped.
        Class superclass = type.getSuperclass();
        boolean object = superclass == null || superclass == Object.class //
                || superclass == Enum.class || superclass == Record.class;

        // JavaScript has no class declaration which can be referenced before its definition,
        // so the class is registered by name. The super class is resolved by its name at the
        // registration time, which removes the temporal dead zone problem.
        String parent;
        if (superclass == Enum.class) {
            parent = "$rt.Enum";
        } else if (object) {
            parent = "";
        } else {
            parent = names().allocate(superclass);
        }
        Join interfaces = Join.of(type.getInterfaces())
                .prefix("[")
                .separator("," + space)
                .suffix("]")
                .converter(x -> quote(simpleName(x)));

        line();
        line("$rt.define(", quote(simpleName(type)), ",", space, quote(parent), ",", space, interfaces, ",", space, "{");
        indent(inner);
        if (options.reflection) {
            line("},", space, metadata(type), ");");
        } else {
            line("});");
        }

        current.set(prev);
    }

    /**
     * Build the reflection metadata of the specified class.
     * 
     * @param type A target class.
     * @return A JavaScript object literal.
     */
    private String metadata(Class type) {
        Class origin = Classlib.origin(type);
        Class identity = origin == null ? type : origin;
        String external = Classlib.name(type);
        StringBuilder builder = new StringBuilder("{n:");
        builder.append(quote(external == null ? identity.getName() : external));
        builder.append(",m:")
                .append(external == null ? identity.getModifiers() : identity.getModifiers() & ~java.lang.reflect.Modifier.PUBLIC);
        builder.append(",e:").append(type.isEnum());
        builder.append(",as:").append(annotations(type));
        builder.append(",tp:").append(genericTypeParameters(identity.getTypeParameters()));
        builder.append(",gs:").append(genericType(identity.getGenericSuperclass()));
        builder.append(",gi:[").append(genericTypes(identity.getGenericInterfaces())).append("]");
        Class[] identityInterfaces = identity.getInterfaces();
        StringBuilder interfaces = new StringBuilder(",i:[");
        for (int i = 0; i < identityInterfaces.length; i++) {
            if (i != 0) {
                interfaces.append(",");
            }
            interfaces.append(quote(simpleName(Classlib.replace(identityInterfaces[i]))));
        }
        builder.append(interfaces).append("]");
        if (origin != null || external != null) {
            builder.append(",a:[").append(quote(type.getName())).append("]");
        }

        StringBuilder methods = new StringBuilder("ms:[");
        Class memberSource = origin == null ? type : origin;
        Join.of(memberSource.getDeclaredMethods())
                .skip(Method::isSynthetic)
                .skip(Method::isBridge)
                .separator(",")
                .converter(m -> "[" + quote(m.getName()) + "," + quote(org.objectweb.asm.Type.getMethodDescriptor(m)) + "," + m
                        .getModifiers() + "," + m.getParameterCount() + "," + annotations(m) + "," + genericTypeParameters(m
                                .getTypeParameters()) + ",[" + genericTypes(m.getGenericParameterTypes()) + "]," + genericType(m
                                        .getGenericReturnType()) + ",[" + genericTypes(m
                                                .getGenericExceptionTypes()) + "]," + exceptionTypes(m.getExceptionTypes()) + ","
                                                + quote(methodName(m)) + "]")
                .write(methods);
        builder.append(",").append(methods).append("]");

        StringBuilder fields = new StringBuilder("fs:[");
        Join.of(memberSource.getDeclaredFields())
                .skip(Field::isSynthetic)
                .separator(",")
                .converter(f -> "[" + quote(f.getName()) + "," + quote(org.objectweb.asm.Type.getDescriptor(f.getType())) + "," + f
                        .getModifiers() + "," + annotations(f) + "," + genericType(f.getGenericType()) + ","
                        + quote(fieldName(f)) + "]")
                .write(fields);
        builder.append(",").append(fields).append("]");

        StringBuilder constructors = new StringBuilder("cs:[");
        Join.of(type.getDeclaredConstructors())
                .skip(Constructor::isSynthetic)
                .separator(",")
                .converter(c -> "[" + quote(org.objectweb.asm.Type.getConstructorDescriptor(c)) + "," + c.getModifiers() + "," + constructorIndex(c) + "," + c
                        .getParameterCount() + "," + annotations(c) + "," + genericTypeParameters(c
                                .getTypeParameters()) + ",[" + genericTypes(c.getGenericParameterTypes()) + "],[" + genericTypes(c
                                        .getGenericExceptionTypes()) + "]," + exceptionTypes(c.getExceptionTypes()) + "]")
                .write(constructors);
        builder.append(",").append(constructors).append("]");

        return builder.append("}").toString();
    }

    /**
     * Build the annotation metadata of the specified element.
     * 
     * @param element A target element.
     * @return A JavaScript array literal.
     */
    private String annotations(AnnotatedElement element) {
        java.lang.annotation.Annotation[] annotations = element.getDeclaredAnnotations();
        StringBuilder builder = new StringBuilder("[");
        boolean first = true;

        for (java.lang.annotation.Annotation annotation : annotations) {
            // The meta annotation (e.g. @Retention and @Target) is not a part of the application
            // metadata, and its enum value references the JDK class.
            if (annotation.annotationType().getName().startsWith("java.lang.annotation.")) {
                continue;
            }
            if (!first) {
                builder.append(",");
            }
            first = false;
            builder.append(annotation(annotation));
        }
        return builder.append("]").toString();
    }

    /**
     * Build the annotation metadata.
     * 
     * @param annotation A target annotation.
     * @return A JavaScript object literal.
     */
    private String annotation(java.lang.annotation.Annotation annotation) {
        Class<?> type = annotation.annotationType();
        StringBuilder builder = new StringBuilder("{t:").append(quote(type.getName())).append(",v:{");
        boolean first = true;

        for (Method attribute : type.getDeclaredMethods()) {
            if (attribute.getParameterCount() != 0 || attribute.isSynthetic()) {
                continue;
            }
            try {
                attribute.setAccessible(true);
                Object value = attribute.invoke(annotation);

                if (!first) {
                    builder.append(",");
                }
                first = false;
                builder.append(quote(attribute.getName())).append(":").append(annotationValue(value));
            } catch (Throwable e) {
                // The annotation value may reference a missing class, so it is ignored.
            }
        }
        return builder.append("}}").toString();
    }

    /**
     * Build the annotation attribute value.
     * 
     * @param value A target value.
     * @return A JavaScript expression.
     */
    private String annotationValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String text) {
            return quote(text);
        } else if (value instanceof Character ch) {
            return quote(String.valueOf(ch));
        } else if (value instanceof Boolean || value instanceof Number) {
            return String.valueOf(value);
        } else if (value instanceof Class clazz) {
            return "{c:" + quote(clazz.getName()) + "}";
        } else if (value instanceof Enum constant) {
            return "{e:" + quote(constant.getDeclaringClass().getName()) + ",n:" + quote(constant.name()) + ",j:"
                    + quote(enumFieldName(constant.getDeclaringClass(), constant.name())) + "}";
        } else if (value instanceof java.lang.annotation.Annotation annotation) {
            return annotation(annotation);
        } else if (value.getClass().isArray()) {
            StringBuilder builder = new StringBuilder("[");
            int length = java.lang.reflect.Array.getLength(value);

            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    builder.append(",");
                }
                builder.append(annotationValue(java.lang.reflect.Array.get(value, i)));
            }
            return builder.append("]").toString();
        }
        return "null";
    }

    /**
     * Build the generic type metadata.
     * 
     * @param type A target type.
     * @return A JavaScript object literal.
     */
    private String genericType(Type type) {
        if (type instanceof Class clazz) {
            return "{k:\"c\",n:" + quote(clazz.getName()) + "}";
        } else if (type instanceof TypeVariable variable) {
            return "{k:\"v\",n:" + quote(variable.getName()) + "}";
        } else if (type instanceof ParameterizedType parameterized) {
            return "{k:\"p\",r:" + genericType(parameterized.getRawType()) + ",a:[" + genericTypes(parameterized
                    .getActualTypeArguments()) + "]}";
        } else if (type instanceof GenericArrayType array) {
            return "{k:\"a\",c:" + genericType(array.getGenericComponentType()) + "}";
        } else if (type instanceof WildcardType wild) {
            return "{k:\"w\",u:[" + genericTypes(wild.getUpperBounds()) + "],l:[" + genericTypes(wild.getLowerBounds()) + "]}";
        }
        return "null";
    }

    /**
     * Build the generic type metadata array.
     * 
     * @param types A target types.
     * @return A JavaScript array literal.
     */
    private String genericTypes(Type[] types) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(genericType(types[i]));
        }
        return builder.toString();
    }

    /**
     * Build the type parameter metadata.
     * 
     * @param variables A target type variables.
     * @return A JavaScript array literal.
     */
    private String genericTypeParameters(TypeVariable<?>[] variables) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < variables.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            TypeVariable<?> variable = variables[i];
            builder.append("{k:\"v\",n:").append(quote(variable.getName())).append(",b:[").append(genericTypes(variable
                    .getBounds())).append("]}");
        }
        return builder.append("]").toString();
    }

    /**
     * Build the exception type metadata.
     * 
     * @param types A target exception types.
     * @return A JavaScript array literal.
     */
    private String exceptionTypes(Class[] types) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append("{k:\"c\",n:").append(quote(types[i].getName())).append("}");
        }
        return builder.append("]").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeField(Field field) {
        writeStaticField(field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticField(Field field) {
        // ignore compiler generated code. The record fields are not declared, because a record
        // field and its accessor method have the same name in JavaScript.
        if (GeneratedCodes.isRecordField(field) || GeneratedCodes.isEnumField(field) || GeneratedCodes.isAssertionField(field)) {
            return;
        }

        // ignore, write fields in static initializer
        if (current.is(Class::isInterface) || field.getDeclaringClass().isEnum()) {
            return;
        }

        writeFieldDefinition(field);
    }

    /**
     * Write field definition.
     * 
     * @param field
     */
    private void writeFieldDefinition(Field field) {
        if (field.isSynthetic()) {
            return;
        }

        // Instance fields are stored as the prototype default, static fields are stored on the
        // class object. The underscore prefix distinguishes the static member in the definition.
        line(Classes.isStatic(field) ? "_" : "", fieldName(field), ":", space, jsDefault(field.getType()), ",");
    }


    /**
     * Write the double value as a JavaScript literal. JavaScript has no NaN, Infinity or -Infinity
     * literal, so they are written as the global constants.
     * 
     * @param value A target value.
     * @return A JavaScript literal.
     */
    private String doubleLiteral(double value) {
        if (Double.isNaN(value)) {
            return "NaN";
        } else if (value == Double.POSITIVE_INFINITY) {
            return "Infinity";
        } else if (value == Double.NEGATIVE_INFINITY) {
            return "-Infinity";
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Compute the JavaScript initial value of the given field type. In JavaScript, the uninitialized
     * field becomes {@code undefined}, so the initial value must be written explicitly to match the
     * Java behavior.
     * 
     * @param type A field type.
     * @return An initial value.
     */
    private String jsDefault(Class type) {
        if (type == boolean.class) {
            return "false";
        } else if (type == char.class) {
            return "\"\\u0000\"";
        } else if (type == long.class) {
            return "0n";
        } else if (type.isPrimitive()) {
            return "0";
        } else {
            return "null";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInitializer(Code code) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticInitializer(Code code) {
        vars.start();

        // The static fields of an enum are not written by writeStaticField, so they are written
        // here as the definition entries.
        if (current.is(Class::isEnum)) {
            I.signal(current.get().getDeclaredFields()).skip(Field::isSynthetic).skip(Field::isEnumConstant).to(this::writeFieldDefinition);

            // values() and valueOf() are removed as the generated methods, so they are
            // synthesized here.
            Class type = current.exact();
            Join values = Join.of(type.getEnumConstants())
                    .separator("," + space)
                    .converter(x -> "$c." + simpleName(type) + "." + enumFieldName(type, ((Enum) x).name()));

            line();
            line("_values: function() { return [", values, "]; },");
            line("_valueOf: function(name) { return $rt.enumValueOf(", classRef(type), ", name); },");
        }

        // The static initializer is deferred until the class is accessed for the first time,
        // which reproduces the Java class initialization semantics.
        line();
        line("_: function() {");
        indent(() -> {
            if (current.is(Class::isInterface)) {
                code.write(new InterfaceStaticInitiailizer(this));
            } else if (current.is(Class::isEnum)) {
                EnumStaticInitializer coder = new EnumStaticInitializer(this);
                code.write(coder);
                coder.codes.forEach(this::writeStatement);
            } else {
                code.write(this);
            }
        });
        line("},");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor con, Code<Code> code) {
        // ignore compiler generated code
        if (GeneratedCodes.isEnumConstructor(con, code)) {
            return;
        }

        vars.start();

        line();
        line(constructorName(con), ":", space, "function", buildParameter(con, naming(code)), space, "{");
        indent(() -> {
            Class owner = con.getDeclaringClass();
            if (Classes.isMemberLike(owner) && Classes.isNonStatic(owner)) {
                code.write(new NonStaticLocalConstructor(this));
            } else {
                code.write(this);
            }
        });
        line("},");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code<Code> code) {
        // ignore compiler generated code
        if (method.isSynthetic() || GeneratedCodes.isEnumMethod(method, code)) {
            return;
        }

        // The record accessors are kept, but the state-driven Object methods cannot be converted.
        if (method.getDeclaringClass().isRecord()
                && (method.getName().equals("toString") || method.getName().equals("hashCode") || method.getName().equals("equals"))) {
            return;
        }

        vars.start();

        line();
        line(Classes.isStatic(method) ? "_" : "", methodName(method), ":", space, "function", buildParameter(method, naming(code)), space, "{");
        if (!options.bundle) {
            placeholders.get(method).forEach(this::writeLocalClass);
        }
        indent(code::write);
        line("},");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLambda(Method method, List<Code> contexts, Code code) {
        vars.start();

        Naming naming = naming(code);
        Join<Parameter> param = Join.of(method.getParameters())
                .ignoreSingle()
                .prefix("(")
                .separator("," + space)
                .suffix(")")
                .take((index, item) -> contexts.size() <= index)
                .converter(p -> {
                    String name = safeVariable(naming.name(p.getName()));
                    vars.declare(name);
                    return name;
                });

        for (int i = 0; i < contexts.size(); i++) {
            vars.declare("arg" + i, contexts.get(i).toString());
        }

        lineNI(param, space, "=>", space, "{");
        indent(code::write);
        lineNB("}");

        vars.end();
    }

    /**
     * Write local class.
     * 
     * @param clazz
     */
    private void writeLocalClass(Class clazz) {
        indent(() -> {
            writeOne(Reincarnation.exhume(clazz));
        });
        line();
    }

    /**
     * Build parameter types.
     * 
     * @param parameters
     * @return
     */
    private Join buildParameter(Executable executable, Naming strategy) {
        List<Ⅱ<Parameter, Type>> params = I.signal(executable.getParameters())
                .combine(I.signal(Classes.fixGenericParameterTypes(executable)))
                .toList();

        return Join.of(params)
                .prefix("(")
                .suffix(")")
                .separator("," + space)
                .skip(x -> GeneratedCodes.isEnumParameter(x.ⅰ))
                .converter(p -> {
                    Parameter param = p.ⅰ;
                    String name = safeVariable(strategy.name(param.getName()));
                    vars.declare(name);
                    return name;
                });
    }

    /**
     * Build throws declaration.
     * 
     * @param parameters
     * @return
     */
    private Join thrower(Type[] exceptions) {
        return Join.of(exceptions).ignoreEmpty().prefix(space + "throws" + space).separator("," + space).converter(type -> {
            return name(type);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStatement(Code<?> code) {
        snapshot(() -> {
            line(code, ";", code.comment().map(" //"::concat));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Variable<Code> code) {
        if (code.isAbsent()) {
            write("return");
        } else {
            write("return ", code.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeYield(Code code) {
        write("yield ", code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBoolean(boolean code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeChar(char code) {
        write("\"", escapeChar(code), "\"");
    }

    /**
     * Escape the character for the JavaScript string literal.
     * 
     * @param code A target character.
     * @return An escaped text.
     */
    private String escapeChar(char code) {
        switch (code) {
        case '"':
            return "\\\"";
        case '\\':
            return "\\\\";
        case '\n':
            return "\\n";
        case '\r':
            return "\\r";
        case '\t':
            return "\\t";
        case '\b':
            return "\\b";
        case '\f':
            return "\\f";
        default:
            // line separator and paragraph separator must be escaped, and other control
            // characters are escaped for readability.
            if (code < 0x20 || code == 0x7f || code == 0x2028 || code == 0x2029) {
                return String.format("\\u%04x", (int) code);
            }
            return String.valueOf(code);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInt(int code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLong(long code) {
        write(code, "n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFloat(float code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDouble(double code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeString(String code) {
        write("\"", code, "\"");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTextBlock(List<String> code) {
        write("`", EoL);
        indent(() -> {
            for (String lineText : code) {
                line(lineText);
            }
            lineNB("`");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStringConcatenation(Iterator<Code> codes) {
        if (codes.hasNext()) {
            write(codes.next());

            while (codes.hasNext()) {
                write(space, "+", space, codes.next());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeClass(Class code) {
        if (options.reflection) {
            requireRuntime();
            if (isReflectionType(code)) {
                write(classRef(code));
            } else if (code.isPrimitive()) {
                write("$rt.Class.primitive(", quote(code.getName()), ")");
            } else if (code.isArray()) {
                write("$rt.Class.array(", buildClass(code.getComponentType()), ")");
            } else {
                write("$rt.reflect(", classRef(code), ")");
            }
        } else {
            write(classRef(code));
        }
    }

    /**
     * Build the class expression for the reflection.
     * 
     * @param code A target class.
     * @return A JavaScript expression.
     */
    private String buildClass(Class code) {
        if (code.isPrimitive()) {
            return "$rt.Class.primitive(" + quote(code.getName()) + ")";
        } else if (code.isArray()) {
            return "$rt.Class.array(" + buildClass(code.getComponentType()) + ")";
        } else {
            return "$rt.reflect(" + classRef(code) + ")";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThis() {
        write("this");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNull() {
        write("null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeEnclose(Runnable code) {
        write("(");
        code.run();
        write(")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
        if (isLong(left)) {
            if (operator == AssignOperator.ASSIGN) {
                write(left, space, "=", space, longOperand(right));
                return;
            }

            switch (operator) {
            case PLUS:
                write(left, space, "=", space, "$rt_lr(", left, " + ", longOperand(right), ")");
                return;
            case MINUS:
                write(left, space, "=", space, "$rt_lr(", left, " - ", longOperand(right), ")");
                return;
            case MULTIPLY:
                write(left, space, "=", space, "$rt_lr(", left, " * ", longOperand(right), ")");
                return;
            case DIVIDE:
                write(left, space, "=", space, "$rt_ldiv(", left, ",", space, longOperand(right), ")");
                return;
            case REMAINDER:
                write(left, space, "=", space, "$rt_lrem(", left, ",", space, longOperand(right), ")");
                return;
            default:
                write(left, space, operator, space, longOperand(right));
                return;
            }
        }
        write(left, space, operator, space, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBinaryOperation(Code left, BinaryOperator operator, Code right) {
        if ((isLong(left) || isLong(right)) && operator != BinaryOperator.AND && operator != BinaryOperator.OR) {
            writeLongOperation(operator, left, right);
        } else {
            write(left, space, operator, space, right);
        }
    }

    /**
     * Write the binary operation on the {@code long} values.
     * 
     * @param operator A target operator.
     * @param left A left value.
     * @param right A right value.
     */
    private void writeLongOperation(BinaryOperator operator, Code left, Code right) {
        Code l = longOperand(left);
        Code r = longOperand(right);

        switch (operator) {
        case PLUS:
            write("$rt_lr(", l, " + ", r, ")");
            break;
        case MINUS:
            write("$rt_lr(", l, " - ", r, ")");
            break;
        case MULTIPLY:
            write("$rt_lr(", l, " * ", r, ")");
            break;
        case DIVIDE:
            write("$rt_ldiv(", l, ",", space, r, ")");
            break;
        case REMAINDER:
            write("$rt_lrem(", l, ",", space, r, ")");
            break;
        case BINARY_AND:
            write("(", l, " & ", r, ")");
            break;
        case BINARY_OR:
            write("(", l, " | ", r, ")");
            break;
        case XOR:
            write("(", l, " ^ ", r, ")");
            break;
        case LEFT_SHIFT:
            write("$rt_lr(", l, " << (", r, " & 63n))");
            break;
        case RIGHT_SHIFT:
            write("(", l, " >> (", r, " & 63n))");
            break;
        case UNSIGNED_RIGHT_SHIFT:
            write("$rt_lushr(", l, ",", space, r, ")");
            break;
        case EQUAL:
            write(l, space, "===", space, r);
            break;
        case NOT_EQUALS:
            write(l, space, "!==", space, r);
            break;
        case LESS:
            write(l, space, "<", space, r);
            break;
        case LESS_EQUALS:
            write(l, space, "<=", space, r);
            break;
        case GREATER:
            write(l, space, ">", space, r);
            break;
        case GREATER_EQUALS:
            write(l, space, ">=", space, r);
            break;
        default:
            write(left, space, operator, space, right);
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePositiveOperation(Code code) {
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNegativeOperation(Code code) {
        // In JavaScript, the unary operator is applied before the comparison operator, so the
        // whole condition must be enclosed. (e.g. "!value.equals(\"\")" -> "!(value === \"\")")
        write("!", "(", code, ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeUnaryOperation(Code code, UnaryOperator operator) {
        if (isLong(code)) {
            switch (operator) {
            case MINUS:
                write("$rt_lr(-", code, ")");
                return;
            case BITWISE_COMPLEMENT:
                write("(~", code, ")");
                return;
            case PRE_INCREMENT:
                write("(", code, space, "=", space, "$rt_lr(", code, " + 1n))");
                return;
            case PRE_DECREMENT:
                write("(", code, space, "=", space, "$rt_lr(", code, " - 1n))");
                return;
            case POST_INCREMENT:
                write("(() => { const $t = ", code, "; ", code, " = $rt_lr($t + 1n); return $t; })()");
                return;
            case POST_DECREMENT:
                write("(() => { const $t = ", code, "; ", code, " = $rt_lr($t - 1n); return $t; })()");
                return;
            default:
                break;
            }
        }

        switch (operator) {
        case POST_DECREMENT:
        case POST_INCREMENT:
            write(code, operator);
            break;

        default:
            write(operator, code);
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInstanceof(Code code, Class type, Code cast) {
        if (cast != null) {
            write(code, space, "instanceof", space, cast);
            return;
        }

        // JavaScript has no interface at runtime, so the interface check is delegated to the
        // runtime which walks the type metadata. The arrays are checked structurally.
        if (options.reflection && isReflectionType(type)) {
            write(code, space, "instanceof", space, classRef(type));
        } else if (type == Object.class) {
            write("(", code, ") != null");
        } else if (type == String.class) {
            write("typeof ", code, " === \"string\"");
        } else if (type == CharSequence.class) {
            // The Java string is a JavaScript string in this mode.
            write("typeof ", code, " === \"string\"");
        } else if (type.isArray()) {
            write("Array.isArray(", code, ")");
        } else if (type.isInterface()) {
            // The registry lookup returns undefined for an interface which is not emitted, so the
            // check fails safely instead of throwing a ReferenceError.
            write("$rt.isInstance(", registryRef(type), ",", space, code, ")");
        } else {
            write(code, space, "instanceof", space, classRef(type));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariable(Type type, int index, String name) {
        String variable = safeVariable(name);
        String prefix;
        if (vars.isDeclared(variable)) {
            prefix = "";
        } else {
            prefix = "let".concat(space);
            vars.declare(variable);
        }
        write(prefix, vars.use(variable));
    }

    /**
     * Convert the given variable name into a valid JavaScript identifier. Some Java parameter names
     * are reserved words in JavaScript (e.g. {@code function}), so they are escaped.
     * 
     * @param name A variable name.
     * @return A safe variable name.
     */
    private String safeVariable(String name) {
        return reservedWords.contains(name) ? name + "$" : name;
    }

    /** The JavaScript reserved words. */
    private static final Set<String> reservedWords = Set.of("await", "break", "case", "catch", "class", "const", "continue", "debugger", "default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for", "function", "if", "implements", "import", "in", "instanceof", "interface", "let", "new", "null", "package", "private", "protected", "public", "return", "static", "super", "switch", "this", "throw", "true", "try", "typeof", "var", "void", "while", "with", "yield");

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context, AccessMode mode) {
        field = Classlib.resolve(field);

        Native nativeCode = field.getAnnotation(Native.class);
        if (nativeCode != null) {
            writeNative(nativeCode.value(), context, List.of());
            return;
        }
        if (Classes.isStatic(field)) {
            if (current.is(field.getDeclaringClass())) {
                write(classRef(field.getDeclaringClass()), ".", fieldName(field));
            } else {
                write(context, ".", fieldName(field));
            }
        } else {
            switch (mode) {
            case THIS:
                write(context, ".", fieldName(field));
                break;

            case SUPER:
                // The generated class has no extends clause, so the super field is accessed
                // through this. The field names are unique per declaring class.
                write("this.", fieldName(field));
                break;

            default:
                write(context, ".", fieldName(field));
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessType(Class type) {
        write(name(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArray(Code array, Code index) {
        write(array, "[", index, "]");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArrayLength(Code array) {
        write(array, ".length");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructorCall(Constructor constructor, List<Code> params) {
        Native nativeCode = (Native) constructor.getAnnotation(Native.class);
        if (nativeCode != null) {
            writeNative(nativeCode.value(), null, params);
            return;
        }

        Class owner = Classlib.replace(constructor.getDeclaringClass());

        if (options.strictApi && isUnmappedApi(owner) && !isRuntimeType(owner) && !isStringBuilder(owner)
                && !Throwable.class.isAssignableFrom(owner)) {
            throw new TranslationException(constructor);
        }

        if (isRuntimeType(owner)) {
            requireRuntime();
        }
        // The throwable classes are not emulated, so they are mapped to the native Error.
        if (Throwable.class.isAssignableFrom(owner)) {
            write("new", space, "Error", buildCallParameter(constructor, params));
            return;
        }
        // The runtime type is a plain JavaScript class, so it must not receive the constructor
        // index. Only the generated class uses the index based constructor dispatch.
        if (isRuntimeType(owner)) {
            write("new", space, classRef(owner), buildCallParameter(constructor, params));
        } else {
            write("new", space, classRef(owner), buildCallParameter(constructor, params, 0, constructorIndex(constructor)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<Code> params) {
        Class owner = constructor.getDeclaringClass();

        if (owner == Enum.class) {
            return;
        } else if (owner == Object.class || owner == Record.class //
                || owner.getName().startsWith("java.") || owner.getName().startsWith("javax.")) {
            // The standard classes are not emitted, so their constructors are not invoked.
            return;
        } else {
            // The super constructor is invoked directly because the generated class does not use
            // the extends clause. It also initializes the fields of the super class.
            write(classRef(owner), ".prototype.", constructorName(constructor), ".call(this,", space,
                    buildArgument(constructor, params, 0, constructorIndex(constructor)), ")");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<Code> params) {
        int start = constructor.getDeclaringClass().isEnum() ? 2 : 0;
        write("this.", constructorName(constructor), buildCallParameter(constructor, params, start, constructorIndex(constructor)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructorReference(Constructor constructor) {
        if (isRuntimeType(constructor.getDeclaringClass())) {
            write("(...args)", space, "=>", space, "new", space, classRef(constructor.getDeclaringClass()), "(...args)");
        } else {
            write("(...args)", space, "=>", space, "new", space, classRef(constructor.getDeclaringClass()), "(...args,", space, constructorIndex(constructor), ")");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<Code> params, AccessMode mode) {
        Native nativeCode = method.getAnnotation(Native.class);
        if (nativeCode != null) {
            writeNative(nativeCode.value(), context, params);
            return;
        }

        if (method.getDeclaringClass() == java.lang.invoke.MethodHandle.class) {
            Join arguments = Join.of(params).separator("," + space);
            if (method.getName().equals("invoke") || method.getName().equals("invokeExact")) {
                write("$rt_invokeHandle(", context, ",", space, "[", arguments, "])");
                return;
            }
            if (method.getName().equals("invokeWithArguments")) {
                write("$rt_invokeHandleArguments(", context, ",", space, "[", arguments, "])");
                return;
            }
        }

        if (isRuntimeType(method.getDeclaringClass())) {
            requireRuntime();
        }
        if (options.reflection && method.getDeclaringClass() == Object.class && method.getName().equals("getClass")
                && method.getParameterCount() == 0) {
            requireRuntime();
            write("$rt.reflect($rt.classOf(", context, "))");
        } else if (isRecordAccessor(method)) {
            // The record accessor (e.g. point.x()) is replaced by the field access (point.x).
            write(context, ".", fieldName(recordField(method)));
        } else if (method.getDeclaringClass() == Enum.class && method.getName().equals("valueOf")) {
            // Enum.valueOf(Class, String) is delegated to the runtime.
            write("$rt.enumValueOf(", params.get(0), ",", space, params.get(1), ")");
        } else if (isStringMethod(method)) {
            writeStringMethod(method, context, params);
        } else if (isCharSequenceMethod(method)) {
            writeCharSequenceMethod(method, context, params);
        } else if (isComparableMethod(method)) {
            write("$rt_compare", "(", context, ",", space, params.get(0), ")");
        } else if (isObjectMethod(method)) {
            writeObjectMethod(method, context, params);
        } else if (isThrowableMethod(method)) {
            writeThrowableMethod(method, context, params);
        } else if (isMathMethod(method)) {
            writeMathMethod(method, context, params);
        } else if (isCharacterMethod(method)) {
            writeCharacterMethod(method, context, params);
        } else if (isNumberMethod(method)) {
            writeNumberMethod(method, context, params);
        } else if (method.isSynthetic()) {
            write(context, ".", methodName(method), buildCallParameter(method, params));
        } else {
            if (mode == AccessMode.SUPER) {
                // The generated class has no extends clause, so the super method is invoked
                // through the prototype of the declaring class.
                write(classRef(method.getDeclaringClass()), ".prototype.", methodName(method), ".call(this");
                if (!params.isEmpty()) {
                    write(",", space, buildArgument(method, params, 0, null));
                }
                write(")");
            } else {
                if (Classes.isWrapper(method)) {
                    write(params.get(0));
                } else {
                    if (Classes.isStatic(method) && current.is(method.getDeclaringClass())) {
                        write(classRef(method.getDeclaringClass()), ".", methodName(method), buildCallParameter(method, params));
                    } else if (isUnboxing(method)) {
                        // JavaScript has no wrapper class, so the unboxing method (e.g.
                        // Integer.intValue) is completely unnecessary.
                        write(context);
                    } else if (isFunctionalMethod(method)) {
                        // The lambda is invoked directly in JavaScript, e.g. the Java code
                        // "supplier.get()" is converted to "supplier()".
                        write(context, buildCallParameter(method, params));
                    } else if (Modifier.isPrivate(method.getModifiers()) && !Classes.isStatic(method)) {
                        // A private method is not dynamically dispatched, so invoke the declaring
                        // class directly instead of looking up the method in the receiver.
                        write(classRef(method.getDeclaringClass()), ".prototype.", methodName(method), ".call(", context);
                        if (!params.isEmpty()) {
                            write(",", space, buildArgument(method, params, 0, null));
                        }
                        write(")");
                    } else {
                        writeDefaultMethodCall(method, context, params);
                    }
                }
            }
        }
    }

    /**
     * Check whether the given method is an unboxing method of the wrapper class. JavaScript has no
     * wrapper class, so such method invocations are completely unnecessary.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isUnboxing(Method method) {
        if (method.getParameterCount() != 0) {
            return false;
        }
        Class type = method.getDeclaringClass();
        if (type != Integer.class && type != Long.class && type != Double.class && type != Float.class //
                && type != Short.class && type != Byte.class && type != Boolean.class && type != Character.class) {
            return false;
        }
        return switch (method.getName()) {
        case "intValue", "longValue", "doubleValue", "floatValue", //
                "shortValue", "byteValue", "booleanValue", "charValue" -> true;
        default -> false;
        };
    }

    /**
     * Mark that the standard runtime (collections) is required.
     */
    private void requireRuntime() {
        runtime = true;
    }

    /**
     * Check whether the specified code has the {@code long} type.
     * 
     * @param code A target code.
     * @return A result.
     */
    private boolean isLong(Code code) {
        return code instanceof Operand operand && operand.type() == long.class;
    }

    /**
     * Convert the specified code into the BigInt expression if needed.
     * 
     * @param code A target code.
     * @return A BigInt expression.
     */
    private Code longOperand(Code code) {
        return isLong(code) ? code : (Code) coder -> coder.write("$rt_l(", code, ")");
    }

    /**
     * Write the native JavaScript expression.
     * 
     * @param template A JavaScript expression template.
     * @param receiver A receiver code. It may be null for a static member.
     * @param params A list of parameters.
     */
    private void writeNative(String template, Code receiver, List<Code> params) {
        for (int i = 0; i < template.length();) {
            char code = template.charAt(i);

            if (code == '$' && template.startsWith("$this", i)) {
                if (receiver == null) {
                    throw new IllegalStateException("The native template '" + template + "' requires a receiver.");
                }
                write("(", receiver, ")");
                i += 5;
                continue;
            }

            if (code == '$') {
                int end = i + 1;
                while (end < template.length() && Character.isDigit(template.charAt(end))) {
                    end++;
                }
                if (i + 1 < end) {
                    int index = Integer.parseInt(template.substring(i + 1, end));
                    if (params.size() <= index) {
                        throw new IllegalStateException("The native template '" + template + "' requires argument " + index + ".");
                    }
                    write("(", params.get(index), ")");
                    i = end;
                    continue;
                }
            }

            write(String.valueOf(code));
            i++;
        }
    }

    /**
     * Write the default method invocation. In the strict mode, the unmapped Java API is rejected
     * instead of being emitted as an undefined JavaScript call.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeDefaultMethodCall(Method method, Code context, List<Code> params) {
        Class owner = Classlib.replace(method.getDeclaringClass());
        if (options.strictApi && isUnmappedApi(owner) && !isNativeApi(owner)) {
            throw new TranslationException(method);
        }
        write(context, ".", methodName(method), buildCallParameter(method, params));
    }

    /**
     * Check whether the specified class belongs to the Java standard library.
     * 
     * @param type A target class.
     * @return A result.
     */
    private static boolean isUnmappedApi(Class type) {
        String name = type.getName();
        return name.startsWith("java.") || name.startsWith("javax.");
    }

    /**
     * Check whether the specified standard class is mapped to a native JavaScript API or the
     * standard runtime.
     * 
     * @param type A target class.
     * @return A result.
     */
    private boolean isNativeApi(Class type) {
        return type == Object.class || type == Math.class || type == StrictMath.class //
                || type == Enum.class || type == String.class || type == CharSequence.class //
                || type == Number.class || type == Character.class || type == Boolean.class //
                || type == Integer.class || type == Long.class || type == Short.class || type == Byte.class //
                || type == Float.class || type == Double.class //
                || type == System.class || type == Arrays.class || type == Collections.class //
                || type == java.io.PrintStream.class || type == java.io.PrintWriter.class //
                || type == Comparable.class || type == Iterable.class || type == Iterator.class //
                || isStringBuilder(type) || Throwable.class.isAssignableFrom(type) //
                || isRuntimeType(type) //
                || type.getName().startsWith("java.util.function.");
    }

    /**
     * Compute the JavaScript method name.
     * 
     * @param method A target method.
     * @return A method name.
     */
    private String methodName(Method method) {
        return names().allocate(method);
    }

    /**
     * Compute the JavaScript field name.
     * 
     * @param field A target field.
     * @return A field name.
     */
    private String fieldName(Field field) {
        return names().allocate(field);
    }

    /**
     * Compute the JavaScript field name of the enum constant.
     * 
     * @param type An enum type.
     * @param name A constant name.
     * @return A field name.
     */
    private String enumFieldName(Class type, String name) {
        try {
            return fieldName(type.getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Unknown enum constant: " + type.getName() + "." + name, e);
        }
    }

    /**
     * Get the field of the record component.
     * 
     * @param accessor A record accessor.
     * @return A field.
     */
    private Field recordField(Method accessor) {
        try {
            return accessor.getDeclaringClass().getDeclaredField(accessor.getName());
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Unknown record component: " + accessor, e);
        }
    }

    /**
     * Compute the JavaScript constructor method name.
     * 
     * @param constructor A target constructor.
     * @return A method name.
     */
    private String constructorName(Constructor constructor) {
        return "$" + constructorIndex(constructor);
    }

    /**
     * Compute the stable index of the specified constructor. The index is derived only from the
     * constructor descriptor, so every coder computes the same index for the same constructor.
     * 
     * @param constructor A target constructor.
     * @return A constructor index.
     */
    private static int constructorIndex(Constructor constructor) {
        String descriptor = org.objectweb.asm.Type.getConstructorDescriptor(constructor);
        int index = 0;

        for (Constructor other : constructor.getDeclaringClass().getDeclaredConstructors()) {
            if (org.objectweb.asm.Type.getConstructorDescriptor(other).compareTo(descriptor) < 0) {
                index++;
            }
        }
        return index;
    }

    /**
     * Check whether the given type is a {@link List} implementation supported by the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isList(Class type) {
        return type == List.class || type == ArrayList.class || type == LinkedList.class //
                || type == Vector.class || type == Stack.class || type == AbstractList.class;
    }

    /**
     * Check whether the given type is a {@link Set} implementation supported by the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isSet(Class type) {
        return type == Set.class || type == HashSet.class || type == LinkedHashSet.class //
                || type == TreeSet.class || type == java.util.SortedSet.class //
                || type == java.util.NavigableSet.class;
    }

    /**
     * Check whether the given type is a {@link Queue} or {@link Deque} implementation supported by
     * the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isQueue(Class type) {
        return type == java.util.Queue.class || type == java.util.Deque.class //
                || type == ArrayDeque.class || type == PriorityQueue.class;
    }

    /**
     * Check whether the given type is a {@link Map} implementation supported by the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isMap(Class type) {
        return type == Map.class || type == HashMap.class || type == LinkedHashMap.class //
                || type == TreeMap.class || type == Hashtable.class || type == java.util.SortedMap.class //
                || type == java.util.NavigableMap.class || type == java.util.concurrent.ConcurrentMap.class;
    }

    /**
     * Check whether the given type is supported by the standard runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isRuntimeType(Class type) {
        if (Classlib.isNative(type) || Classlib.isReplaced(type)) {
            return false;
        }
        return isList(type) || isSet(type) || isQueue(type) || isMap(type) //
                || type == Map.Entry.class || type == java.util.Collection.class //
                || type == java.util.Iterator.class || type == java.util.ListIterator.class //
                || type == Iterable.class || type == Optional.class //
                || type == java.util.stream.Stream.class || type == java.util.stream.Collectors.class //
                || type == java.util.regex.Pattern.class || type == java.util.regex.Matcher.class //
                || (options.reflection && isReflectionType(type));
    }

    /**
     * Check whether the given method is the accessor method of a record. In JavaScript, the record
     * is written as a plain class, so the value is accessed by the field instead of the method.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isRecordAccessor(Method method) {
        if (!method.getDeclaringClass().isRecord() || method.getParameterCount() != 0) {
            return false;
        }
        for (RecordComponent component : method.getDeclaringClass().getRecordComponents()) {
            if (component.getName().equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given method is declared in {@link String}. Some of them have no
     * corresponding method in JavaScript, so they are converted explicitly.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isStringMethod(Method method) {
        return method.getDeclaringClass() == String.class;
    }

    /**
     * Check whether the given method is declared in {@link CharSequence}. Its methods are
     * overlaid to the native JavaScript string.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isCharSequenceMethod(Method method) {
        if (method.getDeclaringClass() != CharSequence.class) {
            return false;
        }
        return switch (method.getName()) {
        case "length", "isEmpty", "charAt", "subSequence", "toString", "codePointAt" -> true;
        default -> false;
        };
    }

    /**
     * Write the overlaid {@link CharSequence} method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeCharSequenceMethod(Method method, Code context, List<Code> params) {
        switch (method.getName()) {
        case "length":
            write(context, ".length");
            break;

        case "isEmpty":
            write(context, ".length", " === 0");
            break;

        case "charAt":
            write(context, ".charAt", buildCallParameter(method, params));
            break;

        case "subSequence":
            write(context, ".substring", buildCallParameter(method, params));
            break;

        case "codePointAt":
            write(context, ".codePointAt", buildCallParameter(method, params));
            break;

        default:
            write("String", "(", context, ")");
            break;
        }
    }

    /**
     * Check whether the given method is {@link Comparable#compareTo(Object)}.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isComparableMethod(Method method) {
        return method.getDeclaringClass() == Comparable.class //
                && method.getName().equals("compareTo") && method.getParameterCount() == 1;
    }

    /**
     * Write the invocation of the given {@link String} method. The same-named methods of
     * JavaScript are used as is, and the methods with no counterpart are converted explicitly.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameter objects.
     */
    private void writeStringMethod(Method method, Code context, List<Code> params) {
        Class[] parameterTypes = method.getParameterTypes();

        switch (method.getName()) {
        case "length":
            // length() method is replaced by the length property.
            write(context, ".length");
            break;

        case "isEmpty":
            write(context, ".length", " === 0");
            break;

        case "isBlank":
            write(context, ".trim().length", " === 0");
            break;

        case "equals":
            write(context, " === ", buildCallParameter(method, params));
            break;

        case "contentEquals":
            write(context, " === ", "String", buildCallParameter(method, params));
            break;

        case "equalsIgnoreCase":
            // The equalsIgnoreCase method has no counterpart in JavaScript, so both sides are
            // converted to lower case.
            write(context, ".toLowerCase()", " === ", params.get(0), ".toLowerCase()");
            break;

        case "compareTo":
            write("$rt_compareString", "(", context, ",", space, params.get(0), ")");
            break;

        case "compareToIgnoreCase":
            write("$rt_compareString", "(", context, ".toLowerCase()", ",", space, params.get(0), ".toLowerCase()", ")");
            break;

        case "contains":
            write(context, ".includes", buildCallParameter(method, params));
            break;

        case "concat":
            write(context, space, "+", space, buildCallParameter(method, params));
            break;

        case "replace":
            // The replace method replaces all occurrences in Java, but the same-named method of
            // JavaScript replaces only the first occurrence. So split().join() is used instead.
            write(context, ".split(", params.get(0), ").join(", params.get(1), ")");
            break;

        case "replaceAll":
            write("$rt_replaceAll", "(", context, ",", space, params.get(0), ",", space, params.get(1), "," + space, "true", ")");
            break;

        case "replaceFirst":
            write("$rt_replaceAll", "(", context, ",", space, params.get(0), ",", space, params.get(1), "," + space, "false", ")");
            break;

        case "matches":
            write("$rt_matches", "(", context, ",", space, params.get(0), ")");
            break;

        case "indexOf":
        case "lastIndexOf":
            // The char based overload takes an int code unit, while JavaScript compares strings.
            if (parameterTypes.length != 0 && parameterTypes[0] == int.class) {
                write(context, ".", method.getName(), "(", "$rt_char", "(", params.get(0), ")");
                for (int i = 1; i < params.size(); i++) {
                    write(",", space, params.get(i));
                }
                write(")");
            } else {
                write(context, ".", method.getName(), buildCallParameter(method, params));
            }
            break;

        case "hashCode":
            write("$rt_hashCode", "(", context, ")");
            break;

        case "toCharArray":
            write("$rt_chars", "(", context, ")");
            break;

        case "intern":
        case "toString":
            write(context);
            break;

        case "strip":
        case "stripLeading":
        case "stripTrailing":
            // JavaScript trim removes the same whitespace characters for the common cases.
            write(context, ".", method.getName().equals("strip") ? "trim" : method.getName().equals("stripLeading")
                    ? "trimStart" : "trimEnd", "()");
            break;

        case "codePointBefore":
            write("$rt_codePointBefore", "(", context, ",", space, params.get(0), ")");
            break;

        case "codePointCount":
            write("$rt_codePointCount", "(", context, ",", space, params.get(0), ",", space, params.get(1), ")");
            break;

        case "offsetByCodePoints":
            write("$rt_offsetByCodePoints", "(", context, ",", space, params.get(0), ",", space, params.get(1), ")");
            break;

        case "valueOf":
        case "copyValueOf":
            if (parameterTypes.length == 1 && parameterTypes[0] == Object.class) {
                write("String", "(", params.get(0), ")");
            } else if (parameterTypes.length == 1 && parameterTypes[0] == char[].class) {
                write(params.get(0), ".join(\"\")");
            } else if (parameterTypes.length == 3 && parameterTypes[0] == char[].class) {
                write(params.get(0), ".slice", "(", params.get(1), ",", space, params.get(1), space, "+", space, params.get(2), ").join(\"\")");
            } else {
                // JavaScript has no String.valueOf, so the global String function is used instead.
                write("String", buildCallParameter(method, params));
            }
            break;

        case "join": {
            // Both the varargs and the Iterable overload receive the values as the last argument.
            Code values = params.get(params.size() - 1);
            write("$rt_join", "(", params.get(0), ",", space, values, ")");
            break;
        }

        case "format": {
            // The optional Locale argument is the first one, so the format string follows it.
            int formatIndex = parameterTypes.length == 3 ? 1 : 0;
            Code args = params.get(params.size() - 1);
            if (params.size() <= formatIndex + 1) {
                write("$rt_format", "(", params.get(formatIndex), ")");
            } else {
                write("$rt_format", "(", params.get(formatIndex), ",", space, args, ")");
            }
            break;
        }

        case "formatted": {
            Code args = params.get(params.size() - 1);
            write("$rt_format", "(", context, ",", space, args, ")");
            break;
        }

        default:
            // e.g. charAt(), substring(), toUpperCase() etc. have the same API in JavaScript.
            write(context, ".", method.getName(), buildCallParameter(method, params));
            break;
        }
    }

    /**
     * Check whether the given type is the string builder which must be overlaid to the native
     * string accumulator in the class library mode.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isStringBuilder(Class type) {
        return type == StringBuilder.class || type == StringBuffer.class || type == Appendable.class;
    }

    /**
     * Write the overlaid string builder method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeStringBuilderMethod(Method method, Code context, List<Code> params) {
        Class[] parameterTypes = method.getParameterTypes();

        switch (method.getName()) {
        case "append":
            if (parameterTypes.length == 1 && parameterTypes[0] == char[].class) {
                write("$rt_sbAppendChars", "(", context, ",", space, params.get(0), ")");
            } else if (parameterTypes.length == 3) {
                if (parameterTypes[0] == char[].class) {
                    // append(char[], offset, length)
                    write("$rt_sbAppendChars", "(", context, ",", space, params.get(0), ",", space, params.get(1), ",", space, params
                            .get(2), ")");
                } else {
                    // append(CharSequence, start, end)
                    write("$rt_sbAppend", "(", context, ",", space, params.get(0), ".substring", "(", params.get(1), ",", space, params
                            .get(2), ")", ")");
                }
            } else {
                write("$rt_sbAppend", "(", context, ",", space, params.get(0), ")");
            }
            break;

        case "appendCodePoint":
            write("$rt_sbAppend", "(", context, ",", space, "String.fromCodePoint", "(", params.get(0), ")", ")");
            break;

        case "insert":
            if (parameterTypes.length == 4) {
                if (parameterTypes[1] == char[].class) {
                    // insert(int, char[], offset, length)
                    write("$rt_sbInsertChars", "(", context, ",", space, params.get(0), ",", space, params.get(1), ",", space, params
                            .get(2), ",", space, params.get(3), ")");
                } else {
                    // insert(int, CharSequence, start, end)
                    write("$rt_sbInsert", "(", context, ",", space, params.get(0), ",", space, params.get(1), ".substring", "(", params
                            .get(2), ",", space, params.get(3), ")", ")");
                }
            } else {
                Code value = params.get(1);
                if (parameterTypes.length == 2 && parameterTypes[1] == char[].class) {
                    value = new CharArrayCode(value);
                }
                write("$rt_sbInsert", "(", context, ",", space, params.get(0), ",", space, value, ")");
            }
            break;

        case "reverse":
            write("$rt_sbReverse", "(", context, ")");
            break;

        case "delete":
            write("$rt_sbDelete", "(", context, ",", space, params.get(0), ",", space, params.get(1), ")");
            break;

        case "deleteCharAt":
            write("$rt_sbDeleteCharAt", "(", context, ",", space, params.get(0), ")");
            break;

        case "replace":
            write("$rt_sbReplace", "(", context, ",", space, params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        case "setCharAt":
            write("$rt_sbSetCharAt", "(", context, ",", space, params.get(0), ",", space, params.get(1), ")");
            break;

        case "setLength":
            write("$rt_sbSetLength", "(", context, ",", space, params.get(0), ")");
            break;

        case "charAt":
            write("$rt_sbCharAt", "(", context, ",", space, params.get(0), ")");
            break;

        case "indexOf":
            write("$rt_sbIndexOf", "(", context, ",", space, params.get(0), params.size() > 1 ? ", " : "", params.size() > 1 ? params
                    .get(1) : "", ")");
            break;

        case "lastIndexOf":
            write("$rt_sbLastIndexOf", "(", context, ",", space, params.get(0), params.size() > 1 ? ", " : "", params.size() > 1 ? params
                    .get(1) : "", ")");
            break;

        case "substring":
        case "subSequence":
            write("$rt_sbSubstring", "(", context, ",", space, params.get(0), ",", space, params.get(1), ")");
            break;

        case "capacity":
            write("$rt_sbCapacity", "(", context, ")");
            break;

        case "ensureCapacity":
        case "trimToSize":
            write(context);
            break;

        case "getChars":
            write("$rt_sbGetChars", "(", context, ",", space, params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space, params
                    .get(3), ")");
            break;

        case "toString":
            write("$rt_sbValue", "(", context, ")");
            break;

        case "length":
            write("$rt_sbValue", "(", context, ")", ".length");
            break;

        default:
            write(context, ".", method.getName(), buildCallParameter(method, params));
            break;
        }
    }

    /**
     * A code which converts a {@code char[]} into a JavaScript string.
     */
    private static class CharArrayCode implements Code {

        /** The array code. */
        private final Code array;

        /**
         * @param array An array code.
         */
        private CharArrayCode(Code array) {
            this.array = array;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(Coder coder) {
            coder.write("$rt_charsToString", "(", array, ")");
        }
    }

    /**
     * Check whether the given method is declared in {@link Math} or its class library
     * counterpart.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isMathMethod(Method method) {
        return method.getDeclaringClass() == Math.class;
    }

    /**
     * Write the overlaid {@link Math} method. The Java math functions are mapped to the native
     * JavaScript Math object.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeMathMethod(Method method, Code context, List<Code> params) {
        String name = method.getName();

        // The JavaScript Math object does not accept a BigInt, so the long argument is handled
        // explicitly.
        if (method.getParameterCount() != 0 && method.getParameterTypes()[0] == long.class) {
            switch (name) {
            case "abs":
            case "absExact":
                write("$rt_lr(", params.get(0), " < 0n ? -", params.get(0), " : ", params.get(0), ")");
                return;
            case "max":
                write("$rt_lr(", params.get(0), " > ", params.get(1), " ? ", params.get(0), " : ", params.get(1), ")");
                return;
            case "min":
                write("$rt_lr(", params.get(0), " < ", params.get(1), " ? ", params.get(0), " : ", params.get(1), ")");
                return;
            case "signum":
                write("$rt_lsignum(", params.get(0), ")");
                return;
            case "negateExact":
                write("$rt_lr(-", params.get(0), ")");
                return;
            case "addExact":
                write("$rt_lr(", params.get(0), " + ", params.get(1), ")");
                return;
            case "subtractExact":
                write("$rt_lr(", params.get(0), " - ", params.get(1), ")");
                return;
            case "multiplyExact":
                write("$rt_lr(", params.get(0), " * ", params.get(1), ")");
                return;
            case "incrementExact":
                write("$rt_lr(", params.get(0), " + 1n)");
                return;
            case "decrementExact":
                write("$rt_lr(", params.get(0), " - 1n)");
                return;
            case "floorDiv":
                write("$rt_lfloorDiv(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "floorMod":
                write("$rt_lfloorMod(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "toIntExact":
                write("$rt_ltoi(", params.get(0), ")");
                return;
            default:
                break;
            }
        }

        switch (name) {
        case "abs":
        case "absExact":
            write("Math.abs", "(", params.get(0), ")");
            break;

        case "sqrt":
        case "cbrt":
        case "sin":
        case "cos":
        case "tan":
        case "asin":
        case "acos":
        case "atan":
        case "exp":
        case "log":
        case "log10":
        case "ceil":
        case "floor":
        case "round":
        case "signum":
        case "sinh":
        case "cosh":
        case "tanh":
        case "expm1":
        case "log1p":
        case "random":
            write("Math.", name, buildCallParameter(method, params));
            break;

        case "pow":
        case "atan2":
        case "hypot":
        case "min":
        case "max":
        case "copySign":
            write("Math.", name, buildCallParameter(method, params));
            break;

        case "toRadians":
            write("$rt_toRadians", "(", params.get(0), ")");
            break;

        case "toDegrees":
            write("$rt_toDegrees", "(", params.get(0), ")");
            break;

        case "rint":
            write("$rt_rint", "(", params.get(0), ")");
            break;

        case "IEEEremainder":
            write("$rt_ieeeRemainder", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "ulp":
            write("$rt_ulp", "(", params.get(0), ")");
            break;

        case "nextUp":
            write("$rt_nextUp", "(", params.get(0), ")");
            break;

        case "nextDown":
            write("$rt_nextDown", "(", params.get(0), ")");
            break;

        case "nextAfter":
            write("$rt_nextAfter", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "getExponent":
            write("$rt_getExponent", "(", params.get(0), ")");
            break;

        case "scalb":
            write("$rt_scalb", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "floorDiv":
        case "floorDivExact":
            write("$rt_floorDiv", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "floorMod":
            write("$rt_floorMod", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "ceilDiv":
        case "ceilDivExact":
            write("$rt_ceilDiv", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "ceilMod":
            write("$rt_ceilMod", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "addExact":
            write("(", params.get(0), space, "+", space, params.get(1), ")");
            break;

        case "subtractExact":
            write("(", params.get(0), space, "-", space, params.get(1), ")");
            break;

        case "multiplyExact":
            write("(", params.get(0), space, "*", space, params.get(1), ")");
            break;

        case "divideExact":
            write("(", params.get(0), space, "/", space, params.get(1), ")");
            break;

        case "incrementExact":
            write("(", params.get(0), space, "+", space, "1", ")");
            break;

        case "decrementExact":
            write("(", params.get(0), space, "-", space, "1", ")");
            break;

        case "negateExact":
            write("(-", params.get(0), ")");
            break;

        case "toIntExact":
            write(params.get(0));
            break;

        case "clamp":
            write("$rt_clamp", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        default:
            write("Math.", name, buildCallParameter(method, params));
            break;
        }
    }

    /**
     * Check whether the given method is declared in {@link Character} or its class library
     * counterpart.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isCharacterMethod(Method method) {
        return method.getDeclaringClass() == Character.class;
    }

    /**
     * Write the overlaid {@link Character} method. The Java Unicode tables are not available in
     * the JavaScript output, so they are mapped to the runtime helpers.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeCharacterMethod(Method method, Code context, List<Code> params) {
        String name = method.getName();

        switch (name) {
        case "isDigit":
            write("$rt_isDigit", "(", params.get(0), ")");
            break;

        case "isLetter":
        case "isAlphabetic":
            write("$rt_isLetter", "(", params.get(0), ")");
            break;

        case "isLetterOrDigit":
            write("$rt_isLetterOrDigit", "(", params.get(0), ")");
            break;

        case "isWhitespace":
            write("$rt_isWhitespace", "(", params.get(0), ")");
            break;

        case "isSpaceChar":
            write("$rt_isSpaceChar", "(", params.get(0), ")");
            break;

        case "isUpperCase":
            write("$rt_isUpperCase", "(", params.get(0), ")");
            break;

        case "isLowerCase":
            write("$rt_isLowerCase", "(", params.get(0), ")");
            break;

        case "isTitleCase":
            write("$rt_isTitleCase", "(", params.get(0), ")");
            break;

        case "toUpperCase":
        case "toLowerCase":
        case "toTitleCase":
            String suffix = name.equals("toUpperCase") ? "Upper" : name.equals("toLowerCase") ? "Lower" : "Title";
            if (method.getParameterCount() == 2) {
                // The Locale-aware overload ignores the locale.
                write("$rt_char", suffix, "(", params.get(0), ")");
            } else if (method.getParameterTypes()[0] == int.class) {
                write("$rt_codePoint", suffix, "(", params.get(0), ")");
            } else {
                write("$rt_char", suffix, "(", params.get(0), ")");
            }
            break;

        case "digit":
            write("$rt_charDigit", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "getNumericValue":
            write("$rt_charNumeric", "(", params.get(0), ")");
            break;

        case "forDigit":
            write("$rt_forDigit", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "compare":
            write("$rt_compareChar", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "hashCode":
            write("$rt_charCode", "(", params.get(0), ")");
            break;

        case "toString":
            write("String", "(", params.get(0), ")");
            break;

        case "isSurrogate":
            write("$rt_isSurrogate", "(", params.get(0), ")");
            break;

        case "isHighSurrogate":
            write("$rt_isHighSurrogate", "(", params.get(0), ")");
            break;

        case "isLowSurrogate":
            write("$rt_isLowSurrogate", "(", params.get(0), ")");
            break;

        case "isSurrogatePair":
            write("$rt_isSurrogatePair", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "isBmpCodePoint":
            write("$rt_isBmpCodePoint", "(", params.get(0), ")");
            break;

        case "isValidCodePoint":
            write("$rt_isValidCodePoint", "(", params.get(0), ")");
            break;

        case "isSupplementaryCodePoint":
            write("$rt_isValidCodePoint", "(", params.get(0), ")", space, "&&", space, params.get(0), space, ">", space, "0xffff");
            break;

        case "charCount":
            write("$rt_charCount", "(", params.get(0), ")");
            break;

        case "toChars":
            write("$rt_codePointToChars", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        case "toCodePoint":
            write("$rt_toCodePoint", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "codePointAt":
            write("$rt_codePointAt", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "codePointBefore":
            write("$rt_codePointBefore", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "codePointCount":
            write("$rt_codePointCount", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        case "offsetByCodePoints":
            write("$rt_offsetByCodePoints", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        case "reverseBytes":
            write("$rt_reverseBytes16", "(", params.get(0), ")");
            break;

        case "highSurrogate":
            write("$rt_highSurrogate", "(", params.get(0), ")");
            break;

        case "lowSurrogate":
            write("$rt_lowSurrogate", "(", params.get(0), ")");
            break;

        case "isISOControl":
            write("$rt_isISOControl", "(", params.get(0), ")");
            break;

        case "isJavaIdentifierStart":
        case "isJavaIdentifierPart":
        case "isUnicodeIdentifierStart":
        case "isUnicodeIdentifierPart":
            write("$rt_isJavaIdentifier", "(", params.get(0), ",", space, name.endsWith("Start") ? "true" : "false", ")");
            break;

        case "isMirrored":
        case "isIdeographic":
            write("false");
            break;

        case "getType":
        case "getDirectionality":
            write("0");
            break;

        case "getName":
            write("\"\"");
            break;

        default:
            write("undefined");
            break;
        }
    }

    /**
     * Check whether the given method is a wrapper class method which must be overlaid in the class
     * library mode.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isNumberMethod(Method method) {
        Class owner = method.getDeclaringClass();
        return owner == Integer.class || owner == Long.class || owner == Short.class || owner == Byte.class //
                || owner == Double.class || owner == Float.class || owner == Boolean.class || owner == Number.class;
    }

    /**
     * Write the overlaid wrapper class method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeNumberMethod(Method method, Code context, List<Code> params) {
        String name = method.getName();
        String owner = method.getDeclaringClass().getSimpleName();
        boolean floating = owner.endsWith("Double") || owner.endsWith("Float");
        boolean bool = owner.endsWith("Boolean");
        boolean longValue = owner.endsWith("Long");

        if (longValue) {
            switch (name) {
            case "parseLong":
                if (params.size() == 4) {
                    write("$rt_lparse(String(", params.get(0), ").substring(", params.get(1), ",", space, params.get(2), "),", space, params
                            .get(3), ")");
                } else if (params.size() == 2) {
                    write("$rt_lparse(", params.get(0), ",", space, params.get(1), ")");
                } else {
                    write("$rt_lparse(", params.get(0), ")");
                }
                return;
            case "toString":
                if (Modifier.isStatic(method.getModifiers())) {
                    if (params.size() == 2) {
                        write("$rt_ltoString(", params.get(0), ",", space, params.get(1), ")");
                    } else {
                        write("$rt_ltoString(", params.get(0), ")");
                    }
                } else {
                    write("$rt_ltoString(", context, ")");
                }
                return;
            case "toHexString":
                write("$rt_lunsigned(", params.get(0), ",", space, "16", ")");
                return;
            case "toOctalString":
                write("$rt_lunsigned(", params.get(0), ",", space, "8", ")");
                return;
            case "toBinaryString":
                write("$rt_lunsigned(", params.get(0), ",", space, "2", ")");
                return;
            case "toUnsignedString":
                if (params.size() == 2) {
                    write("$rt_lunsigned(", params.get(0), ",", space, params.get(1), ")");
                } else {
                    write("$rt_lunsigned(", params.get(0), ",", space, "10", ")");
                }
                return;
            case "hashCode":
                write("$rt_lhash(", params.isEmpty() ? context : params.get(0), ")");
                return;
            case "compare":
                write("$rt_lcmp(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "compareTo":
                write("$rt_lcmp(", context, ",", space, params.get(0), ")");
                return;
            case "compareUnsigned":
                write("$rt_lcompareUnsigned(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "sum":
                write("$rt_lr(", params.get(0), " + ", params.get(1), ")");
                return;
            case "max":
            case "min":
                write("$rt_lr(", params.get(0), name.equals("max") ? " > " : " < ", params.get(1), space, "?", space, params.get(0), space, ":",
                        space, params.get(1), ")");
                return;
            case "signum":
                write("$rt_lsignum(", params.get(0), ")");
                return;
            case "bitCount":
                write("$rt_lbitCount(", params.get(0), ")");
                return;
            case "numberOfLeadingZeros":
                write("$rt_lnumberOfLeadingZeros(", params.get(0), ")");
                return;
            case "numberOfTrailingZeros":
                write("$rt_lnumberOfTrailingZeros(", params.get(0), ")");
                return;
            case "reverse":
                write("$rt_lreverse(", params.get(0), ")");
                return;
            case "reverseBytes":
                write("$rt_lreverseBytes(", params.get(0), ")");
                return;
            case "rotateLeft":
                write("$rt_lrotateLeft(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "rotateRight":
                write("$rt_lrotateRight(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "divideUnsigned":
                write("$rt_ldivideUnsigned(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "remainderUnsigned":
                write("$rt_lremainderUnsigned(", params.get(0), ",", space, params.get(1), ")");
                return;
            case "doubleToLongBits":
            case "doubleToRawLongBits":
                write("$rt_doubleToLongBitsBig(", params.get(0), ")");
                return;
            case "longBitsToDouble":
                write("$rt_longBitsToDoubleBig(", params.get(0), ")");
                return;
            case "valueOf":
                if (params.size() == 1 && method.getParameterTypes()[0] == long.class) {
                    write(params.get(0));
                } else if (params.size() == 2) {
                    write("$rt_lparse(", params.get(0), ",", space, params.get(1), ")");
                } else {
                    write("$rt_lparse(", params.get(0), ")");
                }
                return;
            case "decode":
                write("$rt_lparse(", params.get(0), ")");
                return;
            case "longValue":
                write(context);
                return;
            case "intValue":
            case "shortValue":
            case "byteValue":
                write("$rt_ltoi(", context, ")");
                return;
            case "doubleValue":
            case "floatValue":
                write("$rt_ltod(", context, ")");
                return;
            default:
                break;
            }
        }

        switch (name) {
        case "parseInt":
        case "parseShort":
        case "parseByte":
            if (params.size() == 4) {
                write("$rt_parseIntRange", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space, params
                        .get(3), ")");
            } else if (params.size() == 2) {
                write("$rt_parseInt", "(", params.get(0), ",", space, params.get(1), ")");
            } else {
                write("$rt_parseInt", "(", params.get(0), ")");
            }
            break;

        case "parseLong":
            if (params.size() == 4) {
                write("$rt_parseLongRange", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space, params
                        .get(3), ")");
            } else if (params.size() == 2) {
                write("$rt_parseLong", "(", params.get(0), ",", space, params.get(1), ")");
            } else {
                write("$rt_parseLong", "(", params.get(0), ")");
            }
            break;

        case "parseDouble":
        case "parseFloat":
            write("$rt_parseDouble", "(", params.get(0), ")");
            break;

        case "parseBoolean":
            write("$rt_parseBoolean", "(", params.get(0), ")");
            break;

        case "toString":
            if (Modifier.isStatic(method.getModifiers())) {
                if (floating) {
                    write("$rt_doubleToString", "(", params.get(0), ")");
                } else if (params.size() == 2) {
                    write("$rt_toRadixString", "(", params.get(0), ",", space, params.get(1), ")");
                } else {
                    write("String", "(", params.get(0), ")");
                }
            } else {
                write(floating ? "$rt_doubleToString" : "String", "(", context, ")");
            }
            break;

        case "toHexString":
            write("$rt_unsignedString", "(", params.get(0), ",", space, "16", ",", space, owner.endsWith("Long") ? "64" : "32", ")");
            break;

        case "toOctalString":
            write("$rt_unsignedString", "(", params.get(0), ",", space, "8", ",", space, owner.endsWith("Long") ? "64" : "32", ")");
            break;

        case "toBinaryString":
            write("$rt_unsignedString", "(", params.get(0), ",", space, "2", ",", space, owner.endsWith("Long") ? "64" : "32", ")");
            break;

        case "toUnsignedString":
            if (params.size() == 2) {
                write("$rt_unsignedString", "(", params.get(0), ",", space, params.get(1), ",", space, owner.endsWith("Long") ? "64" : "32", ")");
            } else {
                write("$rt_unsignedString", "(", params.get(0), ",", space, "10", ",", space, owner.endsWith("Long") ? "64" : "32", ")");
            }
            break;

        case "toUnsignedLong":
            write("$rt_l(", params.get(0), space, ">>>", space, "0", ")");
            break;

        case "compare":
            if (floating) {
                write("$rt_doubleCompare", "(", params.get(0), ",", space, params.get(1), ")");
            } else if (bool) {
                write("$rt_compareBoolean", "(", params.get(0), ",", space, params.get(1), ")");
            } else {
                write("$rt_compare", "(", params.get(0), ",", space, params.get(1), ")");
            }
            break;

        case "compareTo":
            if (floating) {
                write("$rt_doubleCompare", "(", context, ",", space, params.get(0), ")");
            } else if (bool) {
                write("$rt_compareBoolean", "(", context, ",", space, params.get(0), ")");
            } else {
                write("$rt_compare", "(", context, ",", space, params.get(0), ")");
            }
            break;

        case "compareUnsigned":
            write("$rt_compareUnsigned", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "max":
        case "min":
        case "sum":
            if (name.equals("sum")) {
                write("(", params.get(0), space, "+", space, params.get(1), ")");
            } else {
                write("Math.", name, "(", params.get(0), ",", space, params.get(1), ")");
            }
            break;

        case "bitCount":
            write("$rt_bitCount", "(", params.get(0), ")");
            break;

        case "highestOneBit":
            write("$rt_highestOneBit", "(", params.get(0), ")");
            break;

        case "lowestOneBit":
            write("$rt_lowestOneBit", "(", params.get(0), ")");
            break;

        case "numberOfLeadingZeros":
            write("$rt_numberOfLeadingZeros", "(", params.get(0), ")");
            break;

        case "numberOfTrailingZeros":
            write("$rt_numberOfTrailingZeros", "(", params.get(0), ")");
            break;

        case "reverse":
            write("$rt_reverseBits", "(", params.get(0), ")");
            break;

        case "reverseBytes":
            write("$rt_reverseBytes", "(", params.get(0), ")");
            break;

        case "rotateLeft":
            write("$rt_rotateLeft", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "rotateRight":
            write("$rt_rotateRight", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "signum":
            write("Math.sign", "(", params.get(0), ")");
            break;

        case "divideUnsigned":
            write("$rt_divideUnsigned", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "remainderUnsigned":
            write("$rt_remainderUnsigned", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "isNaN":
            write("Number.isNaN", "(", params.isEmpty() ? context : params.get(0), ")");
            break;

        case "isInfinite":
            Code value = params.isEmpty() ? context : params.get(0);
            write("(", value, space, "===", space, "Infinity", space, "||", space, value, space, "===", space, "-Infinity", ")");
            break;

        case "isFinite":
            write("Number.isFinite", "(", params.isEmpty() ? context : params.get(0), ")");
            break;

        case "doubleToLongBits":
        case "doubleToRawLongBits":
            write("$rt_doubleToLongBits", "(", params.get(0), ")");
            break;

        case "longBitsToDouble":
            write("$rt_longBitsToDouble", "(", params.get(0), ")");
            break;

        case "floatToIntBits":
        case "floatToRawIntBits":
            write("$rt_doubleToLongBits", "(", params.get(0), ")", space, "|", space, "0");
            break;

        case "intBitsToFloat":
            write("$rt_longBitsToDouble", "(", params.get(0), ")");
            break;

        case "logicalAnd":
            write("(", params.get(0), space, "&&", space, params.get(1), ")");
            break;

        case "logicalOr":
            write("(", params.get(0), space, "||", space, params.get(1), ")");
            break;

        case "logicalXor":
            write("(!!", params.get(0), space, "!==", space, "!!", params.get(1), ")");
            break;

        case "hashCode":
            if (Modifier.isStatic(method.getModifiers())) {
                write("$rt_numberHash", "(", params.get(0), ")");
            } else if (bool) {
                write("(", context, space, "?", space, "1231", space, ":", space, "1237", ")");
            } else {
                write("$rt_numberHash", "(", context, ")");
            }
            break;

        case "valueOf":
        case "decode":
            if (Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 1 && method.getParameterTypes()[0] == String.class) {
                // valueOf(String) and decode(String) are parsed into the primitive value.
                if (bool) {
                    write("$rt_parseBoolean", "(", params.get(0), ")");
                } else if (floating) {
                    write("$rt_parseDouble", "(", params.get(0), ")");
                } else if (owner.endsWith("Long")) {
                    write("$rt_parseLong", "(", params.get(0), ")");
                } else {
                    write("$rt_parseInt", "(", params.get(0), ")");
                }
            } else if (Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 2) {
                // valueOf(String, int) is parsed with the radix.
                write(owner.endsWith("Long") ? "$rt_parseLong" : "$rt_parseInt", "(", params.get(0), ",", space, params
                        .get(1), ")");
            } else {
                write(params.get(0));
            }
            break;

        case "intValue":
        case "longValue":
        case "shortValue":
        case "byteValue":
        case "doubleValue":
        case "floatValue":
        case "booleanValue":
        case "charValue":
            write(context);
            break;

        default:
            write(context, ".", name, buildCallParameter(method, params));
            break;
        }
    }

    /**
     * Check whether the given method is declared in {@link System} or its class library
     * counterpart.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isSystemMethod(Method method) {
        return method.getDeclaringClass() == System.class;
    }

    /**
     * Write the overlaid {@link System} method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeSystemMethod(Method method, Code context, List<Code> params) {
        switch (method.getName()) {
        case "arraycopy":
            write("$rt_arraycopy", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space, params
                    .get(3), ",", space, params.get(4), ")");
            break;

        case "currentTimeMillis":
            write("Date.now()");
            break;

        case "nanoTime":
            write("$rt_nanoTime()");
            break;

        case "identityHashCode":
            write("$rt_identityHashCode", "(", params.get(0), ")");
            break;

        case "lineSeparator":
            write("\"\\n\"");
            break;

        case "getProperty":
            if (params.size() == 2) {
                write(params.get(1));
            } else {
                write("null");
            }
            break;

        case "getenv":
        case "getSecurityManager":
        case "console":
            write("null");
            break;

        case "gc":
        case "runFinalization":
        case "setOut":
        case "setErr":
        case "setIn":
            write("undefined");
            break;

        default:
            write("undefined");
            break;
        }
    }

    /**
     * Check whether the given method is declared in {@link Arrays} or its class library
     * counterpart and needs to be overlaid.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isArrayMethod(Method method) {
        if (method.getDeclaringClass() != Arrays.class) {
            return false;
        }
        return switch (method.getName()) {
        case "sort", "parallelSort", "toString", "deepToString" //
                , "asList", "copyOf", "copyOfRange", "fill" //
                , "equals", "deepEquals", "hashCode", "deepHashCode", "stream" -> true;
        default -> false;
        };
    }

    /**
     * Write the overlaid {@link Arrays} method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeArrayMethod(Method method, Code context, List<Code> params) {
        String name = method.getName();
        Class component = method.getParameterTypes()[0].getComponentType();

        switch (name) {
        case "toString":
            write("$rt_arrayToString", "(", params.get(0), ")");
            return;
        case "deepToString":
            write("$rt_arrayToString", "(", params.get(0), ",", space, "true", ")");
            return;
        case "asList":
            write("List.of", join("(", params, "," + space, ")"));
            return;
        case "copyOf":
            write("$rt_arrayCopyOf", "(", params.get(0), ",", space, params.get(1), ",", space,
                    jsDefault(component == null ? Object.class : component), ")");
            return;
        case "copyOfRange":
            write("$rt_arrayCopyOfRange", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space,
                    jsDefault(component == null ? Object.class : component), ")");
            return;
        case "fill":
            if (params.size() == 2) {
                write("$rt_arrayFill", "(", params.get(0), ",", space, "0,", space, params.get(0), ".length,", space, params.get(1), ")");
            } else {
                write("$rt_arrayFill", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ",", space, params.get(3), ")");
            }
            return;
        case "equals":
            write("$rt_arrayEquals", "(", params.get(0), ",", space, params.get(1), ",", space, "false", ")");
            return;
        case "deepEquals":
            write("$rt_arrayEquals", "(", params.get(0), ",", space, params.get(1), ",", space, "true", ")");
            return;
        case "hashCode":
            write("$rt_arrayHash", "(", params.get(0), ")");
            return;
        case "deepHashCode":
            write("$rt_arrayHash", "(", params.get(0), ",", space, "true", ")");
            return;
        case "stream":
            if (params.size() == 3) {
                write("Stream.of(", params.get(0), ".slice(", params.get(1), ",", space, params.get(2), "))");
            } else {
                write("Stream.of(", params.get(0), ")");
            }
            return;
        default:
            break;
        }

        Class[] types = method.getParameterTypes();
        Code array = params.get(0);

        if (types.length == 1) {
            write("$rt_sortArray", "(", array, ")");
        } else if (types.length == 2) {
            // sort(Object[], Comparator)
            write("$rt_sortArray", "(", array, ",", space, "0", ",", space, array, ".length", ",", space, params.get(1), ")");
        } else if (types.length == 3) {
            // sort(Object[], int, int)
            write("$rt_sortArray", "(", array, ",", space, params.get(1), ",", space, params.get(2), ")");
        } else {
            // sort(Object[], int, int, Comparator)
            write("$rt_sortArray", "(", array, ",", space, params.get(1), ",", space, params.get(2), ",", space, params.get(3), ")");
        }
    }

    /**
     * Check whether the given method is declared in {@link Collections} or its class library
     * counterpart and needs to be overlaid.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isCollectionsMethod(Method method) {
        if (method.getDeclaringClass() != Collections.class) {
            return false;
        }
        return switch (method.getName()) {
        case "sort", "reverse", "swap", "fill", "copy", "max", "min", "binarySearch" -> true;
        case "unmodifiableList", "unmodifiableSet", "unmodifiableMap", "unmodifiableCollection", //
                "synchronizedList", "synchronizedSet", "synchronizedMap", "synchronizedCollection", //
                "checkedList", "checkedSet", "checkedMap", "checkedCollection", //
                "reverseOrder" -> true;
        default -> false;
        };
    }

    /**
     * Write the overlaid {@link Collections} method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeCollectionsMethod(Method method, Code context, List<Code> params) {
        switch (method.getName()) {
        case "sort":
            if (params.size() == 2) {
                write("$rt_sort", "(", params.get(0), ",", space, params.get(1), ")");
            } else {
                write("$rt_sort", "(", params.get(0), ")");
            }
            break;

        case "reverse":
            write("$rt_reverse", "(", params.get(0), ")");
            break;

        case "swap":
            write("$rt_swap", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            break;

        case "fill":
            write("$rt_fillList", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "copy":
            write("$rt_copyList", "(", params.get(0), ",", space, params.get(1), ")");
            break;

        case "max":
            write("$rt_max", "(", params.get(0), params.size() == 2 ? ", " : "", params.size() == 2 ? params.get(1) : "", ")");
            break;

        case "min":
            write("$rt_min", "(", params.get(0), params.size() == 2 ? ", " : "", params.size() == 2 ? params.get(1) : "", ")");
            break;

        case "binarySearch":
            if (params.size() == 2) {
                write("$rt_binarySearch", "(", params.get(0), ",", space, params.get(1), ")");
            } else {
                write("$rt_binarySearch", "(", params.get(0), ",", space, params.get(1), ",", space, params.get(2), ")");
            }
            break;

        case "reverseOrder":
            if (params.isEmpty()) {
                write("$rt_reverseOrder()");
            } else {
                write("$rt_reverseOrder", "(", params.get(0), ")");
            }
            break;

        default:
            // The unmodifiable/synchronized/checked views are identity in a single threaded
            // JavaScript runtime.
            write(params.get(0));
            break;
        }
    }

    /**
     * Check whether the given type is a print stream which must be overlaid to the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private boolean isPrintStream(Class type) {
        return type == java.io.PrintStream.class || type == java.io.PrintWriter.class;
    }

    /**
     * Write the overlaid print stream method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writePrintStreamMethod(Method method, Code context, List<Code> params) {
        String name = method.getName();

        switch (name) {
        case "print":
        case "println":
            if (params.isEmpty()) {
                write(context, ".", name, "()");
            } else {
                Code value = params.get(0);
                Class type = method.getParameterTypes()[0];

                if (type == char[].class) {
                    value = new CharArrayCode(value);
                } else if (type == double.class || type == float.class) {
                    Code original = value;
                    value = (Code) coder -> coder.write("$rt_doubleToString", "(", original, ")");
                }
                write(context, ".", name, "(", value, ")");
            }
            break;

        case "printf":
        case "format": {
            // The optional Locale argument is the first one, so the format string follows it.
            int index = method.getParameterTypes().length == 3 ? 1 : 0;
            if (params.size() <= index + 1) {
                write(context, ".", name, "(", params.get(index), ")");
            } else {
                write(context, ".", name, "(", params.get(index), ",", space, params.get(params.size() - 1), ")");
            }
            break;
        }

        case "write":
            write(context, ".write", buildCallParameter(method, params));
            break;

        default:
            write(context, ".", name, buildCallParameter(method, params));
            break;
        }
    }

    /**
     * Check whether the given method is the Throwable method which must be overlaid to the native
     * Error in the class library mode.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isThrowableMethod(Method method) {
        if (!Throwable.class.isAssignableFrom(method.getDeclaringClass())) {
            return false;
        }
        return switch (method.getName()) {
        case "getMessage", "getLocalizedMessage", "getCause", "printStackTrace" -> true;
        default -> false;
        };
    }

    /**
     * Write the overlaid Throwable method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeThrowableMethod(Method method, Code context, List<Code> params) {
        switch (method.getName()) {
        case "getMessage":
        case "getLocalizedMessage":
            write(context, ".message");
            break;
        case "getCause":
            write(context, ".cause", "||", space, "null");
            break;
        default:
            write("undefined");
            break;
        }
    }

    /**
     * Check whether the given method is the Object method which must be overlaid to the native
     * value in the class library mode.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isObjectMethod(Method method) {
        if (method.getDeclaringClass() != Object.class) {
            return false;
        }
        return switch (method.getName()) {
        case "equals" -> method.getParameterCount() == 1;
        case "hashCode", "toString" -> method.getParameterCount() == 0;
        default -> false;
        };
    }

    /**
     * Write the overlaid Object method.
     * 
     * @param method A target method.
     * @param context A target object.
     * @param params A list of parameters.
     */
    private void writeObjectMethod(Method method, Code context, List<Code> params) {
        switch (method.getName()) {
        case "equals":
            write("$rt_equals", "(", context, ",", space, params.get(0), ")");
            break;
        case "hashCode":
            write("$rt_hashCode", "(", context, ")");
            break;
        default:
            write("$rt_toString", "(", context, ")");
            break;
        }
    }

    /**
     * Check whether the given method is the single abstract method of a functional interface. Such
     * methods correspond to a lambda in JavaScript, so they are invoked directly.
     * 
     * @param method A target method.
     * @return A result.
     */
    private boolean isFunctionalMethod(Method method) {
        Class owner = method.getDeclaringClass();
        if (!owner.isInterface() || owner.isAnnotation() || (options.reflection && isReflectionType(owner))
                || Classlib.isReplaced(owner) || owner.getName().startsWith("reincarnation.classlib.")) {
            return false;
        }
        return Arrays.stream(owner.getDeclaredMethods())
                .filter(m -> Modifier.isAbstract(m.getModifiers()))
                .filter(m -> !m.isDefault())
                .count() == 1;
    }

    /**
     * Build parameter expresison.
     * 
     * @param executable
     * @param params
     * @return
     */
    private Join buildCallParameter(Executable executable, List<Code> params) {
        return buildCallParameter(executable, params, 0);
    }

    /**
     * Build parameter expresison.
     * 
     * @param executable
     * @param params
     * @return
     */
    private Join buildCallParameter(Executable executable, List<Code> params, int start) {
        return buildCallParameter(executable, params, start, null);
    }

    /**
     * Build parameter expresison with the trailing value.
     * 
     * @param executable
     * @param params
     * @param start
     * @param trailing A trailing value.
     * @return
     */
    private Join buildCallParameter(Executable executable, List<Code> params, int start, Object trailing) {
        return buildArgument(executable, params, start, trailing).prefix("(").suffix(")");
    }

    /**
     * Build the argument list without the enclosing parentheses.
     * 
     * @param executable
     * @param params
     * @param start
     * @param trailing A trailing value.
     * @return
     */
    private Join buildArgument(Executable executable, List<Code> params, int start, Object trailing) {
        Join concat = new Join().separator("," + space);

        Parameter[] parameters = executable.getParameters();
        for (int i = start; i < params.size(); i++) {
            Code param = params.get(i);

            if (param == Operand.Null) {
                concat.add(new InferedCode(parameters[i].getType(), param));
            } else if (i < parameters.length && parameters[i].getType() == long.class && !isLong(param)) {
                // The int value must be promoted to the BigInt at the long parameter.
                concat.add((Code) coder -> coder.write("$rt_l(", param, ")"));
            } else {
                concat.add(param);
            }
        }

        if (trailing != null) {
            concat.add((Code) coder -> coder.write(trailing));
        }
        return concat;
    }

    /**
     * @version 2018/10/26 14:10:50
     */
    private static class InferedCode implements Code {

        private final Class type;

        private final Code code;

        /**
         * @param type
         * @param code
         */
        private InferedCode(Class type, Code code) {
            this.type = type;
            this.code = code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(Coder coder) {
            coder.writeCast(type, code);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodReference(Method method, Code context) {
        write(context, ".", methodName(method), ".bind(", context, ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticMethodReference(Method method) {
        write(classRef(method.getDeclaringClass()), ".", methodName(method));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, boolean shorthandable, List<Code> dimensions) {
        if (dimensions.size() <= 1) {
            // e.g. new Array(3).fill(0). JavaScript leaves the elements undefined, while Java
            // initializes them with the default value of the component type.
            write("new", space, "Array", join("(", dimensions, ",", ")"), ".fill(", jsDefault(type), ")");
        } else {
            // e.g. new int[2][3] is converted to nested arrays.
            writeJSDimensionArray(type, dimensions, 0);
        }
    }

    /**
     * Write the nested array creation for multiple dimensions.
     * 
     * @param type A component type.
     * @param dimensions A list of dimension sizes.
     * @param index A current dimension index.
     */
    private void writeJSDimensionArray(Class type, List<Code> dimensions, int index) {
        if (index == dimensions.size() - 1) {
            write("new", space, "Array", "(", dimensions.get(index), ")", ".fill(", jsDefault(type), ")");
        } else {
            write("Array.from({length: ", dimensions.get(index), "}, () => ");
            writeJSDimensionArray(type, dimensions, index + 1);
            write(")");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, boolean shorthandable, List<Code> dimensions, List<Code> initialValues) {
        if (initialValues.isEmpty()) {
            // e.g. new Array(3).fill(0)
            write("new", space, "Array", join("(", dimensions, ",", ")"), ".fill(", jsDefault(type), ")");
        } else if (type == long.class) {
            // The int values in the long array initializer must be promoted to the BigInt.
            write(Join.of(initialValues).prefix("[").separator(", ").suffix("]").converter(this::longOperand));
        } else {
            // e.g. [1, 2, 3]
            write(join("[", initialValues, ", ", "]"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTernary(Code condition, Code then, Code elze) {
        // The conditional operator has a lower precedence than the arithmetic and relational
        // operators, so the whole expression is enclosed to keep the meaning when it is nested.
        write("(", condition, space, "?", space, then, space, ":", space, elze, ")");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCast(Class type, Code code) {
        boolean source = isLong(code);

        if (type == long.class) {
            if (!source) {
                write("$rt_dtol(", code, ")");
                return;
            }
        } else if (source) {
            if (type == int.class || type == short.class || type == byte.class) {
                write("$rt_ltoi(", code, ")");
                return;
            } else if (type == double.class) {
                write("$rt_ltod(", code, ")");
                return;
            } else if (type == float.class) {
                write("Math.fround($rt_ltod(", code, "))");
                return;
            } else if (type == char.class) {
                write("String.fromCharCode($rt_ltoi(", code, "))");
                return;
            }
        }

        // JavaScript has no cast expression. Casting is implicitly performed on assignment, so we
        // only need to output the original code.
        write(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLineComment(Object comment) {
        line("//", space, comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAssert(Code code, Variable<Code> message) {
        // The Java assert throws an AssertionError, so it must throw in JavaScript as well.
        write("if", space, "(!(", code, "))", space, "throw", space, "new", space, "Error");
        if (message.isAbsent()) {
            write("()");
        } else {
            write("(", message.get(), ")");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThrow(Code code) {
        write("throw", space, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBreak(Optional<String> label, boolean omitLabel) {
        line("break", omitLabel ? "" : label.map(v -> space + "l" + v), ";");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeContinue(Optional<String> label, boolean omitLabel) {
        line("continue", omitLabel ? "" : label.map(v -> space + "l" + v), ";");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeIf(Code condition, Code then, Code elze, Code follow) {
        line("if", space, "(", condition, ")", space, "{");
        indent(coder -> {
            vars.start();
            write(then);
            vars.end();
        });

        if (elze.isEmpty()) {
            line("}");
        } else {
            line("}", space, "else", space, "{");
            indent(coder -> {
                vars.start();
                write(elze);
                vars.end();
            });
            line("}");
        }
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFor(Optional<String> label, Code initialize, Code condition, Code updater, Runnable inner, Code follow) {
        vars.start();

        line(label(label), "for", space, "(", initialize, ";", space, expression(condition), ";", space, expressions(updater), ")", space, "{");
        indent(inner);
        line("}");

        vars.end();
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeIterableFor(Optional<String> label, Code variable, Code iterable, Runnable inner, Code follow) {
        vars.start();

        // The runtime iterable supports the array, the runtime collection and the Java iterator.
        line(label(label), "for", space, "(", variable, space, "of", space, "$rt_iterable", "(", iterable, ")", ")", space, "{");
        indent(inner);
        line("}");

        vars.end();
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeWhile(Optional<String> label, Code condition, Runnable inner, Code follow) {
        vars.start();
        line(label(label), "while", space, "(", expression(condition), ")", space, "{");
        indent(inner);
        line("}");
        vars.end();
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDoWhile(Optional<String> label, Code condition, Runnable inner, Code follow) {
        vars.start();
        line(label(label), "do", space, "{");
        indent(inner);
        line("}", space, "while", space, "(", expression(condition), ");");
        vars.end();
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInfinitLoop(Optional<String> label, Runnable inner, Code follow) {
        vars.start();
        line(label(label), "for", space, "(;;)", space, "{");
        indent(inner);
        line("}");
        vars.end();
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTryCatchFinally(Code tryBlock, List<Ⅲ<List<Class>, String, Code>> catchBlocks, Code follow) {
        line("try", space, "{");
        vars.start();
        indent(tryBlock::write);
        vars.end();
        for (Ⅲ<List<Class>, String, Code> catchBlock : catchBlocks) {
            vars.start();
            if (catchBlock.ⅱ != null) {
                vars.declare(catchBlock.ⅱ);
            }
            if (catchBlock.ⅰ != null && catchBlock.ⅱ != null) {
                // JavaScript cannot specify the caught type, so only the variable name is written.
                line("}", space, "catch(", vars.use(catchBlock.ⅱ), ")", space, "{");
            } else {
                line("}", space, "finally", space, "{");
            }
            indent(catchBlock.ⅲ::write);
            vars.end();
        }
        line("}");
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSynchronized(Code lock, Code body, Code follow) {
        // JavaScript is single threaded, so the synchronized block is emitted as a plain block.
        line("{");
        indent(body::write);
        line("}");
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTryWithResources(List<Ⅱ<Operand, Operand>> resources, Code tryBlock, Code follow) {
        // JavaScript cannot express try-with-resources, so the resources are declared up front and
        // closed in a finally block.
        for (Ⅱ<Operand, Operand> resource : resources) {
            String varName = resource.ⅰ.toString();
            vars.declare(varName);
            line("var ", vars.use(varName), space, "=", space, resource.ⅱ, ";");
        }
        line("try", space, "{");
        indent(tryBlock::write);
        line("}");
        if (!resources.isEmpty()) {
            line("finally", space, "{");
            for (Ⅱ<Operand, Operand> resource : resources) {
                line(vars.use(resource.ⅰ.toString()), ".close();");
            }
            line("}");
        }
        write(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeSwitch(boolean statement, Optional<String> label, Code condition, Class conditionType, Runnable caseProcess, Code follow) {
        if (statement) {
            line(label(label), "switch", space, "(", condition, ")", space, "{");
            indent(caseProcess);
            line("}");
            write(follow);
        } else {
            // JavaScript has no switch expression, so we emulate it with an immediately invoked
            // arrow function which returns the matched case value.
            lineNI("(()", space, "=>", space, "{");
            lineNI("switch", space, "(", condition, ")", space, "{");
            indent(caseProcess);
            line("}");
            lineNI("})()");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeIntCase(boolean statement, List<Integer> values, Code caseBlock) {
        if (statement) {
            for (int value : values) {
                line("case ", value, ":");
            }
            indent(() -> write(caseBlock));
        } else {
            for (int value : values) {
                line("case ", value, ":");
            }
            indent(() -> writeSwitchExpressionReturn(caseBlock));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCharCase(boolean statement, List<Character> values, Code caseBlock) {
        if (statement) {
            for (char value : values) {
                line("case \"", value, "\":");
            }
            indent(() -> write(caseBlock));
        } else {
            for (char value : values) {
                line("case \"", value, "\":");
            }
            indent(() -> writeSwitchExpressionReturn(caseBlock));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E extends Enum> void writeEnumCase(boolean statement, Class<E> type, List<E> values, Code caseBlock) {
        String typeName = classRef(type);

        if (statement) {
            for (E value : values) {
                line("case ", typeName, ".", enumFieldName(type, value.name()), ":");
            }
            indent(() -> write(caseBlock));
        } else {
            for (E value : values) {
                line("case ", typeName, ".", enumFieldName(type, value.name()), ":");
            }
            indent(() -> writeSwitchExpressionReturn(caseBlock));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeStringCase(boolean statement, List<String> values, Code caseBlock) {
        if (statement) {
            for (String value : values) {
                line("case ", value, ":");
            }
            indent(() -> write(caseBlock));
        } else {
            for (String value : values) {
                line("case ", value, ":");
            }
            indent(() -> writeSwitchExpressionReturn(caseBlock));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeTypeCase(boolean statement, List<PatternCase> patterns, Code caseBlock) {
        // JavaScript cannot express type patterns, so emit the case with the bound variable.
        for (PatternCase pattern : patterns) {
            if (pattern.isNull()) {
                line("case ", "null", ":");
            } else {
                String varName = pattern.variable.toString();
                vars.declare(varName);
                line("case ", vars.use(varName), ":");
            }
        }
        if (statement) {
            indent(() -> write(caseBlock));
        } else {
            indent(() -> writeSwitchExpressionReturn(caseBlock));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeDefaultCase(boolean statement, Code defaultBlock) {
        if (statement) {
            if (defaultBlock != null && defaultBlock.isNotEmpty()) {
                line("default ", ":");
                indent(() -> write(defaultBlock));
            }
        } else {
            line("default", ":");
            indent(() -> writeSwitchExpressionReturn(defaultBlock));
        }
    }

    /**
     * Write the given case or default block for switch expression.
     * <p>
     * Since JavaScript has no switch expression, each matched case returns the given block as the
     * value of the emulated switch expression.
     * </p>
     * 
     * @param block A case or default block.
     */
    private void writeSwitchExpressionReturn(Code block) {
        vars.start();
        write("return", space);
        block.write(new SwitchResult(this));
        write(";", EoL);
        vars.end();
    }

    /**
     * Write the value of the switch expression. The statements and the yield operators are written
     * as a single expression.
     */
    private class SwitchResult extends DelegatableCoder<CodingOption> {

        /**
         * @param coder
         */
        private SwitchResult(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            write(code);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeYield(Code code) {
            write(code);
        }
    }

    /**
     * Write the given annotation out.
     * 
     * @param element
     */
    private void writeAnnotation(List<AnnotationLike> annotations) {
        I.signal(annotations).skip(annotation -> annotation.clazz == Debuggable.class).to(annotation -> {
            // Checks for full compliance with the Repeatable annotation specification.
            Method[] methods = annotation.clazz.getDeclaredMethods();
            if (methods.length == 1) {
                Method m = methods[0];
                if (m.getName().equals("value")) {
                    Class returnType = m.getReturnType();
                    if (returnType.isArray() && returnType.getComponentType().isAnnotationPresent(Repeatable.class)) {
                        try {
                            for (AnnotationLike like : (List<AnnotationLike>) annotation.values.get("value")) {
                                line(writeAnnotationValue(like));
                            }
                        } catch (Exception e) {
                            throw I.quiet(e);
                        }
                        return;
                    }
                }
            }

            // process for non-repeatable annotation
            line(writeAnnotationValue(annotation));
        });
    }

    /**
     * Write the given annotation value recursively.
     * 
     * @param value
     * @return
     */
    private String writeAnnotationValue(Object value) {
        if (value instanceof AnnotationLike annotation) {
            return "@" + name(annotation.clazz) + Join.of(annotation.clazz.getDeclaredMethods())
                    .ignoreEmpty()
                    .prefix("(")
                    .separator("," + space)
                    .suffix(")")
                    .skip(m -> Objects.deepEquals(readAnnotationValue(annotation, m), m.getDefaultValue()))
                    .converter(m -> m.getName() + space + "=" + space + writeAnnotationValue(readAnnotationValue(annotation, m)));
        } else if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Character) {
            return "'" + value + "'";
        } else if (value instanceof Float) {
            return value + "F";
        } else if (value instanceof Long) {
            return value + "L";
        } else if (value instanceof Enum e) {
            return name(e.getDeclaringClass()) + "." + e.name();
        } else if (value instanceof Class clazz) {
            return name(clazz) + ".class";
        } else if (value instanceof List list) {
            StringJoiner join = new StringJoiner("," + space, "{", "}");
            for (Object item : list) {
                join.add(writeAnnotationValue(item));
            }
            return join.toString();
        } else if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            StringJoiner join = new StringJoiner("," + space, "{", "}");
            for (int i = 0; i < length; i++) {
                join.add(writeAnnotationValue(Array.get(value, i)));
            }
            return join.toString();
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * Read the current value of the specified annotation.
     * 
     * @param annotation
     * @param method
     * @return
     */
    private Object readAnnotationValue(AnnotationLike annotation, Method method) {
        try {
            Object value = annotation.values.get(method.getName());
            if (value == null) {
                return method.getDefaultValue();
            } else {
                return value;
            }
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * Write all statements as single expression.
     * 
     * @param code A target code.
     * @return A converted code.
     */
    private Code expression(Code code) {
        return c -> code.write(new Expression(c));
    }

    /**
     * Write all statements as multiple expressions.
     * 
     * @param code A target code.
     * @return A converted code.
     */
    private Code expressions(Code code) {
        return c -> code.write(new Expressions(c));
    }

    /**
     * Write labeled block.
     * 
     * @param label
     * @return
     */
    private Optional<String> label(Optional<String> label) {
        return label.map(v -> "l" + v + space + ":" + space);
    }

    /**
     * Qualify the specified name of type.
     * 
     * @param type
     * @return
     */
    private String name(Type type) {
        return qualify(type, false);
    }

    /**
     * Qualify the specified name of types.
     * 
     * @param type
     * @return
     */
    private Join name(Type[] types) {
        return Join.of(types).prefix("<").separator(", ").suffix(">" + space).converter(type -> qualify(1, type, false)).ignoreEmpty();
    }

    /**
     * Qualify the specified name of type.
     * 
     * @param type
     * @return
     */
    private String qualify(Type type, boolean vararg) {
        return qualify(0, type, vararg);
    }

    /**
     * Qualify the specified name of type.
     * 
     * @param type
     * @return
     */
    private String qualify(int level, Type type, boolean vararg) {
        StringBuilder builder = new StringBuilder();
        qualify(level, type, builder);

        if (vararg) {
            int length = builder.length();
            if (builder.charAt(length - 2) == '[' && builder.charAt(length - 1) == ']') {
                builder.delete(length - 2, length).append("...");
            }
        }
        return builder.toString();
    }

    /**
     * Qualify the specified name of type.
     * 
     * @param type
     */
    private void qualify(int level, Type type, StringBuilder builder) {
        if (type instanceof Class clazz) {
            if (isRuntimeType(clazz)) {
                requireRuntime();
            }
            builder.append(classRef(clazz));
        } else if (type instanceof TypeVariable variable) {
            builder.append(variable);
            if (level != 0) {
                Join.of(variable.getBounds())
                        .ignoreEmpty()
                        .prefix(" extends ")
                        .separator(space + "&" + space)
                        .converter(x -> name(x))
                        .take(x -> x != Object.class)
                        .write(builder);
            }
        } else if (type instanceof ParameterizedType parameterized) {
            qualify(level + 1, parameterized.getRawType(), builder);
            Join.of(parameterized.getActualTypeArguments())
                    .ignoreEmpty()
                    .prefix("<")
                    .separator("," + space)
                    .suffix(">")
                    .converter(this::name)
                    .write(builder);
        } else if (type instanceof WildcardType wild) {
            // lower bounds
            Join.of(wild.getLowerBounds())
                    .ignoreEmpty()
                    .prefix("? super ")
                    .separator(space + "&" + space)
                    .converter(this::name)
                    .write(builder, () -> {

                        // upper bounds
                        Join.of(wild.getUpperBounds())
                                .ignoreEmpty()
                                .prefix("? extends ")
                                .separator(space + "&" + space)
                                .converter(this::name)
                                .take(x -> x != Object.class)
                                .write(builder, () -> {

                                    // simple wildcard
                                    builder.append("?");
                                });
                    });
        } else if (type instanceof GenericArrayType array) {
            qualify(level + 1, array.getGenericComponentType(), builder);
            builder.append("[]");
        } else {
            throw new Error(String.valueOf(type));
        }
    }

    /**
     * Simple name.
     * 
     * @param type
     * @return
     */
    private String simpleName(Class type) {
        return names().allocate(type);
    }

    /**
     * Write the reference to the specified class. In the class registration mode, the class which
     * is defined by this coder is accessed through the runtime registry.
     * 
     * @param type A target class.
     * @return A JavaScript expression.
     */
    private String classRef(Class type) {
        type = Classlib.replace(type);

        if (options.reflection && isReflectionType(type)) {
            return "$rt." + type.getSimpleName();
        }

        if (type.isArray()) {
            return classRef(type.getComponentType()) + "[]";
        }

        String name = names().allocate(type);
        return defined.contains(type) ? "$c." + name : name;
    }

    /**
     * Check whether the given type is the reflection type which is provided by the runtime.
     * 
     * @param type A target type.
     * @return A result.
     */
    private static boolean isReflectionType(Class type) {
        return type == Class.class || type == java.lang.reflect.Method.class //
                || type == java.lang.reflect.Field.class || type == java.lang.reflect.Constructor.class //
                || type == java.lang.reflect.Modifier.class || type == java.lang.reflect.Array.class //
                || type == Type.class || type == TypeVariable.class //
                || type == ParameterizedType.class || type == GenericArrayType.class //
                || type == WildcardType.class;
    }

    /**
     * Write the registry reference of the specified class. It never throws even if the class is not
     * defined, so it is suitable for the interface check.
     * 
     * @param type A target class.
     * @return A JavaScript expression.
     */
    private String registryRef(Class type) {
        return "$c." + names().allocate(Classlib.replace(type));
    }

    /**
     * Quote the specified text as a JavaScript string literal.
     * 
     * @param text A target text.
     * @return A string literal.
     */
    private static String quote(String text) {
        StringBuilder builder = new StringBuilder(text.length() + 2).append('"');

        for (int i = 0; i < text.length(); i++) {
            char code = text.charAt(i);
            switch (code) {
            case '"':
                builder.append("\\\"");
                break;
            case '\\':
                builder.append("\\\\");
                break;
            case '\n':
                builder.append("\\n");
                break;
            case '\r':
                builder.append("\\r");
                break;
            case '\t':
                builder.append("\\t");
                break;
            default:
                builder.append(code);
                break;
            }
        }
        return builder.append('"').toString();
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(Class member) {
        return modifier(member.getModifiers(), member, false, member.getDeclaringClass());
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(Parameter member) {
        return modifier(member.getModifiers(), member, false, null);
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(Member member, boolean isDefault) {
        return modifier(member.getModifiers(), member, isDefault, member.getDeclaringClass());
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(int modifier, Object type, boolean isDefault, Class declaring) {
        boolean inInterface = declaring != null && declaring.isInterface();
        Join joiner = new Join().ignoreEmpty().separator(" ").suffix(" ");

        if (Modifier.isPublic(modifier)) {
            if (!inInterface) joiner.add("public");
        } else if (Modifier.isProtected(modifier)) {
            joiner.add("protected");
        } else if (Modifier.isPrivate(modifier)) {
            joiner.add("private");
        }

        if (Modifier.isStatic(modifier)) {
            joiner.add("static");
        } else if (isDefault) {
            joiner.add("default");
        }

        if (Modifier.isAbstract(modifier)) {
            if (!inInterface) joiner.add("abstract");
        } else if (Modifier.isFinal(modifier)) {
            joiner.add("readonly");
        }

        if (Modifier.isSynchronized(modifier)) {
            joiner.add("synchronized");
        }

        if (Modifier.isNative(modifier)) {
            joiner.add("native");
        }

        if (type == Field.class) {
            if (Modifier.isStrict(modifier)) {
                joiner.add("strictfp");
            }
            if (Modifier.isTransient(modifier)) {
                joiner.add("transient");
            }
            if (Modifier.isVolatile(modifier)) {
                joiner.add("volatile");
            }
        }

        if (type instanceof Class clazz) {
            if (clazz.isSealed()) {
                joiner.add("sealed");
            } else if (Classes.isSealedSubclass(clazz) && !Modifier.isFinal(modifier)) {
                joiner.add("non-sealed");
            }
        }
        return joiner;
    }

    /**
     * Compute the fully qualified class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A fully qualified class name.
     */
    public static final String computeName(Class clazz) {
        if (clazz.isAnonymousClass() || clazz.isLocalClass()) {
            String name = clazz.getName();
            return clazz.getEnclosingClass().getName() + "$" + name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getName();
        }
    }

    /**
     * Compute the simple class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A simple class name.
     */
    public static final String computeSimpleName(Class clazz) {
        return new NameAllocator().allocate(clazz);
    }

    /**
     * @version 2018/11/01 15:33:55
     */
    private class Expression extends DelegatableCoder<CodingOption> {

        /**
         * @param coder
         */
        private Expression(Coder<CodingOption> coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            write(code);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeYield(Code code) {
            write(code, ";");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeThrow(Code code) {
            write("throw ", code, ";");
        }
    }

    /**
     * @version 2018/11/05 14:26:01
     */
    private class Expressions extends DelegatableCoder<CodingOption> {

        /** The state. */
        private boolean first = true;

        /**
         * @param coder
         */
        private Expressions(Coder<CodingOption> coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            if (first) {
                first = false;

                write(code);
            } else {
                write(",", space, code);
            }
        }
    }

    /**
     * Special coder for interface static initializer.
     */
    private class InterfaceStaticInitiailizer extends DelegatableCoder<CodingOption> {

        /**
         * @param coder
         */
        private InterfaceStaticInitiailizer(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
            write(left, space, operator, space, right);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAccessField(Field field, Code context, AccessMode mode) {
            write(simpleName(field.getType()), space, fieldName(field));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            line(code, ";");
        }
    }

    /**
     * Special coder for enum static initializer.
     */
    private class EnumStaticInitializer extends DelegatableCoder<CodingOption> {

        /** The enum type. */
        private final Class type;

        /** The defined constants. */
        private final List<Enum> constants;

        /** The number of initialized constants. */
        private int initialized;

        /** The user defined custom initializer code. */
        private final List<Code> codes = new ArrayList();

        /**
         * @param coder
         */
        private EnumStaticInitializer(Coder coder) {
            super(coder);

            this.type = current.exact();
            this.constants = I.signal(type.getEnumConstants()).as(Enum.class).toList();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
            if (whileInitializing()) {
                Enum constant = constants.get(Math.min(initialized - 1, constants.size() - 1));

                // The enum constants are created in the deferred static initializer, so they are
                // assigned to the class object. The name and ordinal are stored to the instance
                // because they are not a part of the user defined constructor.
                write(classRef(type), ".", enumFieldName(type, constant.name()), space, "=", space, "$rt.enumValue(new", space, classRef(type), space, right, ",",
                        space, quote(constant.name()), ",", space, constants.indexOf(constant), ");");
            } else {
                super.writeAssignOperation(left, operator, right);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeConstructorCall(Constructor constructor, List<Code> params) {
            if (whileInitializing()) {
                Class owner = constructor.getDeclaringClass();
                if (current.get().isAssignableFrom(owner) && 2 <= params.size()) {
                    if (initialized <= constants.size()) {
                        // The parameter list is shared with the cached code, so it must be copied
                        // before removing the implicit parameters.
                        List<Code> actual = new ArrayList(params);
                        actual.remove(0);
                        actual.remove(0);
                        write(buildCallParameter(constructor, actual, 0, constructorIndex(constructor)));
                    }
                }
            } else {
                super.writeConstructorCall(constructor, params);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            if (whileInitializing()) {
                if (initialized++ < constants.size()) {
                    line(code);
                }
            } else {
                codes.add(code);
            }
        }

        private boolean whileInitializing() {
            return initialized <= constants.size();
        }

        /**
         * Special coder for enum constant class constructor.
         */
        private static class EnumConstantClassConstructor extends DelegatableCoder<CodingOption> {

            /** The number of processed statements. */
            private int statements;

            /**
             * @param coder
             */
            private EnumConstantClassConstructor(Coder coder) {
                super(coder);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeConstructor(Constructor con, Code<Code> code) {
                snapshot(() -> {
                    line("{");
                    indent(code::write);
                    line("}");

                    if (statements <= 1) revert();
                });
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeStatement(Code code) {
                // need to skip super call
                if (0 < statements++) {
                    super.writeStatement(code);
                }
            }
        }
    }

    /**
     * Special coder for non-static local class constructor.
     */
    private class NonStaticLocalConstructor extends DelegatableCoder<CodingOption> {

        /** The number of initialized constants. */
        private List<Code> initializer = new ArrayList();

        /**
         * @param coder
         */
        private NonStaticLocalConstructor(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code<?> code) {
            if (initializer == null) {
                super.writeStatement(code);
            } else {
                if (code instanceof OperandConstructorCall call) {
                    super.writeStatement(call);
                    for (Code init : initializer) {
                        super.writeStatement(init);
                    }
                    initializer = null;
                } else {
                    initializer.add(code);
                }
            }
        }
    }
}