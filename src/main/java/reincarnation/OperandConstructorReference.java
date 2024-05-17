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

import java.lang.reflect.Constructor;
import java.util.Objects;

import reincarnation.coder.Coder;

class OperandConstructorReference extends Operand {

    /** The lambda body. */
    private final Constructor reference;

    /**
     * Create the operand for constructor reference.
     * 
     * @param reference
     */
    OperandConstructorReference(Constructor reference) {
        this.reference = reference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeConstructorReference(reference);
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
        return reference.getName() + "::new";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandConstructorReference other) {
            return Objects.equals(reference, other.reference);
        } else {
            return false;
        }
    }
}