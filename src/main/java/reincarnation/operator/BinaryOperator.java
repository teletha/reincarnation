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

import kiss.Variable;

public enum BinaryOperator {

    /** && */
    AND("&&"),

    /** || */
    OR("||"),

    /** == */
    EQUAL("=="),

    /** != */
    NOT_EQUALS("!="),

    /** < */
    LESS("<"),

    /** <= */
    LESS_EQUALS("<="),

    /** > */
    GREATER(">"),

    /** >= */
    GREATER_EQUALS(">="),

    /** & */
    BINARY_AND("&"),

    /** | */
    BINARY_OR("|"),

    /** ^ */
    XOR("^"),

    /** + */
    PLUS("+"),

    /** - */
    MINUS("-"),

    /** * */
    MULTIPLY("*"),

    /** \/ */
    DIVIDE("/"),

    /** % */
    REMAINDER("%"),

    /** << */
    LEFT_SHIFT("<<"),

    /** >> */
    RIGHT_SHIFT(">>"),

    /** >>> */
    UNSIGNED_RIGHT_SHIFT(">>>");

    /** The operator value. */
    private final String operator;

    /**
     * Hide constructor.
     * 
     * @param operator An operator word.
     */
    private BinaryOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Convert to the suitable {@link AssignOperator}.
     * 
     * @return
     */
    public Variable<AssignOperator> toAssignOperator() {
        return Variable.of(switch (this) {
        case PLUS -> AssignOperator.PLUS;
        case MINUS -> AssignOperator.MINUS;
        case MULTIPLY -> AssignOperator.MULTIPLY;
        case DIVIDE -> AssignOperator.DIVIDE;
        case REMAINDER -> AssignOperator.REMAINDER;
        case LEFT_SHIFT -> AssignOperator.LEFT_SHIFT;
        case RIGHT_SHIFT -> AssignOperator.RIGHT_SHIFT;
        case UNSIGNED_RIGHT_SHIFT -> AssignOperator.UNSIGNED_RIGHT_SHIFT;
        default -> null;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return operator;
    }
}