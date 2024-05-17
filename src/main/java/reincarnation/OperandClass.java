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

class OperandClass extends Operand {

    /** The actual value. */
    private final Class value;

    /**
     * Class literal like <code>String.class</code>.
     * 
     * @param value
     */
    OperandClass(Class value) {
        this.value = value;
        type.set(Class.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeClass(value);
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
        return value.getSimpleName() + ".class";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Operand obj) {
        if (obj instanceof OperandClass other) {
            return match(value, other.value);
        } else {
            return false;
        }
    }
}