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

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Util.load;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /** The this type. */
    private final Class<?> clazz;

    /** Flag for static or normal. */
    private int offset;

    /** The ignorable variable index. */
    private final List<Integer> ignores = new ArrayList<>();

    /** The local variable manager. */
    private final Map<Integer, OperandLocalVariable> locals = new HashMap<>();

    /**
     * @param clazz
     * @param isStatic
     * @param types
     */
    LocalVariables(Class<?> clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
        this.clazz = clazz;

        if (isStatic == false) {
            locals.put(offset++, new OperandLocalVariable(clazz, "this"));
        }

        for (int i = 0; i < types.length; i++) {
            Class<?> type = Util.load(types[i]);
            OperandLocalVariable local = new OperandLocalVariable(type, parameters[i].getName()).declared();
            local.fix();
            locals.put(offset, local);

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
    OperandLocalVariable name(int order, int opcode, Node reference) {
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
        OperandLocalVariable variable = locals.computeIfAbsent(order, key -> new OperandLocalVariable(load(opcode), "local" + key));
        variable.references.add(reference);

        return variable;
    }

    void replace(OperandLocalVariable replaced, OperandLocalVariable replacer) {
        for (Entry<Integer, OperandLocalVariable> entry : locals.entrySet()) {
            if (entry.getValue() == replaced) {
                entry.setValue(replacer);
                return;
            }
        }
    }

    boolean isLocal(OperandLocalVariable variable) {
        for (Entry<Integer, OperandLocalVariable> entry : locals.entrySet()) {
            if (entry.getValue() == variable) {
                return offset < entry.getKey();
            }
        }
        return false;
    }

    /**
     * Analyze all local variables except parameters and "this".
     */
    void analyze(Structure root) {
        I.signal(locals.values()).skip(offset).to(v -> v.analyze(root));
    }
}
