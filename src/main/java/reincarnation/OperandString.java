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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.StringLiteralExpr;

/**
 * @version 2018/10/05 19:36:10
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
