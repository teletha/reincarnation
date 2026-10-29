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
import reincarnation.classlib.java.time.temporal.TemporalAdjuster;
import reincarnation.classlib.java.time.temporal.TemporalField;
import reincarnation.classlib.java.time.temporal.TemporalUnit;

/**
 * The Java implementation of {@link java.time.LocalDateTime} which is transpiled into JavaScript.
 */
public final class LocalDateTime implements Temporal, TemporalAdjuster, Comparable<LocalDateTime>, Serializable {

    /** The native Temporal date and time. */
    private final Object value;

    /**
     * Create the date and time.
     * 
     * @param value A native value.
     */
    LocalDateTime(Object value) {
        this.value = value;
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
     * Get the current date and time.
     * 
     * @return A date and time.
     */
    public static LocalDateTime now() {
        return new LocalDateTime(NativeTemporal.dateTimeNow());
    }

    /**
     * Create the date and time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @return A date and time.
     */
    public static LocalDateTime of(int year, int month, int day, int hour, int minute) {
        return of(year, month, day, hour, minute, 0, 0);
    }

    /**
     * Create the date and time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @return A date and time.
     */
    public static LocalDateTime of(int year, int month, int day, int hour, int minute, int second) {
        return of(year, month, day, hour, minute, second, 0);
    }

    /**
     * Create the date and time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @param nano A nanosecond.
     * @return A date and time.
     */
    public static LocalDateTime of(int year, int month, int day, int hour, int minute, int second, int nano) {
        return new LocalDateTime(NativeTemporal.dateTimeOf(year, month, day, hour, minute, second, nano));
    }

    /**
     * Create the date and time.
     * 
     * @param date A date.
     * @param time A time.
     * @return A date and time.
     */
    public static LocalDateTime of(LocalDate date, LocalTime time) {
        return new LocalDateTime(NativeTemporal.dateAtTime(date.nativeValue(), time.nativeValue()));
    }

    /**
     * Parse the date and time.
     * 
     * @param text A text.
     * @return A date and time.
     */
    public static LocalDateTime parse(CharSequence text) {
        return new LocalDateTime(NativeTemporal.dateTimeFrom(text.toString()));
    }

    /**
     * Get the date.
     * 
     * @return A date.
     */
    public LocalDate toLocalDate() {
        return new LocalDate(NativeTemporal.dateTimeDate(value));
    }

    /**
     * Get the time.
     * 
     * @return A time.
     */
    public LocalTime toLocalTime() {
        return new LocalTime(NativeTemporal.dateTimeTime(value));
    }

    /**
     * Get the year.
     * 
     * @return A year.
     */
    public int getYear() {
        return NativeTemporal.dateTimeYear(value);
    }

    /**
     * Get the month value.
     * 
     * @return A month value.
     */
    public int getMonthValue() {
        return NativeTemporal.dateTimeMonth(value);
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
        return NativeTemporal.dateTimeDay(value);
    }

    /**
     * Get the day of year.
     * 
     * @return A day of year.
     */
    public int getDayOfYear() {
        return NativeTemporal.dateTimeDayOfYear(value);
    }

    /**
     * Get the day of week.
     * 
     * @return A day of week.
     */
    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.of(NativeTemporal.dateTimeDayOfWeek(value));
    }

    /**
     * Get the hour.
     * 
     * @return An hour.
     */
    public int getHour() {
        return NativeTemporal.dateTimeHour(value);
    }

    /**
     * Get the minute.
     * 
     * @return A minute.
     */
    public int getMinute() {
        return NativeTemporal.dateTimeMinute(value);
    }

    /**
     * Get the second.
     * 
     * @return A second.
     */
    public int getSecond() {
        return NativeTemporal.dateTimeSecond(value);
    }

    /**
     * Get the nanosecond.
     * 
     * @return A nanosecond.
     */
    public int getNano() {
        return NativeTemporal.dateTimeNano(value);
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A date and time.
     */
    public LocalDateTime plusDays(long days) {
        return withDate(toLocalDate().plusDays(days));
    }

    /**
     * Add the weeks.
     * 
     * @param weeks Weeks.
     * @return A date and time.
     */
    public LocalDateTime plusWeeks(long weeks) {
        return withDate(toLocalDate().plusWeeks(weeks));
    }

    /**
     * Add the months.
     * 
     * @param months Months.
     * @return A date and time.
     */
    public LocalDateTime plusMonths(long months) {
        return withDate(toLocalDate().plusMonths(months));
    }

    /**
     * Add the years.
     * 
     * @param years Years.
     * @return A date and time.
     */
    public LocalDateTime plusYears(long years) {
        return withDate(toLocalDate().plusYears(years));
    }

    /**
     * Add the hours.
     * 
     * @param hours Hours.
     * @return A date and time.
     */
    public LocalDateTime plusHours(long hours) {
        return hours == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeAdd(value, hours, "hours"));
    }

    /**
     * Add the minutes.
     * 
     * @param minutes Minutes.
     * @return A date and time.
     */
    public LocalDateTime plusMinutes(long minutes) {
        return minutes == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeAdd(value, minutes, "minutes"));
    }

    /**
     * Add the seconds.
     * 
     * @param seconds Seconds.
     * @return A date and time.
     */
    public LocalDateTime plusSeconds(long seconds) {
        return seconds == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeAdd(value, seconds, "seconds"));
    }

    /**
     * Add the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A date and time.
     */
    public LocalDateTime plusNanos(long nanos) {
        return nanos == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeAdd(value, nanos, "nanoseconds"));
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A date and time.
     */
    public LocalDateTime minusDays(long days) {
        return withDate(toLocalDate().minusDays(days));
    }

    /**
     * Subtract the weeks.
     * 
     * @param weeks Weeks.
     * @return A date and time.
     */
    public LocalDateTime minusWeeks(long weeks) {
        return withDate(toLocalDate().minusWeeks(weeks));
    }

    /**
     * Subtract the months.
     * 
     * @param months Months.
     * @return A date and time.
     */
    public LocalDateTime minusMonths(long months) {
        return withDate(toLocalDate().minusMonths(months));
    }

    /**
     * Subtract the years.
     * 
     * @param years Years.
     * @return A date and time.
     */
    public LocalDateTime minusYears(long years) {
        return withDate(toLocalDate().minusYears(years));
    }

    /**
     * Subtract the hours.
     * 
     * @param hours Hours.
     * @return A date and time.
     */
    public LocalDateTime minusHours(long hours) {
        return hours == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeSubtract(value, hours, "hours"));
    }

    /**
     * Subtract the minutes.
     * 
     * @param minutes Minutes.
     * @return A date and time.
     */
    public LocalDateTime minusMinutes(long minutes) {
        return minutes == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeSubtract(value, minutes, "minutes"));
    }

    /**
     * Subtract the seconds.
     * 
     * @param seconds Seconds.
     * @return A date and time.
     */
    public LocalDateTime minusSeconds(long seconds) {
        return seconds == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeSubtract(value, seconds, "seconds"));
    }

    /**
     * Subtract the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A date and time.
     */
    public LocalDateTime minusNanos(long nanos) {
        return nanos == 0L ? this : new LocalDateTime(NativeTemporal.dateTimeSubtract(value, nanos, "nanoseconds"));
    }

    /**
     * Replace the year.
     * 
     * @param year A year.
     * @return A date and time.
     */
    public LocalDateTime withYear(int year) {
        return withDate(toLocalDate().withYear(year));
    }

    /**
     * Replace the month.
     * 
     * @param month A month.
     * @return A date and time.
     */
    public LocalDateTime withMonth(int month) {
        return withDate(toLocalDate().withMonth(month));
    }

    /**
     * Replace the day of month.
     * 
     * @param day A day of month.
     * @return A date and time.
     */
    public LocalDateTime withDayOfMonth(int day) {
        return withDate(toLocalDate().withDayOfMonth(day));
    }

    /**
     * Replace the hour.
     * 
     * @param hour An hour.
     * @return A date and time.
     */
    public LocalDateTime withHour(int hour) {
        return withTime(toLocalTime().withHour(hour));
    }

    /**
     * Replace the minute.
     * 
     * @param minute A minute.
     * @return A date and time.
     */
    public LocalDateTime withMinute(int minute) {
        return withTime(toLocalTime().withMinute(minute));
    }

    /**
     * Replace the second.
     * 
     * @param second A second.
     * @return A date and time.
     */
    public LocalDateTime withSecond(int second) {
        return withTime(toLocalTime().withSecond(second));
    }

    /**
     * Replace the nanosecond.
     * 
     * @param nano A nanosecond.
     * @return A date and time.
     */
    public LocalDateTime withNano(int nano) {
        return withTime(toLocalTime().withNano(nano));
    }

    /**
     * Replace the date part.
     * 
     * @param date A date.
     * @return A date and time.
     */
    private LocalDateTime withDate(LocalDate date) {
        return new LocalDateTime(NativeTemporal.dateAtTime(date.nativeValue(), toLocalTime().nativeValue()));
    }

    /**
     * Replace the time part.
     * 
     * @param time A time.
     * @return A date and time.
     */
    private LocalDateTime withTime(LocalTime time) {
        return new LocalDateTime(NativeTemporal.dateAtTime(toLocalDate().nativeValue(), time.nativeValue()));
    }

    /**
     * Create the zoned date and time.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public ZonedDateTime atZone(ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.dateTimeAtZone(value, zone.temporalId()));
    }

    /**
     * Check whether this date and time is before the other.
     * 
     * @param other A date and time.
     * @return A result.
     */
    public boolean isBefore(java.time.chrono.ChronoLocalDateTime<?> other) {
        return compareTo((LocalDateTime) (Object) other) < 0;
    }

    /**
     * Check whether this date and time is after the other.
     * 
     * @param other A date and time.
     * @return A result.
     */
    public boolean isAfter(java.time.chrono.ChronoLocalDateTime<?> other) {
        return compareTo((LocalDateTime) (Object) other) > 0;
    }

    /**
     * Check whether this date and time is equal to the other.
     * 
     * @param other A date and time.
     * @return A result.
     */
    public boolean isEqual(java.time.chrono.ChronoLocalDateTime<?> other) {
        return compareTo((LocalDateTime) (Object) other) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        return unit != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime plus(long amount, TemporalUnit unit) {
        if (unit == ChronoUnit.NANOS) {
            return plusNanos(amount);
        }
        if (unit == ChronoUnit.MICROS) {
            return plusNanos(amount * 1000L);
        }
        if (unit == ChronoUnit.MILLIS) {
            return plusNanos(amount * 1000000L);
        }
        if (unit == ChronoUnit.SECONDS) {
            return plusSeconds(amount);
        }
        if (unit == ChronoUnit.MINUTES) {
            return plusMinutes(amount);
        }
        if (unit == ChronoUnit.HOURS) {
            return plusHours(amount);
        }
        if (unit == ChronoUnit.HALF_DAYS) {
            return plusHours(amount * 12L);
        }
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
    public LocalDateTime with(TemporalField field, long value) {
        if (field.isDateBased()) {
            return withDate(toLocalDate().with(field, value));
        }
        if (field.isTimeBased()) {
            return withTime(toLocalTime().with(field, value));
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal end, TemporalUnit unit) {
        LocalDateTime other = (LocalDateTime) end;
        if (unit.isDateBased()) {
            return toLocalDate().until(other.toLocalDate(), unit);
        }
        long nanoseconds = (other.toLocalDate().toEpochDay() - toLocalDate().toEpochDay()) * 86400000000000L + (other.toLocalTime().toNanoOfDay() - toLocalTime().toNanoOfDay());
        if (unit == ChronoUnit.NANOS) {
            return nanoseconds;
        }
        if (unit == ChronoUnit.MICROS) {
            return nanoseconds / 1000L;
        }
        if (unit == ChronoUnit.MILLIS) {
            return nanoseconds / 1000000L;
        }
        if (unit == ChronoUnit.SECONDS) {
            return nanoseconds / 1000000000L;
        }
        if (unit == ChronoUnit.MINUTES) {
            return nanoseconds / 60000000000L;
        }
        if (unit == ChronoUnit.HOURS) {
            return nanoseconds / 3600000000000L;
        }
        if (unit == ChronoUnit.HALF_DAYS) {
            return nanoseconds / 43200000000000L;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(TemporalField field) {
        if (field.isDateBased()) {
            return toLocalDate().getLong(field);
        }
        if (field.isTimeBased()) {
            return toLocalTime().getLong(field);
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.EPOCH_DAY, toLocalDate().toEpochDay()).with(ChronoField.NANO_OF_DAY, toLocalTime().toNanoOfDay());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(LocalDateTime other) {
        return NativeTemporal.dateTimeCompare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof LocalDateTime dateTime && NativeTemporal.dateTimeEquals(value, dateTime.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return toLocalDate().hashCode() ^ toLocalTime().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toLocalDate().toString() + "T" + toLocalTime().toString();
    }
}
