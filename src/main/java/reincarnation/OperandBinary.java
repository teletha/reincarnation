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

import java.util.Objects;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.Expression;

import kiss.I;
import kiss.Signal;

/**
 * Binary operation expression.
 * 
 * @version 2018/10/07 1:09:20
 */
public class OperandBinary extends Operand {

    /** The left value. */
    private final Operand left;

    /** The right value. */
    private final Operand right;

    /** The operator. */
    private final BinaryOperator operator;

    /**
     * Create binary operation.
     * 
     * @param left A left value.
     * @param operator A operator.
     * @param right A right value.
     */
    public OperandBinary(Operand left, BinaryOperator operator, Operand right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        this.operator = Objects.requireNonNull(operator);
        type.set(left.infer().type());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new BinaryExpr(left.build(), right.build(), operator.op);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return I.signal(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }

    /**
     * @version 2018/10/07 1:24:48
     */
    public enum BinaryOperator {
        /** & */
        AND("&", Operator.BINARY_AND),

        /** | */
        OR("|", Operator.BINARY_OR),

        /** ^ */
        XOR("^", Operator.XOR),

        /** + */
        PLUS("+", Operator.PLUS),

        /** - */
        MINUS("-", Operator.MINUS),

        /** * */
        MULTIPLY("*", Operator.MULTIPLY),

        /** \/ */
        DIVIDE("/", Operator.DIVIDE),

        /** % */
        REMAINDER("%", Operator.REMAINDER),

        /** << */
        LEFT_SHIFT("<<", Operator.LEFT_SHIFT),

        /** >> */
        RIGHT_SHIFT(">>", Operator.SIGNED_RIGHT_SHIFT),

        /** >>> */
        UNSIGNED_RIGHT_SHIFT(">>>", Operator.UNSIGNED_RIGHT_SHIFT);

        /** The operator value. */
        private final String operator;

        /** The OP. */
        private final BinaryExpr.Operator op;

        /**
         * @param operator
         */
        private BinaryOperator(String operator, BinaryExpr.Operator op) {
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
