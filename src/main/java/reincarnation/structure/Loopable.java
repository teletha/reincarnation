/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.structure;

import reincarnation.Node;

/**
 * @version 2018/10/31 13:41:44
 */
public abstract class Loopable extends Breakable {

    /** The super dominator for all nodes in this loop structure. */
    public final Node entrance;

    /** The exit node of this loop structure if present. */
    public final Node exit;

    /** The checkpoint node (i.e. condition or update) of this loop structure if present. */
    private final Node checkpoint;

    /**
     * Build {@link Loopable} block structure.
     * 
     * @param entrance The super dominator for all nodes in this loop structure.
     * @param exit The exit node of this loop structure if present.
     * @param first The first processing node of this loop structure.
     * @param checkpoint The checkpoint node (i.e. condition or update) of this loop structure if
     *            present.
     */
    protected Loopable(Node that, Node entrance, Node exit, Node first, Node checkpoint) {
        super(that, first);

        this.entrance = entrance;
        this.exit = exit;
        this.checkpoint = checkpoint;

        if (exit != null) {
            exit.loopExit.let(this);
        }

        if (checkpoint != null) {
            checkpoint.loopHeader.let(this);
        }
    }

    /**
     * Detect whether the specified node is the header of this loop or not.
     * 
     * @param node A target node.
     * @return A result.
     */
    public final boolean containsAsHeader(Node node) {
        return node == entrance || node == checkpoint || node == first;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Loop[entrance=" + entrance.id + ", first=" + first.id + ", exit=" + exit.id + ", check=" + checkpoint.id + "]";
    }
}
