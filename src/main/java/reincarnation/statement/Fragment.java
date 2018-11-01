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

import java.util.Objects;

import reincarnation.Node;
import reincarnation.coder.Coder;

/**
 * @version 2018/11/01 9:40:49
 */
public class Fragment extends Statement {

    /** The actual code. */
    private final Node code;

    /** The following statement. */
    private final Statement follow;

    /**
     * Code fragment.
     * 
     * @param code
     */
    public Fragment(Node code) {
        this(code, null);
    }

    /**
     * Code fragment.
     * 
     * @param code
     */
    public Fragment(Node code, Statement follow) {
        super(code);

        this.code = Objects.requireNonNull(code);
        this.follow = Objects.requireNonNullElse(follow, Statement.Empty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        code.write(coder);
        follow.write(coder);
    }
}
