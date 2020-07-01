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

import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 10:25:56
 */
class OperandArrayAccess extends Operand {

    /** The array value. */
    private final Operand array;

    /** The index value. */
    private final Operand index;

    /**
     * @param array
     * @param index
     */
    OperandArrayAccess(Operand array, Operand index) {
        this.array = array;
        this.index = index;

        fix(array.type.v.getComponentType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAccessArray(array, index);
    }
}