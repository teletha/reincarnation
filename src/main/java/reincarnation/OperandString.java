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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.StringLiteralExpr;

/**
 * @version 2013/08/15 16:35:58
 */
class OperandString extends Operand {

    /** The actual string expression of this operand. */
    final String expression;

    /**
     * Create String operand.
     */
    OperandString(String expression) {
        this.expression = expression.replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\"", "\\\\\"")
                .replaceAll("\r", "\\\\r")
                .replaceAll("\n", "\\\\n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return new InferredType(String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "\"" + expression + "\"";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new StringLiteralExpr(expression);
    }
}
