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

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/13 17:54:19
 */
class OperandEnclose extends Operand {

    /** The value operand. */
    final Operand value;

    /**
     * @param value
     */
    OperandEnclose(Operand value) {
        this.value = value;

        bindTo(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return value.infer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand invert() {
        value.invert();

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand encolose() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand disclose() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Operand> Variable<T> as(Class<T> type) {
        return I.signal(value).as(type).to();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return I.signal(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatement() {
        return value.isStatement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeEnclose(() -> {
            value.write(coder);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " - " + value.info();
    }
}
