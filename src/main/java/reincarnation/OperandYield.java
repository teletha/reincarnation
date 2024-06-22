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

class OperandYield extends Operand {

    /** The statement. */
    final Operand value;

    /**
     * Build return expression.
     * 
     * @param value A returned value, may be null.
     */
    public OperandYield(Operand value) {
        this.value = value.disclose();

        bindTo(value);
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
    protected boolean isValue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeYield(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " - " + value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "yield " + value.view();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandYield other) {
            return Objects.equals(value, other.value);
        } else {
            return false;
        }
    }
}