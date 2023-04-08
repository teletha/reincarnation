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

import java.lang.reflect.Type;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.operator.BinaryOperator;

/**
 * <h1>Algorithm for restoration of logical expression</h1>
 * <p>
 * Please note that the necessary information are transition label of JumpInstruction and
 * LabelInstruction when you restore the logical expression. Do not use other information because it
 * change depending on the content of the description of the logical expression.
 * </p>
 */
class OperandCondition extends Operand {

    /** The relational operator. */
    static final int AND = 0x00;

    /** The relational operator. */
    static final int OR = ~AND;

    /** The relational operator. */
    static final int EQ = 0x01;

    /** The relational operator. */
    static final int NE = ~EQ;

    /** The relational operator. */
    static final int GT = 0x02;

    /** The relational operator. */
    static final int LE = ~GT;

    /** The relational operator. */
    static final int LT = 0x03;

    /** The relational operator. */
    static final int GE = ~LT;

    /** The left operand of this conditional expression. */
    Operand left;

    /** The right operand of this conditional expression. */
    Operand right;

    /** The transition node. */
    Node then;

    /** The transition node. */
    Node elze;

    /** The operator of this conditional expression. */
    private int operator;

    /** The flag of grouping. */
    private boolean group = false;

    /**
     * @param condition
     */
    OperandCondition(OperandCondition condition) {
        this.then = condition.then;
        this.elze = condition.elze;
        this.operator = condition.operator;
        this.group = condition.group;
    }

    /**
     * @param left
     * @param operator
     * @param right
     * @param transition
     */
    OperandCondition(Operand left, int operator, Operand right, Node transition) {
        this.left = left;
        this.right = right;
        this.operator = operator;
        this.then = transition;

        left.bindTo(right);
    }

    /**
     * 
     */
    OperandCondition(OperandCondition left, OperandCondition right) {
        if (left.group || !(left.right instanceof OperandCondition)) {
            this.left = encloseIfTernay(left);
            this.right = encloseIfTernay(right);
            this.operator = OR;
        } else {
            this.left = encloseIfTernay(left);
            this.right = new OperandCondition((OperandCondition) left.right, right);
            this.operator = left.operator;
        }
        this.then = right.then;
        this.elze = right.elze;

        if (left.then != right.then) {
            this.left.invert();
            this.operator = ~this.operator;
        }

        // Make group if left transition node equals to transition node or next node of the
        // right condition node.
        group = left.then == right.then || left.then == right.elze;
    }

    /**
     * <p>
     * Enclose conditional operand if it it {@link OperandTernary}.
     * </p>
     * 
     * @param condition A target condition.
     * @return A result.
     */
    private Operand encloseIfTernay(OperandCondition condition) {
        if (condition instanceof OperandTernary) {
            return condition.encolose();
        } else {
            return condition;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    int computeMultiplicity() {
        return left.computeMultiplicity() + right.computeMultiplicity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    OperandCondition invert() {
        // invert each operands
        left.invert();
        right.invert();

        // invert operator
        operator = ~operator;

        // API definition
        return this;
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
    protected void writeCode(Coder coder) {
        // convert int to char if needed
        Type leftType = left.type.v;
        Type rightType = right.type.v;

        if (leftType == char.class && rightType != char.class) {
            right = right.fix(char.class);
        }

        if (rightType == char.class && leftType != char.class) {
            left = left.fix(char.class);
        }

        BinaryOperator operator;
        switch (this.operator) {
        case AND:
            operator = BinaryOperator.AND;
            break;

        case OR:
            operator = BinaryOperator.OR;
            break;

        case EQ:
            operator = BinaryOperator.EQUAL;
            break;

        case NE:
            operator = BinaryOperator.NOT_EQUALS;
            break;

        case LT:
            operator = BinaryOperator.LESS;
            break;

        case GT:
            operator = BinaryOperator.GREATER;
            break;

        case LE:
            operator = BinaryOperator.LESS_EQUALS;
            break;

        case GE:
            operator = BinaryOperator.GREATER_EQUALS;
            break;

        default:
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        Runnable writer = null;

        if (leftType == boolean.class) {
            writer = write(coder, left, operator, right);
        } else if (rightType == boolean.class) {
            writer = write(coder, right, operator, left);
        }

        if (writer == null) {
            writer = () -> coder.writeBinaryOperation(left, operator, right);
        }

        if (group) {
            coder.writeEnclose(writer);
        } else {
            writer.run();
        }
    }

    private Runnable write(Coder coder, Operand condition, BinaryOperator operator, Operand expectedValue) {
        if (operator == BinaryOperator.EQUAL) {
            if (expectedValue.isTrue()) {
                return () -> coder.writePositiveOperation(condition);
            } else if (expectedValue.isFalse()) {
                if (condition.isNegatable()) {
                    return () -> coder.writeNegativeOperation(condition);
                } else {
                    return () -> coder.writePositiveOperation(condition.invert());
                }
            }
        } else if (operator == BinaryOperator.NOT_EQUALS) {
            if (expectedValue.isTrue()) {
                if (condition.isNegatable()) {
                    return () -> coder.writeNegativeOperation(condition);
                } else {
                    return () -> coder.writePositiveOperation(condition.invert());
                }
            } else if (expectedValue.isFalse()) {
                return () -> coder.writePositiveOperation(condition);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String info() {
        return super.info() + " then " + then.id + " else " + (elze == null ? "SAME" : elze.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return true;
    }
}