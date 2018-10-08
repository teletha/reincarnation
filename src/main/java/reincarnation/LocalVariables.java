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

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;

/**
 * Manage local variables.
 * 
 * @version 2018/10/04 12:53:34
 */
class LocalVariables {

    /** The this type. */
    private final Class clazz;

    /**  */
    private final int offset;

    /** The ignorable variable index. */
    private final List<Integer> ignores = new ArrayList();

    /** The local variable manager. */
    private final Map<Integer, OperandLocalVariable> locals = new HashMap();

    /** The parameter manager. */
    final NodeList<Parameter> parameters = new NodeList();

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

        for (int i = 0; i < types.length; i++) {
            OperandLocalVariable local = new OperandLocalVariable(Util.load(types[i]), "param" + i);
            local.declared = true;

            locals.put(i + offset, local);
            parameters.add(new Parameter(Util.loadType(local.type), local.name));
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
            local.name.setId(name);
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
            return new InferredType(local.type);
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

        local.name.setId(name);
    }
}