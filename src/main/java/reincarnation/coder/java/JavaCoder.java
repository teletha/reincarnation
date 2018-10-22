/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder.java;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import kiss.Variable;
import reincarnation.Reincarnation;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

/**
 * @version 2018/10/21 21:36:48
 */
public class JavaCoder extends Coder<JavaCodingOption> {

    /** The current type. (maybe null in debug context) */
    private final Variable<Class> current = Variable.empty();

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
        writeImport(reincarnation.classes);

        if (options.writeMemberFromTopLevel && Classes.isMemberLike(reincarnation.clazz)) {
            writeHierarchy(Hierarchy.calculate(reincarnation));
        } else {
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
        String extend;

        if (type.isInterface()) {
            kind = "interface";
            extend = join(type.getInterfaces(), this::name, ", ", " extends ", "");
        } else if (type.isEnum()) {
            kind = "enum";
            extend = join(type.getInterfaces(), this::name, ", ", " implements ", "");
        } else {
            kind = "class";
            extend = type.getSuperclass() == Object.class ? "" : " extends " + name(type.getSuperclass());
            extend += join(type.getInterfaces(), this::name, ", ", " implements ", "");
        }

        line();
        line(accessor(type.getModifiers()), kind, space, simpleName(type), extend, space, "{");
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
        line(accessor(field.getModifiers()), name(field.getType()), space, field.getName(), ";");
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
        line();
        line("static {");
        indent(code::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor c, Code code) {
        String params = join(c.getParameters(), p -> {
            return name(p.getType()) + space + p.getName();
        }, ", ", "", "");

        line();
        line(accessor(c.getModifiers()), simpleName(c.getDeclaringClass()), "(", params, ")", space, "{");
        indent(code::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code code) {
        String params = join(method.getParameters(), p -> {
            return name(p.getType()) + space + p.getName();
        }, ", ", "", "");

        line();
        if (method.isSynthetic()) {
            line(name(method.getReturnType()), space, method.getName(), "()", space, "{");
        } else {
            line(accessor(method.getModifiers()), name(method.getReturnType()), space, method.getName(), "(", params, ")", space, "{");
        }
        indent(code::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Optional<Code> code) {
        if (code.isEmpty()) {
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
    public void writeLocalVariable(String name) {
        write(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariableDeclaration(Class type, String name) {
        write(name(type), space, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context) {
        if (imports.hierarchy.contains(field.getDeclaringClass())) {
            if (Modifier.isStatic(field.getModifiers())) {
                write(field.getName());
            } else {
                write("this.", field.getName());
            }
        } else {
            write(context, ".", field.getName());
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
        write("new", space, name(constructor.getDeclaringClass()), join("(", params, ", ", ")"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("super", join("(", params, ", ", ")"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<? extends Code> params) {
        write("this", join("(", params, ", ", ")"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<? extends Code> params) {
        if (method.isSynthetic()) {
            write(params.get(0), ".", method.getName(), "()");
        } else {
            write(context, ".", method.getName(), join("(", params, ", ", ")"));
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
        write("(", name(type), ")", space, code);
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
    public void writeIf(Code condition, Code then, Code elze) {
        line("if", space, "(", condition, ")", space, "{");
        indent(coder -> {
            write(then);
        });

        if (elze == null) {
            line("}");
        } else {
            line("}", space, "else", space, "{");
            indent(coder -> {
                write(elze);
            });
            line("}");
        }
    }

    /**
     * Qualified name.
     * 
     * @param type
     * @return
     */
    private String name(Class type) {
        return imports.name(type);
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
    private String accessor(int modifier) {
        StringJoiner joiner = new StringJoiner(space);

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

        if (Modifier.isStrict(modifier)) {
            joiner.add("strict");
        }
        if (Modifier.isTransient(modifier)) {
            joiner.add("transient");
        }
        if (Modifier.isVolatile(modifier)) {
            joiner.add("volatile");
        }

        String value = joiner.toString();

        if (value.isEmpty()) {
            return "";
        } else {
            return value.concat(" ");
        }
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
}
