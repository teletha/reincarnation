/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import kiss.I;
import kiss.Ⅱ;
import kiss.model.Model;
import reincarnation.LocalVariableDeclaration;
import reincarnation.Operand;
import reincarnation.Reincarnation;
import reincarnation.operator.AccessMode;
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
    protected Appendable appendable;

    /** The current indent size. */
    protected int indentSize = 0;

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
     * Create joinable code.
     * 
     * @param prefix The prefix.
     * @return A joinable code.
     */
    protected final <T> Join<T> join(String prefix, Collection<T> values, String separator, String suffix) {
        return new Join().prefix(prefix).add(values).separator(separator).suffix(suffix).ignoreEmpty(false);
    }

    /**
     * Write code.
     * 
     * @param codes
     */
    protected final void write(Object... codes) {
        try {
            for (Object code : codes) {
                if (code != null) {
                    if (code instanceof Code) {
                        ((Code) code).write(this);
                    } else if (code instanceof Optional) {
                        ((Optional) code).ifPresent(this::write);
                    } else {
                        appendable.append(String.valueOf(code));
                    }
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
     * Statement.
     * 
     * @param code
     */
    public abstract void writeStatement(Code<?> code);

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
     * Instanceof expression.
     * 
     * @param code A value to be checked.
     * @param type A type to check.
     */
    public abstract void writeInstanceof(Code code, Class type);

    /**
     * Access the local variable.
     * 
     * @param type A infered type of local variabel.
     * @param name A local variable name.
     * @param declaration Declaration type.
     */
    public abstract void writeLocalVariable(Class type, String name, LocalVariableDeclaration declaration);

    /**
     * Access to field.
     * 
     * @param field A field info.
     * @param context A field context.
     * @param mode A field access mode.
     */
    public abstract void writeAccessField(Field field, Code context, AccessMode mode);

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
     * @param mode An access mode.
     */
    public abstract void writeMethodCall(Method method, Code context, List<? extends Code> params, AccessMode mode);

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
     * Line comment.
     * 
     * @param comment A comment.
     */
    public abstract void writeLineComment(Object comment);

    /**
     * Throw statement.
     * 
     * @param code A value to be thrown.
     */
    public abstract void writeThrow(Code code);

    /**
     * Break statement.
     * 
     * @param label A label of break point.
     */
    public abstract void writeBreak(Optional<String> label);

    /**
     * Continue statement.
     * 
     * @param label A label of continue point.
     * @param omitLabel You can omit label or not.
     */
    public abstract void writeContinue(Optional<String> label, boolean omitLabel);

    /**
     * If statement.
     * 
     * @param condition A condition.
     * @param then A then part.
     * @param elze A else part. (maybe null)
     * @param follow A following part.
     */
    public abstract void writeIf(Code condition, Code then, Code elze, Code follow);

    /**
     * For statement.
     * 
     * @param label A block label.
     * @param initialize A initializer part.
     * @param condition A condition.
     * @param updater A updater part.
     * @param inner A inner contents.
     * @param follow A following part.
     */
    public abstract void writeFor(Optional<String> label, Code initialize, Code condition, Code updater, Runnable inner, Code follow);

    /**
     * While statement.
     * 
     * @param label A block label.
     * @param condition A condition.
     * @param inner A inner contents.
     * @param follow A following part.
     */
    public abstract void writeWhile(Optional<String> label, Code condition, Runnable inner, Code follow);

    /**
     * Do-While statement.
     * 
     * @param label A block label.
     * @param condition A condition.
     * @param inner A inner contents.
     * @param follow A following part.
     */
    public abstract void writeDoWhile(Optional<String> label, Code condition, Runnable inner, Code follow);

    /**
     * Infinit loop structure.
     * 
     * @param label A block label.
     * @param inner A inner contents.
     * @param follow A following part.
     */
    public abstract void writeInfinitLoop(Optional<String> label, Runnable inner, Code follow);

    /**
     * Try-Catch-Finally structure.
     * 
     * @param follow A following part.
     * @param label A block label.
     * @param inner A inner contents.
     */
    public abstract void writeTryCatchFinally(Code tryBlock, List<Ⅱ<Operand, Code>> catchBlocks, Code follow);
}
