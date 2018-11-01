/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

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
    private final Structure then;

    /** The code. */
    private final Structure elze;

    /** The following. */
    private final Structure follow;

    /**
     * If statement.
     * 
     * @param condition
     * @param then
     * @param elze
     */
    public If(Node that, Operand condition, Node then, Node elze, Node follow) {
        super(that);

        this.condition = condition;
        this.then = that.process(then);
        this.elze = that.process(elze);
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeIf(condition, then, elze, follow);
    }
}
