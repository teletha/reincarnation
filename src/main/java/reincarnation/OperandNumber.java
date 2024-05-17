/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.Objects;

import reincarnation.coder.Coder;

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
     * Check whether this value is positive or not.
     * 
     * @return
     */
    final boolean isPositive() {
        return value.doubleValue() > 0;
    }

    /**
     * Check whether this value is negative or not.
     * 
     * @return
     */
    final boolean isNegative() {
        return value.doubleValue() < 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final OperandNumber invert() {
        if (value instanceof Integer n) {
            return new OperandNumber(n * -1);
        } else if (value instanceof Long n) {
            return new OperandNumber(n * -1);
        } else if (value instanceof Float n) {
            return new OperandNumber(n * -1);
        } else if (value instanceof Double n) {
            return new OperandNumber(n * -1);
        } else if (value instanceof Short n) {
            return new OperandNumber(n * -1);
        } else if (value instanceof Byte n) {
            return new OperandNumber(n * -1);
        } else {
            return this;
        }
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
    protected boolean isValue() {
        return true;
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return value.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandNumber other) {
            return Objects.equals(value, other.value);
        } else {
            return false;
        }
    }
}