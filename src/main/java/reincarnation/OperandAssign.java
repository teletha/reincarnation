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

import java.util.Objects;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.AssignOperator;

/**
 * Binary operation expression.
 */
public class OperandAssign extends Operand {

    /** The left value. */
    final Operand left;

    /** The right value. */
    final Operand right;

    /** The operator. */
    private final AssignOperator operator;

    /**
     * Create binary operation.
     * 
     * @param left A left value.
     * @param operator A operator.
     * @param right A right value.
     */
    public OperandAssign(Operand left, AssignOperator operator, Operand right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right).disclose();
        this.operator = Objects.requireNonNull(operator);

        bindTo(left.bindTo(right));
    }

    /**
     * Test whether this operand assigns to the specified node or not.
     * 
     * @param node
     * @return
     */
    final boolean isAssignedTo(Operand node) {
        return left.equals(node) && operator == AssignOperator.ASSIGN;
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
}