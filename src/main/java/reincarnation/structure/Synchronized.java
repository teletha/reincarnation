/*
 * Copyright (C) 2026 The REINCARNATION Development Team
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
 * A synchronized block.
 */
public class Synchronized extends Structure {

    /** The lock object. */
    private final Code lock;

    /** The synchronized body. */
    private final Structure inner;

    /** The following structure. */
    private final Structure follow;

    /**
     * @param that The node which indicate 'this' variable.
     * @param lock A lock object.
     * @param inner A synchronized body.
     * @param follow A following structure.
     */
    public Synchronized(Node that, Code lock, Node inner, Node follow) {
        super(that);

        this.lock = lock;
        this.inner = inner.analyze();
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(inner).skipNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> follower() {
        return I.signal(follow).skipNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeSynchronized(lock, inner, follow);
    }
}
