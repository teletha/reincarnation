/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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
 * @version 2018/10/13 17:48:04
 */
class OperandBoolean extends Operand {

    /** The reusable boolean. */
    public static final OperandBoolean True = new OperandBoolean(true);

    /** The reusable boolean. */
    public static final OperandBoolean False = new OperandBoolean(false);

    /** The actual value. */
    private final boolean value;

    /**
     * Primitive boolean expression.
     * 
     * @param value
     */
    OperandBoolean(boolean value) {
        this.value = value;
        type.set(boolean.class);
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
    protected void writeCode(Coder coder) {
        coder.writeBoolean(value);
    }
}