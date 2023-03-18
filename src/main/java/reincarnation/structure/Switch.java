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

import java.util.List;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import kiss.Ⅱ;
import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Coder;

public class Switch extends Breakable {

    private final Operand condition;

    private final List<Ⅱ<Integer, Structure>> cases;

    private final Structure defaultCase;

    private final Structure follow;

    /**
     * @param that
     * @param condition
     * @param cases
     * @param defaultCase
     * @param follow
     */
    public Switch(Node that, Operand condition, List<Ⅱ<Integer, Node>> cases, Node defaultCase, Variable<Node> follow) {
        super(that, that);

        follow.to(node -> {
            node.loopExit.set(this);
            node.loopExit.fix();
        });

        this.condition = condition;
        this.cases = cases.stream().map(x -> I.pair(x.ⅰ, that.process(x.ⅱ))).toList();
        this.defaultCase = defaultCase == null ? null : that.process(defaultCase);
        this.follow = follow.isAbsent() ? null : follow.v.analyze();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(cases).map(Ⅱ::ⅱ).startWith(defaultCase).skipNull();
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
        coder.writeSwitch(label(), condition, cases, defaultCase, follow);
    }
}