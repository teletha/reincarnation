/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import reincarnation.coder.Coder;

/**
 * @version 2018/11/01 16:29:25
 */
class Empty extends Structure {

    /**
     */
    Empty() {
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EmptyStatement";
    }
}