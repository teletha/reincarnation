/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

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
}