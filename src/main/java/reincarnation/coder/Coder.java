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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

import kiss.I;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

/**
 * @version 2018/10/13 11:03:28
 */
public abstract class Coder {

    /** The end of line. */
    protected static final String EoL = "\r\n";

    /** The token separator. */
    protected String space = " ";

    /** The actual writer. */
    private final Appendable appendable;

    /** The indent character. */
    private String indentChar = "    ";

    /** The current indent size. */
    private int indentSize = 0;

    /**
     * Create {@link Coder}.
     */
    protected Coder() {
        this(new StringBuilder());
    }

    /**
     * Create {@link Coder}.
     * 
     * @param appendable
     */
    protected Coder(Appendable appendable) {
        this.appendable = appendable;
    }

    /**
     * Join by space.
     * 
     * @param values
     * @param converter
     * @return
     */
    protected final <T> String join(T[] values, Function<T, String> converter) {
        return join(values, converter, space, "", "");
    }

    /**
     * Join by space.
     * 
     * @param values
     * @param converter
     * @return
     */
    protected final <T> String join(Collection<T> values, Function<T, String> converter) {
        return join(values, converter, space, "", "");
    }

    /**
     * Join by space.
     * 
     * @param values
     * @param converter
     * @return
     */
    protected final <T> String join(T[] values, Function<T, String> converter, String separator, String prefix, String suffix) {
        return join(Arrays.asList(values), converter, separator, prefix, suffix);
    }

    /**
     * Join by space.
     * 
     * @param values
     * @param converter
     * @return
     */
    protected final <T> String join(Collection<T> values, Function<T, String> converter, String separator, String prefix, String suffix) {
        if (values.isEmpty()) {
            return "";
        }

        StringJoiner joiner = new StringJoiner(separator, prefix, suffix);

        for (T value : values) {
            joiner.add(converter.apply(value));
        }
        return joiner.toString();
    }

    /**
     * Write code.
     * 
     * @param codes
     */
    protected final void write(Object... codes) {
        try {
            for (Object code : codes) {
                if (code instanceof Code) {
                    ((Code) code).write(this);
                } else {
                    appendable.append(String.valueOf(code));
                }
            }
        } catch (IOException e) {
            throw I.quiet(e);
        }
    }

    /**
     * Write code with line.
     * 
     * @param codes
     */
    protected final void line(Object... codes) {
        if (codes.length != 0) {
            write(indentChar.repeat(indentSize));
            write(codes);
        }
        write(EoL);
    }

    /**
     * Write indent.
     * 
     * @param inner
     */
    protected final void indent(Runnable inner) {
        indentSize++;
        inner.run();
        indentSize--;
    }

    /**
     * Write indent.
     * 
     * @param inner
     */
    protected final void indent(Consumer<Coder> inner) {
        indentSize++;
        inner.accept(this);
        indentSize--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return appendable.toString();
    }

    /**
     * Package declaration.
     * 
     * @param info
     */
    public abstract void writePackage(Package info);

    /**
     * Import declaration.
     * 
     * @param classes
     */
    public abstract void writeImport(Set<Class> classes);

    /**
     * Type declaration.
     * 
     * @param type
     * @param inner
     */
    public abstract void writeType(Class type, Runnable inner);

    /**
     * Field declaration.
     * 
     * @param field
     */
    public abstract void writeField(Field field);

    /**
     * Static field declaration.
     * 
     * @param field
     */
    public abstract void writeStaticField(Field field);

    /**
     * Initializer declaration.
     * 
     * @param code
     */
    public abstract void writeInitializer(Code code);

    /**
     * Static initializer declaration.
     * 
     * @param code
     */
    public abstract void writeStaticInitializer(Code code);

    /**
     * Constructor declaration.
     * 
     * @param constructor
     */
    public abstract void writeConstructor(Constructor constructor, Code code);

    /**
     * Method declaration.
     * 
     * @param method
     */
    public abstract void writeMethod(Method method, Code code);

    /**
     * Return expression.
     * 
     * @param code
     */
    public abstract void writeReturn(Code code);

    /**
     * Boolean literal.
     * 
     * @param code A boolean literal.
     */
    public abstract void writeBoolean(boolean code);

    /**
     * Character literal.
     * 
     * @param code A char literal.
     */
    public abstract void writeChar(char code);

    /**
     * Integer literal.
     * 
     * @param code A int literal.
     */
    public abstract void writeInt(int code);

    /**
     * Long literal.
     * 
     * @param code A long literal.
     */
    public abstract void writeLong(long code);

    /**
     * Float literal.
     * 
     * @param code A float literal.
     */
    public abstract void writeFloat(float code);

    /**
     * Double literal.
     * 
     * @param code A double literal.
     */
    public abstract void writeDouble(double code);

    /**
     * String literal.
     * 
     * @param code A string literal.
     */
    public abstract void writeString(String code);

    /**
     * This literal.
     * 
     * @param code A this literal.
     */
    public abstract void writeThis();

    /**
     * Enclosed expression.
     * 
     * @param code A inner code.
     */
    public abstract void writeEnclose(Code code);

    /**
     * Assign operation expression.
     * 
     * @param left A inner code.
     * @param operator A operator.
     * @param right A inner code.
     */
    public abstract void writeAssignOperation(Code left, AssignOperator operator, Code right);

    /**
     * Binary operation expression.
     * 
     * @param left A inner code.
     * @param operator A operator.
     * @param right A inner code.
     */
    public abstract void writeBinaryOperation(Code left, BinaryOperator operator, Code right);

    /**
     * Unary operation expression.
     * 
     * @param code A inner code.
     * @param operator A operator.
     */
    public abstract void writeUnaryOperation(Code code, UnaryOperator operator);

    /**
     * Access to the local variable.
     * 
     * @param name A local variable name.
     */
    public abstract void writeLocalVariable(String name);

    /**
     * Declare the local variable.
     * 
     * @param type A infered type of local variabel.
     * @param name A local variable name.
     */
    public abstract void writeLocalVariableDeclaration(Class type, String name);

    /**
     * Statement declaration.
     * 
     * @param code A inner code.
     */
    public abstract void writeStatement(Code code);
}
