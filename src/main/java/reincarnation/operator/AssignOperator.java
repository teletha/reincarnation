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

import com.github.javaparser.ast.expr.AssignExpr.Operator;

/**
 * @version 2018/10/07 1:24:48
 */
public enum AssignOperator {
    /** = */
    ASSIGN("=", Operator.ASSIGN),

    /** &= */
    AND("&=", Operator.BINARY_AND),

    /** |= */
    OR("|=", Operator.BINARY_OR),

    /** ^= */
    XOR("^=", Operator.XOR),

    /** += */
    PLUS("+=", Operator.PLUS),

    /** -= */
    MINUS("-=", Operator.MINUS),

    /** *= */
    MULTIPLY("*=", Operator.MULTIPLY),

    /** \/= */
    DIVIDE("/=", Operator.DIVIDE),

    /** %= */
    REMAINDER("%=", Operator.REMAINDER),

    /** <<= */
    LEFT_SHIFT("<<=", Operator.LEFT_SHIFT),

    /** >>= */
    RIGHT_SHIFT(">>=", Operator.SIGNED_RIGHT_SHIFT),

    /** >>>= */
    UNSIGNED_RIGHT_SHIFT(">>>=", Operator.UNSIGNED_RIGHT_SHIFT);

    /** The operator value. */
    private final String operator;

    /** The OP. */
    public final Operator op;

    /**
     * @param operator
     */
    private AssignOperator(String operator, Operator op) {
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