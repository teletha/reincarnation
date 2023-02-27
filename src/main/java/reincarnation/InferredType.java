/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

class InferredType {

    /** The reusable type. */
    static final InferredType Boolean = new InferredType(boolean.class);

    /** The actual type. */
    private Class type;

    /**
     * 
     */
    InferredType() {
    }

    /**
     * 
     */
    InferredType(Class type) {
        this.type = type;
    }

    /**
     * 
     */
    InferredType(Operand... operands) {
        for (Operand operand : operands) {
            InferredType type = operand.infer();

            if (type != null) {
                this.type = type.type;
                return;
            }
        }
    }

    /**
     * <p>
     * Set the inferred type.
     * </p>
     * 
     * @param type
     */
    void type(Class type) {
        this.type = type;
    }

    /**
     * Get the inferred type.
     * 
     * @return
     */
    Class type() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return InferredType.class.getSimpleName() + "[" + (type == null ? null : type.getName()) + "]";
    }
}