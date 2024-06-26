/*
 * Copyright (C) 2024 The REINCARNATION Development Team
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
import java.lang.reflect.Type;
import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.AccessMode;

class OperandFieldAccess extends Operand {

    /** The field. */
    final Field field;

    /** The field context. */
    private final Operand context;

    /** The access mode. */
    private final AccessMode mode;

    /**
     * Create field access like <code>owner.field</code>.
     * 
     * @param ownerType A field owner.
     * @param name A field name.
     */
    OperandFieldAccess(Class ownerType, String name, Operand context) {
        this.field = find(ownerType, name);
        this.context = Objects.requireNonNull(context);

        Type contextType = context.type.v;

        if (contextType == ownerType) {
            this.mode = AccessMode.THIS;
        } else {
            this.mode = AccessMode.CAST;
        }
        fix(field.getType());
    }

    /**
     * Find the suitable {@link Field}.
     * 
     * @param owner A field owner.
     * @param name A field name.
     * @return
     */
    private Field find(Class owner, String name) {
        Class clazz = owner;
        Field field = null;
        boolean acceptPrivate = true;

        while (clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(name);

                if (!acceptPrivate && Modifier.isPrivate(field.getModifiers())) {
                    clazz = clazz.getSuperclass();
                } else {
                    break;
                }
            } catch (NoSuchFieldException e) {
                acceptPrivate = false;
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null) {
            for (Field f : owner.getFields()) {
                if (f.getName().equals(name)) {
                    field = f;
                    break;
                }
            }
        }

        return field;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNegatable() {
        return type.is(boolean.class) || type.is(Boolean.class);
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
        coder.writeAccessField(field, context, mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return context.view() + "." + field.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandFieldAccess other) {
            return Objects.equals(field, other.field) && Objects.equals(context, other.context) && Objects.equals(mode, other.mode);
        } else {
            return false;
        }
    }
}