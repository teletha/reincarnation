/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import reincarnation.Node;
import reincarnation.coder.Coder;

public class Continue extends Jumpable<Loopable> {

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
    public void writeCode(Coder coder) {
        if (hasFollowers.is(true) || omitLabel.is(false)) {
            coder.writeContinue(breakable.label(), omitLabel.v);
        }
    }
}