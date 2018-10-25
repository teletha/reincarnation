/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kiss.I;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.CodingOption;
import reincarnation.coder.DelegatableCoder;
import reincarnation.operator.FieldAccessMode;

/**
 * @version 2018/10/23 14:26:49
 */
class OperandMethodCall extends Operand {

    /** The method. */
    private final Method method;

    /** The context. */
    private final Operand owner;

    /** The method parameters. */
    private final List<Operand> params;

    /**
     * @param ownerType
     * @param methodName
     * @param parameters
     * @param remove
     * @param contexts
     */
    OperandMethodCall(Class ownerType, String methodName, Class[] parameterTypes, Operand owner, ArrayList<Operand> parameters) {
        try {
            this.method = ownerType.getDeclaredMethod(methodName, parameterTypes);
            this.owner = owner;
            this.params = parameters;
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (!method.isSynthetic()) {
            coder.writeMethodCall(method, owner, params);
        } else {
            Code code = Reincarnation.exhume(method.getDeclaringClass()).methods.get(method);
            code.write(new SyntheticMethodInliner(coder));
        }
    }

    /**
     * @version 2018/10/23 14:03:05
     */
    private class SyntheticMethodInliner extends DelegatableCoder<CodingOption> {

        /**
         * Delegate {@link Coder}
         */
        private SyntheticMethodInliner(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeReturn(Optional<Code> code) {
            code.stream().forEach(c -> c.write(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAccessField(Field field, Code context, FieldAccessMode mode) {
            coder.writeAccessField(field, params.get(0), mode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            code.write(this);
        }
    }
}
