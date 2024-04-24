/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.BinaryOperator;

/**
 * Binary operation expression.
 */
class OperandBinary extends Operand {

    /** The left value. */
    final Operand left;

    /** The right value. */
    final Operand right;

    /** The operator. */
    final BinaryOperator operator;

    /**
     * Create binary operation.
     * 
     * @param left A left value.
     * @param operator A operator.
     * @param right A right value.
     */
    OperandBinary(Operand left, BinaryOperator operator, Operand right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        this.operator = Objects.requireNonNull(operator);
        type.set(left.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeBinaryOperation(left, operator, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return left.view() + " " + operator + " " + right.view();
    }
}