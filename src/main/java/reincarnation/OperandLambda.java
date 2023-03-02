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

import reincarnation.coder.Code;
import reincarnation.coder.Coder;

class OperandLambda extends Operand {

    /** The lambda body. */
    private final Method lambda;

    private final Reincarnation source;

    OperandLambda(Class interfaceClass, Method lambda, Reincarnation source) {
        this.lambda = lambda;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        Code code = source.methods.get(lambda);
        if (code == null) {
        } else {
            coder.writeLambda(lambda, code);
        }
    }
}