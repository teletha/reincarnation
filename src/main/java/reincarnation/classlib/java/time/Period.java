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

import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.util.List;
import reincarnation.classlib.java.time.temporal.ChronoUnit;
import reincarnation.classlib.java.time.temporal.Temporal;
import reincarnation.classlib.java.time.temporal.TemporalAmount;
import reincarnation.classlib.java.time.temporal.TemporalUnit;

/**
 * The Java implementation of {@link java.time.Period} which is transpiled into JavaScript.
 */
public final class Period implements TemporalAmount, Serializable {

    /** The zero period. */
    public static final Period ZERO = new Period(0, 0, 0);

    /** The years. */
    private final int years;

    /** The months. */
    private final int months;

    /** The days. */
    private final int days;

    /**
     * Create the period.
     * 
     * @param years Years.
     * @param months Months.
     * @param days Days.
     */
    private Period(int years, int months, int days) {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    /**
     * Create the period.
     * 
     * @param years Years.
     * @param months Months.
     * @param days Days.
     * @return A period.
     */
    public static Period of(int years, int months, int days) {
        return new Period(years, months, days);
    }

    /**
     * Create the period of years.
     * 
     * @param years Years.
     * @return A period.
     */
    public static Period ofYears(int years) {
        return of(years, 0, 0);
    }

    /**
     * Create the period of months.
     * 
     * @param months Months.
     * @return A period.
     */
    public static Period ofMonths(int months) {
        return of(0, months, 0);
    }

    /**
     * Create the period of weeks.
     * 
     * @param weeks Weeks.
     * @return A period.
     */
    public static Period ofWeeks(int weeks) {
        return of(0, 0, weeks * 7);
    }

    /**
     * Create the period of days.
     * 
     * @param days Days.
     * @return A period.
     */
    public static Period ofDays(int days) {
        return of(0, 0, days);
    }

    /**
     * Compute the period between the dates.
     * 
     * @param start A start date.
     * @param end An end date.
     * @return A period.
     */
    public static Period between(LocalDate start, LocalDate end) {
        long totalMonths = end.prolepticMonth() - start.prolepticMonth();
        LocalDate date = start.plusMonths(totalMonths);
        if (date.compareTo(end) > 0) {
            totalMonths--;
            date = start.plusMonths(totalMonths);
        }
        int days = (int) (end.toEpochDay() - date.toEpochDay());
        return of((int) (totalMonths / 12L), (int) (totalMonths % 12L), days);
    }

    /**
     * Get the years.
     * 
     * @return Years.
     */
    public int getYears() {
        return years;
    }

    /**
     * Get the months.
     * 
     * @return Months.
     */
    public int getMonths() {
        return months;
    }

    /**
     * Get the days.
     * 
     * @return Days.
     */
    public int getDays() {
        return days;
    }

    /**
     * Check whether this period is zero.
     * 
     * @return A result.
     */
    public boolean isZero() {
        return years == 0 && months == 0 && days == 0;
    }

    /**
     * Check whether this period is negative.
     * 
     * @return A result.
     */
    public boolean isNegative() {
        return years < 0 || months < 0 || days < 0;
    }

    /**
     * Replace the years.
     * 
     * @param years Years.
     * @return A period.
     */
    public Period withYears(int years) {
        return of(years, months, days);
    }

    /**
     * Replace the months.
     * 
     * @param months Months.
     * @return A period.
     */
    public Period withMonths(int months) {
        return of(years, months, days);
    }

    /**
     * Replace the days.
     * 
     * @param days Days.
     * @return A period.
     */
    public Period withDays(int days) {
        return of(years, months, days);
    }

    /**
     * Add the period.
     * 
     * @param other A period.
     * @return A period.
     */
    public Period plus(Period other) {
        return of(years + other.years, months + other.months, days + other.days);
    }

    /**
     * Add the years.
     * 
     * @param years Years.
     * @return A period.
     */
    public Period plusYears(long years) {
        return of((int) (this.years + years), months, days);
    }

    /**
     * Add the months.
     * 
     * @param months Months.
     * @return A period.
     */
    public Period plusMonths(long months) {
        return of(years, (int) (this.months + months), days);
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A period.
     */
    public Period plusDays(long days) {
        return of(years, months, (int) (this.days + days));
    }

    /**
     * Subtract the period.
     * 
     * @param other A period.
     * @return A period.
     */
    public Period minus(Period other) {
        return of(years - other.years, months - other.months, days - other.days);
    }

    /**
     * Subtract the years.
     * 
     * @param years Years.
     * @return A period.
     */
    public Period minusYears(long years) {
        return of((int) (this.years - years), months, days);
    }

    /**
     * Subtract the months.
     * 
     * @param months Months.
     * @return A period.
     */
    public Period minusMonths(long months) {
        return of(years, (int) (this.months - months), days);
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A period.
     */
    public Period minusDays(long days) {
        return of(years, months, (int) (this.days - days));
    }

    /**
     * Multiply this period.
     * 
     * @param scalar A scalar.
     * @return A period.
     */
    public Period multipliedBy(int scalar) {
        return of(years * scalar, months * scalar, days * scalar);
    }

    /**
     * Negate this period.
     * 
     * @return A period.
     */
    public Period negated() {
        return multipliedBy(-1);
    }

    /**
     * Normalize this period.
     * 
     * @return A period.
     */
    public Period normalized() {
        long totalMonths = toTotalMonths();
        return of((int) (totalMonths / 12L), (int) (totalMonths % 12L), days);
    }

    /**
     * Get the total months.
     * 
     * @return Total months.
     */
    public long toTotalMonths() {
        return years * 12L + months;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long get(TemporalUnit unit) {
        if (unit == ChronoUnit.YEARS) {
            return years;
        }
        if (unit == ChronoUnit.MONTHS) {
            return months;
        }
        if (unit == ChronoUnit.DAYS) {
            return days;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TemporalUnit> getUnits() {
        return List.of(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal addTo(Temporal temporal) {
        return temporal.plus(years, ChronoUnit.YEARS).plus(months, ChronoUnit.MONTHS).plus(days, ChronoUnit.DAYS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return temporal.minus(years, ChronoUnit.YEARS).minus(months, ChronoUnit.MONTHS).minus(days, ChronoUnit.DAYS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Period period && years == period.years && months == period.months && days == period.days;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = years;
        result += (months << 8) | (months >>> 24);
        result += (days << 16) | (days >>> 16);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (isZero()) {
            return "P0D";
        }
        StringBuilder builder = new StringBuilder("P");
        if (years != 0) {
            builder.append(years).append('Y');
        }
        if (months != 0) {
            builder.append(months).append('M');
        }
        if (days != 0) {
            builder.append(days).append('D');
        }
        return builder.toString();
    }
}
