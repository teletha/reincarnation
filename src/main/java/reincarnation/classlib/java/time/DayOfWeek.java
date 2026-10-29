/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.time;

import reincarnation.classlib.java.time.temporal.ChronoField;
import reincarnation.classlib.java.time.temporal.TemporalAccessor;

/**
 * The Java implementation of {@link java.time.DayOfWeek} which is transpiled into JavaScript.
 */
public enum DayOfWeek {

    /** Monday. */
    MONDAY,

    /** Tuesday. */
    TUESDAY,

    /** Wednesday. */
    WEDNESDAY,

    /** Thursday. */
    THURSDAY,

    /** Friday. */
    FRIDAY,

    /** Saturday. */
    SATURDAY,

    /** Sunday. */
    SUNDAY;

    /**
     * Get the day of week from the temporal.
     * 
     * @param temporal A temporal.
     * @return A day of week.
     */
    public static DayOfWeek from(TemporalAccessor temporal) {
        return of(temporal.get(ChronoField.DAY_OF_WEEK));
    }

    /**
     * Get the day of week of the value.
     * 
     * @param dayOfWeek A day of week value.
     * @return A day of week.
     */
    public static DayOfWeek of(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new IllegalArgumentException("Invalid value for DayOfWeek: " + dayOfWeek);
        }
        return values()[dayOfWeek - 1];
    }

    /**
     * Get the day of week value.
     * 
     * @return A day of week value.
     */
    public int getValue() {
        return ordinal() + 1;
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A day of week.
     */
    public DayOfWeek plus(long days) {
        int amount = (int) (days % 7L);
        int value = (ordinal() + amount) % 7;
        if (value < 0) {
            value += 7;
        }
        return values()[value];
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A day of week.
     */
    public DayOfWeek minus(long days) {
        return plus(-(days % 7L));
    }
}
