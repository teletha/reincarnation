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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

import kiss.I;
import kiss.model.Model;
import reincarnation.Reincarnation;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

/**
 * @version 2018/10/13 11:03:28
 */
public abstract class Coder<O extends CodingOption> {

    /** The end of line. */
    protected static final String EoL = "\r\n";

    /** The token separator. */
    protected String space = " ";

    /** The actual writer. */
    private final Appendable appendable;

    /** The current indent size. */
    private int indentSize = 0;

    /** The coding options. */
    protected O options;

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
        this.options = I.make((Class<O>) Model.collectParameters(getClass(), Coder.class)[0]);
    }

    /**
     * Set options.
     * 
     * @param options
     */
    public final void config(O options) {
        this.options = Objects.requireNonNull(options);
    }

    /**
     * Config coding options.
     * 
     * @param options
     * @return
     */
    public final Coder config(Consumer<O> options) {
        if (options != null) {
            options.accept(this.options);
        }
        return this;
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
     * Create joinable code.
     * 
     * @param values The values to join.
     * @return A joinable code.
     */
    protected final Joiner join(Collection values) {
        return new Joiner().values(values);
    }

    /**
     * Create joinable code.
     * 
     * @param prefix The prefix.
     * @return A joinable code.
     */
    protected final Joiner prefix(String prefix) {
        return new Joiner().prefix(prefix);
    }

    /**
     * Create joinable code.
     * 
     * @param prefix The prefix.
     * @return A joinable code.
     */
    protected final Joiner join(String prefix, Collection values, String separator, String suffix) {
        return new Joiner().prefix(prefix).values(values, separator).suffix(suffix);
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
    public final void line(Object... codes) {
        if (codes.length != 0) {
            write(options.indentChar.repeat(indentSize));
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
     * Write code by AST.
     * 
     * @param reincarnation
     */
    public abstract void write(Reincarnation reincarnation);

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
     * @param code A return code, may be empty.
     */
    public abstract void writeReturn(Optional<Code> code);

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
     * Class literal.
     * 
     * @param code A class literal.
     */
    public abstract void writeClass(Class code);

    /**
     * This literal.
     */
    public abstract void writeThis();

    /**
     * Null literal.
     */
    public abstract void writeNull();

    /**
     * Enclosed expression.
     * 
     * @param code A inner code.
     */
    public final void writeEnclose(Code code) {
        writeEnclose(() -> {
            code.write(this);
        });
    }

    /**
     * Enclosed expression.
     * 
     * @param code A inner code.
     */
    public abstract void writeEnclose(Runnable code);

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
     * Access to field.
     * 
     * @param field A field info.
     * @param context A field context.
     */
    public abstract void writeAccessField(Field field, Code context);

    /**
     * Access to type.
     * 
     * @param type A type info.
     */
    public abstract void writeAccessType(Class type);

    /**
     * Access to array value by index.
     * 
     * @param array An array info.
     * @param index An index to access.
     */
    public abstract void writeAccessArray(Code array, Code index);

    /**
     * Access to array length.
     * 
     * @param array An array info.
     */
    public abstract void writeAccessArrayLength(Code array);

    /**
     * Constructor call expression.
     * 
     * @param constructor A constructor info.
     * @param params A list of parameter objects.
     */
    public abstract void writeConstructorCall(Constructor constructor, List<? extends Code> params);

    /**
     * Constructor call expression.
     * 
     * @param constructor A constructor info.
     * @param params A list of parameter objects.
     */
    public abstract void writeSuperConstructorCall(Constructor constructor, List<? extends Code> params);

    /**
     * Constructor call expression.
     * 
     * @param constructor A constructor info.
     * @param params A list of parameter objects.
     */
    public abstract void writeThisConstructorCall(Constructor constructor, List<? extends Code> params);

    /**
     * Method call expression.
     * 
     * @param method A method info.
     * @param context A context object.
     * @param params A list of parameter objects.
     */
    public abstract void writeMethodCall(Method method, Code context, List<? extends Code> params);

    /**
     * Create array expression.
     * 
     * @param type A type of array.
     * @param dimensions A list of dimension size.
     */
    public abstract void writeCreateArray(Class type, List<Code> dimensions);

    /**
     * Create array expression.
     * 
     * @param type A type of array.
     * @param dimensions A list of dimension size.
     * @param initialValues A list of initial values.
     */
    public abstract void writeCreateArray(Class type, List<Code> dimensions, List<Code> initialValues);

    /**
     * Ternary condition expression.
     * 
     * @param condition A condition.
     * @param then A then part.
     * @param elze A else part.
     */
    public abstract void writeTernary(Code condition, Code then, Code elze);

    /**
     * Cast expression.
     * 
     * @param type A type to cast.
     * @param code A value to be casted.
     */
    public abstract void writeCast(Class type, Code code);

    /**
     * Throw statement.
     * 
     * @param code A value to be thrown.
     */
    public abstract void writeThrow(Code code);

    /**
     * If statement.
     * 
     * @param condition A condition.
     * @param then A then part.
     * @param elze A else part. (maybe null)
     */
    public abstract void writeIf(Code condition, Code then, Code elze);

}
