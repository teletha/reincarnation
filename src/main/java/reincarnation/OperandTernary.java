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

/**
 * @version 2015/08/10 13:26:14
 */
class OperandTernary extends OperandCondition {

    /** The codition. */
    private Operand condition;

    /** The left value. */
    private Operand left;

    /** The right value. */
    private Operand right;

    /** The inferred type. */
    private InferredType type;

    /**
     * 
     */
    OperandTernary(OperandCondition condition, Operand left, Operand right) {
        super(condition);

        this.condition = condition;
        this.left = left.disclose();
        this.right = right.disclose();
        this.type = new InferredType(right, left);

        // if (left instanceof OperandCondition && right instanceof OperandCondition) {
        // OperandCondition leftCondition = (OperandCondition) left;
        // OperandCondition rightCondition = (OperandCondition) right;
        //
        // if (leftCondition.then == rightCondition.then && leftCondition.elze ==
        // rightCondition.elze) {
        // condition.invert();
        // }
        // }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    OperandTernary invert() {
        condition.invert();

        // API definition
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand castActual(Class type) {
        if (type == char.class && this.type.type() == Number.class) {
            return new OperandExpression("String.fromCharCode(" + this + ")", type);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return type;
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
