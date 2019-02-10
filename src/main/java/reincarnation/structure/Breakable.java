/*
 * Copyright (C) 2019 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import kiss.I;
import kiss.Variable;
import reincarnation.Node;

/**
 * @version 2018/10/31 16:06:57
 */
public abstract class Breakable extends Structure {

    /** The first processing node of this block structure. */
    protected final Node first;

    /** The associated jumpers. */
    protected final Set<Jumpable<? extends Breakable>> jumpers = new HashSet();

    /**
     * Build {@link Breakable} block structure.
     * 
     * @param that The node which indicate 'this' variable.
     * @param first The first processing node of this block structure.
     */
    protected Breakable(Node that, Node first) {
        super(that);

        this.first = first;
    }

    /**
     * Compute label text.
     * 
     * @return
     */
    protected final Optional<String> label() {
        Variable<Boolean> requireLabel = I.signal(jumpers).any(jumper -> jumper.omitLabel.is(false)).to();

        return requireLabel.v ? Optional.of(associated.id) : Optional.empty();
    }
}
