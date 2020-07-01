/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/13 23:42:59
 */
class OperandExpression extends Operand {

    /** The actual string expression of this operand. */
    private Object expression;

    /** The inferred type. */
    private final InferredType type;

    /**
     * 
     */
    OperandExpression(Object expression) {
        this(expression, Object.class);
    }

    /**
     * 
     */
    OperandExpression(Object expression, Class type) {
        this(expression, new InferredType(type));
    }

    /**
     * 
     */
    OperandExpression(Object expression, InferredType type) {
        this.expression = expression;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand invert() {
        if (expression != null) {
            if (expression instanceof Operand) {
                ((Operand) expression).invert();
            } else {
                String value = expression.toString();

                if (value.charAt(0) == '(') {
                    expression = "!".concat(value);
                } else if (value.charAt(0) == '!') {
                    expression = value.substring(1);
                }
            }
        }

        // API definition
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        if (expression instanceof Operand) {
            return I.signal((Operand) expression);
        } else {
            return I.signal();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(expression);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (expression == null) {
            coder.writeNull();
        } else if (expression instanceof reincarnation.coder.Code) {
            ((reincarnation.coder.Code) expression).write(coder);
        } else {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error(expression.toString() + "  " + expression.getClass());
        }
    }
}