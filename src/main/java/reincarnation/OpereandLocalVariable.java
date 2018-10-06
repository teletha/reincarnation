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

import com.github.javaparser.ast.expr.Expression;

/**
 * @version 2018/10/06 18:23:55
 */
public class OpereandLocalVariable extends Operand {

    /** The local variable name. */
    private String name;

    /**
     * Build local variable.
     * 
     * @param name
     */
    OpereandLocalVariable(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Expression build() {
    }
}
