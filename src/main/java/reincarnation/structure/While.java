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
import reincarnation.coder.Code;
import reincarnation.coder.Coder;

public class While extends Loopable {

    /** The code. */
    private final Code condition;

    /** The code. */
    private final Structure inner;

    /** The following. */
    private final Structure follow;

    /** The special for loop. */
    private final Variable<Ⅱ<Operand, OperandLocalVariable>> enhanced;

    /**
     * While statement.
     * 
     * @param that The node which indicate 'this' variable.
     * @param inner
     */
    public While(Node that, Node condition, Node inner, Node follow, Variable<Ⅱ<Operand, OperandLocalVariable>> variable) {
        super(that, condition, follow, inner, condition);

        this.condition = condition;
        this.inner = that.process(inner);
        this.follow = that.process(follow);
        this.enhanced = variable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Structure> children() {
        return I.signal(inner);
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
                if (inner != null) {
                    inner.write(coder);
                }
            }, follow);
        }, () -> {
            coder.writeWhile(label(), condition, () -> {
                if (inner != null) {
                    inner.write(coder);
                }
            }, follow);
        });
    }
}