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

import kiss.Variable;
import reincarnation.Node;

/**
 * @version 2018/11/11 9:45:58
 */
public abstract class Jumpable<B extends Breakable> extends Structure {

    /** The target. */
    protected final B breakable;

    /** The omit state. */
    protected final Variable<Boolean> omitLabel = Variable.of(false);

    /**
     * @param that The node which indicate 'this' variable.
     * @param breakable The target {@link Breakable} structure to jump.
     */
    protected Jumpable(Node that, B breakable) {
        super(that);

        this.breakable = breakable;
        this.breakable.jumpers.add(this);
    }
}
