/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.BinaryOperator;

public class OperandInstanceOf extends Operand {

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
    public OperandInstanceOf(Operand value, Class type) {
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
    public OperandInstanceOf withCast(Operand op) {
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
}