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

import reincarnation.coder.Coder;

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

    // /**
    // * {@inheritDoc}
    // */
    // @Override
    // protected Operand castActual(Class type) {
    // if (type == char.class) {
    // char ch = (char) value.intValue();
    //
    // if (Character.isSurrogate(ch) == false) {
    // return new OperandString(String.valueOf(ch));
    // }
    // } else if (type == boolean.class) {
    // if (value.intValue() == 0) {
    // return new OperandBoolean(false);
    // } else if (value.intValue() == 1) {
    // return new OperandBoolean(true);
    // }
    // }
    // return this;
    // }

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
    protected void writeCode(Coder coder) {
        if (type.is(boolean.class)) {
            if (value.intValue() == 0) {
                coder.writeBoolean(false);
            } else {
                coder.writeBoolean(true);
            }
        } else if (type.is(char.class)) {
            coder.writeChar((char) value.intValue());
        } else if (value instanceof Integer || value instanceof Short || value instanceof Byte) {
            coder.writeInt(value.intValue());
        } else if (value instanceof Long) {
            coder.writeLong(value.longValue());
        } else if (value instanceof Float) {
            coder.writeFloat(value.floatValue());
        } else if (value instanceof Double) {
            coder.writeDouble(value.doubleValue());
        } else {
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error("[" + value + "] is illegal number.");
        }
    }
}