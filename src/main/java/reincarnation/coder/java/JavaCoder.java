/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.java;

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
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import kiss.I;
import kiss.Variable;
import kiss.Ⅱ;
import kiss.Ⅲ;
import reincarnation.Operand;
import reincarnation.OperandUtil;
import reincarnation.Reincarnation;
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

/**
 * @version 2018/10/21 21:36:48
 */
public class JavaCoder extends Coder<JavaCodingOption> {

    /** The current type. (maybe null in debug context) */
    private final Variable<Class> current = Variable.empty();

    /** The variable holder. */
    private final VariableNaming vars = new VariableNaming();

    /** The import manager. */
    final Imports imports = new Imports();

    /**
     * 
     */
    public JavaCoder() {
        super();
    }

    /**
     * @param appendable
     */
    public JavaCoder(Appendable appendable) {
        super(appendable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Reincarnation reincarnation) {
        imports.setBase(reincarnation.clazz);

        writePackage(reincarnation.clazz.getPackage());

        if (options.writeMemberFromTopLevel && Classes.isMemberLike(reincarnation.clazz)) {
            Hierarchy hierarchy = Hierarchy.calculate(reincarnation);

            writeImport(hierarchy.classes);
            writeHierarchy(hierarchy);
        } else {
            writeImport(reincarnation.classes);
            writeOne(reincarnation);
        }
    }

    private void writeOne(Reincarnation reincarnation) {
        writeType(reincarnation.clazz, () -> {
            List<Field> statics = new ArrayList();
            List<Field> fields = new ArrayList();
            for (Field field : reincarnation.clazz.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    statics.add(field);
                } else {
                    fields.add(field);
                }
            }

            statics.forEach(this::writeStaticField);
            reincarnation.staticInitializer.forEach(this::writeStaticInitializer);
            fields.forEach(this::writeField);
            reincarnation.initializer.forEach(this::writeInitializer);
            reincarnation.constructors.entrySet().forEach(e -> writeConstructor(e.getKey(), e.getValue()));
            reincarnation.methods.entrySet().forEach(e -> writeMethod(e.getKey(), e.getValue()));
            reincarnation.anonymous.forEach(e -> writeOne(Reincarnation.exhume(e)));
        });
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
    public void writeImport(Set<Class> classes) {
        if (!classes.isEmpty()) {
            imports.add(classes);

            line();
            for (Class clazz : imports.imported) {
                line("import", space, clazz.getCanonicalName(), ";");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
        current.set(type);

        String kind;
        String extend = "";
        Join<Class> implement;
        Join accessor = modifier(type);

        if (type.isInterface()) {
            kind = "interface";
            implement = Join.of(type.getInterfaces()).prefix(" extends ").converter(this::name);
            accessor.remove("static", "abstract");
        } else if (type.isEnum()) {
            kind = "enum";
            implement = Join.of(type.getInterfaces()).prefix(" implements ").converter(this::name);
        } else {
            kind = "class";
            extend = type.getSuperclass() == Object.class ? "" : " extends " + name(type.getSuperclass());
            implement = Join.of(type.getInterfaces()).prefix(" implements ").converter(this::name);
        }

        line();
        line(accessor, kind, space, simpleName(type), extend, implement, space, "{");
        indent(inner::run);
        line("}");
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
        if (current.is(Class::isInterface)) {
            // ignore, write fields in static initializer
        } else {
            line(modifier(field), name(field.getType()), space, field.getName(), ";");
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

        if (current.is(Class::isInterface)) {
            code.write(new InterfaceCoder(this));
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
    public void writeConstructor(Constructor constructor, Code code) {
        vars.start();

        line();
        line(modifier(constructor), simpleName(constructor.getDeclaringClass()), parameter(constructor, naming(code)), space, "{");
        indent(code::write);
        line("}");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code code) {
        // ignore compiler generated method
        if (method.isSynthetic()) {
            return;
        }

        vars.start();

        line();
        line(modifier(method), name(method.getReturnType()), space, method
                .getName(), parameter(method, naming(code)), thrower(method.getExceptionTypes()), space, "{");
        indent(code::write);
        line("}");

        vars.end();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLambda(Method method, Code code) {
        vars.start();

        lineNI(parameter(method, naming(code)), space, "->", space, "{");
        indent(code::write);
        lineNB("}");

        vars.end();
    }

    /**
     * Build parameter types.
     * 
     * @param parameters
     * @return
     */
    private Join parameter(Executable executable, Naming strategy) {
        List<Ⅱ<Parameter, Type>> params = I.signal(executable.getParameters())
                .combine(I.signal(executable.getGenericParameterTypes()))
                .toList();

        return Join.of(params).prefix("(").suffix(")").ignoreEmpty(false).separator("," + space).converter(p -> {
            String name = strategy.name(p.ⅰ.getName());
            vars.declare(name);

            StringBuilder builder = new StringBuilder();
            if (Modifier.isFinal(p.ⅰ.getModifiers())) {
                builder.append("final ");
            }

            if (p.ⅰ.isVarArgs()) {
                builder.append(name(p.ⅰ.getType().getComponentType())).append("... ");
            } else {
                builder.append(name(p.ⅱ)).append(" ");
            }
            builder.append(name);

            return builder.toString();
        });
    }

    /**
     * Build throws declaration.
     * 
     * @param parameters
     * @return
     */
    private Join thrower(Class[] exceptions) {
        return Join.of(exceptions).prefix(space + "throws" + space).ignoreEmpty(true).separator("," + space).converter(type -> {
            return name(type);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStatement(Code<?> code) {
        line(code, ";", code.comment().map(" // "::concat));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Variable<Code> code) {
        if (code.isAbsent()) {
            write("return");
        } else {
            write("return", space, code.get());
        }
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
    public void writeLocalVariable(Type type, String name) {
        String prefix;
        if (vars.isDeclared(name)) {
            prefix = "";
        } else {
            boolean needInfer = (type instanceof Class clazz && clazz.getTypeParameters().length != 0) || type instanceof ParameterizedType;
            prefix = (needInfer ? "var" : name(type)).concat(space);
            vars.declare(name);
        }

        write(prefix, vars.name(name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context, AccessMode mode) {
        if (Modifier.isStatic(field.getModifiers())) {
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
    public void writeConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("new", space, name(constructor.getDeclaringClass()), buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("super", buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("this", buildParameter(constructor, params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<? extends Code> params, AccessMode mode) {
        if (method.isSynthetic()) {
            write(context, ".", method.getName(), buildParameter(method, params));
        } else {
            if (mode == AccessMode.SUPER) {
                write("super.", method.getName(), buildParameter(method, params));
            } else {
                if (OperandUtil.isWrapper(method)) {
                    write(params.get(0));
                } else {
                    write(context, ".", method.getName(), buildParameter(method, params));
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
    private Join buildParameter(Executable executable, List<? extends Code> params) {
        Join concat = new Join().ignoreEmpty(false).prefix("(").suffix(")").separator("," + space);
        Parameter[] parameters = executable.getParameters();

        for (int i = 0; i < params.size(); i++) {
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
    public void writeCreateArray(Class type, List<Code> dimensions) {
        write("new", space, name(type), join("[", dimensions, "][", "]"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, List<Code> dimensions, List<Code> initialValues) {
        write("new", space, name(type), join("[", dimensions, "][", "]"), space, join("{", initialValues, ", ", "}"));
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
    public void writeThrow(Code code) {
        write("throw", space, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBreak(Optional<String> label) {
        line("break", label.map(v -> space + "l" + v), ";");
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
                line("}", space, "catch(", name(catchBlock.ⅰ), space, vars.name(catchBlock.ⅱ), ")", space, "{");
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
     * Qualify the specified name of type..
     * 
     * @param type
     * @return
     */
    private String name(Type type) {
        StringBuilder builder = new StringBuilder();
        qualify(type, builder);
        return builder.toString();
    }

    /**
     * Qualify the specified name of type..
     * 
     * @param type
     */
    private void qualify(Type type, StringBuilder builder) {
        if (type instanceof Class clazz) {
            builder.append(imports.name(clazz));
        } else if (type instanceof TypeVariable variable) {
            builder.append(variable);
        } else if (type instanceof ParameterizedType parameterized) {
            qualify(parameterized.getRawType(), builder);

            StringJoiner join = new StringJoiner("," + space, "<", ">");
            for (Type param : parameterized.getActualTypeArguments()) {
                join.add(name(param));
            }
            builder.append(join);
        } else if (type instanceof WildcardType wild) {
            Type[] lowers = wild.getLowerBounds();
            if (lowers.length != 0) {
                StringJoiner join = new StringJoiner(space + "&" + space, "? super ", "");
                for (Type lower : lowers) {
                    join.add(name(lower));
                }
                builder.append(join);
                return;
            }

            Type[] uppers = wild.getUpperBounds();
            if (uppers.length != 0) {
                StringJoiner join = new StringJoiner(space + "&" + space, "? extends ", "");
                for (Type upper : uppers) {
                    join.add(name(upper));
                }
                builder.append(join);
                return;
            }
            throw new Error();
        } else if (type instanceof GenericArrayType array) {
            throw new Error("Generic array");
        } else {
            throw new Error();
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
        return modifier(member.getModifiers(), member);
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(Parameter member) {
        return modifier(member.getModifiers(), member);
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(Member member) {
        return modifier(member.getModifiers(), member);
    }

    /**
     * Accessor keyword.
     * 
     * @param modifier
     * @return
     */
    private Join modifier(int modifier, Object type) {
        Join joiner = new Join().separator(" ").suffix(" ");

        if (Modifier.isPublic(modifier)) {
            joiner.add("public");
        } else if (Modifier.isProtected(modifier)) {
            joiner.add("protected");
        } else if (Modifier.isPrivate(modifier)) {
            joiner.add("private");
        }

        if (Modifier.isStatic(modifier)) {
            joiner.add("static");
        }

        if (Modifier.isAbstract(modifier)) {
            joiner.add("abstract");
        } else if (Modifier.isFinal(modifier)) {
            joiner.add("final");
        } else if (Modifier.isNative(modifier)) {
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
        if (clazz.isAnonymousClass() || clazz.isLocalClass()) {
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
     * @version 2018/10/25 20:03:24
     */
    private class InterfaceCoder extends DelegatableCoder<CodingOption> {

        /**
         * @param coder
         */
        private InterfaceCoder(Coder coder) {
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
}