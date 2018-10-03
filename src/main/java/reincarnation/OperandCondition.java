/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * <h1>Algorithm for restoration of logical expression</h1>
 * <p>
 * Please note that the necessary information are transition label of JumpInstruction and
 * LabelInstruction when you restore the logical expression. Do not use other information because it
 * change depending on the content of the description of the logical expression.
 * </p>
 * 
 * @version 2014/06/25 15:46:41
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
     * Enclose conditional operand if it it {@link OperandTernaryCondition}.
     * </p>
     * 
     * @param condition A target condition.
     * @return A result.
     */
    private Operand encloseIfTernay(OperandCondition condition) {
        if (condition instanceof OperandTernaryCondition) {
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
    InferredType infer() {
        return new InferredType(boolean.class);
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
    Operand disclose() {
        group = false;

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // convert int to char if needed
        Class leftType = left.infer().type();
        Class rightType = right.infer().type();

        if (leftType == char.class && rightType != char.class) {
            right = right.cast(char.class);
        }

        if (rightType == char.class && leftType != char.class) {
            left = left.cast(char.class);
        }

        // write out
        StringBuilder builder = new StringBuilder();

        if (group) {
            builder.append('(');
        }

        builder.append(left);

        switch (this.operator) {
        case AND:
            builder.append("&&");
            break;

        case OR:
            builder.append("||");
            break;

        case EQ:
            builder.append("==");
            break;

        case NE:
            builder.append("!=");
            break;

        case LT:
            builder.append('<');
            break;

        case GT:
            builder.append('>');
            break;

        case LE:
            builder.append("<=");
            break;

        case GE:
            builder.append(">=");
            break;
        }

        builder.append(right);

        if (group) {
            builder.append(')');
        }

        // API definition
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        // convert int to char if needed
        Class leftType = left.infer().type();
        Class rightType = right.infer().type();

        if (leftType == char.class && rightType != char.class) {
            right = right.cast(char.class);
        }

        if (rightType == char.class && leftType != char.class) {
            left = left.cast(char.class);
        }

        Operator operator;
        switch (this.operator) {
        case AND:
            operator = Operator.AND;
            break;

        case OR:
            operator = Operator.OR;
            break;

        case EQ:
            operator = Operator.EQUALS;
            break;

        case NE:
            operator = Operator.NOT_EQUALS;
            break;

        case LT:
            operator = Operator.LESS;
            break;

        case GT:
            operator = Operator.GREATER;
            break;

        case LE:
            operator = Operator.LESS_EQUALS;
            break;

        case GE:
            operator = Operator.GREATER_EQUALS;
            break;

        default:
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
        }

        Expression expression = new BinaryExpr(left.build(), right.build(), operator);

        if (group) {
            expression = new EnclosedExpr(expression);
        }
        return expression;
    }
}
