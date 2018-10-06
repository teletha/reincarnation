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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;

/**
 * @version 2018/10/07 3:46:54
 */
public class OperandLocalVariable extends Operand {

    /** The variable name. */
    String name;

    /** The variable model. */
    Class type;

    /**
     * Create local variable with index.
     * 
     * @param index A local index.
     */
    OperandLocalVariable(int index, Class type) {
        this.name = "local" + index;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new NameExpr(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }
}