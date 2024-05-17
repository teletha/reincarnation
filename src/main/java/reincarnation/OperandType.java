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

import reincarnation.coder.Coder;

class OperandType extends Operand {

    /** The actual value. */
    private final Class value;

    /**
     * Type access like <code>java.lang.String</code>.
     * 
     * @param value
     */
    OperandType(Class value) {
        this.value = value;
        this.type.set(Class.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAccessType(value);
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
        return value.getSimpleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Operand obj) {
        if (obj instanceof OperandType other) {
            return match(value, other.value);
        } else {
            return false;
        }
    }
}