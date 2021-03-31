/*
 * Copyright (C) 2020 Reincarnation Development Team
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
import reincarnation.structure.Structure;

/**
 * @version 2018/10/22 18:08:07
 */
public class OperandLocalVariable extends Operand {

    /** The variable name. */
    String name;

    /** The declration type. */
    private LocalVariableDeclaration declaration = LocalVariableDeclaration.None;

    private boolean firstAccess = true;

    /** Holds all nodes that refer to this local variable. */
    private final Set<Node> references = new HashSet();

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
        } else if (firstAccess == false) {
            coder.writeLocalVariable(type.v, name, LocalVariableDeclaration.None);
        } else {
            coder.writeLocalVariable(type.v, name, declaration);
            if (!Debugger.whileDebug) {
                firstAccess = false;
            }
        }
    }

    /**
     * <p>
     * Analyze at which node this local variable is declared. Some local variables are used across
     * multiple nodes, and it is not always possible to uniquely identify the declaration location.
     * </p>
     * <p>
     * Check the lowest common dominator node of all nodes that refer to this local variable, and if
     * the dominator node is included in the reference node, declare it at the first reference.
     * Otherwise, declare in the header of the dominator node.
     * </p>
     * 
     * @param root
     */
    void analyze(Structure root) {
        // calculate the lowest common dominator node
        Node common = Node.getLowestCommonDominator(references);

        if (common == null) {
            // do nothing
        } else if (references.contains(common)) {
            // do nothing
        } else {
            // insert variable declaration at the header of common dominator node
            declaration = LocalVariableDeclaration.Only;
            root.unclearLocalVariable(this);
        }
    }

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

    /**
     * @param reference
     */
    public void add(Node reference) {
        this.references.add(reference);
    }
}