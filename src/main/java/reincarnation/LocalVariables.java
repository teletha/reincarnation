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

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Type;

/**
 * Manage local variables.
 * 
 * @version 2018/10/13 23:21:23
 */
class LocalVariables {

    /** The this type. */
    private final Class clazz;

    /** Flag for static or normal. */
    private final int offset;

    /** The ignorable variable index. */
    private final List<Integer> ignores = new ArrayList();

    /** The local variable manager. */
    private final Map<Integer, OperandLocalVariable> locals = new HashMap();

    private final int size;

    /**
     * @param clazz
     * @param isStatic
     * @param types
     */
    LocalVariables(Class clazz, boolean isStatic, Type[] types) {
        this.clazz = clazz;
        this.offset = isStatic ? 0 : 1;

        if (isStatic == false) {
            locals.put(0, new OperandLocalVariable(clazz, "this"));
        }

        // count index because primitive long and double occupy double stacks
        int index = 0;

        for (int i = 0; i < types.length; i++) {
            Class type = Util.load(types[i]);
            OperandLocalVariable local = new OperandLocalVariable(type, "arg" + index);
            local.declared = true;
            local.fix();
            locals.put(index + offset, local);

            index++;

            if (type == long.class || type == double.class) {
                index++;
            }
        }
        this.size = index + offset;
    }

    /**
     * <p>
     * Compute the identified qualified local variable name for ECMAScript.
     * </p>
     * 
     * @param order An order by which this variable was declared.
     * @return An identified local variable name for ECMAScript.
     */
    OperandLocalVariable name(int order) {
        return name(order, 0);
    }

    /**
     * <p>
     * Compute the identified qualified local variable name for ECMAScript.
     * </p>
     * 
     * @param order An order by which this variable was declared.
     * @return An identified local variable name for ECMAScript.
     */
    OperandLocalVariable name(int order, int opcode) {
        // ignore long or double second index
        switch (opcode) {
        case LLOAD:
        case LSTORE:
        case DLOAD:
        case DSTORE:
            ignores.add(order + 1);
            break;
        }

        // Compute local variable name
        return locals.computeIfAbsent(order, key -> new OperandLocalVariable(load(opcode), "local" + key));
    }

    /**
     * Rename local variable.
     * 
     * @param index
     * @param name
     */
    void name(int index, String name) {
        OperandLocalVariable local = locals.get(index);

        if (local != null) {
            local.name = name;
        }
    }

    /**
     * <p>
     * Find {@link InferredType} for the specified position.
     * </p>
     * 
     * @param position
     * @return
     */
    InferredType type(int position) {
        if (position == -1) {
            return new InferredType(clazz);
        }

        OperandLocalVariable local = locals.get(position);

        if (local == null) {
            return new InferredType();
        } else {
            return new InferredType(local.type.v);
        }
    }

    /** The number of update calling sequencially. */
    private int sequencialUpdateCount;

    /**
     * Update parameter info.
     * 
     * @param name A parameter name.
     * @param access A parameter modifier.
     */
    void updateParameterInfo(String name, int access) {
        OperandLocalVariable local = locals.get(sequencialUpdateCount++ + offset);

        local.name = name;

        if (local.type.v == long.class || local.type.v == double.class) {
            sequencialUpdateCount++;
        }
    }

    /**
     * 
     */
    void reset() {
        for (Entry<Integer, OperandLocalVariable> e : locals.entrySet()) {
            if (size <= e.getKey()) {
                e.getValue().declared = false;
            }
        }
    }
}
