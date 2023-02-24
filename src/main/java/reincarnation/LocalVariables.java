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

import static reincarnation.LocalVariableDeclaration.*;
import static reincarnation.Util.*;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Type;

import kiss.I;

/**
 * Generic variable manager.
 */
final class LocalVariables {

    /** Flag for static or normal. */
    private int offset;

    /** The parameter manager. */
    private final Map<Integer, OperandLocalVariable> params = new HashMap<>();

    /** The local variable manager. */
    final Map<Integer, OperandLocalVariable> variables = new HashMap<>();

    /**
     * Create variable manager.
     * 
     * @param clazz
     * @param isStatic
     * @param types
     * @param parameters
     */
    LocalVariables(Class<?> clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
        if (isStatic == false) {
            params.put(offset++, new OperandLocalVariable(clazz, "this"));
        }

        for (int i = 0; i < types.length; i++) {
            Class<?> type = Util.load(types[i]);
            OperandLocalVariable variable = new OperandLocalVariable(type, parameters[i].getName());
            variable.fix();
            params.put(offset, variable);

            // count index because primitive long and double occupy double stacks
            offset += type == long.class || type == double.class ? 2 : 1;
        }
    }

    /**
     * Compute the identified qualified local variable name.
     * 
     * @param order An order by which this variable was declared.
     * @param opcode A variable type.
     * @param referrer A referrer node.
     * @return An identified local variable name.
     */
    OperandLocalVariable find(int order, int opcode, Node referrer) {
        // check parameters
        OperandLocalVariable variable = params.get(order);

        if (variable != null) {
            return variable;
        }

        // compute local variable
        Class type = load(opcode);

        variable = variables.computeIfAbsent(order, id -> new OperandLocalVariable(type, "local" + id).set(With));
        variable.referrers.add(I.pair(referrer, type));

        return variable;
    }

    void replace(OperandLocalVariable replaced, OperandLocalVariable replacer) {
        for (Entry<Integer, OperandLocalVariable> entry : variables.entrySet()) {
            if (entry.getValue() == replaced) {
                entry.setValue(replacer);
                return;
            }
        }
    }

    boolean isLocal(OperandLocalVariable variable) {
        for (Entry<Integer, OperandLocalVariable> entry : variables.entrySet()) {
            if (entry.getValue() == variable) {
                return offset < entry.getKey();
            }
        }
        return false;
    }
}