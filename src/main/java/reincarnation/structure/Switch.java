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
import reincarnation.coder.Coder;

public class Switch extends Structure {

    private final Node condition;

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
    public Switch(Node that, Node condition, List<Ⅱ<Integer, Structure>> cases, Node defaultCase, Node follow) {
        super(that);

        this.condition = condition;
        this.cases = cases;
        this.defaultCase = defaultCase.analyze();
        this.follow = that.process(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(defaultCase).merge(I.signal(cases).map(v -> v.ⅱ)).skipNull();
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
        coder.writeSwitch(condition, cases.stream().map(Ⅱ::ⅰ).toList(), cases.stream().map(Ⅱ::ⅱ).toList(), defaultCase);
        follow.writeCode(coder);
    }
}