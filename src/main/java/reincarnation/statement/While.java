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
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/27 21:55:57
 */
public class While extends Statement {

    /** The code. */
    private final Code condition;

    /** The code. */
    private final Node inner;

    /** The following. */
    private final Node follow;

    /**
     * While statement.
     * 
     * @param inner
     * @param elze
     */
    public While(Code condition, Node inner, Node follow) {
        this.condition = condition;
        this.inner = inner;
        this.follow = follow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeWhile(condition, () -> {
            if (inner != null) {
                inner.write(coder);
            }
        }, follow);
    }
}
