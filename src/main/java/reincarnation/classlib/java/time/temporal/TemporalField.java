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

/**
 * The Java implementation of {@link java.time.temporal.TemporalField} which is transpiled into
 * JavaScript.
 */
public interface TemporalField {

    /**
     * Get the value of this field from the temporal.
     * 
     * @param temporal A temporal.
     * @return A value.
     */
    default long getFrom(TemporalAccessor temporal) {
        return temporal.getLong(this);
    }

    /**
     * Replace this field of the temporal.
     * 
     * @param temporal A temporal.
     * @param value A value.
     * @return A temporal.
     */
    default Temporal adjustInto(Temporal temporal, long value) {
        return temporal.with(this, value);
    }

    /**
     * Check whether this field is date based.
     * 
     * @return A result.
     */
    default boolean isDateBased() {
        return false;
    }

    /**
     * Check whether this field is time based.
     * 
     * @return A result.
     */
    default boolean isTimeBased() {
        return false;
    }
}
