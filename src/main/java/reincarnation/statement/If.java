/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.statement;

import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/27 21:55:57
 */
public class If extends Nestable {

    /** The code. */
    private final Operand condition;

    /** The code. */
    private final Node then;

    /** The code. */
    private final Node elze;

    /** The following. */
    private final Node follow;

    /**
     * If statement.
     * 
     * @param condition
     * @param then
     * @param elze
     */
    public If(Operand condition, Node then, Node elze, Node follow) {
        this.condition = condition;
        this.then = then;
        this.elze = elze;
        this.follow = follow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeIf(condition, then, elze, follow);
    }
}
