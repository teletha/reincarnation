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

class OperandInstanceOf extends Operand {

    /** The value. */
    final Operand value;

    /** The type to check. */
    final Class type;

    /** The casted variable. */
    private Operand cast;

    /**
     * Build instanceof expression.
     * 
     * @param value A value to check.
     * @param type A type to check.
     */
    OperandInstanceOf(Operand value, Class type) {
        this.value = value;
        this.type = type;
        fix(boolean.class);
    }

    /**
     * Set the variable for cast.
     * 
     * @param op
     * @return
     */
    OperandInstanceOf withCast(Operand op) {
        this.cast = op;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand invert() {
        return new OperandBinary(this, BinaryOperator.EQUAL, OperandBoolean.False);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return value == null ? I.signal() : I.signal(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeInstanceof(value, type, cast);
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
        return value.view() + " instanceof " + type.getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandInstanceOf other) {
            return Objects.equals(value, other.value) && Objects.equals(type, other.type) && Objects.equals(cast, other.cast);
        } else {
            return false;
        }
    }
}