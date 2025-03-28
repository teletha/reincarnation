/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

import java.lang.annotation.Repeatable;
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
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import kiss.I;
import kiss.Variable;
import kiss.Ⅱ;
import kiss.Ⅲ;
import reincarnation.Debuggable;
import reincarnation.Operand;
import reincarnation.OperandConstructorCall;
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

public class JavaCoder extends Coder<JavaCodingOption> {

    /** The current type. (maybe null in debug context) */
    private final Variable<Class> current = Variable.empty();

    /** The variable holder. */
    private final VariableNaming vars = new VariableNaming();

    /** The placeholder of local classes. */
    private final MultiMap<Executable, Class> placeholders = new MultiMap(false);

    /** The import manager. */
    private final Imports imports = new Imports();

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

        imports.setBase(reincarnation.clazz);

        writePackage(reincarnation.clazz.getPackage());

        if (options.writeMemberFromTopLevel && Classes.isMemberLike(reincarnation.clazz)) {
            Hierarchy hierarchy = Hierarchy.calculate(reincarnation);

            writeLazy(this::writeImport);
            writeHierarchy(hierarchy);
        } else {
            writeLazy(this::writeImport);
            writeOne(reincarnation);
        }

        // pop decompiler info
        this.reincarnation = old;
    }

    private void writeOne(Reincarnation reincarnation) {
        // push decompiler info
        Reincarnation old = this.reincarnation;
        this.reincarnation = reincarnation;

        writeType(reincarnation.clazz, () -> {
            // Separate anonymous classes into Enum subclasses and non-Enum subclasses
            List<Class> enums = new ArrayList();
            List<Class> anons = new ArrayList();
            for (Class clazz : reincarnation.anonymous) {
                Class superclass = clazz.getSuperclass();
                if (superclass != null && superclass.isEnum()) {
                    enums.add(clazz);
                } else {
                    anons.add(clazz);
                }
            }

            reincarnation.staticFields.forEach(this::writeStaticField);
            reincarnation.staticInitializer.forEach(this::writeStaticInitializer);
            reincarnation.fields.forEach(this::writeField);
            reincarnation.initializer.forEach(this::writeInitializer);
            reincarnation.constructors.forEach(this::writeConstructor);
            reincarnation.methods.forEach(this::writeMethod);
            anons.forEach(e -> writeOne(Reincarnation.exhume(e)));
        });

        // pop decompiler info
        this.reincarnation = old;
    }

    /**
     * Write hierarchy skelton.
     * 
     * @param hierarchy
     */
    private void writeHierarchy(Hierarchy hierarchy) {
        if (hierarchy.skelton) {
            writeType(hierarchy.clazz, () -> {
                for (Hierarchy child : hierarchy.children) {
                    writeHierarchy(child);
                }
            });
        } else {
            writeOne(Reincarnation.exhume(hierarchy.clazz));
        }
    }

    /**
     * Write import part.
     */
    private void writeImport() {
        imports.export().values().forEach(classes -> {
            line();
            for (Class clazz : classes) {
                line("import ", clazz.getCanonicalName(), ";");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePackage(Package info) {
        line("package", space, info.getName(), ";");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
        Class prev = current.set(type);

        String kind;
        String extend = "";
        Join<Type> implement;
        Join<Class> permit = type.isSealed()
                ? Join.of(type.getPermittedSubclasses()).ignoreEmpty().prefix(" permits ").separator("," + space).converter(this::name)
                : null;
        Join accessor = modifier(type);
        Join variable = name(type.getTypeParameters());

        if (type.isRecord()) {
            kind = "record";
            implement = Join.of(type.getGenericInterfaces()).ignoreEmpty().prefix(" implements ").converter(this::name);
            accessor.remove("static", "final");

            boolean vararg = type.getDeclaredConstructors()[0].isVarArgs();
            int max = type.getRecordComponents().length - 1;
            variable = Join.of(type.getRecordComponents())
                    .prefix("(")
                    .separator("," + space)
                    .suffix(")")
                    .converter((index, x) -> qualify(x.getGenericType(), vararg && index == max) + " " + x.getName());
        } else if (type.isAnnotation()) {
            kind = "@interface";
            implement = null;
            accessor.remove("static", "abstract");
        } else if (type.isInterface()) {
            kind = "interface";
            implement = Join.of(type.getGenericInterfaces()).ignoreEmpty().prefix(" extends ").converter(this::name);
            accessor.remove("static", "abstract");
        } else if (type.isEnum()) {
            kind = "enum";
            implement = Join.of(type.getGenericInterfaces()).ignoreEmpty().prefix(" implements ").converter(this::name);
            accessor.remove("static", "final", "abstract", "sealed");
            permit = null;
        } else {
            kind = "class";
            extend = type.getSuperclass() == Object.class ? "" : " extends " + name(type.getGenericSuperclass());
            implement = Join.of(type.getGenericInterfaces()).ignoreEmpty().prefix(" implements ").converter(this::name);
        }

        line();
        writeAnnotation(reincarnation.annotations.get(type));
        line(accessor, kind, space, simpleName(type), variable, extend, implement, permit, space, "{");
        indent(inner);
        line("}");

        current.set(prev);
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
        // ignore compiler generated code
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
        writeAnnotation(reincarnation.annotations.get(field));
        line(modifier(field, false), name(field.getType()), space, field.getName(), ";");
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

        if (current.is(Class::isInterface)) {
            code.write(new InterfaceStaticInitiailizer(this));
        } else if (current.is(Class::isEnum)) {
            EnumStaticInitializer coder = new EnumStaticInitializer(this);
            code.write(coder);

            I.signal(current.v.getDeclaredFields()).skip(Field::isSynthetic).skip(Field::isEnumConstant).to(this::writeFieldDefinition);

            if (!coder.codes.isEmpty()) {
                line();
                line("static {");
                indent(() -> coder.codes.forEach(this::writeStatement));
                line("}");
            }
        } else {
            line();
            line("static {");
            indent(code::write);
            line("}");
        }

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor con, Code<Code> code) {
        // ignore compiler generated code
        if (GeneratedCodes.isRecordConstructor(con, code) //
                || GeneratedCodes.isEnumConstructor(con, code) //
                || GeneratedCodes.isImplicitConstructor(con, code)) {
            return;
        }

        vars.start();

        line();
        writeAnnotation(reincarnation.annotations.get(con));
        line(modifier(con, false), name(con.getTypeParameters()), simpleName(con
                .getDeclaringClass()), buildParameter(con, naming(code)), space, "{");
        indent(() -> {
            Class owner = con.getDeclaringClass();
            if (Classes.isMemberLike(owner) && Classes.isNonStatic(owner)) {
                code.write(new NonStaticLocalConstructor(this));
            } else {
                code.write(this);
            }
        });
        line("}");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code<Code> code) {
        // ignore compiler generated code
        if (method.isSynthetic() || GeneratedCodes.isRecordMethod(method, code) || GeneratedCodes.isEnumMethod(method, code)) {
            return;
        }

        vars.start();

        line();
        writeAnnotation(reincarnation.annotations.get(method));
        lineNB(modifier(method, method.isDefault()), name(method.getTypeParameters()), name(method.getGenericReturnType()), " ", method
                .getName(), buildParameter(method, naming(code)), thrower(method.getGenericExceptionTypes()));

        if (Classes.isAbstract(method) || Classes.isNative(method)) {
            lineNI(";");
        } else {
            lineNI(space, "{");
            placeholders.get(method).forEach(this::writeLocalClass);
            indent(code::write);
            line("}");
        }

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
                    String name = naming.name(p.getName());
                    vars.declare(name);
                    return name;
                });

        for (int i = 0; i < contexts.size(); i++) {
            vars.declare("arg" + i, contexts.get(i).toString());
        }

        lineNI(param, space, "->", space, "{");
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
                    Type type = p.ⅱ;
                    String name = strategy.name(param.getName());
                    vars.declare(name);

                    Join annotations = Join.of(reincarnation.annotations.get(param))
                            .ignoreEmpty()
                            .separator(" ")
                            .suffix(" ")
                            .converter(JavaCoder.this::writeAnnotationValue);

                    return new StringBuilder() //
                            .append(annotations)
                            .append(modifier(param))
                            .append(qualify(type, param.isVarArgs()))
                            .append(' ')
                            .append(name)
                            .toString();
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
        write("'", code, "'");
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
        write(code, "L");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFloat(float code) {
        write(code, "F");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDouble(double code) {
        write(code, "D");
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
        write("\"\"\"", EoL);
        indent(() -> {
            for (String line : code) {
                line(line.replaceAll(" $", "\\\\s"));
            }
            lineNB("\"\"\"");
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
        write(name(code), ".class");
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
        write(left, space, operator, space, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBinaryOperation(Code left, BinaryOperator operator, Code right) {
        write(left, space, operator, space, right);
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
        write("!", code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeUnaryOperation(Code code, UnaryOperator operator) {
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
        if (cast == null) {
            write(code, space, "instanceof", space, name(type));
        } else {
            write(code, space, "instanceof", space, cast);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariable(Type type, int index, String name) {
        String prefix;
        if (vars.isDeclared(name)) {
            prefix = "";
        } else {
            boolean needInfer = (type instanceof Class clazz && clazz.getTypeParameters().length != 0);
            prefix = (needInfer ? "var" : name(type)).concat(space);
            vars.declare(name);
        }
        write(prefix, vars.use(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context, AccessMode mode) {
        if (Classes.isStatic(field)) {
            if (current.is(field.getDeclaringClass())) {
                write(field.getName());
            } else {
                write(context, ".", field.getName());
            }
        } else {
            switch (mode) {
            case THIS:
                write(context, ".", field.getName());
                break;

            case SUPER:
                write("super.", field.getName());
                break;

            default:
                write("((", name(field.getDeclaringClass()), ")", space, context, ").", field.getName());
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
        write("new", space, name(constructor.getDeclaringClass()), buildCallParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<Code> params) {
        if (params.isEmpty() || constructor.getDeclaringClass() == Enum.class) {
            revert();
        } else {
            write("super", buildCallParameter(constructor, params));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<Code> params) {
        if (constructor.getDeclaringClass().isEnum()) {
            write("this", buildCallParameter(constructor, params, 2));
        } else {
            write("this", buildCallParameter(constructor, params));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructorReference(Constructor constructor) {
        write(name(constructor.getDeclaringClass()), "::new");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<Code> params, AccessMode mode) {
        if (method.isSynthetic()) {
            write(context, ".", method.getName(), buildCallParameter(method, params));
        } else {
            if (mode == AccessMode.SUPER) {
                write("super.", method.getName(), buildCallParameter(method, params));
            } else {
                if (Classes.isWrapper(method)) {
                    write(params.get(0));
                } else {
                    if (Classes.isStatic(method) && current.is(method.getDeclaringClass())) {
                        write(method.getName(), buildCallParameter(method, params));
                    } else {
                        write(context, ".", method.getName(), buildCallParameter(method, params));
                    }
                }
            }
        }
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
        Join concat = new Join().prefix("(").suffix(")").separator("," + space);

        Parameter[] parameters = executable.getParameters();
        for (int i = start; i < params.size(); i++) {
            Code param = params.get(i);

            if (param == Operand.Null) {
                concat.add(new InferedCode(parameters[i].getType(), param));
            } else {
                concat.add(param);
            }
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
        write(context, "::", method.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticMethodReference(Method method) {
        write(name(method.getDeclaringClass()), "::", method.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, boolean shorthandable, List<Code> dimensions) {
        write("new", space, name(type), join("[", dimensions, "][", "]"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, boolean shorthandable, List<Code> dimensions, List<Code> initialValues) {
        if (shorthandable) {
            write(join("{", initialValues, ", ", "}"));
        } else {
            write("new", space, name(type), join("[", dimensions, "][", "]"), space, join("{", initialValues, ", ", "}"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTernary(Code condition, Code then, Code elze) {
        write(condition, space, "?", space, then, space, ":", space, elze);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCast(Class type, Code code) {
        write("(", name(type), ")", space, "(", code, ")");
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
        if (message.isAbsent()) {
            write("assert", space, code);
        } else {
            write("assert", space, code, space, ":", space, message.v);
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

        line(label(label), "for", space, "(", variable, space, ":", space, iterable, ")", space, "{");
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
    public void writeTryCatchFinally(Code tryBlock, List<Ⅲ<Class, String, Code>> catchBlocks, Code follow) {
        line("try", space, "{");
        vars.start();
        indent(tryBlock::write);
        vars.end();
        for (Ⅲ<Class, String, Code> catchBlock : catchBlocks) {
            vars.start();
            vars.declare(catchBlock.ⅱ);
            if (catchBlock.ⅰ != null) {
                line("}", space, "catch(", name(catchBlock.ⅰ), space, vars.use(catchBlock.ⅱ), ")", space, "{");
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
    protected void writeSwitch(boolean statement, Optional<String> label, Code condition, Class conditionType, Runnable caseProcess, Code follow) {
        if (statement) {
            line(label(label), "switch", space, "(", condition, ")", space, "{");
            indent(caseProcess);
            line("}");
            write(follow);
        } else {
            lineNI("switch", space, "(", condition, ")", space, "{");
            indent(caseProcess);
            lineNB("}");
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
            Join labels = Join.of(values).prefix("case ").separator("," + space);
            writeSwitchExpressionBlock(labels, caseBlock);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCharCase(boolean statement, List<Character> values, Code caseBlock) {
        if (statement) {
            for (char value : values) {
                line("case '", value, "':");
            }
            indent(() -> write(caseBlock));
        } else {
            Join labels = Join.of(values).prefix("case ").separator("," + space).converter(v -> "'" + v + "'");
            writeSwitchExpressionBlock(labels, caseBlock);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E extends Enum> void writeEnumCase(boolean statement, Class<E> type, List<E> values, Code caseBlock) {
        String name = name(type);
        boolean qualified = type.getName().equals(name);

        if (statement) {
            for (E value : values) {
                line("case ", qualified ? name + "." : "", value.name(), ":");
            }
            indent(() -> write(caseBlock));
        } else {
            Join labels = Join.of(values)
                    .prefix("case ")
                    .separator("," + space)
                    .converter(v -> qualified ? name + "." + v.name() : v.name());
            writeSwitchExpressionBlock(labels, caseBlock);
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
            Join labels = Join.of(values).prefix("case ").separator("," + space);
            writeSwitchExpressionBlock(labels, caseBlock);
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
            Join labels = Join.of().prefix("default");
            writeSwitchExpressionBlock(labels, defaultBlock);
        }
    }

    /**
     * Write the given case or default block for switch expression.
     * 
     * @param labels
     * @param block
     */
    private void writeSwitchExpressionBlock(Join labels, Code block) {
        vars.start();

        if (block instanceof Fragment frag && !frag.hasFollower()) {
            line(labels, space, "->", space, expression(block));
        } else {
            line(labels, space, "->", space, "{");
            indent(block::write);
            line("}");
        }

        vars.end();
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
            builder.append(imports.name(clazz));
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
        return computeSimpleName(type);
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
            joiner.add("final");
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
        if (clazz.isLocalClass()) {
            return clazz.getSimpleName();
        } else if (clazz.isAnonymousClass()) {
            String name = clazz.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getSimpleName();
        }
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
            write(name(field.getType()), space, field.getName());
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
                write(left, right);
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
                if (current.v.isAssignableFrom(owner) && 2 <= params.size()) {
                    if (initialized <= constants.size()) {
                        // remove implicit parameters
                        params.remove(0);
                        params.remove(0);
                        Join parameters = Join.of(params).ignoreEmpty().prefix("(").separator("," + space).suffix(")");

                        write(parameters);
                        if (!owner.isEnum()) {
                            // We assume it is a customized Enum since it uses subclasses for
                            // constant definitions.
                            lineNI(space, "{");
                            indent(() -> {
                                Class prev = current.set(owner);
                                Reincarnation exhumed = Reincarnation.exhume(owner);

                                exhumed.staticFields.forEach(this::writeStaticField);
                                exhumed.fields.forEach(this::writeField);
                                exhumed.constructors.forEach(new EnumConstantClassConstructor(coder)::writeConstructor);
                                exhumed.methods.forEach(this::writeMethod);
                                current.set(prev);
                            });
                            lineNB("}");
                        }
                        write(initialized < constants.size() ? "," + space : ";");
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