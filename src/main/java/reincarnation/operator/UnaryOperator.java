/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.operator;

import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr.Operator;

/**
 * @version 2018/10/13 18:48:58
 */
public enum UnaryOperator {
    /** + */
    PLUS("+", Operator.PLUS),

    /** - */
    MINUS("-", Operator.MINUS),

    /** ! */
    LOGICAL_COMPLEMENT("!", Operator.LOGICAL_COMPLEMENT),

    /** ~ */
    BITWISE_COMPLEMENT("~", Operator.BITWISE_COMPLEMENT),

    /** -- */
    POST_DECREMENT("--", Operator.POSTFIX_DECREMENT),

    /** ++ */
    POST_INCREMENT("++", Operator.POSTFIX_INCREMENT),

    /** -- */
    PRE_DECREMENT("--", Operator.PREFIX_DECREMENT),

    /** ++ */
    PRE_INCREMENT("++", Operator.PREFIX_INCREMENT);

    /** The operator value. */
    private final String operator;

    /** The OP. */
    private final UnaryExpr.Operator op;

    /**
     * @param operator
     */
    private UnaryOperator(String operator, UnaryExpr.Operator op) {
        this.operator = operator;
        this.op = op;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return operator;
    }
}
