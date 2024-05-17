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

class OperandThrow extends Operand {

    /** The statement. */
    private final Operand value;

    /**
     * Build throw expression.
     * 
     * @param value A thrown value.
     */
    OperandThrow(Operand value) {
        this.value = Objects.requireNonNull(value);
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
    public boolean isStatement() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeThrow(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "throw " + value.view();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Operand obj) {
        if (obj instanceof OperandThrow other) {
            return match(value, other.value);
        } else {
            return false;
        }
    }
}