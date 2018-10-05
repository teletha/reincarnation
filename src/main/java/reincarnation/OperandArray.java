/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.util.ArrayList;

import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.Expression;

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
    Expression build() {
        Class component = type.getComponentType();
        ArrayCreationExpr expr = new ArrayCreationExpr();

        return expr;
    }
}
