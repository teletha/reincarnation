/*
 * Copyright (C) 2020 Reincarnation Development Team
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

/**
 * @version 2018/11/11 9:51:58
 */
public class Continue extends Jumpable<Loopable> {

    /** The omit state. */
    protected final Variable<Boolean> hasFollowers = Variable.of(false);

    /**
     * Build continue statement.
     * 
     * @param that The node which indicate 'this' variable.
     * @param loopable A target to continue.
     */
    public Continue(Node that, Loopable loopable) {
        super(that, loopable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void analyze() {
        LinkedList<Structure> ancestors = ancestor().takeUntil(s -> s instanceof Loopable).to(LinkedList.class);

        I.signal(ancestors).skip(breakable).flatMap(Structure::follower).skip(Structure::isEmpty).isEmitted().to(hasFollowers);
        I.signal(ancestors).as(Breakable.class).first().is(s -> s == breakable).to(omitLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        if (hasFollowers.is(true) || omitLabel.is(false)) {
            coder.writeContinue(breakable.label(), omitLabel.v);
        }
    }
}