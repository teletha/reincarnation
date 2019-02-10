/*
 * Copyright (C) 2019 Reincarnation Development Team
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
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/11/05 12:47:50
 */
public class DoWhile extends Loopable {

    /** The code. */
    private final Code condition;

    /** The code. */
    private final Structure inner;

    /** The following. */
    private final Structure follow;

    /**
     * Do-While statement.
     * 
     * @param that The node which indicate 'this' variable.
     * @param inner
     * @param elze
     */
    public DoWhile(Node that, Node condition, Node inner, Node follow) {
        super(that, inner, follow, inner, condition);

        this.condition = condition;
        this.inner = that.process(inner);
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(inner);
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
        coder.writeDoWhile(label(), condition, () -> {
            if (inner != null) {
                inner.write(coder);
            }
        }, follow);
    }
}
