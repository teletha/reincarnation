/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.statement;

import reincarnation.Operand;
import reincarnation.OperandAssign;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/30 11:11:54
 */
public class OperandStatement extends Statement {

    private final Operand operand;

    /**
     * @param operand
     */
    public OperandStatement(Operand operand) {
        this.operand = operand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (operand.isExpression() || operand instanceof OperandAssign) {
            coder.writeStatement(operand);
        } else {
            operand.write(coder);
        }
    }
}
