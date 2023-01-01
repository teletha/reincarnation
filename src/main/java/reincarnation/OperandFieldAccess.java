/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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
import reincarnation.operator.AccessMode;

/**
 * @version 2018/10/13 23:34:58
 */
public class OperandFieldAccess extends Operand {

    /** The field. */
    private final Field field;

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
    public OperandFieldAccess(Class ownerType, String name, Operand context) {
        this.field = find(ownerType, name);
        this.context = Objects.requireNonNull(context);

        Class contextType = context.type.v;

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
}