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

public class OperandStringSwitch extends OperandSwitch {

    /**
     * @param entrance
     * @param condition
     * @param keys
     * @param caseNodes
     * @param defaultNode
     */
    OperandStringSwitch(Node entrance, Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode) {
        super(entrance, condition, keys, caseNodes, defaultNode);
    }
}
