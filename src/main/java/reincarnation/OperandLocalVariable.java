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
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

/**
 * @version 2018/10/07 3:46:54
 */
public class OperandLocalVariable extends Operand {

    /** The variable name. */
    final SimpleName name = new SimpleName();

    /** Check whether this local variable is declared or not. */
    boolean declared;

    /**
     * Create local variable with index.
     * 
     * @param index A local index.
     */
    OperandLocalVariable(Class type, String name) {
        this.name.setId(name);
        this.type.set(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        if (name.getId().equals("this")) {
            return new ThisExpr();
        } else {
            if (declared == false) {
                declared = true;
                return new VariableDeclarationExpr(Util.loadType(type.v), name.getId());
            } else {
                return new NameExpr(name);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name.getId();
    }
}