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

class OperandCast extends Operand {

    /** The actual value. */
    private final Operand value;

    /** The type to cast. */
    private final Class type;

    /**
     * Create cast code.
     * 
     * @param value
     * @param type
     */
    OperandCast(Operand value, Class type) {
        this.value = value;
        this.type = type;
        fix(type);

        encolose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (Inference.instanceOf(value.type.v, type)) {
            value.writeCode(coder);
        } else {
            coder.writeCast(type, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(value);
    }
}