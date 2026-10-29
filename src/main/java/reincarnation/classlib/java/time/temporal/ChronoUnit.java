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
 * The Java implementation of {@link java.time.temporal.ChronoUnit} which is transpiled into
 * JavaScript.
 */
public enum ChronoUnit implements TemporalUnit {

    /** Nanoseconds. */
    NANOS,

    /** Microseconds. */
    MICROS,

    /** Milliseconds. */
    MILLIS,

    /** Seconds. */
    SECONDS,

    /** Minutes. */
    MINUTES,

    /** Hours. */
    HOURS,

    /** Half days. */
    HALF_DAYS,

    /** Days. */
    DAYS,

    /** Weeks. */
    WEEKS,

    /** Months. */
    MONTHS,

    /** Years. */
    YEARS,

    /** Decades. */
    DECADES,

    /** Centuries. */
    CENTURIES,

    /** Millennia. */
    MILLENNIA,

    /** Eras. */
    ERAS,

    /** Forever. */
    FOREVER;

    /**
     * {@inheritDoc}
     */
    @Override
    public long between(Temporal start, Temporal end) {
        return start.until(end, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Duration getDuration() {
        if (this == NANOS) {
            return Duration.ofNanos(1L);
        }
        if (this == MICROS) {
            return Duration.ofNanos(1000L);
        }
        if (this == MILLIS) {
            return Duration.ofMillis(1L);
        }
        if (this == SECONDS) {
            return Duration.ofSeconds(1L);
        }
        if (this == MINUTES) {
            return Duration.ofSeconds(60L);
        }
        if (this == HOURS) {
            return Duration.ofSeconds(3600L);
        }
        if (this == HALF_DAYS) {
            return Duration.ofSeconds(43200L);
        }
        if (this == DAYS) {
            return Duration.ofSeconds(86400L);
        }
        throw new UnsupportedOperationException("Unsupported unit: " + this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDateBased() {
        return this == DAYS || this == WEEKS || this == MONTHS || this == YEARS || this == DECADES || this == CENTURIES || this == MILLENNIA || this == ERAS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTimeBased() {
        return this == NANOS || this == MICROS || this == MILLIS || this == SECONDS || this == MINUTES || this == HOURS || this == HALF_DAYS;
    }
}
