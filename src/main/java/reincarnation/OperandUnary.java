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
import reincarnation.operator.UnaryOperator;

/**
 * Binary operation expression.
 */
class OperandUnary extends Operand {

    /** The value. */
    public final Operand value;

    /** The operator. */
    public final UnaryOperator operator;

    /**
     * Create unary operation.
     * 
     * @param value A value.
     * @param operator A operator.
     */
    public OperandUnary(Operand value, UnaryOperator operator) {
        this.value = Objects.requireNonNull(value);
        this.operator = Objects.requireNonNull(operator);

        fix(value.type.v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeUnaryOperation(value, operator);
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
        return operator + "" + value.view();
    }
}