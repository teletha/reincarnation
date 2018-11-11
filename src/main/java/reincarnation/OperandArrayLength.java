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

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 0:53:38
 */
public class OperandArrayLength extends Operand {

    /** The array context. */
    private final Operand context;

    /**
     * @param context
     */
    public OperandArrayLength(Operand context) {
        this.context = context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAccessArrayLength(context);
    }
}
