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
import kiss.Variable;
import reincarnation.coder.Coder;

class OperandReturn extends Operand {

    /** The empty return for reuse. */
    public static final OperandReturn Empty = new OperandReturn(null);

    /** The statement. */
    final Variable<Operand> value;

    /**
     * Build return expression.
     * 
     * @param value A returned value, may be null.
     */
    public OperandReturn(Operand value) {
        this.value = value == null ? Variable.empty() : Variable.of(value.disclose());

        bindTo(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(value::get);
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
        coder.writeReturn(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " - " + value.map(v -> v.info()).v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "return " + value.map(Operand::view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandReturn other) {
            return Objects.equals(value, other.value);
        } else {
            return false;
        }
    }
}