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

import reincarnation.coder.Coder;

class OperandArrayAccess extends Operand {

    /** The array value. */
    final Operand array;

    /** The index value. */
    final Operand index;

    /**
     * @param array
     * @param index
     */
    OperandArrayAccess(Operand array, Operand index) {
        this.array = array;
        this.index = index;

        fix(array.type.v instanceof Class clazz && clazz.isArray() ? clazz.getComponentType() : Object.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAccessArray(array, index);
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
        return array.view() + ".[" + index.view() + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandArrayAccess other) {
            return Objects.equals(array, other.array) && Objects.equals(index, other.index);
        } else {
            return false;
        }
    }
}