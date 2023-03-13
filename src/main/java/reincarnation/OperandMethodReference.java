/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.Method;

import reincarnation.coder.Coder;

class OperandMethodReference extends Operand {

    /** The lambda body. */
    private final Method reference;

    /** The context. */
    private final Operand context;

    /**
     * Create the operand for method reference.
     * 
     * @param reference
     * @param context
     */
    OperandMethodReference(Method reference, Operand context) {
        this.reference = reference;
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (context == null) {
            coder.writeStaticMethodReference(reference);
        } else {
            coder.writeMethodReference(reference, context);
        }
    }
}
