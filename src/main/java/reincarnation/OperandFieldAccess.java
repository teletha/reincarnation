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
import java.lang.reflect.Modifier;
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
     * @param ownerType A field owner.
     * @param name A field name.
     */
    public OperandFieldAccess(Class ownerType, String name, Operand context) {
        Class contextType = context.type.v;

        if (contextType == ownerType) {

        }
        if (duplicated) {

        }
        this.field = find(ownerType, name, true);
        this.context = Objects.requireNonNull(context);
        fix(field.getType());
    }

    /**
     * Find the suitable {@link Field}.
     * 
     * @param owner A field owner.
     * @param name A field name.
     * @param acceptPrivate Flag for private field.
     * @return
     */
    private Field find(Class owner, String name, boolean acceptPrivate) {
        try {
            Field field = owner.getDeclaredField(name);

            if (Modifier.isPrivate(field.getModifiers()) && !acceptPrivate) {
                return find(owner.getSuperclass(), name, false);
            }
            return field;
        } catch (NoSuchFieldException e) {
            return find(owner.getSuperclass(), name, false);
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
