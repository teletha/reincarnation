/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import kiss.I;
import kiss.Model;
import kiss.Variable;
import kiss.Ⅲ;
import reincarnation.Reincarnation;
import reincarnation.operator.AccessMode;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;
import reincarnation.util.MultiMap;

public abstract class Coder<O extends CodingOption> {

    /** The end of line. */
    protected static final String EoL = "\r\n";

    /** The token separator. */
    protected final String space = " ";

    /** The actual writer. */
    private StringBuilder builder;

    /** The current indent size. */
    private final AtomicInteger indentSize;

    /** The lazy writers. */
    private final Deque<Ⅲ<Integer, Integer, Runnable>> lazy = new ArrayDeque();

    /** The snapshot state. */
    private final LinkedList<Integer> snapshots = new LinkedList();

    /** The coding options. */
    protected O options = I.make((Class<O>) Model.collectParameters(getClass(), Coder.class)[0]);

    /**
     * Create {@link Coder}.
     */
    protected Coder() {
        this.builder = new StringBuilder();
        this.indentSize = new AtomicInteger(0);
    }

    /**
     * For delegation.
     * 
     * @param original
     */
    protected Coder(Coder original) {
        this.builder = original.builder;
        this.indentSize = original.indentSize;
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
        return new Join().prefix(prefix).add(values).separator(separator).suffix(suffix);
    }

    /**
     * Create the cascading strategy.
     * 
     * @return
     */
    protected final Naming naming(Object original) {
        Naming base = Naming.Incremental;

        for (Object o : new Object[] {options.variableNaming, original, Naming.Incremental}) {
            if (o instanceof Naming naming) {
                base = base.then(naming);
            }
        }

        return base;
    }

    /**
     * Write code.
     * 
     * @param codes
     */
    public final void write(Object... codes) {
        for (Object code : codes) {
            if (code != null) {
                if (code instanceof Code) {
                    ((Code) code).write(this);
                } else if (code instanceof Optional) {
                    ((Optional) code).ifPresent(this::write);
                } else {
                    builder.append(String.valueOf(code));
                }
            }
        }
    }

    /**
     * Write code with line.
     * 
     * @param codes
     */
    protected final void line(Object... codes) {
        if (codes.length != 0) {
            write(options.indentChar.repeat(indentSize.get()));
            write(codes);
        }
        write(EoL);
    }

    /**
     * Write code with line without head indent.
     * 
     * @param codes
     */
    protected final void lineNI(Object... codes) {
        if (codes.length != 0) {
            write(codes);
        }
        write(EoL);
    }

    /**
     * Write code with line without tail break.
     * 
     * @param codes
     */
    protected final void lineNB(Object... codes) {
        if (codes.length != 0) {
            write(options.indentChar.repeat(indentSize.get()));
            write(codes);
        }
    }

    /**
     * Write indent.
     * 
     * @param inner
     */
    protected final void indent(Runnable inner) {
        indent(coder -> inner.run());
    }

    /**
     * Write indent.
     * 
     * @param inner
     */
    protected final void indent(Consumer<Coder> inner) {
        indentSize.incrementAndGet();
        inner.accept(this);
        indentSize.decrementAndGet();
    }

    /**
     * Perform lazy code writing.
     * 
     * @param writer
     */
    protected final void writeLazy(Runnable writer) {
        lazy.addLast(I.pair(builder.length(), indentSize.get(), writer));
    }

    /**
     * Create a new snapshot point to revert.
     * 
     * @param writer
     */
    protected final void snapshot(Runnable writer) {
        // create new snapshot
        snapshots.addFirst(-builder.length());

        try {
            writer.run();
        } finally {
            int snapshot = snapshots.pollFirst();

            // revert to the latest snapshot
            if (0 < snapshot) {
                builder.delete(snapshot, builder.length());
            }
        }
    }

    /**
     * Revert to the latest snapshot.
     */
    protected final void revert() {
        if (!snapshots.isEmpty()) {
            int point = snapshots.peekFirst();
            if (point < 0) {
                snapshots.set(0, -point);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder writer = builder;

        Iterator<Ⅲ<Integer, Integer, Runnable>> iterator = lazy.descendingIterator();
        while (iterator.hasNext()) {
            Ⅲ<Integer, Integer, Runnable> x = iterator.next();
            builder = new StringBuilder();
            indentSize.set(x.ⅱ);

            x.ⅲ.run();
            writer.insert(x.ⅰ, builder);
        }
        return writer.toString();
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
    public abstract void writeConstructor(Constructor constructor, Code<Code> code);

    /**
     * Method declaration.
     * 
     * @param method
     */
    public abstract void writeMethod(Method method, Code<Code> code);

    /**
     * Lambda.
     * 
     * @param method
     * @param contexts
     */
    public abstract void writeLambda(Method method, List<Code> contexts, Code code);

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
    public abstract void writeReturn(Variable<Code> code);

    /**
     * Yield expression.
     * 
     * @param code A yield code.
     */
    public abstract void writeYield(Code code);

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
     * String concatenation.
     * 
     * @param code A list of values.
     */
    public abstract void writeStringConcatenation(Iterator<Code> code);

    /**
     * Block text.
     * 
     * @param code A line of text block.
     */
    public abstract void writeTextBlock(List<String> code);

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
     * Shorthand for "condition == true" binary operation expression.
     * 
     * @param code A conditional code.
     */
    public abstract void writePositiveOperation(Code code);

    /**
     * Shorthand for "condition == false" binary operation expression.
     * 
     * @param code A conditional code.
     */
    public abstract void writeNegativeOperation(Code code);

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
     * @param cast A casting code.
     */
    public abstract void writeInstanceof(Code code, Class type, Code cast);

    /**
     * Access the local variable.
     * 
     * @param type A infered type of local variabel.
     * @param index A local variable index.
     * @param name A local variable name.
     */
    public abstract void writeLocalVariable(Type type, int index, String name);

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
    public abstract void writeConstructorCall(Constructor constructor, List<Code> params);

    /**
     * Constructor call expression.
     * 
     * @param constructor A constructor info.
     * @param params A list of parameter objects.
     */
    public abstract void writeSuperConstructorCall(Constructor constructor, List<Code> params);

    /**
     * Constructor call expression.
     * 
     * @param constructor A constructor info.
     * @param params A list of parameter objects.
     */
    public abstract void writeThisConstructorCall(Constructor constructor, List<Code> params);

    /**
     * Method call expression.
     * 
     * @param method A method info.
     * @param context A context object.
     * @param params A list of parameter objects.
     * @param mode An access mode.
     */
    public abstract void writeMethodCall(Method method, Code context, List<Code> params, AccessMode mode);

    /**
     * Method reference.
     * 
     * @param method A method info.
     * @param context A invocation context.
     */
    public abstract void writeMethodReference(Method method, Code context);

    /**
     * Method reference.
     * 
     * @param method A method info.
     */
    public abstract void writeStaticMethodReference(Method method);

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
     * Assert statement.
     * 
     * @param code A value to be assertion.
     * @param message A message.
     */
    public abstract void writeAssert(Code code, Variable<Code> message);

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
     * @param omitLabel You can omit label or not.
     */
    public abstract void writeBreak(Optional<String> label, boolean omitLabel);

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
     * Enhanced for statement.
     * 
     * @param label A block label.
     * @param variable A variable part.
     * @param iterable A iterable part.
     * @param inner A inner contents.
     * @param follow A following part.
     */
    public abstract void writeIterableFor(Optional<String> label, Code variable, Code iterable, Runnable inner, Code follow);

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
     */
    public abstract void writeTryCatchFinally(Code tryBlock, List<Ⅲ<Class, String, Code>> catchBlocks, Code follow);

    /**
     * Writes switch statement.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param label An optional label for the block.
     * @param condition The condition to switch on.
     * @param type The type of the condition.
     * @param caseBlocks A map of cases and their associated code blocks.
     * @param follow The code block to execute after the switch statement.
     */
    public final void writeSwitch(boolean statement, Optional<String> label, Code condition, Class type, MultiMap<Code, Object> caseBlocks, Code follow) {
        writeSwitch(statement, label, condition, type, () -> {
            if (Enum.class.isAssignableFrom(type)) {
                caseBlocks.forEach((code, keys) -> {
                    if (keys.isEmpty()) {
                        writeDefaultCase(statement, code);
                    } else {
                        writeEnumCase(statement, type, keys.stream().map(x -> (Enum) type.getEnumConstants()[(int) x - 1]).toList(), code);
                    }
                });
            } else if (type == char.class) {
                caseBlocks.forEach((code, keys) -> {
                    if (keys.isEmpty()) {
                        writeDefaultCase(statement, code);
                    } else {
                        writeCharCase(statement, keys.stream().map(x -> (char) (int) x).toList(), code);
                    }
                });
            } else if (type == String.class) {
                caseBlocks.forEach((code, keys) -> {
                    if (keys.isEmpty()) {
                        writeDefaultCase(statement, code);
                    } else {
                        writeStringCase(statement, keys.stream().map(String::valueOf).toList(), code);
                    }
                });
            } else {
                caseBlocks.forEach((code, keys) -> {
                    if (keys.isEmpty()) {
                        writeDefaultCase(statement, code);
                    } else {
                        writeIntCase(statement, keys.stream().map(x -> (Integer) x).toList(), code);
                    }
                });
            }
        }, follow);
    }

    /**
     * Write switch statement.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param label An optional label for the switch block, empty means expression mode.
     * @param condition The condition to switch on.
     * @param type The type of the condition.
     * @param caseProcess A internal process for case and default blocks.
     * @param follow The code block to execute after the switch statement.
     */
    protected abstract void writeSwitch(boolean statement, Optional<String> label, Code condition, Class type, Runnable caseProcess, Code follow);

    /**
     * Write case block for integral value.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param values A key value.
     * @param caseBlock A code for case block.
     */
    protected abstract void writeIntCase(boolean statement, List<Integer> values, Code caseBlock);

    /**
     * Write case block for char value.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param values A key value.
     * @param caseBlock A code for case block.
     */
    protected abstract void writeCharCase(boolean statement, List<Character> values, Code caseBlock);

    /**
     * Write case block for enum value.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param values A key value.
     * @param caseBlock A code for case block.
     */
    protected abstract <E extends Enum> void writeEnumCase(boolean statement, Class<E> type, List<E> values, Code caseBlock);

    /**
     * Write case block for string value.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param values A key value.
     * @param caseBlock A code for case block.
     */
    protected abstract void writeStringCase(boolean statement, List<String> values, Code caseBlock);

    /**
     * Write case block for default value.
     * 
     * @param statement True means statement mode, False means expression mode.
     * @param defaultBlock A code for default block.
     */
    protected abstract void writeDefaultCase(boolean statement, Code defaultBlock);
}