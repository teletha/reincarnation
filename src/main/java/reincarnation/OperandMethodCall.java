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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import kiss.I;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 2:02:20
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
        coder.writeMethodCall(method, owner, params);
    }
}
