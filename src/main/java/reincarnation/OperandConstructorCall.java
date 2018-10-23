/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.Constructor;
import java.util.List;

import kiss.I;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/10 10:00:19
 */
class OperandConstructorCall extends Operand {

    /** The call kind. (this, super or others) */
    private final String kind;

    /** The constructor. */
    private final Constructor constructor;

    /** The constructor parameter. */
    private final List<Operand> params;

    /**
     * Create constructor call.
     * 
     * @param ownerType
     * @param parameterTypes
     * @param parameters
     */
    OperandConstructorCall(String kind, Class ownerType, Class[] parameterTypes, List<Operand> parameters) {
        try {
            this.kind = kind;
            this.constructor = ownerType.getDeclaredConstructor(parameterTypes);
            this.params = parameters;

            fix(ownerType);
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (kind == null) {
            coder.writeConstructorCall(constructor, params);
        } else if (kind.equals("super")) {
            coder.writeSuperConstructorCall(constructor, params);
        } else if (kind.equals("this")) {
            coder.writeThisConstructorCall(constructor, params);
        }
    }
}
