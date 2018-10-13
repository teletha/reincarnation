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

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;

import kiss.I;
import kiss.Signal;
import reincarnation.OperandExpression.StatementExpression;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/03 16:15:40
 */
public class OperandReturn extends Operand {

    /** The empty return for reuse. */
    public static final OperandReturn Empty = new OperandReturn(null);

    /** The statement. */
    private final Operand statement;

    /**
     * @param statement
     */
    public OperandReturn(Operand statement) {
        this.statement = statement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new StatementExpression(statement == null ? new ReturnStmt() : new ReturnStmt(statement.build()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeReturn(statement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Signal<Operand> children() {
        return statement == null ? Signal.empty() : I.signal(statement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return statement == null ? "return" : "return " + statement;
    }
}
