/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.statement;

import java.util.LinkedList;

import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Code;

/**
 * @version 2018/10/31 14:48:14
 */
public abstract class Nestable extends Statement {

    /** The parent structure. */
    private Nestable parent;

    /** The children manager. */
    private final LinkedList<Statement> children = new LinkedList();

    /**
     * Add simple statement.
     * 
     * @param code
     */
    public final void add(Code... codes) {
        if (codes != null) {
            children.add(new Following(codes));
        }
    }

    /**
     * Add if statement.
     * 
     * @param condition
     * @param then
     * @param elze
     * @param follow
     * @return
     */
    public final Nestable addIf(Operand condition, Node then, Node elze, Node follow) {
        If child = new If(condition, then, elze, follow);
        children.add(child);

        return child;
    }
}
