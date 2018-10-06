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
import com.github.javaparser.ast.expr.SimpleName;

/**
 * @version 2018/10/07 2:13:47
 */
public class OperandName extends Operand {

    private final SimpleName name;

    /**
     * @param name
     */
    public OperandName(SimpleName name) {
        this.name = name;
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
        return name.toString();
    }
}
