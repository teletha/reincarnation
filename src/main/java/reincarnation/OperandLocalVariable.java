/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import reincarnation.coder.Coder;

public class OperandLocalVariable extends Operand {

    /** The variable name. */
    private final String name;

    /** The declration type. */
    private LocalVariableDeclaration declaration = LocalVariableDeclaration.None;

    private boolean firstAccess = true;

    /** Holds all nodes that refer to this variable. */
    final Set<Node> referrers = new HashSet();

    /**
     * Create local variable with index.
     */
    OperandLocalVariable(Class type, String name) {
        this.name = Objects.requireNonNull(name);
        this.type.set(type);
    }

    /**
     * Set declaration type.
     * 
     * @param declaration
     * @return Chainable API.
     */
    final OperandLocalVariable set(LocalVariableDeclaration declaration) {
        if (declaration != null) {
            this.declaration = declaration;
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (name.equals("this")) {
            coder.writeThis();
        } else {
            coder.writeLocalVariable(type.v, name, firstAccess ? declaration : LocalVariableDeclaration.None);

            if (!Debugger.whileDebug) {
                firstAccess = false;
            }
        }
    }

    /**
     * Reset access flag.
     */
    void reset() {
        firstAccess = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // don't call #write, it will throw error in debug mode.
        return name;
    }
}