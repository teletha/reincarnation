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
import java.util.List;
import java.util.Objects;

import reincarnation.coder.Code;
import reincarnation.coder.Coder;

class OperandLambda extends Operand {

    /** The lambda body. */
    private final Method lambda;

    /** The context parameters. */
    private final List<Operand> context;

    private final Reincarnation source;

    OperandLambda(Class interfaceClass, Method lambda, List<Operand> context, Reincarnation source) {
        this.lambda = lambda;
        this.context = context;
        this.source = source;

        fix(new SpecializedType(interfaceClass).specializeByReturnAndParameterTypes(lambda));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        Code code = source.methods.get(lambda);

        if (code == null) {
        } else {
            coder.writeLambda(lambda, context, code);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "Lambda$" + lambda.toGenericString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandLambda other) {
            return Objects.equals(lambda, other.lambda) && match(context, other.context);
        } else {
            return false;
        }
    }
}