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

import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;

/**
 * @version 2018/10/05 19:36:00
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
        return new InferredType(value.getClass());
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
        } else if (type == boolean.class) {
            if (value.intValue() == 0) {
                return new OperandBoolean(false);
            } else if (value.intValue() == 1) {
                return new OperandBoolean(true);
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isTrue() {
        return value.intValue() == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFalse() {
        return value.intValue() == 0;
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
        if (type.is(boolean.class)) {
            if (value.intValue() == 0) {
                return new BooleanLiteralExpr(false);
            } else {
                return new BooleanLiteralExpr(true);
            }
        }

        if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
            return new IntegerLiteralExpr(value.intValue());
        } else if (value instanceof Long) {
            return new LongLiteralExpr(value + "L");
        } else if (value instanceof Float) {
            return new DoubleLiteralExpr(value + "F");
        } else if (value instanceof Double) {
            return new DoubleLiteralExpr(value + "D");
        } else {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error("[" + value + "] is illegal number.");
        }
    }
}
