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
import kiss.Variable;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/14 10:17:27
 */
public class OperandReturn extends Operand {

    /** The empty return for reuse. */
    public static final OperandReturn Empty = new OperandReturn(null);

    /** The statement. */
    private final Operand value;

    /**
     * Build return expression.
     * 
     * @param value A returned value, may be null.
     */
    public OperandReturn(Operand value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return value == null ? Signal.empty() : I.signal(value);
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
    public <T extends Operand> Variable<T> as(Class<T> type) {
        return I.signal(value).as(type).to();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (value == null) {
            coder.writeReturn(Optional.empty());
        } else {
            coder.writeReturn(Optional.of(value));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " - " + value.info();
    }
}
