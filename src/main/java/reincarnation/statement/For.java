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

import java.util.List;

import reincarnation.Node;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 0:27:20
 */
public class For extends Statement {

    /** The code. */
    private final Code initializer;

    /** The code. */
    private final Code condition;

    /** The code. */
    private final List<? extends Code> updater;

    /** The code. */
    private final Node inner;

    /** The following. */
    private final Node follow;

    /**
     * For statement.
     * 
     * @param initializer
     * @param condition
     * @param updater
     * @param inner
     * @param follow
     */
    public For(Code initializer, Code condition, List<? extends Code> updater, Node inner, Node follow) {
        this.initializer = initializer;
        this.condition = condition;
        this.updater = updater;
        this.inner = inner;
        this.follow = follow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeFor(initializer, condition, updater, () -> {
            if (inner != null) inner.write(coder);
        }, follow);
    }
}
