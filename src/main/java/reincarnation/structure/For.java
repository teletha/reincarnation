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
import kiss.Variable;
import kiss.Ⅱ;
import reincarnation.Node;
import reincarnation.Operand;
import reincarnation.OperandLocalVariable;
import reincarnation.coder.Coder;

public class For extends Loopable {

    /** The code. */
    private final Node initializer;

    /** The code. */
    private final Node condition;

    /** The code. */
    private final Structure updater;

    /** The code. */
    private final Structure inner;

    /** The following. */
    private final Structure follow;

    /** The special for loop. */
    private final Variable<Ⅱ<Operand, OperandLocalVariable>> enhanced;

    /**
     * For statement.
     * 
     * @param that The node which indicate 'this' variable.
     * @param initializer
     * @param condition
     * @param updater
     * @param inner
     * @param follow
     */
    public For(Node that, Node initializer, Node condition, Node updater, Node inner, Node follow, Variable<Ⅱ<Operand, OperandLocalVariable>> variable) {
        super(that, that, follow, inner, updater);

        this.initializer = initializer;
        this.condition = condition;
        this.inner = that.process(inner);
        this.updater = new Fragment(updater);
        this.follow = that.process(follow);
        this.enhanced = variable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(updater, inner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> follower() {
        return I.signal(follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCode(Coder coder) {
        enhanced.to(x -> {
            coder.writeIterableFor(label(), x.ⅱ, x.ⅰ, () -> {
                if (inner != null) inner.write(coder);
                if (updater != null) updater.write(coder);
            }, follow);
        }, () -> {
            coder.writeFor(label(), initializer, condition, updater, () -> {
                if (inner != null) inner.write(coder);
            }, follow);
        });
    }
}