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
import kiss.Ⅱ;
import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.coder.Coder;

public class Switch extends Structure {

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
    public Switch(Node that, Operand condition, List<Ⅱ<Integer, Node>> cases, Node defaultCase, Node follow) {
        super(that);

        this.condition = condition;
        this.cases = cases.stream().map(x -> I.pair(x.ⅰ, x.ⅱ.analyze())).toList();
        this.defaultCase = defaultCase.analyze();
        this.follow = that.process(follow);
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
        coder.writeSwitch(condition, cases, defaultCase, follow);
    }
}