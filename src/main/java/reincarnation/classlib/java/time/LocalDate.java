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

import js.lang.NativeTemporal;
import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Comparable;
import reincarnation.classlib.java.time.temporal.ChronoField;
import reincarnation.classlib.java.time.temporal.ChronoUnit;
import reincarnation.classlib.java.time.temporal.Temporal;
import reincarnation.classlib.java.time.temporal.TemporalAccessor;
import reincarnation.classlib.java.time.temporal.TemporalAdjuster;
import reincarnation.classlib.java.time.temporal.TemporalField;
import reincarnation.classlib.java.time.temporal.TemporalUnit;

/**
 * The Java implementation of {@link java.time.LocalDate} which is transpiled into JavaScript.
 */
public final class LocalDate implements Temporal, TemporalAdjuster, Comparable<LocalDate>, Serializable {

    /** The native Temporal date. */
    private final Object value;

    /**
     * Create the date.
     * 
     * @param value A native value.
     */
    LocalDate(Object value) {
        this.value = value;
    }

    /**
     * Get the current date.
     * 
     * @return A date.
     */
    public static LocalDate now() {
        return new LocalDate(NativeTemporal.dateNow());
    }

    /**
     * Create the date.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @return A date.
     */
    public static LocalDate of(int year, int month, int day) {
        return new LocalDate(NativeTemporal.dateOf(year, month, day));
    }

    /**
     * Create the date from the epoch day.
     * 
     * @param epochDay An epoch day.
     * @return A date.
     */
    public static LocalDate ofEpochDay(long epochDay) {
        return new LocalDate(NativeTemporal.dateOfEpochDay(epochDay));
    }

    /**
     * Parse the date.
     * 
     * @param text A text.
     * @return A date.
     */
    public static LocalDate parse(CharSequence text) {
        return new LocalDate(NativeTemporal.dateFrom(text.toString()));
    }

    /**
     * Get the year.
     * 
     * @return A year.
     */
    public int getYear() {
        return NativeTemporal.dateYear(value);
    }

    /**
     * Get the month value.
     * 
     * @return A month value.
     */
    public int getMonthValue() {
        return NativeTemporal.dateMonth(value);
    }

    /**
     * Get the month.
     * 
     * @return A month.
     */
    public Month getMonth() {
        return Month.of(getMonthValue());
    }

    /**
     * Get the day of month.
     * 
     * @return A day of month.
     */
    public int getDayOfMonth() {
        return NativeTemporal.dateDay(value);
    }

    /**
     * Get the day of year.
     * 
     * @return A day of year.
     */
    public int getDayOfYear() {
        return NativeTemporal.dateDayOfYear(value);
    }

    /**
     * Get the day of week.
     * 
     * @return A day of week.
     */
    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.of(NativeTemporal.dateDayOfWeek(value));
    }

    /**
     * Check whether the year is a leap year.
     * 
     * @return A result.
     */
    public boolean isLeapYear() {
        return NativeTemporal.dateInLeapYear(value);
    }

    /**
     * Get the length of the month.
     * 
     * @return A length.
     */
    public int lengthOfMonth() {
        return NativeTemporal.dateDaysInMonth(value);
    }

    /**
     * Get the length of the year.
     * 
     * @return A length.
     */
    public int lengthOfYear() {
        return NativeTemporal.dateDaysInYear(value);
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A date.
     */
    public LocalDate plusDays(long days) {
        return days == 0L ? this : new LocalDate(NativeTemporal.dateAdd(value, days, "days"));
    }

    /**
     * Add the weeks.
     * 
     * @param weeks Weeks.
     * @return A date.
     */
    public LocalDate plusWeeks(long weeks) {
        return plusDays(weeks * 7L);
    }

    /**
     * Add the months.
     * 
     * @param months Months.
     * @return A date.
     */
    public LocalDate plusMonths(long months) {
        return months == 0L ? this : new LocalDate(NativeTemporal.dateAdd(value, months, "months"));
    }

    /**
     * Add the years.
     * 
     * @param years Years.
     * @return A date.
     */
    public LocalDate plusYears(long years) {
        return years == 0L ? this : new LocalDate(NativeTemporal.dateAdd(value, years, "years"));
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A date.
     */
    public LocalDate minusDays(long days) {
        return days == 0L ? this : new LocalDate(NativeTemporal.dateSubtract(value, days, "days"));
    }

    /**
     * Subtract the weeks.
     * 
     * @param weeks Weeks.
     * @return A date.
     */
    public LocalDate minusWeeks(long weeks) {
        return minusDays(weeks * 7L);
    }

    /**
     * Subtract the months.
     * 
     * @param months Months.
     * @return A date.
     */
    public LocalDate minusMonths(long months) {
        return months == 0L ? this : new LocalDate(NativeTemporal.dateSubtract(value, months, "months"));
    }

    /**
     * Subtract the years.
     * 
     * @param years Years.
     * @return A date.
     */
    public LocalDate minusYears(long years) {
        return years == 0L ? this : new LocalDate(NativeTemporal.dateSubtract(value, years, "years"));
    }

    /**
     * Replace the year.
     * 
     * @param year A year.
     * @return A date.
     */
    public LocalDate withYear(int year) {
        return new LocalDate(NativeTemporal.dateWith(value, year, getMonthValue(), getDayOfMonth()));
    }

    /**
     * Replace the month.
     * 
     * @param month A month.
     * @return A date.
     */
    public LocalDate withMonth(int month) {
        return new LocalDate(NativeTemporal.dateWith(value, getYear(), month, getDayOfMonth()));
    }

    /**
     * Replace the day of month.
     * 
     * @param day A day of month.
     * @return A date.
     */
    public LocalDate withDayOfMonth(int day) {
        return new LocalDate(NativeTemporal.dateWith(value, getYear(), getMonthValue(), day));
    }

    /**
     * Get the epoch day.
     * 
     * @return An epoch day.
     */
    public long toEpochDay() {
        return NativeTemporal.dateEpochDay(value);
    }

    /**
     * Create the date and time.
     * 
     * @param time A time.
     * @return A date and time.
     */
    public LocalDateTime atTime(LocalTime time) {
        return new LocalDateTime(NativeTemporal.dateAtTime(value, time.nativeValue()));
    }

    /**
     * Create the date and time.
     * 
     * @param hour An hour.
     * @param minute A minute.
     * @return A date and time.
     */
    public LocalDateTime atTime(int hour, int minute) {
        return atTime(LocalTime.of(hour, minute));
    }

    /**
     * Create the date and time.
     * 
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @return A date and time.
     */
    public LocalDateTime atTime(int hour, int minute, int second) {
        return atTime(LocalTime.of(hour, minute, second));
    }

    /**
     * Create the date and time at the start of the day.
     * 
     * @return A date and time.
     */
    public LocalDateTime atStartOfDay() {
        return new LocalDateTime(NativeTemporal.dateAtStartOfDay(value));
    }

    /**
     * Create the zoned date and time at the start of the day.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public ZonedDateTime atStartOfDay(ZoneId zone) {
        return atStartOfDay().atZone(zone);
    }

    /**
     * Check whether this date is before the other.
     * 
     * @param other A date.
     * @return A result.
     */
    public boolean isBefore(java.time.chrono.ChronoLocalDate other) {
        return compareTo((LocalDate) (Object) other) < 0;
    }

    /**
     * Check whether this date is after the other.
     * 
     * @param other A date.
     * @return A result.
     */
    public boolean isAfter(java.time.chrono.ChronoLocalDate other) {
        return compareTo((LocalDate) (Object) other) > 0;
    }

    /**
     * Check whether this date is equal to the other.
     * 
     * @param other A date.
     * @return A result.
     */
    public boolean isEqual(java.time.chrono.ChronoLocalDate other) {
        return compareTo((LocalDate) (Object) other) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        return unit == ChronoUnit.DAYS || unit == ChronoUnit.WEEKS || unit == ChronoUnit.MONTHS || unit == ChronoUnit.YEARS || unit == ChronoUnit.DECADES || unit == ChronoUnit.CENTURIES || unit == ChronoUnit.MILLENNIA || unit == ChronoUnit.ERAS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate plus(long amount, TemporalUnit unit) {
        if (unit == ChronoUnit.DAYS) {
            return plusDays(amount);
        }
        if (unit == ChronoUnit.WEEKS) {
            return plusWeeks(amount);
        }
        if (unit == ChronoUnit.MONTHS) {
            return plusMonths(amount);
        }
        if (unit == ChronoUnit.YEARS) {
            return plusYears(amount);
        }
        if (unit == ChronoUnit.DECADES) {
            return plusYears(amount * 10L);
        }
        if (unit == ChronoUnit.CENTURIES) {
            return plusYears(amount * 100L);
        }
        if (unit == ChronoUnit.MILLENNIA) {
            return plusYears(amount * 1000L);
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate with(TemporalField field, long value) {
        if (field == ChronoField.YEAR) {
            return withYear((int) value);
        }
        if (field == ChronoField.MONTH_OF_YEAR) {
            return withMonth((int) value);
        }
        if (field == ChronoField.DAY_OF_MONTH) {
            return withDayOfMonth((int) value);
        }
        if (field == ChronoField.DAY_OF_YEAR) {
            return plusDays(value - getDayOfYear());
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal end, TemporalUnit unit) {
        LocalDate other = (LocalDate) end;
        long days = other.toEpochDay() - toEpochDay();
        if (unit == ChronoUnit.DAYS) {
            return days;
        }
        if (unit == ChronoUnit.WEEKS) {
            return days / 7L;
        }
        if (unit == ChronoUnit.MONTHS) {
            return Period.between(this, other).toTotalMonths();
        }
        if (unit == ChronoUnit.YEARS) {
            return Period.between(this, other).toTotalMonths() / 12L;
        }
        if (unit == ChronoUnit.DECADES) {
            return Period.between(this, other).toTotalMonths() / 120L;
        }
        if (unit == ChronoUnit.CENTURIES) {
            return Period.between(this, other).toTotalMonths() / 1200L;
        }
        if (unit == ChronoUnit.MILLENNIA) {
            return Period.between(this, other).toTotalMonths() / 12000L;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(TemporalField field) {
        if (field == ChronoField.EPOCH_DAY) {
            return toEpochDay();
        }
        if (field == ChronoField.YEAR) {
            return getYear();
        }
        if (field == ChronoField.MONTH_OF_YEAR) {
            return getMonthValue();
        }
        if (field == ChronoField.DAY_OF_MONTH) {
            return getDayOfMonth();
        }
        if (field == ChronoField.DAY_OF_YEAR) {
            return getDayOfYear();
        }
        if (field == ChronoField.DAY_OF_WEEK) {
            return NativeTemporal.dateDayOfWeek(value);
        }
        if (field == ChronoField.PROLEPTIC_MONTH) {
            return prolepticMonth();
        }
        if (field == ChronoField.YEAR_OF_ERA) {
            return getYear() > 0 ? getYear() : 1L - getYear();
        }
        if (field == ChronoField.ERA) {
            return getYear() > 0 ? 1L : 0L;
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * Get the native value.
     * 
     * @return A native value.
     */
    Object nativeValue() {
        return value;
    }

    /**
     * Get the proleptic month.
     * 
     * @return A proleptic month.
     */
    long prolepticMonth() {
        return getYear() * 12L + getMonthValue() - 1L;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.EPOCH_DAY, toEpochDay());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(LocalDate other) {
        return NativeTemporal.dateCompare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof LocalDate date && NativeTemporal.dateEquals(value, date.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int year = getYear();
        int month = getMonthValue();
        int day = getDayOfMonth();
        return (year & 0xFFFFF800) ^ ((year << 11) + (month << 6) + day);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendYear(builder, getYear());
        builder.append('-');
        appendTwoDigits(builder, getMonthValue());
        builder.append('-');
        appendTwoDigits(builder, getDayOfMonth());
        return builder.toString();
    }

    /**
     * Get the digit value.
     * 
     * @param value A character.
     * @return A digit or -1.
     */
    static int digit(char value) {
        if (value == '0') {
            return 0;
        }
        if (value == '1') {
            return 1;
        }
        if (value == '2') {
            return 2;
        }
        if (value == '3') {
            return 3;
        }
        if (value == '4') {
            return 4;
        }
        if (value == '5') {
            return 5;
        }
        if (value == '6') {
            return 6;
        }
        if (value == '7') {
            return 7;
        }
        if (value == '8') {
            return 8;
        }
        if (value == '9') {
            return 9;
        }
        return -1;
    }

    /**
     * Divide the integer.
     * 
     * @param value A value.
     * @param divisor A divisor.
     * @return A quotient.
     */
    public static int divide(int value, int divisor) {
        return (int) ((long) value / (long) divisor);
    }

    /**
     * Append the year.
     * 
     * @param builder A builder.
     * @param year A year.
     */
    public static void appendYear(StringBuilder builder, int year) {
        if (year >= 0 && year <= 9999) {
            pad(builder, year, 4);
            return;
        }
        if (year > 9999) {
            builder.append('+').append(year);
            return;
        }
        builder.append('-');
        int absolute = -year;
        if (absolute <= 9999) {
            pad(builder, absolute, 4);
        } else {
            builder.append(absolute);
        }
    }

    /**
     * Append the two digits.
     * 
     * @param builder A builder.
     * @param value A value.
     */
    public static void appendTwoDigits(StringBuilder builder, int value) {
        pad(builder, value, 2);
    }

    /**
     * Append the zero-padded number.
     * 
     * @param builder A builder.
     * @param value A value.
     * @param width A width.
     */
    public static void pad(StringBuilder builder, int value, int width) {
        if (width >= 9) {
            if (value < 100000000) {
                builder.append('0');
            }
            if (value < 10000000) {
                builder.append('0');
            }
            if (value < 1000000) {
                builder.append('0');
            }
            if (value < 100000) {
                builder.append('0');
            }
            if (value < 10000) {
                builder.append('0');
            }
            if (value < 1000) {
                builder.append('0');
            }
            if (value < 100) {
                builder.append('0');
            }
            if (value < 10) {
                builder.append('0');
            }
        } else if (width >= 6) {
            if (value < 100000) {
                builder.append('0');
            }
            if (value < 10000) {
                builder.append('0');
            }
            if (value < 1000) {
                builder.append('0');
            }
            if (value < 100) {
                builder.append('0');
            }
            if (value < 10) {
                builder.append('0');
            }
        } else if (width >= 4) {
            if (value < 1000) {
                builder.append('0');
            }
            if (value < 100) {
                builder.append('0');
            }
            if (value < 10) {
                builder.append('0');
            }
        } else if (width >= 3) {
            if (value < 100) {
                builder.append('0');
            }
            if (value < 10) {
                builder.append('0');
            }
        } else if (width >= 2) {
            if (value < 10) {
                builder.append('0');
            }
        }
        builder.append(value);
    }
}
