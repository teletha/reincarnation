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

import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.AssignOperator;

/**
 * Binary operation expression.
 */
class OperandAssign extends Operand {

    /** The left value. */
    final Operand left;

    /** The right value. */
    Operand right;

    /** The operator. */
    private AssignOperator operator;

    /**
     * Create binary operation.
     * 
     * @param left A left value.
     * @param operator A operator.
     * @param right A right value.
     */
    OperandAssign(Operand left, AssignOperator operator, Operand right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        this.operator = Objects.requireNonNull(operator);

        bindTo(left.bindTo(right));
    }

    /**
     * Test whether this operand assigns to the specified operand or not.
     * 
     * @param operand
     * @return
     */
    final boolean isAssignedTo(Operand operand) {
        return left.equals(operand) && operator == AssignOperator.ASSIGN;
    }

    /**
     * Select the assigned value to this operand.
     * 
     * @param operand
     * @return
     */
    final Signal<Operand> assignedTo(Operand operand) {
        return isAssignedTo(operand) ? I.signal(right) : I.signal();
    }

    /**
     * Select the assign value on this operand.
     * 
     * @return
     */
    final Signal<Operand> right() {
        return I.signal(right);
    }

    /**
     * Change to the shorter assignment if possible.
     */
    final void shorten() {
        if (operator == AssignOperator.ASSIGN && right instanceof OperandBinary binary && left == binary.left) {
            binary.operator.toAssignOperator().to(assignOperator -> {
                operator = assignOperator;
                right = binary.right;
            });
        }

        if (operator == AssignOperator.PLUS && right instanceof OperandNumber num && num.isNegative()) {
            operator = AssignOperator.MINUS;
            right = num.invert();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatement() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeAssignOperation(left, operator, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return left.view() + " " + operator + " " + right.view();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandAssign other) {
            return Objects.equals(left, other.left) && Objects.equals(right, other.right) && Objects.equals(operator, other.operator);
        } else {
            return false;
        }
    }
}