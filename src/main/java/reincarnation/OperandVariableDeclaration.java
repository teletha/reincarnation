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

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

/**
 * @version 2018/10/07 2:12:34
 */
public class OperandVariableDeclaration extends Operand {

    private final VariableDeclarator declarator;

    /**
     * @param declarator
     */
    public OperandVariableDeclaration(VariableDeclarator declarator) {
        this.declarator = declarator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new VariableDeclarationExpr(declarator);
    }
}
