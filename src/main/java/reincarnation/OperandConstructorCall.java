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

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

public class OperandConstructorCall extends Operand {

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
            for (int i = 0; i < parameterTypes.length; i++) {
                parameters.get(i).fix(parameterTypes[i]);
            }
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (kind == null) {
            coder.writeConstructorCall(constructor, params);
        } else if (kind.equals("super")) {
            coder.writeSuperConstructorCall(constructor, params);
        } else if (kind.equals("this")) {
            coder.writeThisConstructorCall(constructor, params);
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
        return constructor.getName() + params.stream().map(Operand::view).collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandConstructorCall other) {
            return Objects.equals(kind, other.kind) && Objects.equals(constructor, other.constructor) && match(params, other.params);
        } else {
            return false;
        }
    }
}