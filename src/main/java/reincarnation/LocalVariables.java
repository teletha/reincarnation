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

    /** This or parameter offset. */
    private int offset;

    /** The ignorable variable index. */
    private final List<Integer> ignores = new ArrayList<>();

    /** The local variable manager. */
    private final Map<Integer, List<OperandLocalVariable>> locals = new HashMap<>();

    /**
     * @param clazz
     * @param isStatic
     * @param types
     */
    LocalVariables(Class<?> clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
        this.clazz = clazz;

        if (isStatic == false) {
            locals.put(offset++, I.list(new OperandLocalVariable(clazz, "this", LocalVariableDeclaration.None)));
        }

        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = Util.load(types[i]);
            OperandLocalVariable local = new OperandLocalVariable(type, parameters[i].getName(), LocalVariableDeclaration.None);
            local.fix();
            locals.put(offset, I.list(local));

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
        List<OperandLocalVariable> list = locals.get(order);

        if (list == null) {
            // first access
            OperandLocalVariable variable = new OperandLocalVariable(load(opcode), "local" + order, LocalVariableDeclaration.None)
                    .usedAt(reference);
            locals.put(order, I.list(variable));
            return variable;
        } else {
            // other access
            OperandLocalVariable base = list.get(0);
            base.usedAt(reference);

            if (list.size() == 1) {
                list.add(new OperandLocalVariable(base.type.v, base.name, LocalVariableDeclaration.None));
            }
            return list.get(1);
        }
    }

    /**
     * Rename local variable.
     * 
     * @param index
     * @param name
     */
    void rename(int index, String name) {
        List<OperandLocalVariable> list = locals.get(index);

        if (list != null) {
            for (OperandLocalVariable variable : list) {
                variable.name = name;
            }
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

        OperandLocalVariable local = locals.get(position).get(0);

        if (local == null) {
            return new InferredType();
        } else {
            return new InferredType(local.type.v);
        }
    }

    void replace(OperandLocalVariable replaced, OperandLocalVariable replacer) {
        for (Entry<Integer, List<OperandLocalVariable>> entry : locals.entrySet()) {
            if (entry.getValue().get(0) == replaced) {
                entry.setValue(I.list(replacer));
                return;
            }
        }
    }

    boolean isLocal(OperandLocalVariable variable) {
        for (Entry<Integer, List<OperandLocalVariable>> entry : locals.entrySet()) {
            if (entry.getValue().get(0) == variable) {
                return offset < entry.getKey();
            }
        }
        return false;
    }

    /**
     * Analyze all local variables except parameters and "this".
     */
    void analyze(Structure root) {
        I.signal(locals.entrySet()).skip(e -> e.getKey() < offset).to(e -> e.getValue().get(0).analyze(root));
    }
}
