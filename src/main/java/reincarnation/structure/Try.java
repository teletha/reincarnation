/*
 * Copyright (C) 2019 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.structure;

import java.util.List;

import kiss.I;
import kiss.Signal;
import kiss.Ⅱ;
import reincarnation.Node;
import reincarnation.coder.Coder;

public class Try extends Structure {

    private final Structure tryBlock;

    private final List<Ⅱ<Class, Structure>> catchBlocks;

    private final Structure follow;

    /**
     * @param that
     * @param tryBlock
     * @param catchBlocks
     * @param follow
     */
    public Try(Node that, Node tryBlock, List<Ⅱ<Class, Structure>> catchBlocks, Node follow) {
        super(that);

        this.tryBlock = tryBlock.analyze();
        this.catchBlocks = catchBlocks;
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(tryBlock).merge(I.signal(catchBlocks).map(v -> v.ⅱ())).skipNull();
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
        coder.writeTryCatchFinally(tryBlock, catchBlocks, follow);
    }
}
