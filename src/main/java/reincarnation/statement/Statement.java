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

import java.util.Optional;

import reincarnation.Node;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/30 11:06:40
 */
public abstract class Statement implements Code {

    /** The empty statement. */
    public static final Statement Empty = new Empty();

    /** The parent structure. */
    protected Statement parent;

    /** The comment. */
    private String comment;

    /** The associated node. */
    private final Node node;

    /**
     * @param node
     */
    protected Statement(Node node) {
        this.node = node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<String> comment() {
        return Optional.ofNullable(comment);
    }

    /**
     * Set comment.
     * 
     * @param comment A comment.
     */
    public final void comment(String comment) {
        this.comment = comment;
    }

    /**
     * @version 2018/11/01 16:29:25
     */
    private static class Empty extends Statement {

        /**
         * @param node
         */
        private Empty() {
            super(null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(Coder coder) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "EmptyStatement";
        }
    }
}
