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
    private final MultiMap<Structure, Integer> cases;

    /** The Node representing the default case, if any. */
    private final Structure defaultCase;

    /** The Node representing the next structure to be executed after the Switch structure. */
    private final Structure follow;

    /**
     * Creates a new instance of Switch with the given parameters.
     * 
     * @param that the Node that represents this Switch structure.
     * @param condition the Operand representing the condition to be evaluated.
     * @param cases a MultiMap containing the cases and their corresponding keys.
     * @param defaultCase the Node representing the default case, if any.
     * @param follow the Node representing the next structure to be executed after the Switch
     *            structure.
     */
    public Switch(Node that, Operand condition, MultiMap<Node, Integer> cases, Node defaultCase, Node follow) {
        super(that, that);

        follow.loopExit.set(this);
        follow.loopExit.fix();

        this.condition = condition;
        this.cases = cases.convertKeys(that::process);
        this.defaultCase = that.process(defaultCase);
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return cases.keys().startWith(defaultCase).skipNull();
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
        coder.writeSwitch(label(), condition, condition.type(), cases, defaultCase, follow);
    }
}