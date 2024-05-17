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

import java.lang.reflect.Method;
import java.util.Objects;

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
    protected String view() {
        if (context == null) {
            return reference.getDeclaringClass().getSimpleName() + "::" + reference.getName();
        } else {
            return context.view() + "::" + reference.getName();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandMethodReference other) {
            return Objects.equals(reference, other.reference) && Objects.equals(context, other.context);
        } else {
            return false;
        }
    }
}