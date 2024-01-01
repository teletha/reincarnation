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

import reincarnation.coder.Coder;

/**
 * Represents a boolean operand in the Reincarnation programming language. This class provides a
 * boolean value, along with methods to invert the value and generate code to evaluate the operand
 * in the target language.
 */
class OperandBoolean extends Operand {

    /** The reusable boolean. */
    public static final OperandBoolean True = new OperandBoolean(true);

    /** The reusable boolean. */
    public static final OperandBoolean False = new OperandBoolean(false);

    /** The actual value. */
    private final boolean value;

    /**
     * Create a primitive boolean expression with the specified value.
     * 
     * @param value boolean value of this operand
     */
    private OperandBoolean(boolean value) {
        this.value = value;
        fix(boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Operand invert() {
        return this == True ? False : True;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeBoolean(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isTrue() {
        return value == true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFalse() {
        return value == false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return true;
    }
}