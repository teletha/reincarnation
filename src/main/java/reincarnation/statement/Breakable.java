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
 * @version 2018/10/31 16:06:57
 */
public abstract class Breakable extends Nestable {

    /** The first processing node of this block structure. */
    protected final Node first;

    /**
     * Build {@link Breakable} block structure.
     * 
     * @param first The first processing node of this block structure.
     */
    protected Breakable(Node that, Node first) {
        super(that);

        this.first = first;
    }
}
