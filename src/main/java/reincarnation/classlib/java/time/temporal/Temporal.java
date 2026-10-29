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
 * The Java implementation of {@link java.time.temporal.Temporal} which is transpiled into
 * JavaScript.
 */
public interface Temporal extends TemporalAccessor {

    /**
     * Check whether the unit is supported.
     * 
     * @param unit A unit.
     * @return A result.
     */
    default boolean isSupported(TemporalUnit unit) {
        return unit != null;
    }

    /**
     * Adjust this temporal.
     * 
     * @param adjuster An adjuster.
     * @return An adjusted temporal.
     */
    default Temporal with(TemporalAdjuster adjuster) {
        return adjuster.adjustInto(this);
    }

    /**
     * Replace the field of this temporal.
     * 
     * @param field A field.
     * @param value A value.
     * @return A temporal.
     */
    default Temporal with(TemporalField field, long value) {
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * Add the amount to this temporal.
     * 
     * @param amount An amount.
     * @return A temporal.
     */
    default Temporal plus(TemporalAmount amount) {
        return amount.addTo(this);
    }

    /**
     * Subtract the amount from this temporal.
     * 
     * @param amount An amount.
     * @return A temporal.
     */
    default Temporal minus(TemporalAmount amount) {
        return amount.subtractFrom(this);
    }

    /**
     * Add the amount of the unit to this temporal.
     * 
     * @param amount An amount.
     * @param unit A unit.
     * @return A temporal.
     */
    Temporal plus(long amount, TemporalUnit unit);

    /**
     * Subtract the amount of the unit from this temporal.
     * 
     * @param amount An amount.
     * @param unit A unit.
     * @return A temporal.
     */
    default Temporal minus(long amount, TemporalUnit unit) {
        return amount == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1L, unit) : plus(-amount, unit);
    }

    /**
     * Compute the amount until the other temporal.
     * 
     * @param end An end temporal.
     * @param unit A unit.
     * @return An amount.
     */
    default long until(Temporal end, TemporalUnit unit) {
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }
}
