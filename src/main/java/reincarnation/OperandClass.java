/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/07 1:03:46
 */
public class OperandClass extends Operand {

    /** The actual value. */
    private final Class value;

    /**
     * Class literal like <code>String.class</code>.
     * 
     * @param value
     */
    OperandClass(Class value) {
        this.value = value;
        type.set(Class.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
        return new ClassExpr(Util.loadType(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return value.getSimpleName() + ".class";
    }
}
