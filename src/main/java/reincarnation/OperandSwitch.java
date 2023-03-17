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
import kiss.Variable;
import kiss.Ⅱ;
import reincarnation.coder.Coder;
import reincarnation.structure.Structure;
import reincarnation.structure.Switch;

class OperandSwitch extends Operand {

    private static final Comparator<Ⅱ<Integer, Node>> SORTER = (o1, o2) -> {
        return o1.ⅱ.isBefore(o2.ⅱ) ? -1 : 1;
    };

    private final Operand condition;

    private final List<Ⅱ<Integer, Node>> cases;

    private final Node defautlNode;

    /**
     * @param condition
     * @param keys
     * @param caseNodes
     * @param defaultNode
     */
    OperandSwitch(Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode) {
        this.condition = condition;
        this.defautlNode = defaultNode;

        List<Ⅱ<Integer, Node>> list = new ArrayList();
        for (int i = 0; i < keys.length; i++) {
            list.add(I.pair(keys[i], caseNodes.get(i)));
        }
        this.cases = list;
    }

    private Signal<Node> nodes() {
        return I.signal(cases).map(Ⅱ::ⅱ).startWith(defautlNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
    }

    /**
     * @return
     */
    public Structure structurize(Node that) {
        cases.sort(SORTER);
        cases.removeIf(x -> x.ⅱ == defautlNode);

        nodes().to(node -> {
            node.disconnect(true, false);
        });

        Variable<Node> end = nodes().flatMap(node -> node.outgoingRecursively().take(n -> !n.hasDominator(node)).first()).distinct().to();
        if (end.isPresent()) {

        }

        return new Switch(that, condition, cases, defautlNode, null);
    }
}
