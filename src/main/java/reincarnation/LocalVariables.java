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

import static reincarnation.Util.*;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Type;

import kiss.I;
import reincarnation.coder.Coder;
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
    private final Map<Integer, OperandLocalVariable> locals = new HashMap<>();

    /**
     * @param clazz
     * @param isStatic
     * @param types
     */
    LocalVariables(Class<?> clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
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
        // Compute local variable name
        OperandLocalVariable variable = locals.computeIfAbsent(order, key -> new OperandLocalVariable(load(opcode), "local" + key));
        variable.add(reference);

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
     * <p>
     * Analyze at which node this local variable is declared. Some local variables are used across
     * multiple nodes, and it is not always possible to uniquely identify the declaration location.
     * </p>
     * <p>
     * Check the lowest common dominator node of all nodes that refer to this local variable, and if
     * the dominator node is included in the reference node, declare it at the first reference.
     * Otherwise, declare in the header of the dominator node.
     * </p>
     */
    void analyze(Structure root) {
        I.signal(locals.values()).skip(offset).to(variable -> {
            // calculate the lowest common dominator node
            Node common = Node.getLowestCommonDominator(variable.references);

            if (common == null) {
                // do nothing
            } else if (variable.references.contains(common)) {

            } else {
                // insert variable declaration at the header of common dominator node
                OperandLocalVariable insert = new OperandLocalVariable(variable.type.v, variable.name, LocalVariableDeclaration.Only);
                root.unclearLocalVariable(insert);
                variable.declared();
            }
        });
    }

    /**
     * 
     */
    private static class OperandLocalVariableReference extends Operand {

        /** The delegation. */
        private OperandLocalVariable delegation;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void writeCode(Coder coder) {
            delegation.write(coder);
        }
    }
}
