/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import java.util.Optional;

import reincarnation.Node;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 11:38:43
 */
public class Continue extends Structure {

    /** The target . */
    private final Loopable loopable;

    /**
     * Build continue statement.
     * 
     * @param loopable A target to continue.
     */
    public Continue(Node that, Loopable loopable) {
        super(that);

        this.loopable = loopable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        coder.writeContinue(Optional.ofNullable(loopable.entrance.id));
    }
}
