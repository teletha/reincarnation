/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2014/06/22 14:02:22
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
    public String toString() {
        return condition + "?" + left + ":" + right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ConditionalExpr(condition.build(), left.build(), right.build());
    }
}
