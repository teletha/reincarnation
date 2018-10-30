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
import reincarnation.coder.Coder;

/**
 * @version 2018/10/27 21:55:57
 */
public class Following extends Statement {

    /** The code. */
    private final Node now;

    /** The following. */
    private final Node follow;

    /**
     * If statement.
     * 
     * @param then
     * @param elze
     */
    public Following(Node now, Node follow) {
        this.now = now;
        this.follow = follow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        now.write(coder);
        follow.write(coder);
    }
}
