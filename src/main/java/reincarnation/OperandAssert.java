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
import kiss.Variable;
import reincarnation.coder.Coder;

class OperandAssert extends Operand {

    /** The condition. */
    private final Operand condition;

    /** The message. */
    private final Variable<Operand> message;

    /**
     * Create assert code.
     * 
     * @param condition
     */
    OperandAssert(Operand condition, Variable<Operand> message) {
        this.condition = condition;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAssert(condition, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "assert " + condition.view();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean match(Operand obj) {
        if (obj instanceof OperandAssert other) {
            return match(condition, other.condition) && match(message.v, other.message.v);
        } else {
            return false;
        }
    }
}