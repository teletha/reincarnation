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

import java.util.Optional;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 10:17:27
 */
public class OperandReturn extends Operand {

    /** The empty return for reuse. */
    public static final OperandReturn Empty = new OperandReturn(null);

    /** The statement. */
    private final Optional<Operand> value;

    /**
     * Build return expression.
     * 
     * @param value A returned value, may be null.
     */
    public OperandReturn(Operand value) {
        this.value = value == null ? Optional.empty() : Optional.of(value.disclose());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(value::get);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatement() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeReturn(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " - " + value.get().info();
    }
}
