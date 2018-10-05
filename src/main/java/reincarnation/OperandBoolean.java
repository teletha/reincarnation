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

import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/06 1:31:36
 */
class OperandBoolean extends Operand {

    /** The actual value. */
    private final boolean value;

    /**
     * @param value
     */
    OperandBoolean(boolean value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return new InferredType(boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new BooleanLiteralExpr(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
