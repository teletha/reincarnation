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

import kiss.I;
import kiss.Signal;
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
     * @param that The node which indicate 'this' variable.
     * @param initializer
     * @param condition
     * @param updater
     * @param inner
     * @param follow
     */
    public For(Node that, Node initializer, Node condition, Node updater, Node inner, Node follow) {
        super(that, that, follow, inner, updater);

        this.initializer = initializer;
        this.condition = condition;
        this.inner = that.process(inner);
        this.updater = new Fragment(updater);
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(updater, inner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> follower() {
        return I.signal(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        coder.writeFor(label(), initializer, condition, updater, () -> {
            if (inner != null) inner.write(coder);
        }, follow);
    }
}
