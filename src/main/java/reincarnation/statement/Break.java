/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.statement;

import java.util.Optional;

import reincarnation.Node;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/31 0:36:33
 */
public class Break extends Statement {

    /** The target label. */
    private final String label;

    /**
     * Build break statement.
     * 
     * @param label A target name.
     */
    public Break(Node that, String label) {
        super(that);

        this.label = label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        coder.writeBreak(Optional.ofNullable(label));
    }
}
