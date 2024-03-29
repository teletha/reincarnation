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

import java.util.Optional;

import kiss.I;
import kiss.Signal;
import reincarnation.Node;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

public abstract class Structure implements Code<Structure> {

    /** The empty statement. */
    public static final Structure Empty = new Empty();

    /** The associated node. */
    protected final Node associated;

    /** The parent structure. */
    private Structure parent;

    /** The comment. */
    private String comment;

    /**
     * @param that The node which indicate 'this' variable.
     */
    protected Structure(Node that) {
        this.associated = that;

        if (that != null) {
            this.associated.structure = this;
        }
    }

    /**
     * Structurize.
     */
    public final void structurize() {
        children().to(child -> {
            child.parent = this;
            child.structurize();
        });
        follower().to(follow -> {
            follow.parent = parent;
            follow.structurize();
        });
        analyze();
    }

    /**
     * Collect the parent structure.
     * 
     * @return
     */
    public final Signal<Structure> parent() {
        return I.signal(parent).skipNull();
    }

    /**
     * Collect all ancestor structures.
     * 
     * @return
     */
    public final Signal<Structure> ancestor() {
        return I.signal(parent).recurseMap(s -> s.skipNull().flatMap(v -> I.signal(v.parent).skipNull()));
    }

    /**
     * Collect all inner structures.
     * 
     * @return
     */
    @Override
    public Signal<Structure> children() {
        return I.signal();
    }

    /**
     * Find all follower {@link Code} fragments.
     * 
     * @return
     */
    public Signal<Structure> follower() {
        return I.signal();
    }

    /**
     * Test whether this code has followers or not.
     * 
     * @return
     */
    public boolean hasFollower() {
        return follower().skip(Empty).to().isPresent();
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
     * Analyze this {@link Structure} only once.
     */
    protected void analyze() {
        // do nothing
    }

    /**
     * Write code actually.
     * 
     * @param coder A target {@link Coder}.
     */
    protected abstract void writeCode(Coder coder);
}