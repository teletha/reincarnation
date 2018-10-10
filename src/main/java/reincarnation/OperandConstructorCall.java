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
import java.util.StringJoiner;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;

import kiss.I;

/**
 * @version 2018/10/10 10:00:19
 */
class OperandConstructorCall extends Operand {

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
    OperandConstructorCall(Class ownerType, Class[] parameterTypes, List<Operand> parameters) {
        try {
            this.constructor = ownerType.getDeclaredConstructor(parameterTypes);
            this.params = parameters;
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ObjectCreationExpr(null, Util.loadType(constructor.getDeclaringClass()).asClassOrInterfaceType(), list(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        for (Operand param : params) {
            joiner.add(param.toString());
        }
        return "new " + constructor.getDeclaringClass().getSimpleName() + joiner;
    }
}
