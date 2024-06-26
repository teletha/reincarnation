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

class OperandAmbiguousZeroOneTernary extends Operand {

    /** The condition. */
    private final OperandCondition condition;

    /**
     * @param condition
     */
    OperandAmbiguousZeroOneTernary(Operand condition) {
        this.condition = (OperandCondition) condition.disclose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (type.is(boolean.class)) {
            condition.write(coder);
        } else {
            coder.writeTernary(condition, new OperandNumber(1), new OperandNumber(0));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return condition.view() + "? 1 : 0";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandAmbiguousZeroOneTernary other) {
            return Objects.equals(condition, other.condition);
        } else {
            return false;
        }
    }
}