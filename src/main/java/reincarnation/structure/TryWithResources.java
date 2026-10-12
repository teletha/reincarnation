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

import java.util.List;

import kiss.I;
import kiss.Signal;
import kiss.Ⅱ;
import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Coder;

/**
 * A try statement with resources (try-with-resources), such as
 * {@code try (InputStream in = new ...) { ... }}.
 */
public class TryWithResources extends Structure {

    /** The resource declaration operands (variable, initializer). */
    private final List<Ⅱ<Operand, Operand>> resources;

    /** The try block. */
    private final Structure tryBlock;

    /** The following structure. */
    private final Structure follow;

    /**
     * Get the resource declarations of this try-with-resources.
     * 
     * @return The resources as (variable, initializer) pairs.
     */
    public List<Ⅱ<Operand, Operand>> getResources() {
        return resources;
    }

    /**
     * Get the try block of this try-with-resources.
     * 
     * @return The try block.
     */
    public Structure getTryBlock() {
        return tryBlock;
    }

    /**
     * Get the following structure of this try-with-resources.
     * 
     * @return The following structure.
     */
    public Structure getFollow() {
        return follow;
    }

    /**
     * @param that The node which indicate 'this' variable.
     * @param resources The resources as (variable, initializer) pairs.
     * @param tryBlock The try block body.
     * @param follow The following structure.
     */
    public TryWithResources(Node that, List<Ⅱ<Operand, Operand>> resources, Structure tryBlock, Structure follow) {
        super(that);

        this.resources = resources;
        this.tryBlock = tryBlock;
        this.follow = follow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(tryBlock).skipNull();
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
        coder.writeTryWithResources(resources, tryBlock, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "try(" + resources + ")";
    }
}