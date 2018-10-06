/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.util.Objects;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.Expression;

/**
 * Binary operation expression.
 * 
 * @version 2018/10/07 1:09:20
 */
public class OperandAssign extends Operand {

    /** The left value. */
    private final Operand left;

    /** The right value. */
    private final Operand right;

    /** The operator. */
    private final AssignOperation operator;

    /**
     * Create binary operation.
     * 
     * @param left A left value.
     * @param operator A operator.
     * @param right A right value.
     */
    public OperandAssign(Operand left, AssignOperation operator, Operand right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        this.operator = Objects.requireNonNull(operator);
        type = left.infer().type();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new AssignExpr(left.build(), right.build(), operator.op);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return build().toString();
    }

    /**
     * @version 2018/10/07 1:24:48
     */
    public enum AssignOperation {
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
        private final Operator op;

        /**
         * @param operator
         */
        private AssignOperation(String operator, Operator op) {
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
}
