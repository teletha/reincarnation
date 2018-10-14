/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 9:56:07
 */
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeCast(type, value);
    }
}
