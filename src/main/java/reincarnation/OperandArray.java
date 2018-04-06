/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import static booton.translator.Javascript.*;

import java.util.ArrayList;

/**
 * <p>
 * The code like the following will generate difference bytecode by compiler.
 * </p>
 * 
 * <pre>
 * String[] array = {null, null, "third"};
 * </pre>
 * <p>
 * Eclipse Java Compiler generates the following bytecode.
 * </p>
 *
 * <pre>
 * mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
 * mv.visitInsn(DUP);
 * mv.visitInsn(ICONST_2);
 * mv.visitLdcInsn("third");
 * mv.visitInsn(AASTORE); // Assign third value suddenly
 *</pre>
 * <p>
 * JDK Compiler generates the following bytecode.
 * </p>
 * 
 * <pre>
 * mv.visitTypeInsn(ANEWARRAY,  "java/lang/String");
 * mv.visitInsn(DUP);
 * mv.visitInsn(ICONST_0);
 * mv.visitInsn(ACONST_NULL);
 * mv.visitInsn(AASTORE); // Assign first null value
 * mv.visitInsn(DUP);
 * mv.visitInsn(ICONST_1);
 * mv.visitInsn(ACONST_NULL);
 * mv.visitInsn(AASTORE); // Assign second null value
 * mv.visitInsn(DUP);
 * mv.visitInsn(ICONST_2);
 * mv.visitLdcInsn("third");
 * mv.visitInsn(AASTORE); // Assign third value
 *</pre>
 *
 * @version 2014/06/26 9:30:10
 */
class OperandArray extends Operand {

    /** The operand which indicates the size of this array. */
    private final Operand size;

    /** The array type. */
    private final Class type;

    /** The list of item operands. */
    private final ArrayList<Operand> items = new ArrayList();

    /**
     * <p>
     * Create Array operand.
     * </p>
     * 
     * @param size A initial size.
     * @param type A array type.
     */
    OperandArray(Operand size, Class type) {
        this.size = size.disclose();
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    InferredType infer() {
        return new InferredType(type);
    }

    /**
     * <p>
     * Set the value operand to the index operand. This method is used only for syntax sugar of
     * array initialization. So we can compute the actual index value safely.
     * </p>
     * 
     * @param index A index of array. We can compute actual value safely.
     * @param value A value of array.
     */
    void set(Operand index, Operand value) {
        int i = Integer.valueOf(index.toString()).intValue();

        if (items.size() <= i) {
            for (int j = 0; j < i + 2; j++) {
                items.add(null);
            }
        }
        items.set(i, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Class component = type.getComponentType();
        String undefined;

        if (component.isPrimitive()) {
            if (component == boolean.class) {
                undefined = "false";
            } else if (component == long.class) {
                undefined = computeFieldFullName(PrimitiveLong, "ZERO");
            } else {
                undefined = "0";
            }
        } else {
            undefined = "null";
        }

        ScriptWriter writer = new ScriptWriter();
        writer.append("Φ(").string(Javascript.computeSimpleClassName(component)).append(",");

        if (items.size() == 0) {
            // new array with the specified size
            writer.append(size, ",", undefined);
        } else {
            // new array by syntax sugar
            writer.append("[");

            int length = Integer.valueOf(size.toString()).intValue();

            for (int i = 0; i < length; i++) {
                if (items.size() <= i) {
                    writer.append(undefined);
                } else {
                    Operand item = items.get(i);

                    if (item == null) {
                        writer.append(undefined);
                    } else {
                        writer.append(item);
                    }
                }

                if (i + 1 != length) {
                    writer.append(",");
                }
            }
            writer.append("]");
        }
        writer.append(")");

        // API definition
        return writer.toString();
    }
}
