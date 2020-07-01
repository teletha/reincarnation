/*
 * Copyright (C) 2020 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.structure;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import kiss.I;
import kiss.Signal;
import reincarnation.Node;
import reincarnation.OperandLocalVariable;
import reincarnation.coder.Coder;

/**
 * @version 2018/11/01 9:40:49
 */
public class Fragment extends Structure {

    /** The actual code. */
    private final Node code;

    /** The following structure. */
    private final Structure follow;

    /** The local variable manager. */
    private final Set<OperandLocalVariable> variables = new HashSet();

    /**
     * Code fragment.
     * 
     * @param code
     */
    public Fragment(Node code) {
        this(code, null);
    }

    /**
     * Code fragment.
     * 
     * @param code
     */
    public Fragment(Node code, Structure follow) {
        super(code);

        this.code = Objects.requireNonNull(code);
        this.follow = Objects.requireNonNullElse(follow, Structure.Empty);

        code.children()
                .to(operand -> {
                    // top level opereands MUST NOT be enclosed.
                    operand.disclose();

                    // top level operands MUST be statement.
                    operand.markAsStatement();

                    // collect all local variables
                    operand.descendent().as(OperandLocalVariable.class).to(variables::add);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void analyze() {
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
        code.write(coder);
        follow.write(coder);
    }
}