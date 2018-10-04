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

/**
 * @version 2013/09/28 8:50:37
 */
class InferredType {

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
}
