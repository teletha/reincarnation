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

import kiss.I;
import kiss.Signal;
import reincarnation.Node;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

/**
 * @version 2018/10/30 11:06:40
 */
public abstract class Structure implements Code {

    /** The empty statement. */
    public static final Structure Empty = new Empty();

    /** The parent structure. */
    private Structure parent;

    /** The comment. */
    private String comment;

    /** The associated node. */
    protected final Node associated;

    /**
     * @param node
     */
    protected Structure(Node node) {
        this.associated = node;

        if (node != null) {
            this.associated.structure = this;
        }
    }

    /**
     * Structurize.
     */
    public void structurize() {
        inner().to(child -> {
            child.parent = this;
            child.structurize();
        });
        follower().to(follow -> {
            follow.parent = parent;
            follow.structurize();
        });
    }

    /**
     * Collec the parent structure.
     * 
     * @return
     */
    public final Signal<Structure> parent() {
        return I.signal(parent).skipNull();
    }

    /**
     * Collec all ancestor structures.
     * 
     * @return
     */
    public final Signal<Structure> ancestor() {
        return I.signal(true, parent, s -> s.flatMap(v -> I.signal(v.parent).skipNull()));
    }

    /**
     * Collect all inner structures.
     * 
     * @return
     */
    public Signal<Structure> inner() {
        return Signal.empty();
    }

    /**
     * Collect all follower structures.
     * 
     * @return
     */
    public Signal<Structure> follower() {
        return Signal.empty();
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
     * {@inheritDoc}
     */
    @Override
    public final void write(Coder coder) {
        if (comment != null && !comment.isBlank()) coder.writeLineComment(comment);
        writeCode(coder);
    }

    /**
     * {@inheritDoc}
     */
    protected abstract void writeCode(Coder coder);
}
