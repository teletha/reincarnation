/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.operator;

public enum UnaryOperator {
    /** + */
    PLUS("+"),

    /** - */
    MINUS("-"),

    /** ! */
    LOGICAL_COMPLEMENT("!"),

    /** ~ */
    BITWISE_COMPLEMENT("~"),

    /** -- */
    POST_DECREMENT("--"),

    /** ++ */
    POST_INCREMENT("++"),

    /** -- */
    PRE_DECREMENT("--"),

    /** ++ */
    PRE_INCREMENT("++");

    /** The operator value. */
    private final String operator;

    /**
     * Hide constructor.
     * 
     * @param operator An operator word.
     */
    private UnaryOperator(String operator) {
        this.operator = operator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return operator;
    }
}