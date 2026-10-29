/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.time.temporal;

import reincarnation.classlib.java.time.Duration;

/**
 * The Java implementation of {@link java.time.temporal.TemporalUnit} which is transpiled into
 * JavaScript.
 */
public interface TemporalUnit {

    /**
     * Compute the amount between the temporals.
     * 
     * @param start A start temporal.
     * @param end An end temporal.
     * @return An amount.
     */
    default long between(Temporal start, Temporal end) {
        return start.until(end, this);
    }

    /**
     * Get the duration of this unit.
     * 
     * @return A duration.
     */
    default Duration getDuration() {
        throw new UnsupportedOperationException("Unsupported unit: " + this);
    }

    /**
     * Check whether this unit is date based.
     * 
     * @return A result.
     */
    default boolean isDateBased() {
        return false;
    }

    /**
     * Check whether this unit is time based.
     * 
     * @return A result.
     */
    default boolean isTimeBased() {
        return false;
    }
}
