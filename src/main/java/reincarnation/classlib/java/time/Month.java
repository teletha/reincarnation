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
 * The Java implementation of {@link java.time.Month} which is transpiled into JavaScript.
 */
public enum Month {

    /** January. */
    JANUARY,

    /** February. */
    FEBRUARY,

    /** March. */
    MARCH,

    /** April. */
    APRIL,

    /** May. */
    MAY,

    /** June. */
    JUNE,

    /** July. */
    JULY,

    /** August. */
    AUGUST,

    /** September. */
    SEPTEMBER,

    /** October. */
    OCTOBER,

    /** November. */
    NOVEMBER,

    /** December. */
    DECEMBER;

    /**
     * Get the month from the temporal.
     * 
     * @param temporal A temporal.
     * @return A month.
     */
    public static Month from(TemporalAccessor temporal) {
        return of(temporal.get(ChronoField.MONTH_OF_YEAR));
    }

    /**
     * Get the month of the value.
     * 
     * @param month A month value.
     * @return A month.
     */
    public static Month of(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid value for MonthOfYear: " + month);
        }
        return values()[month - 1];
    }

    /**
     * Get the month value.
     * 
     * @return A month value.
     */
    public int getValue() {
        return ordinal() + 1;
    }

    /**
     * Add the months.
     * 
     * @param months Months.
     * @return A month.
     */
    public Month plus(long months) {
        int amount = (int) (months % 12L);
        int value = (ordinal() + amount) % 12;
        if (value < 0) {
            value += 12;
        }
        return values()[value];
    }

    /**
     * Subtract the months.
     * 
     * @param months Months.
     * @return A month.
     */
    public Month minus(long months) {
        return plus(-(months % 12L));
    }

    /**
     * Get the length of this month.
     * 
     * @param leapYear Whether the year is a leap year.
     * @return A length.
     */
    public int length(boolean leapYear) {
        if (this == FEBRUARY) {
            return leapYear ? 29 : 28;
        }
        if (this == APRIL || this == JUNE || this == SEPTEMBER || this == NOVEMBER) {
            return 30;
        }
        return 31;
    }

    /**
     * Get the first month of the quarter.
     * 
     * @return A month.
     */
    public Month firstMonthOfQuarter() {
        if (ordinal() < 3) {
            return JANUARY;
        }
        if (ordinal() < 6) {
            return APRIL;
        }
        if (ordinal() < 9) {
            return JULY;
        }
        return OCTOBER;
    }
}
