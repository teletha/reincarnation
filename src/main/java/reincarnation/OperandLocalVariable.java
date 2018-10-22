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

import com.github.javaparser.ast.expr.SimpleName;

import reincarnation.coder.Coder;

/**
 * @version 2018/10/22 18:08:07
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
    public void write(Coder coder) {
        if (name.getId().equals("this")) {
            coder.writeThis();
        } else {
            if (declared == false) {
                declared = true;
                coder.writeLocalVariableDeclaration(type.v, name.getId());
            } else {
                coder.writeLocalVariable(name.getId());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // don't call #write, it will throw error in debug mode.
        return name.getId();
    }
}
