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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import kiss.I;
import kiss.Signal;
import kiss.Ⅱ;
import reincarnation.coder.Coder;
import reincarnation.structure.Structure;
import reincarnation.structure.Switch;

class OperandSwitch extends Operand {

    /** The case sorter. */
    private static final Comparator<Ⅱ<Integer, Node>> SORTER = (o1, o2) -> {
        return o1.ⅱ.isBefore(o2.ⅱ) ? -1 : 1;
    };

    /** The condition. */
    private final Operand condition;

    /** The special case manager. */
    private final List<Ⅱ<Integer, Node>> cases;

    /** The default case. */
    private Node defaultNode;

    /** The end node. */
    Node follow;

    /**
     * @param condition
     * @param keys
     * @param caseNodes
     * @param defaultNode
     */
    OperandSwitch(Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode) {
        this.condition = condition;
        this.defaultNode = defaultNode;

        List<Ⅱ<Integer, Node>> list = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            list.add(I.pair(keys[i], caseNodes.get(i)));
        }
        this.cases = list;
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
     * Analyze end node.
     */
    Ⅱ<Node, List<Node>> analyzeFollow() {
        cases.sort(SORTER);
        cases.removeIf(x -> x.ⅱ == defaultNode);

        nodes().to(node -> {
            node.hideIncoming();
            node.additionalCall++;
        });

        nodes().flatMap(node -> node.outgoingRecursively().take(n -> !n.hasDominator(node)).first()).distinct().to().to(node -> {
            follow = node;
        }, () -> {
            follow = defaultNode;
            follow.revealIncoming();
            defaultNode = null;
        });

        List<Node> cases = nodes().toList();

        List<Node> incomings = I.signal(follow.getPureIncoming()).take(n -> n.hasDominatorAny(cases)).toList();

        return I.pair(follow, incomings);
    }

    /**
     * Traverse all cases.
     * 
     * @return
     */
    private Signal<Node> nodes() {
        return I.signal(cases).map(Ⅱ::ⅱ).startWith(defaultNode);
    }
}
