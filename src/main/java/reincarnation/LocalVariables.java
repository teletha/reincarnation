/*
 * Copyright (C) 2019 Reincarnation Development Team
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
import reincarnation.structure.Structure;

/**
 * Manage local variables.
 * 
 * @version 2018/10/13 23:21:23
 */
class LocalVariables {

    /** Flag for static or normal. */
    private int offset;

    /** The local variable manager. */
    private final Map<Integer, OperandLocalVariable> declared = new HashMap<>();

    private final Map<Integer, OperandLocalVariable> undeclared = new HashMap<>();

    /**
     * @param clazz
     * @param isStatic
     * @param types
     */
    LocalVariables(Class<?> clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
        if (isStatic == false) {
            declared.put(offset++, new OperandLocalVariable(clazz, "this"));
        }

        for (int i = 0; i < types.length; i++) {
            Class<?> type = Util.load(types[i]);
            OperandLocalVariable variable = new OperandLocalVariable(type, parameters[i].getName());
            variable.fix();
            declared.put(offset, variable);

            // count index because primitive long and double occupy double stacks
            offset += type == long.class || type == double.class ? 2 : 1;
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
    OperandLocalVariable find(int order, int opcode, Node reference) {
        // check declared variables
        OperandLocalVariable variable = declared.get(order);

        if (variable != null) {
            return variable;
        }

        // Compute local variable name
        variable = undeclared.computeIfAbsent(order, key -> new OperandLocalVariable(load(opcode), "local" + key).set(With));
        variable.add(reference);

        return variable;
    }

    void replace(OperandLocalVariable replaced, OperandLocalVariable replacer) {
        for (Entry<Integer, OperandLocalVariable> entry : undeclared.entrySet()) {
            if (entry.getValue() == replaced) {
                entry.setValue(replacer);
                return;
            }
        }
    }

    boolean isLocal(OperandLocalVariable variable) {
        for (Entry<Integer, OperandLocalVariable> entry : undeclared.entrySet()) {
            if (entry.getValue() == variable) {
                return offset < entry.getKey();
            }
        }
        return false;
    }

    private boolean analyzed = false;

    /**
     * Analyze all local variables except parameters and "this".
     */
    synchronized void analyze(Structure root) {
        I.signal(undeclared.values()).to(v -> {
            if (analyzed) {
                v.reset();
            } else {
                v.analyze(root);
            }
        });
        analyzed = true;
    }
}
