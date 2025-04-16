/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.operator;

public enum AssignOperator {
    /** = */
    ASSIGN("="),

    /** &amp;= */
    AND("&="),

    /** |= */
    OR("|="),

    /** ^= */
    XOR("^="),

    /** += */
    PLUS("+="),

    /** -= */
    MINUS("-="),

    /** *= */
    MULTIPLY("*="),

    /** \/= */
    DIVIDE("/="),

    /** %= */
    REMAINDER("%="),

    /** &lt;&lt;= */
    LEFT_SHIFT("<<="),

    /** >>= */
    RIGHT_SHIFT(">>="),

    /** >>>= */
    UNSIGNED_RIGHT_SHIFT(">>>=");

    /** The operator value. */
    private final String operator;

    /**
     * Hide constructor.
     * 
     * @param operator An operator word.
     */
    private AssignOperator(String operator) {
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