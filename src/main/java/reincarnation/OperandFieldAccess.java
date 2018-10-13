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

import java.lang.reflect.Field;
import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/13 23:34:58
 */
public class OperandFieldAccess extends Operand {

    /** The field. */
    private final Field field;

    /** The field context. */
    private final Operand context;

    /**
     * Create field access like <code>owner.field</code>.
     * 
     * @param field A field owner.
     * @param name A field name.
     */
    public OperandFieldAccess(Class owner, String name, Operand context) {
        try {
            this.field = owner.getDeclaredField(name);
            this.context = Objects.requireNonNull(context);
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return I.signal(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeAccessField(field, context);
    }
}
