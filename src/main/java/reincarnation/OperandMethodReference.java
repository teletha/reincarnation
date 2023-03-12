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
import java.lang.reflect.Type;
import java.util.Arrays;

import reincarnation.coder.Coder;

class OperandMethodReference extends Operand {

    /** The lambda body. */
    private final Method reference;

    /** The context. */
    private final Operand context;

    OperandMethodReference(Class interfaceClass, Method reference, Operand context, Type type) {
        this.reference = reference;
        this.context = context;

        System.out.println(Arrays.toString(reference.getGenericParameterTypes()));
        fix(type);
    }

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
