/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import reincarnation.coder.Coder;

/**
 * @version 2018/10/13 18:34:01
 */
public class OperandStatement extends Operand {

    /** The inner operand. */
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
    Operand statement() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeStatement(operand);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return operand.toString();
    }
}
