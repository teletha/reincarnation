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
import com.github.javaparser.ast.expr.ThisExpr;

/**
 * Binary operation expression.
 * 
 * @version 2018/10/07 1:09:20
 */
public class OperandThis extends Operand {

    /**
     * Create binary operation.
     */
    public OperandThis() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ThisExpr();
    }
}