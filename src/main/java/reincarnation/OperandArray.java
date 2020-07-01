/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * <p>
 * The code like the following will generate difference bytecode by compiler.
 * </p>
 * <pre>
 * String[] array = {null, null, "third"};
 * </pre>
 * <p>
 * Eclipse Java Compiler generates the following bytecode.
 * </p>
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
 * @version 2018/10/05 19:36:39
 */
class OperandArray extends Operand {

    /** The array dimension size. */
    private final int dimension;

    /** The operand which indicates the size of this array. */
    private final List<Operand> dimensions;

    /** The list of item operands. */
    private final ArrayList<Operand> items = new ArrayList();

    /** The component type. */
    private final Class type;

    /**
     * <p>
     * Create Array operand.
     * </p>
     * 
     * @param size A initial size.
     * @param type A array type.
     */
    OperandArray(Operand size, Class type) {
        this(List.of(size), type);
    }

    /**
     * Create array.
     * 
     * @param size A initial size.
     * @param type A array type.
     */
    OperandArray(List<Operand> dimensions, Class type) {
        this.dimension = calculateDimension(type);
        this.dimensions = Objects.requireNonNull(dimensions);
        this.type = type.getComponentType();

        fix(type);
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
            for (int j = items.size(); j <= i; j++) {
                items.add(null);
            }
        }
        items.set(i, value.fix(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(dimensions).merge(I.signal(items));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (items.isEmpty()) {
            List<Code> levels = new ArrayList();

            // fill by empty value
            for (int i = 0; i < dimension; i++) {
                levels.add(i, Code.Empty);
            }
            // fill by specified value
            for (int i = 0; i < dimensions.size(); i++) {
                levels.set(i, dimensions.get(i));
            }
            coder.writeCreateArray(root(type), levels);
        } else {
            int requiredSize = dimensions.isEmpty() ? items.size() : Math.max(Integer.parseInt(dimensions.get(0).toString()), items.size());

            List<Code> levels = List.of(Code.Empty);
            List<Code> initializer = new ArrayList();

            // fill by default value
            for (int i = 0; i < requiredSize; i++) {
                initializer.add(i, Util.defaultValueFor(type));
            }

            // assign by specified value
            for (int i = 0; i < items.size(); i++) {
                Operand operand = items.get(i);

                if (operand != null) {
                    initializer.set(i, items.get(i));
                }
            }
            coder.writeCreateArray(type, levels, initializer);
        }
    }

    /**
     * Find the root type.
     * 
     * @param type
     * @return
     */
    private Class root(Class type) {
        if (type.isArray()) {
            return root(type.getComponentType());
        }
        return type;
    }

    /**
     * Calculate the array dimension.
     * 
     * @param type A target array type.
     * @return An array dimension size.
     */
    private int calculateDimension(Class type) {
        int size = 0;

        while (type.isArray()) {
            type = type.getComponentType();
            size++;
        }
        return size;
    }
}