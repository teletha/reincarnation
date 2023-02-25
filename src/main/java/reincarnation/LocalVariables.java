/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import static reincarnation.OperandUtil.load;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.objectweb.asm.Type;

import reincarnation.util.MultiMap;

/**
 * Local variable manager.
 */
final class LocalVariables {

    /** The special binding. */
    private final Map<Integer, Integer> binder = new HashMap();

    /** Flag for static or normal. */
    private final int offset;

    /** The declared local variable (argument) manager. */
    private final Map<Integer, OperandLocalVariable> params = new HashMap();

    /** The undeclared local variable manager. */
    private final Map<Integer, OperandLocalVariable> variables = new HashMap();

    /** Holds all types that each variables use. */
    private final MultiMap<OperandLocalVariable, Class> types = new MultiMap(false);

    /** Holds all nodes that each variables is referred. */
    private final MultiMap<OperandLocalVariable, Node> referrers = new MultiMap(false);

    /**
     * Create variable manager.
     * 
     * @param clazz
     * @param isStatic
     * @param types
     * @param parameters
     */
    LocalVariables(Class clazz, boolean isStatic, Type[] types, Parameter[] parameters) {
        int offset = 0;

        if (isStatic == false) {
            params.put(offset++, new OperandLocalVariable(clazz, 0, "this"));
        }

        for (int i = 0; i < types.length; i++) {
            Class<?> type = OperandUtil.load(types[i]);
            OperandLocalVariable variable = new OperandLocalVariable(type, offset, parameters[i].getName());
            variable.fix();
            params.put(offset, variable);

            // count index because primitive long and double occupy double stacks
            offset += type == long.class || type == double.class ? 2 : 1;
        }

        this.offset = offset;
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

        Integer binding = binder.get(order);
        if (binding != null) {
            order = binding.intValue();
        }

        Class type = load(opcode);
        int id = order;

        variable = variables.computeIfAbsent(id * 10000 + type.hashCode(), key -> new OperandLocalVariable(type, id, "local" + id));
        referrers.put(variable, referrer);
        types.put(variable, type);

        return variable;
    }

    /**
     * @param order
     * @param variable
     */
    void register(int order, OperandLocalVariable variable) {
        binder.put(order, variable.index);
    }

    boolean isLocal(OperandLocalVariable variable) {
        return offset < variable.index;
    }

    /**
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
    void analyzeVariableDeclarationNode(BiConsumer<Node, OperandLocalVariable> createDeclarationNode) {
        for (OperandLocalVariable local : variables.values()) {
            List<Node> refs = referrers.get(local);

            // calculate the lowest common dominator node
            Node common = Node.getLowestCommonDominator(refs);

            if (common != null && !refs.contains(common) && types.get(local).size() <= 1) {
                createDeclarationNode.accept(common, local);
            }
        }
    }
}
