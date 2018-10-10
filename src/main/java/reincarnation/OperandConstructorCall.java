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

import java.util.List;
import java.util.StringJoiner;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;

/**
 * @version 2018/10/10 10:00:19
 */
class OperandConstructorCall extends Operand {

    /** The owner. */
    private final Operand context;

    /** The constructor owner. */
    private final Class owner;

    /** The constructor parameter. */
    private final List<Operand> params;

    /**
     * @param owner
     */
    OperandConstructorCall(Class owner) {
        this(owner, List.of());
    }

    /**
     * @param owner
     * @param params
     */
    OperandConstructorCall(Class owner, List<Operand> params) {
        this(null, owner, params);
    }

    /**
     * @param context
     * @param owner
     * @param params
     */
    OperandConstructorCall(Operand context, Class owner, List<Operand> params) {
        this.context = context;
        this.owner = owner;
        this.params = params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ObjectCreationExpr(context.build(), Util.loadType(owner).asClassOrInterfaceType(), list(params));
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
        return "new " + owner.getSimpleName() + joiner;
    }
}
