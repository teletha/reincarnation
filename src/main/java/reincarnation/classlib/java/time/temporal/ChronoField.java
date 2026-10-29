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
 * The Java implementation of {@link java.time.temporal.ChronoField} which is transpiled into
 * JavaScript.
 */
public enum ChronoField implements TemporalField {

    /** Nanosecond of second. */
    NANO_OF_SECOND,

    /** Nanosecond of day. */
    NANO_OF_DAY,

    /** Microsecond of second. */
    MICRO_OF_SECOND,

    /** Microsecond of day. */
    MICRO_OF_DAY,

    /** Millisecond of second. */
    MILLI_OF_SECOND,

    /** Millisecond of day. */
    MILLI_OF_DAY,

    /** Second of minute. */
    SECOND_OF_MINUTE,

    /** Second of day. */
    SECOND_OF_DAY,

    /** Minute of hour. */
    MINUTE_OF_HOUR,

    /** Minute of day. */
    MINUTE_OF_DAY,

    /** Hour of am/pm. */
    HOUR_OF_AMPM,

    /** Clock hour of am/pm. */
    CLOCK_HOUR_OF_AMPM,

    /** Hour of day. */
    HOUR_OF_DAY,

    /** Clock hour of day. */
    CLOCK_HOUR_OF_DAY,

    /** Am/pm of day. */
    AMPM_OF_DAY,

    /** Day of week. */
    DAY_OF_WEEK,

    /** Aligned day of week in month. */
    ALIGNED_DAY_OF_WEEK_IN_MONTH,

    /** Aligned day of week in year. */
    ALIGNED_DAY_OF_WEEK_IN_YEAR,

    /** Day of month. */
    DAY_OF_MONTH,

    /** Day of year. */
    DAY_OF_YEAR,

    /** Epoch day. */
    EPOCH_DAY,

    /** Aligned week of month. */
    ALIGNED_WEEK_OF_MONTH,

    /** Aligned week of year. */
    ALIGNED_WEEK_OF_YEAR,

    /** Month of year. */
    MONTH_OF_YEAR,

    /** Proleptic month. */
    PROLEPTIC_MONTH,

    /** Year of era. */
    YEAR_OF_ERA,

    /** Year. */
    YEAR,

    /** Era. */
    ERA,

    /** Instant seconds. */
    INSTANT_SECONDS,

    /** Offset seconds. */
    OFFSET_SECONDS;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getFrom(TemporalAccessor temporal) {
        return temporal.getLong(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDateBased() {
        return ordinal() >= DAY_OF_WEEK.ordinal() && ordinal() <= ERA.ordinal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTimeBased() {
        return ordinal() <= AMPM_OF_DAY.ordinal();
    }
}
