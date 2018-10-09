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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.TypeExpr;

/**
 * @version 2018/10/07 1:03:46
 */
public class OperandType extends Operand {

    /** The actual value. */
    private final Class value;

    /**
     * Type access like <code>java.lang.String</code>.
     * 
     * @param value
     */
    OperandType(Class value) {
        this.value = value;
        this.type.set(Class.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new TypeExpr(Util.loadType(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return value.getSimpleName();
    }
}
