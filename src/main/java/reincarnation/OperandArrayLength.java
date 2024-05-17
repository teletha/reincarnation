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

class OperandArrayLength extends Operand {

    /** The array context. */
    final Operand owner;

    /**
     * @param owner
     */
    OperandArrayLength(Operand owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAccessArrayLength(owner);
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
        return owner.view() + ".length";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Operand obj) {
        if (obj instanceof OperandArrayLength other) {
            return match(owner, other.owner);
        } else {
            return false;
        }
    }
}