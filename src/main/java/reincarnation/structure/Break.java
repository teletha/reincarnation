/*
 * Copyright (C) 2023 The REINCARNATION Development Team
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
import reincarnation.coder.Coder;

public class Break extends Jumpable<Breakable> {

    /** The omit state. */
    protected final Variable<Boolean> hasFollowers = Variable.of(false);

    /**
     * Build break statement.
     * 
     * @param that The node which indicate 'this' variable.
     */
    public Break(Node that, Breakable breakable) {
        super(that, breakable);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        coder.writeBreak(breakable.label(), omitLabel.v);
    }
}