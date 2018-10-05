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

import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/05 19:36:22
 */
class OperandEnclose extends Operand {

    /** The value operand. */
    final Operand value;

    /**
     * @param value
     */
    OperandEnclose(Operand value) {
        this.value = value;
    }

    /**
     * @param value
     */
    OperandEnclose(Expression value) {
        this.value = new OperandExpression(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand castActual(Class type) {
        return value.cast(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return value.infer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand invert() {
        value.invert();

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand disclose() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + value + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new EnclosedExpr(value.build());
    }
}
