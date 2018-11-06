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
 * @version 2018/11/06 14:17:11
 */
public class OperandTernary extends OperandCondition {

    /** The codition. */
    private Operand condition;

    /** The left value. */
    private Operand left;

    /** The right value. */
    private Operand right;

    /**
     * 
     */
    OperandTernary(OperandCondition condition, Operand left, Operand right) {
        super(condition);

        this.condition = condition;
        this.left = left.disclose();
        this.right = right.disclose();

        if (left instanceof OperandCondition && right instanceof OperandCondition) {
            OperandCondition leftCondition = (OperandCondition) left;
            OperandCondition rightCondition = (OperandCondition) right;

            if (leftCondition.then == rightCondition.then && leftCondition.elze == rightCondition.elze) {
                this.then = leftCondition.then;
                this.elze = leftCondition.elze;
            }
        }
        bindTo(left).bindTo(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    OperandTernary invert() {
        condition.invert();
        left.invert();
        right.invert();

        // swap side
        Operand swap = left;
        left = right;
        right = swap;

        // API definition
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (isStatement()) {
            coder.writeIf(condition, c -> c.writeStatement(left), c -> c.writeStatement(right), null);
        } else {
            coder.writeTernary(condition, left, right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + "<" + left.info() + " and " + right.info() + ">";
    }
}
