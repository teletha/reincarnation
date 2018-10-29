/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import reincarnation.coder.Coder;

/**
 * @version 2018/10/27 21:55:57
 */
public class OperandIf extends Operand {

    /** The code. */
    private final Operand condition;

    /** The code. */
    private final Node then;

    /** The code. */
    private final Node elze;

    /**
     * If statement.
     * 
     * @param condition
     * @param then
     * @param elze
     */
    OperandIf(Operand condition, Node then, Node elze) {
        this.condition = condition;
        this.then = then;
        this.elze = elze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isStatement() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeIf(condition, then, elze);
    }
}
