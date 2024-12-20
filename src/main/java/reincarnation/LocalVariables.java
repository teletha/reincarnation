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

import static reincarnation.OperandUtil.*;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import reincarnation.coder.Naming;
import reincarnation.util.Classes;

/**
 * Local variable manager.
 */
final class LocalVariables implements Naming {

    /** Flag for static or normal. */
    private final int offset;

    /** The declared local variable (argument) manager. */
    private final Map<Integer, OperandLocalVariable> params = new HashMap();

    /** The undeclared local variable manager. */
    private final Map<String, OperandLocalVariable> variables = new HashMap();

    /** The unused variables. */
    private final Set<Integer> unregistered = new HashSet();

    /**
     * Create variable manager.
     * 
     * @param clazz
     * @param isStatic
     * @param exe
     */
    LocalVariables(Class clazz, boolean isStatic, Executable exe) {
        int offset = 0;

        if (isStatic == false) {
            params.put(offset, new OperandLocalVariable(clazz, offset, "this"));
            offset++;
        }

        if (exe != null) {
            Type[] types = Classes.fixGenericParameterTypes(exe);
            Parameter[] parameters = exe.getParameters();

            for (int i = 0; i < types.length; i++) {
                OperandLocalVariable variable = new OperandLocalVariable(types[i], offset, parameters[i].getName());
                variable.fix();
                params.put(offset, variable);

                // count index because primitive long and double occupy double stacks
                offset += types[i] == long.class || types[i] == double.class ? 2 : 1;
            }
        }

        this.offset = offset;
    }

    boolean has(int order, int opcode) {
        // check parameters
        OperandLocalVariable variable = params.get(order);

        if (variable != null) {
            return true;
        }

        int index = order;
        Class type = load(opcode);

        return variables.containsKey(id(index, type));
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

        int index = order;
        Class type = load(opcode);

        variable = variables.computeIfAbsent(id(index, type), key -> new OperandLocalVariable(type, index, "local" + index));
        variable.registerReferrer(referrer);

        return variable;
    }

    /**
     * Compute the identifier of the specified variable.
     * 
     * @param index
     * @param type
     * @return
     */
    private String id(int index, Class type) {
        return index + "#" + type.getName();
    }

    /**
     * Unregister the local variable by index.
     * 
     * @param index
     */
    void unregister(int index) {
        unregistered.add(index);
    }

    boolean isLocal(OperandLocalVariable variable) {
        return offset <= variable.index.v;
    }

    boolean isParam(OperandLocalVariable variable) {
        return variable.index.v < offset;
    }

    /**
     * Register the name of variable at the specified index.
     * 
     * @param index
     * @param name
     */
    void registerName(int index, String name) {
        OperandLocalVariable param = params.get(index);
        if (param != null) {
            param.original.set(name);
        } else {
            for (Entry<String, OperandLocalVariable> entry : variables.entrySet()) {
                String id = entry.getKey();
                if (id.startsWith(index + "#")) {
                    OperandLocalVariable variable = entry.getValue();
                    variable.original.set(name);
                }
            }
        }
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
    void analyzeVariableDeclarationNode(NodeManipulator manipulator) {
        root: for (OperandLocalVariable variable : variables.values()) {
            if (unregistered.contains(variable.index.v)) continue;

            // Determine if you need to add a node for variable declarations. For example, if the
            // same variable is referenced by multiple child nodes, you must consider whether you
            // should declare the variable at each child node or create a common parent node and
            // declare the variable there. In addition, common variable declarations cannot be used
            // even when the same variable is used in multiple types.

            // Calculate the lowest common dominator node.
            Node header = Node.getLowestCommonDominator(variable.referrers);

            // If the header node is not one of the referent nodes, a common variable declaration
            // must be used.
            if (header != null && !variable.referrers.contains(header)) {
                // Variable declarations for nodes that have already been discarded need not be
                // handled.
                if (manipulator.isDisposed(header)) {
                    continue;
                }

                // Variable declarations with switch expressions as common nodes need not be
                // considered, since it is believed that the variable declarations used in each case
                // are simply duplicates.
                if (header.isSwitchExpression()) {
                    continue;
                }

                // If multiple types use the same variable, a common variable declaration cannot be
                // used if one header node is the dominator of the other header node.
                for (OperandLocalVariable other : variables.values()) {
                    if (other.index.v == variable.index.v && !other.type.equals(variable.type)) {
                        Node otherHeader = Node.getLowestCommonDominator(other.referrers);
                        if (header.hasDominator(otherHeader) || (otherHeader != null && otherHeader.hasDominator(header))) {
                            continue root;
                        }
                    }
                }

                // Create additional nodes for declarations to use common variable declarations.
                manipulator.createNodeBefore(header, variable);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name(String name) {
        for (OperandLocalVariable param : params.values()) {
            if (param.name.is(name)) {
                return param.toString();
            }
        }

        for (OperandLocalVariable variable : variables.values()) {
            if (variable.name.is(name)) {
                return variable.toString();
            }
        }
        return name;
    }
}