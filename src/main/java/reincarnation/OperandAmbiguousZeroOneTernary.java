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

/**
 * @version 2014/01/01 12:11:14
 */
class OperandAmbiguousZeroOneTernary extends Operand {

    /** The condition. */
    private final OperandCondition condition;

    /**
     * @param condition
     */
    OperandAmbiguousZeroOneTernary(Operand condition) {
        this.condition = (OperandCondition) condition.disclose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand castActual(Class type) {
        if (type == boolean.class) {
            return condition;
        }

        // no cast
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return new InferredType(int.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return condition + "?1:0";
    }
}
