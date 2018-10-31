/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.statement;

import reincarnation.Node;

/**
 * @version 2018/10/31 13:41:44
 */
public abstract class Loopable extends Breakable {

    /** The super dominator for all nodes in this loop structure. */
    private final Node entrance;

    /** The exit node of this loop structure if present. */
    private final Node exit;

    /** The checkpoint node (i.e. condition or update) of this loop structure if present. */
    private final Node checkpoint;

    /**
     * Build {@link Loopable} block structure.
     * 
     * @param entrance The super dominator for all nodes in this loop structure.
     * @param first The first processing node of this loop structure.
     * @param exit The exit node of this loop structure if present.
     * @param checkpoint The checkpoint node (i.e. condition or update) of this loop structure if
     *            present.
     */
    protected Loopable(Node entrance, Node first, Node exit, Node checkpoint) {
        super(first);

        this.entrance = entrance;
        this.exit = exit;
        this.checkpoint = checkpoint;
    }

}
