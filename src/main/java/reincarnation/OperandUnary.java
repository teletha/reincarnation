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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr.Operator;

import kiss.I;
import kiss.Signal;

/**
 * Binary operation expression.
 * 
 * @version 2018/10/07 1:09:20
 */
public class OperandUnary extends Operand {

    /** The value. */
    private final Operand value;

    /** The operator. */
    private final UnaryOperator operator;

    /**
     * Create unary operation.
     * 
     * @param value A value.
     * @param operator A operator.
     */
    public OperandUnary(Operand value, UnaryOperator operator) {
        this.value = Objects.requireNonNull(value);
        this.operator = Objects.requireNonNull(operator);

        fix(value.type.v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new UnaryExpr(value.build(), operator.op);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return I.signal(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        switch (operator) {
        case POST_DECREMENT:
        case POST_INCREMENT:
            return value.toString() + operator;

        default:
            return operator.toString() + value;
        }
    }

    /**
     * @version 2018/10/07 1:24:48
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
}
