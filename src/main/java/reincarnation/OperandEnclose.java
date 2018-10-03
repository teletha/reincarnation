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

import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2013/08/03 2:18:38
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
