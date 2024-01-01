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

import kiss.I;
import kiss.Signal;
import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Coder;
import reincarnation.util.MultiMap;

/**
 * Represents a switch statement structure in the abstract syntax tree of a Java program.
 */
public class Switch extends Breakable {

    /** The Operand representing the condition to be evaluated. */
    private final Operand condition;

    /** A MultiMap containing the cases and their corresponding keys. */
    private final MultiMap<Structure, Object> cases;

    /** The Node representing the next structure to be executed after the Switch structure. */
    private final Structure follow;

    /**
     * Creates a new instance of Switch with the given parameters.
     * 
     * @param that the Node that represents this Switch structure.
     * @param condition the Operand representing the condition to be evaluated.
     * @param cases a MultiMap containing the cases and their corresponding keys.
     * @param follow the Node representing the next structure to be executed after the Switch
     *            structure.
     */
    public Switch(Node that, Operand condition, MultiMap<Node, Object> cases, Node follow) {
        super(that, that);

        if (follow != null) {
            follow.loopExit.set(this);
            follow.loopExit.fix();
        }

        this.condition = condition;
        this.cases = cases.convertKeys(that::process);
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return cases.keys().skipNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> follower() {
        return I.signal(follow).skipNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        coder.writeSwitch(true, label(), condition, condition.type(), cases, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "switch(" + condition + ")";
    }
}