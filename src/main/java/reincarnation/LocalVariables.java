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

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Type;

/**
 * Manage local variables.
 * 
 * @version 2018/10/04 12:53:34
 */
class LocalVariables {

    /** The this type. */
    private final Class clazz;

    /** The current processing method is static or not. */
    private final boolean isStatic;

    /** The parameter size. */
    private final int parameterSize;

    /** The max size of variables. */
    private int max = 0;

    /** The ignorable variable index. */
    private final List<Integer> ignores = new ArrayList();

    /** The local variable manager. */
    private final Map<Integer, OperandLocalVariable> locals = new HashMap();

    /**
     * @param clazz
     * @param isStatic
     * @param parameterTypes
     */
    LocalVariables(Class clazz, boolean isStatic, Type[] parameterTypes) {
        this.clazz = clazz;
        this.isStatic = isStatic;
        this.parameterSize = parameterTypes.length;

        for (int i = 0; i < parameterTypes.length; i++) {
            locals.put(i, new OperandLocalVariable(load(parameterTypes[i]), "local" + i));
        }
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

        // order 0 means "this", but static method doesn't have "this" variable
        if (!isStatic) {
            order--;
        }

        if (order == -1) {
            return new OperandLocalVariable(clazz, "this");
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
        if (!isStatic) {
            index--;
        }

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
        // order 0 means "this", but static method doesn't have "this" variable
        if (!isStatic) {
            position--;
        }

        if (position == -1) {
            return new InferredType(clazz);
        }

        OperandLocalVariable local = locals.get(position);

        if (local == null) {
            return new InferredType();
        } else {
            return new InferredType(local.type);
        }
    }
}