/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.List;
import java.util.function.Function;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.java.JavaCoder;

/**
 * @version 2018/10/22 19:00:10
 */
abstract class Operand implements Code {

    protected static final Operand Null = new OperandExpression(null).fix(null);

    /** The infered type. */
    protected Variable<Class> type = Variable.of(Object.class);

    /** The flag for operand duplication. */
    boolean duplicated = false;

    /**
     * Fix as the current type.
     * 
     * @return Chainable API.
     */
    protected final Operand fix() {
        return fix(type.v);
    }

    /**
     * Fix as the specified type.
     * 
     * @param type A type to fix.
     * @return Chainable API.
     */
    protected final Operand fix(Class type) {
        this.type.let(type);

        return this;
    }

    /**
     * Check type inference state.
     * 
     * @return
     */
    protected final boolean isFixed() {
        return this.type.isFixed();
    }

    /**
     * Bind infered type.
     * 
     * @param other
     */
    protected final Operand bindTo(Operand other) {
        if (isFixed()) {
            if (other.isFixed()) {
                // do nothing
            } else {
                other.fix(type.v);
            }
        } else {
            if (other.isFixed()) {
                fix(other.type.v);
            } else {
                type.observeNow().skip(Object.class).to(other.type::set);
            }
        }
        return this;
    }

    protected boolean isTrue() {
        return false;
    }

    protected boolean isFalse() {
        return false;
    }

    /**
     * <p>
     * Invert operand value if we can.
     * </p>
     * 
     * @return A inverted operand.
     */
    Operand invert() {
        return this;
    }

    int computeMultiplicity() {
        return 1;
    }

    /**
     * <p>
     * Enclose this operand.
     * </p>
     * 
     * @return A disclosed operand.
     */
    Operand encolose() {
        return new OperandEnclose(this);
    }

    /**
     * <p>
     * Disclose the outmost parenthesis if we can.
     * </p>
     * 
     * @return A disclosed operand.
     */
    Operand disclose() {
        return this;
    }

    /**
     * <p>
     * Infer the type of this {@link Operand}.
     * </p>
     * 
     * @return
     */
    InferredType infer() {
        return new InferredType(type.v);
    }

    /**
     * @return
     */
    boolean isLarge() {
        return infer().type() == long.class || infer().type() == double.class;
    }

    Expression build() {
        // If this exception will be thrown, it is bug of this program. So we must rethrow the
        // wrapped error in here.
        throw new Error();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        // If this exception will be thrown, it is bug of this program. So we must rethrow the
        // wrapped error in here.
        throw new Error(getClass() + " must implement write method!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        JavaCoder coder = new JavaCoder();
        write(coder);
        return coder.toString();
    }

    /**
     * Find all typed {@link Operand}s.
     * 
     * @param type
     * @return
     */
    public final <T extends Operand> Signal<T> find(Class<T> type) {
        if (type == getClass()) {
            return I.signal((T) this);
        } else {
            return children().flatMap(o -> o.find(type));
        }
    }

    /**
     * Collect all child {@link Operand}s.
     * 
     * @return
     */
    protected Signal<Operand> children() {
        // If this exception will be thrown, it is bug of this program. So we must rethrow the
        // wrapped error in here.
        throw new Error("Implemetn #children at " + getClass().getName());
    }

    /**
     * Converter.
     * 
     * @param operands
     * @return
     */
    protected final NodeList<Expression> list(List<Operand> operands) {
        return list(operands, Operand::build);
    }

    /**
     * Converter.
     * 
     * @param operands
     * @return
     */
    protected final <N extends com.github.javaparser.ast.Node> NodeList<N> list(List<Operand> operands, Function<Operand, N> converter) {
        NodeList<N> list = new NodeList();

        for (Operand operand : operands) {
            list.add(converter.apply(operand));
        }
        return list;
    }
}
