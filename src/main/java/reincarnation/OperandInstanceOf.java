/*
 * Copyright (C) 2020 Reincarnation Development Team
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
 * @version 2018/10/23 15:03:32
 */
public class OperandInstanceOf extends Operand {

    /** The value. */
    private final Operand value;

    /** The type to check. */
    private final Class type;

    /**
     * Build instanceof expression.
     * 
     * @param value A value to check.
     * @param type A type to check.
     */
    public OperandInstanceOf(Operand value, Class type) {
        this.value = value;
        this.type = type;
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
        coder.writeInstanceof(value, type);
    }
}