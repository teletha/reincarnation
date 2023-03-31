/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.util.Collection;

public interface NodeManipulator {

    /**
     * Create a new node before the specified node.
     *
     * @param index The index node before which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    Node createNodeBefore(Node index, boolean connectable);

    /**
     * Create a new node with the specified initial operand before the specified node.
     * 
     * @param index The index node before which to create the new node.
     * @param initial The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeBefore(Node index, Operand initial) {
        return createNodeBefore(index, true).addOperand(initial);
    }

    /**
     * Create a new splitter node before the specified node, with the specified incoming nodes.
     * 
     * @param index The index node before which to create the new node.
     * @param incomings The incoming nodes for the new splitter node.
     * @return The newly created splitter node.
     */
    default Node createSplitterNodeBefore(Node index, Collection<Node> incomings) {
        if (index.incoming.size() == 1 && index.incoming.containsAll(incomings)) {
            return index;
        }

        Node created = createNodeBefore(index, false);

        for (Node incoming : incomings) {
            incoming.disconnect(index);
            incoming.connect(created);
        }
        created.connect(index);

        return created;
    }

    /**
     * Create a new node after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    Node createNodeAfter(Node index, boolean connectable);

    /**
     * Create a new node with the specified initial operand after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param initial The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeAfter(Node index, Operand initial) {
        return createNodeAfter(index, true).addOperand(initial);
    }

    /**
     * Create a new splitter node after the specified node, with the specified outgoing nodes.
     * 
     * @param index The index node after which to create the new node.
     * @param outgoings The outgoing nodes for the new splitter node.
     * @return The newly created splitter node.
     */
    default Node createSplitterNodeAfter(Node index, Collection<Node> outgoings) {
        if (index.outgoing.size() == 1 && index.outgoing.containsAll(outgoings)) {
            return index;
        }

        Node created = createNodeBefore(index, false);

        for (Node outgoing : outgoings) {
            index.disconnect(outgoing);
            created.connect(outgoing);
        }
        index.connect(created);

        return created;
    }

    /**
     * Test whether the given node is disposed or not.
     * 
     * @param node A target node to test.
     * @return A result of test.
     */
    boolean isDisposed(Node node);

    /**
     * Dispose the specified node.
     * 
     * @param node A target node to dispose.
     */
    default void dispose(Node node) {
        dispose(node, false, true);
    }

    /**
     * Helper method to dispose the specified node.
     * 
     * @param target A target node to dipose.
     * @param clearStack true will clear all operands in target node, false will transfer them into
     *            the previous node.
     * @param recursive true will dispose the previous node if it is empty.
     */
    void dispose(Node target, boolean clearStack, boolean recursive);
}
