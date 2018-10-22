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
 * @version 2018/10/22 19:10:58
 */
class OperandTernaryCondition extends OperandCondition {

    /** The condition operand. */
    private final OperandCondition condition;

    /**
     * @param left
     * @param right
     */
    protected OperandTernaryCondition(OperandCondition condition, OperandCondition left, OperandCondition right) {
        super(left, right);

        this.condition = condition;

        if (left.then == right.then && left.elze == right.elze) {
            condition.invert();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeTernary(condition, left, right);
    }
}
