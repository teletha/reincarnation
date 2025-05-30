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

import java.util.Collection;
import java.util.List;

public interface NodeManipulator {

    /**
     * Create a new node before the specified node.
     *
     * @param index The index node before which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    default Node createNodeBefore(Node index, boolean connectable) {
        return createNodeBefore(index, connectable, false);
    }

    /**
     * Create a new node before the specified node.
     *
     * @param index The index node before which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @return The newly created node.
     */
    Node createNodeBefore(Node index, boolean connectable, boolean transferOperands);

    /**
     * Create a new node with the specified initial operand before the specified node.
     * 
     * @param index The index node before which to create the new node.
     * @param initials The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeBefore(Node index, Operand... initials) {
        return createNodeBefore(index, List.of(initials));
    }

    /**
     * Create a new node with the specified initial operand before the specified node.
     * 
     * @param index The index node before which to create the new node.
     * @param initials The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeBefore(Node index, Iterable<? extends Operand> initials) {
        Node created = createNodeBefore(index, true);
        for (Operand initial : initials) {
            created.addOperand(initial);
            index.stack.remove(initial);
        }
        return created;
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
    default Node createNodeAfter(Node index, boolean connectable) {
        return createNodeAfter(index, connectable, false);
    }

    /**
     * Create a new node after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param connectable Whether the new node should be connectable.
     * @param transferOperands Whether the new node should spoil all original operands.
     * @return The newly created node.
     */
    Node createNodeAfter(Node index, boolean connectable, boolean transferOperands);

    /**
     * Create a new node with the specified initial operand after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param initials The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeAfter(Node index, Operand... initials) {
        return createNodeAfter(index, List.of(initials));
    }

    /**
     * Create a new node with the specified initial operand after the specified node.
     * 
     * @param index The index node after which to create the new node.
     * @param initials The initial operand for the new node.
     * @return The newly created node.
     */
    default Node createNodeAfter(Node index, Iterable<? extends Operand> initials) {
        Node created = createNodeAfter(index, true);
        for (Operand initial : initials) {
            created.addOperand(initial);
            index.stack.remove(initial);
        }
        return created;
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
     * @param target A target node to dispose.
     */
    default void dispose(Node target) {
        dispose(target, false, true);
    }

    /**
     * Helper method to dispose the specified node.
     * 
     * @param target A target node to dipose.
     * @param clearStack true will clear all operands in target node, false will transfer them into
     *            the previous node.
     * @param previousRecursively true will dispose the previous node if it is empty.
     */
    void dispose(Node target, boolean clearStack, boolean previousRecursively);

    /**
     * Link all nodes as order of appearance.
     * 
     * @param nodes A sequence of nodes.
     * @return A last node.
     */
    default Node link(Node... nodes) {
        int size = nodes.length - 1;

        for (int i = 0; i < size; i++) {
            Node prev = nodes[i];
            Node next = nodes[i + 1];

            if (prev != null) prev.next = next;
            if (next != null) next.previous = prev;
        }
        return nodes[size];
    }

    /**
     * The specified node and the previous node are combined into a single node. The
     * previous node will be disposed.
     * 
     * @param target A node that is the starting point for merging
     */
    default void mergePrevious(Node target) {
        Node prev = target.previous;

        if (prev != null) {
            target.stack.addAll(0, prev.stack);
            dispose(prev, true, false);
        }
    }
}