/*
 * Copyright (C) 2019 Reincarnation Development Team
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

/**
 * @version 2018/10/22 18:08:07
 */
public class OperandLocalVariable extends Operand {

    /** The variable name. */
    String name;

    /** Check whether this local variable is declared or not. */
    private boolean declared = false;

    /** The declration type. */
    private final LocalVariableDeclaration declaration;

    /** Holds all nodes that refer to this local variable. */
    final Set<Node> references = new HashSet();

    /**
     * Create local variable with index.
     * 
     * @param index A local index.
     */
    OperandLocalVariable(Class type, String name) {
        this(type, name, LocalVariableDeclaration.None);
    }

    /**
     * Create local variable with index.
     * 
     * @param index A local index.
     */
    OperandLocalVariable(Class type, String name, LocalVariableDeclaration declaration) {
        this.name = Objects.requireNonNull(name);
        this.type.set(type);
        this.declaration = declaration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (name.equals("this")) {
            coder.writeThis();
        } else if (declaration == LocalVariableDeclaration.Only) {
            coder.writeLocalVariable(type.v, name, declaration);
        } else {
            coder.writeLocalVariable(type.v, name, declared ? LocalVariableDeclaration.None : LocalVariableDeclaration.With);

            if (!Debugger.whileDebug) {
                declared();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // don't call #write, it will throw error in debug mode.
        return name;
    }

    OperandLocalVariable declared() {
        if (declared == false) {
            this.declared = true;
        }
        return this;
    }

    /**
     * @param reference
     */
    public void add(Node reference) {
        boolean add = this.references.add(reference);

        if (add == false) {
            System.out.println(name + "   is alread added " + reference.id);
        }
    }
}
