/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.StringJoiner;

import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

/**
 * @version 2018/10/13 11:05:47
 */
public class JavaCoder extends Coder {

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
            line();
            for (Class clazz : classes) {
                line("import", space, clazz.getName(), ";");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticField(Field field) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInitializer(Code codable) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticInitializer(Code codable) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor constructor, Code codable) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code codable) {
        String params = join(method.getParameters(), p -> {
            return name(p.getType()) + space + p.getName();
        }, ", ", "", "");

        line();
        line(accessor(method.getModifiers()), name(method.getReturnType()), space, method.getName(), "(", params, ")", space, "{");
        indent(codable::write);
        line("}");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Code code) {
        line("return ", code, ";");
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
    public void writeThis() {
        write("this");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeEnclose(Code code) {
        write("(", code, ")");
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
    public void writeStatement(Code code) {
        line(code, ";");
    }

    /**
     * Qualified name.
     * 
     * @param type
     * @return
     */
    private String name(Class type) {
        return type.getCanonicalName();
    }

    /**
     * Simple name.
     * 
     * @param type
     * @return
     */
    private String simpleName(Class type) {
        if (type.isAnonymousClass()) {
            String name = type.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        } else {
            return type.getSimpleName();
        }
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
}
