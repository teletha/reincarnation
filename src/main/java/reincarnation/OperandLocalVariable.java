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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import reincarnation.coder.Coder;

public class OperandLocalVariable extends Operand {

    /** The variable index. */
    final int index;

    /** The variable name. */
    final String name;

    /** The referrer node manager. */
    final List<Node> referrers = new ArrayList();

    /**
     * Create local variable with index.
     */
    OperandLocalVariable(Class type, int index, String name) {
        this.name = Objects.requireNonNull(name);
        this.index = index;
        this.type.set(type);
    }

    /**
     * Register as referrer node.
     * 
     * @param node
     */
    final void registerReferrer(Node node) {
        if (!referrers.contains(node)) {
            referrers.add(node);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNegatable() {
        return type.is(boolean.class) || type.is(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (name.equals("this")) {
            coder.writeThis();
        } else {
            coder.writeLocalVariable(type.v, name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof OperandLocalVariable op ? index == op.index : false;
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