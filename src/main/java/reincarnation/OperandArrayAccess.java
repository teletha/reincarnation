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

import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/09 16:12:05
 */
class OperandArrayAccess extends Operand {

    /** The array value. */
    private final Operand array;

    /** The index value. */
    private final Operand index;

    /**
     * @param array
     * @param index
     */
    OperandArrayAccess(Operand array, Operand index) {
        this.array = array;
        this.index = index;

        fix(array.type.v.getComponentType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ArrayAccessExpr(array.build(), index.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return array + "[" + index + "]";
    }
}
