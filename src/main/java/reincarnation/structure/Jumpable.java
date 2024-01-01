/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import java.util.LinkedList;

import kiss.I;
import kiss.Variable;
import reincarnation.Node;

public abstract class Jumpable<B extends Breakable> extends Structure {

    /** The target. */
    protected final B breakable;

    /** The omit state. */
    protected final Variable<Boolean> omitLabel = Variable.of(false);

    /** The omit state. */
    protected final Variable<Boolean> hasFollowers = Variable.of(false);

    /**
     * @param that The node which indicate 'this' variable.
     * @param breakable The target {@link Breakable} structure to jump.
     */
    protected Jumpable(Node that, B breakable) {
        super(that);

        this.breakable = breakable;
        this.breakable.jumpers.add(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void analyze() {
        LinkedList<Structure> ancestors = ancestor().takeUntil(s -> s instanceof Loopable).toCollection(new LinkedList());

        I.signal(ancestors).skip(breakable).flatMap(Structure::follower).skip(Structure::isEmpty).isEmitted().to(hasFollowers);
        I.signal(ancestors).as(Breakable.class).first().is(s -> s == breakable).to(omitLabel);
    }
}