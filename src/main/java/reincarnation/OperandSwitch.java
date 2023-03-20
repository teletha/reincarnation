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

import java.util.Comparator;
import java.util.List;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.structure.Structure;
import reincarnation.structure.Switch;
import reincarnation.util.MultiMap;

class OperandSwitch extends Operand {

    /** The case sorter. */
    private static final Comparator<Node> SORTER = (o1, o2) -> {
        return o1.isBefore(o2) ? -1 : 1;
    };

    /** The condition. */
    private final Operand condition;

    /** The special case manager. */
    private final MultiMap<Node, Integer> cases = new MultiMap(true);

    /** The default case. */
    private Node defaultNode;

    /** The end node. */
    private Node follow;

    /**
     * @param condition
     * @param keys
     * @param caseNodes
     * @param defaultNode
     */
    OperandSwitch(Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode) {
        this.condition = condition;
        this.defaultNode = defaultNode;

        for (int i = 0; i < keys.length; i++) {
            cases.put(caseNodes.get(i), keys[i]);
        }
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
        return "switch(" + condition + ") case " + nodes().skipNull().map(x -> x == defaultNode ? "Default" + x.id : x.id).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
    }

    /**
     * @param that
     * @return
     */
    public Structure structurize(Node that) {
        return new Switch(that, condition, cases, defaultNode, follow);
    }

    /**
     * Analyze following node.
     */
    void analyze(NodeCreator creator) {
        cases.sort(SORTER);
        cases.remove(defaultNode);

        nodes().to(Node::hideIncoming);
        List<Node> candidates = nodes().flatMap(node -> node.outgoingRecursively().take(n -> !n.hasDominator(node)).first())
                .distinct()
                .toList();

        if (candidates.isEmpty()) {
            follow = defaultNode;
            follow.revealIncoming();
            defaultNode = null;
        } else {
            for (Node candidate : candidates) {
                System.out.println("@@@ " + candidate.id);
                follow = candidate;
            }
        }
        nodes().to(Node::revealIncoming);

        System.out.println(condition + "   " + follow.id + " @ " + defaultNode);

        if (defaultNode != null) {
            List<Node> cases = nodes().toList();
            List<Node> incomings = I.signal(follow.getPureIncoming()).take(n -> n.hasDominatorAny(cases)).toList();

            follow = creator.createSplitterNodeBefore(follow, incomings);
            follow.additionalCall++;
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
