/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kiss.Variable;
import reincarnation.coder.Coder;

class OperandLocalVariable extends Operand {

    /** The variable index. */
    final Variable<Integer> index;;

    /** The variable name. */
    final Variable<String> name;

    /** The referrer node manager. */
    final List<Node> referrers = new ArrayList();

    /** The detected original name. */
    final Variable<String> original = Variable.empty();

    /**
     * Create local variable with index.
     */
    OperandLocalVariable(Type type, int index, String name) {
        this.name = Variable.of(name);
        this.index = Variable.of(index);
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
     * Synchronizes the state of this local variable with the specified target local variable.
     * <p>
     * This method sets up observers on the fields of this object (`index`, `name`, `type`, and
     * `original`) and binds their values to the corresponding fields of the target local variable.
     * Any updates to this object's fields will automatically propagate to the target.
     * </p>
     *
     * @param target the {@code OperandLocalVariable} whose fields will be updated to match this
     *            object's fields
     */
    void assimilate(OperandLocalVariable target) {
        index.observing().to(target.index::set);
        name.observing().to(target.name::set);
        original.observing().to(target.original::set);
        type.observing().to(target.type::set);
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
    protected boolean isValue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (name.v.equals("this")) {
            coder.writeThis();
        } else {
            coder.writeLocalVariable(type.v, index.v, original.isAbsent() ? name.v : original.v);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return index.v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof OperandLocalVariable op ? index.v == op.index.v : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // don't call #write, it will throw error in debug mode.
        return original.isAbsent() ? name.v : original.v;
    }
}