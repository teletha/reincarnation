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

import reincarnation.coder.Coder;

class OperandSwitch extends Operand {

    private Operand condition;

    private final int min;

    private final int max;

    private final Node defautlNode;

    private final List<Node> caseNodes;

    /**
     * @param min
     * @param max
     * @param caseNodes
     * @param defaultNode
     */
    OperandSwitch(Operand condition, int min, int max, Node defaultNode, List<Node> caseNodes) {
        this.condition = condition;
        this.min = min;
        this.max = max;
        this.defautlNode = defaultNode;
        this.caseNodes = caseNodes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
    }
}
