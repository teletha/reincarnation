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

import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;

/**
 * @version 2018/10/05 19:36:29
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
    Operand castActual(Class type) {
        if (type == boolean.class) {
            return condition;
        }

        // no cast
        return this;
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
    public String toString() {
        if (type.is(boolean.class)) {
            return condition.toString();
        } else {
            return condition + "? 1 : 0";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        if (type.is(boolean.class)) {
            return condition.build();
        } else {
            return new ConditionalExpr(condition.build(), new IntegerLiteralExpr(1), new IntegerLiteralExpr(0));
        }
    }
}
