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
import java.util.StringJoiner;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;

import kiss.I;

/**
 * @version 2018/10/09 0:12:41
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
    Expression build() {
        NodeList<Expression> params = new NodeList();
        for (Operand o : this.params) {
            params.add(o.build());
        }
        return new MethodCallExpr(owner.build(), method.getName(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        for (Operand operand : params) {
            joiner.add(operand.toString());
        }

        return owner + "." + method.getName() + joiner;
    }
}
