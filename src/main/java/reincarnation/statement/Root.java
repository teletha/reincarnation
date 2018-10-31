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

import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 14:45:44
 */
public class Root extends Nestable {

    /**
     * Build the empty root statement.
     */
    public Root() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
    }
}
