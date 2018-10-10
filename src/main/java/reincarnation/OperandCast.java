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

import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/10 9:31:14
 */
class OperandCast extends Operand {

    /** The actual value. */
    private final Operand value;

    /** The type to cast. */
    private final Class type;

    /**
     * Create cast code.
     * 
     * @param value
     * @param type
     */
    OperandCast(Operand value, Class type) {
        this.value = value;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new CastExpr(Util.loadType(type), value.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + type.getSimpleName() + ") " + value;
    }
}
