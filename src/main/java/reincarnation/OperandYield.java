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

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

class OperandYield extends Operand {

    /** The statement. */
    private final Operand value;

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
}