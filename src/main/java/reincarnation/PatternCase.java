/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

/**
 * A case label of pattern matching for switch (JDK 21), such as {@code case String s when
 * s.length() > 3}, {@code case Integer i} or {@code case null}.
 */
public final class PatternCase {

    /** The special pattern for the null case. */
    public static final PatternCase NULL = new PatternCase(null, null, null);

    /** The pattern type. It is null for the null case. */
    public final Class type;

    /** The pattern variable bound to the matched value. It is null for the null case. */
    public final OperandLocalVariable variable;

    /** The guard condition (the {@code when} clause) of this case. It is null if unguarded. */
    public final Operand guard;

    PatternCase(Class type, OperandLocalVariable variable, Operand guard) {
        this.type = type;
        this.variable = variable;
        this.guard = guard;
    }

    /**
     * Test whether this case is the null case or not.
     * 
     * @return A result.
     */
    public boolean isNull() {
        return type == null;
    }
}
