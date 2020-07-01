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
 * @version 2018/10/14 9:49:12
 */
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
    InferredType infer() {
        return new InferredType(int.class);
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