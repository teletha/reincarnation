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

import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 2:00:50
 */
public class OperandClass extends Operand {

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
    public void write(Coder coder) {
        coder.writeClass(value);
    }
}
