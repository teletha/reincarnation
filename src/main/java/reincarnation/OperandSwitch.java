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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.structure.Structure;
import reincarnation.structure.Switch;
import reincarnation.util.MultiMap;

/**
 * Operand representing a switch statement.
 */
class OperandSwitch extends Operand {

    /** The entrance node. */
    private final Node entrance;

    /** The condition. */
    private final Operand condition;

    /** The special case manager. */
    private MultiMap<Node, Object> cases = new MultiMap(true);

    /** The default case. */
    private Node defaultNode;

    /** The switch type mode. */
    private final boolean switchForString;

    /** The end node. */
    private Node follow;

    /**
     * Creates a new {@code OperandSwitch} instance.
     *
     * @param entrance the entrance node
     * @param condition the condition operand
     * @param keys the keys for each case
     * @param caseNodes the nodes corresponding to each case
     * @param defaultNode the default node
     */
    OperandSwitch(Node entrance, Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode, boolean switchForString) {
        this.entrance = entrance;
        this.condition = switchForString ? ((OperandMethodCall) condition).owner : condition;
        this.defaultNode = defaultNode;
        this.switchForString = switchForString;

        for (int i = 0; i < keys.length; i++) {
            cases.put(caseNodes.get(i), keys[i]);
        }

        markAsStatement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "switch(" + condition + "){ case:" + nodes().skipNull()
                .map(x -> x == defaultNode ? x.id + "D" : x.id)
                .toList() + " exit:" + (follow == null ? "none" : follow.id) + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (Debugger.whileDebug) {
            coder.write(toString());
        } else {
            MultiMap<Structure, Object> caseBlocks = cases.convertKeys(entrance::process);
            Structure defaultBlock = entrance.process(defaultNode);

            coder.writeSwitch(false, Optional.empty(), condition, condition.type(), caseBlocks, defaultBlock, null);
        }
    }

    /**
     * @return
     */
    public Structure structurize() {
        return new Switch(entrance, condition, cases, defaultNode, follow);
    }

    /**
     * Test the given node is any case or not.
     * 
     * @param node
     * @return
     */
    boolean isCase(Node node) {
        return cases.containsKey(node);
    }

    /**
     * Test the given node is default case or not.
     * 
     * @param node
     * @return
     */
    boolean isDefault(Node node) {
        return defaultNode == node;
    }

    /**
     * Tests whether a given node is unrelated to this switch.
     * 
     * @param node
     * @return
     */
    boolean isOther(Node node) {
        return !isCase(node) && !isDefault(node) && entrance != node;
    }

    /**
     * Analyze following node.
     */
    void analyze(NodeManipulator manipulator) {
        cases.sort();
        cases.remove(defaultNode);

        // ===============================================
        // Special handling for string switch
        // ===============================================
        if (switchForString) {
            MultiMap<Node, Object> renewed = new MultiMap(true);
            cases.keys().to(oldCaseBlock -> {
                OperandCondition condition = oldCaseBlock.child(OperandCondition.class).exact();

                // retrieve the actual matching text
                String text = oldCaseBlock.children(OperandCondition.class, OperandMethodCall.class, OperandString.class)
                        .to()
                        .exact()
                        .toString();

                renewed.put(condition.then, text);

                manipulator.dispose(condition.elze, true, true);
                manipulator.dispose(oldCaseBlock, true, true);
            });
            this.cases = renewed;
        }

        // ===============================================
        // Detect the really default node
        // ===============================================
        if (isReallyDefaultNode(defaultNode)) {
            List<Node> candidates = nodes()
                    .flatMap(node -> node.outgoingRecursively(n -> isCase(n) || isDefault(n)).take(n -> !n.hasDominator(node)).first())
                    .toList();

            if (candidates.isEmpty()) {
                follow = defaultNode;
                defaultNode = null;
            } else {
                for (Node candidate : candidates) {
                    follow = candidate;
                }
            }
        } else {
            follow = defaultNode;
            defaultNode = null;
        }

        if (isExpression()) {
            follow.getPureIncoming().forEach(in -> {
                OperandYield yield = new OperandYield(in.remove(0));
                in.addOperand(yield);

                // type inference
                bindTo(yield);
            });
        }

        // if (entrance.isSwitchExpression()) {
        // List<Node> incomings = new ArrayList(entrance.incoming);
        // entrance.disconnect(true, false);
        // follow.disconnect(true, false);
        //
        // incomings.forEach(in -> in.connect(follow));
        // }

        if (defaultNode != null) {
            List<Node> cases = nodes().toList();

            // group incomings by cases
            MultiMap<Node, Node> group = new MultiMap(true);
            for (Node in : follow.getPureIncoming()) {
                for (Node node : cases) {
                    if (in.hasDominator(node)) {
                        group.put(node, in);
                    }
                }
            }

            // create splitter node for each cases
            Set<Node> incomings = group.values().map(nodes -> manipulator.createSplitterNodeBefore(follow, nodes)).toSet();

            // create splitter node for this switch
            follow = manipulator.createSplitterNodeBefore(follow, incomings);
            follow.additionalCall++;
        }
    }

    /**
     * Determine if the default node is really the default node or actually an exit node.
     * 
     * @return
     */
    private boolean isReallyDefaultNode(Node node) {
        // In searching for follow nodes in switches, there are several things to consider.
        //
        // First, we need to determine if the default node is indeed a default node or if it is
        // actually a follow node without a default node. What conditions must be met to determine
        // that a node is a default node?
        //
        // The default node has zero or one other directly (without going through other case nodes)
        // connected case nodes.
        List<Node> directlyConnectedCases = cases.keys()
                .take(caseNode -> caseNode.canReachTo(node, cases.keys().skip(caseNode).toSet()))
                .toList();

        switch (directlyConnectedCases.size()) {
        // It is an independent default node that is not connected to any case node.
        case 0:
            return true;

        // It is necessary to determine whether the node is a default node that has been
        // fall-through from the previous case or a follow node that is connected by break only from
        // one case.
        case 1:
            Node directly = directlyConnectedCases.get(0);
            // If the connected case node is declared immediately before def, it is the default
            // node. Conversely, if the connected case node has not been declared immediately
            // before, it is an exit node.
            return directly.isBefore(node, cases.keys().skip(directly).toSet());

        // It is a follow node because it is connected to two or more case nodes
        default:
            return false;
        }
    }

    /**
     * Traverse all cases.
     * 
     * @return
     */
    private Signal<Node> nodes() {
        return cases.keys().startWith(defaultNode).skipNull();
    }
}
