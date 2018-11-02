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

import reincarnation.coder.Coder;

/**
 * @version 2018/11/01 16:29:25
 */
class Empty extends Structure {

    /**
     * @param associated
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
    public String toString() {
        return "EmptyStatement";
    }
}