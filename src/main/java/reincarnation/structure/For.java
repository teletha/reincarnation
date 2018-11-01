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
import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 0:27:20
 */
public class For extends Loopable {

    /** The code. */
    private final Node initializer;

    /** The code. */
    private final Node condition;

    /** The code. */
    private final Structure updater;

    /** The code. */
    private final Structure inner;

    /** The following. */
    private final Structure follow;

    /**
     * For statement.
     * 
     * @param initializer
     * @param condition
     * @param updater
     * @param inner
     * @param follow
     */
    public For(Node that, Node initializer, Node condition, Node updater, Node inner, Node follow) {
        super(that, that, inner, follow, updater);

        this.initializer = initializer;
        this.condition = condition;
        this.updater = that.process(updater);
        this.inner = that.process(inner);
        this.follow = that.process(follow);
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
