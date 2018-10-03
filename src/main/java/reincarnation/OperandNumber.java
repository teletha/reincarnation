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
import com.github.javaparser.ast.expr.IntegerLiteralExpr;

/**
 * @version 2013/08/15 16:07:23
 */
class OperandNumber extends Operand {

    /** The actual value of this operand. */
    final Number value;

    /**
     * 
     */
    OperandNumber(Number value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return new InferredType(Number.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Operand castActual(Class type) {
        if (type == char.class) {
            char ch = (char) value.intValue();

            if (Character.isSurrogate(ch) == false) {
                return new OperandString(String.valueOf(ch));
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new IntegerLiteralExpr(value.intValue());
    }
}
