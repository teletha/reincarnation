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

/**
 * An operand that represents a type cast operation.
 */
class OperandCast extends Operand {

    /** The actual value to be cast. */
    private final Operand value;

    /** The type to cast to. */
    private final Class type;

    /** Whether the cast is necessary or not. */
    private final boolean needCast;

    /**
     * Constructs a new {@code OperandCast} instance that represents a cast to the specified type.
     * 
     * @param value The operand to be cast.
     * @param type The type to cast to.
     */
    OperandCast(Operand value, Class type) {
        this.value = value;
        this.type = type;
        fix(type);
        this.needCast = !Inference.instanceOf(value.type.v, type);

        if (needCast) {
            encolose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (needCast) {
            coder.writeCast(type, value);
        } else {
            value.writeCode(coder);
        }
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
    protected boolean isValue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "(" + type.getSimpleName() + ") " + value.view();
    }
}