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
    protected String type() {
        return value.type() + " in " + super.type();
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
    protected Signal<Operand> children() {
        return I.signal(value);
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
}
