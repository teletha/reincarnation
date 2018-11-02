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

import java.util.Optional;

import reincarnation.Node;

/**
 * @version 2018/10/31 16:06:57
 */
public abstract class Breakable extends Structure {

    /** The first processing node of this block structure. */
    protected final Node first;

    /** The label state. */
    private boolean requireLabel;

    /**
     * Build {@link Breakable} block structure.
     * 
     * @param first The first processing node of this block structure.
     */
    protected Breakable(Node that, Node first) {
        super(that);

        this.first = first;
    }

    /**
     * Set label for this structure.
     */
    public final void requireLabel() {
        requireLabel = true;
    }

    /**
     * Compute label text.
     * 
     * @return
     */
    public final Optional<String> label() {
        return requireLabel ? Optional.of(associated.id) : Optional.empty();
    }
}
