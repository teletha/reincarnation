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
 * The Java implementation of {@link java.time.ZonedDateTime} which is transpiled into JavaScript.
 */
public final class ZonedDateTime implements Temporal, TemporalAdjuster, Comparable<ZonedDateTime>, Serializable {

    /** The native Temporal zoned date and time. */
    private final Object value;

    /**
     * Create the zoned date and time.
     * 
     * @param value A native value.
     */
    ZonedDateTime(Object value) {
        this.value = value;
    }

    /**
     * Get the current zoned date and time in the system time zone.
     * 
     * @return A zoned date and time.
     */
    public static ZonedDateTime now() {
        return new ZonedDateTime(NativeTemporal.zonedNow());
    }

    /**
     * Get the current zoned date and time in the time zone.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public static ZonedDateTime now(ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.zonedNow(zone.temporalId()));
    }

    /**
     * Create the zoned date and time from the instant.
     * 
     * @param instant An instant.
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public static ZonedDateTime ofInstant(Instant instant, ZoneId zone) {
        return instant.atZone(zone);
    }

    /**
     * Create the zoned date and time.
     * 
     * @param date A date.
     * @param time A time.
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public static ZonedDateTime of(LocalDate date, LocalTime time, ZoneId zone) {
        return of(LocalDateTime.of(date, time), zone);
    }

    /**
     * Create the zoned date and time.
     * 
     * @param dateTime A date and time.
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public static ZonedDateTime of(LocalDateTime dateTime, ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.dateTimeAtZone(dateTime.nativeValue(), zone.temporalId()));
    }

    /**
     * Create the zoned date and time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @param nano A nanosecond.
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public static ZonedDateTime of(int year, int month, int day, int hour, int minute, int second, int nano, ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.zonedOf(year, month, day, hour, minute, second, nano, zone.temporalId()));
    }

    /**
     * Parse the zoned date and time.
     * 
     * @param text A text.
     * @return A zoned date and time.
     */
    public static ZonedDateTime parse(CharSequence text) {
        return new ZonedDateTime(NativeTemporal.zonedFrom(text.toString()));
    }

    /**
     * Get the date.
     * 
     * @return A date.
     */
    public LocalDate toLocalDate() {
        return new LocalDate(NativeTemporal.zonedDate(value));
    }

    /**
     * Get the time.
     * 
     * @return A time.
     */
    public LocalTime toLocalTime() {
        return new LocalTime(NativeTemporal.zonedTime(value));
    }

    /**
     * Get the date and time.
     * 
     * @return A date and time.
     */
    public LocalDateTime toLocalDateTime() {
        return new LocalDateTime(NativeTemporal.zonedDateTime(value));
    }

    /**
     * Get the instant.
     * 
     * @return An instant.
     */
    public Instant toInstant() {
        return new Instant(NativeTemporal.zonedInstant(value));
    }

    /**
     * Get the epoch second.
     * 
     * @return An epoch second.
     */
    public long toEpochSecond() {
        return toInstant().getEpochSecond();
    }

    /**
     * Get the time zone.
     * 
     * @return A time zone.
     */
    public ZoneId getZone() {
        return ZoneId.of(NativeTemporal.zonedZone(value));
    }

    /**
     * Get the offset.
     * 
     * @return An offset.
     */
    public ZoneOffset getOffset() {
        return ZoneOffset.of(NativeTemporal.zonedOffset(value));
    }

    /**
     * Get the year.
     * 
     * @return A year.
     */
    public int getYear() {
        return NativeTemporal.zonedYear(value);
    }

    /**
     * Get the month value.
     * 
     * @return A month value.
     */
    public int getMonthValue() {
        return NativeTemporal.zonedMonth(value);
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
        return NativeTemporal.zonedDay(value);
    }

    /**
     * Get the day of year.
     * 
     * @return A day of year.
     */
    public int getDayOfYear() {
        return NativeTemporal.zonedDayOfYear(value);
    }

    /**
     * Get the day of week.
     * 
     * @return A day of week.
     */
    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.of(NativeTemporal.zonedDayOfWeek(value));
    }

    /**
     * Get the hour.
     * 
     * @return An hour.
     */
    public int getHour() {
        return NativeTemporal.zonedHour(value);
    }

    /**
     * Get the minute.
     * 
     * @return A minute.
     */
    public int getMinute() {
        return NativeTemporal.zonedMinute(value);
    }

    /**
     * Get the second.
     * 
     * @return A second.
     */
    public int getSecond() {
        return NativeTemporal.zonedSecond(value);
    }

    /**
     * Get the nanosecond.
     * 
     * @return A nanosecond.
     */
    public int getNano() {
        return NativeTemporal.zonedNano(value);
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusDays(long days) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, days, "days"));
    }

    /**
     * Add the weeks.
     * 
     * @param weeks Weeks.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusWeeks(long weeks) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, weeks, "weeks"));
    }

    /**
     * Add the months.
     * 
     * @param months Months.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusMonths(long months) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, months, "months"));
    }

    /**
     * Add the years.
     * 
     * @param years Years.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusYears(long years) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, years, "years"));
    }

    /**
     * Add the hours.
     * 
     * @param hours Hours.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusHours(long hours) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, hours, "hours"));
    }

    /**
     * Add the minutes.
     * 
     * @param minutes Minutes.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusMinutes(long minutes) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, minutes, "minutes"));
    }

    /**
     * Add the seconds.
     * 
     * @param seconds Seconds.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusSeconds(long seconds) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, seconds, "seconds"));
    }

    /**
     * Add the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A zoned date and time.
     */
    public ZonedDateTime plusNanos(long nanos) {
        return new ZonedDateTime(NativeTemporal.zonedAdd(value, nanos, "nanoseconds"));
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusDays(long days) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, days, "days"));
    }

    /**
     * Subtract the weeks.
     * 
     * @param weeks Weeks.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusWeeks(long weeks) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, weeks, "weeks"));
    }

    /**
     * Subtract the months.
     * 
     * @param months Months.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusMonths(long months) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, months, "months"));
    }

    /**
     * Subtract the years.
     * 
     * @param years Years.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusYears(long years) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, years, "years"));
    }

    /**
     * Subtract the hours.
     * 
     * @param hours Hours.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusHours(long hours) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, hours, "hours"));
    }

    /**
     * Subtract the minutes.
     * 
     * @param minutes Minutes.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusMinutes(long minutes) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, minutes, "minutes"));
    }

    /**
     * Subtract the seconds.
     * 
     * @param seconds Seconds.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusSeconds(long seconds) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, seconds, "seconds"));
    }

    /**
     * Subtract the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A zoned date and time.
     */
    public ZonedDateTime minusNanos(long nanos) {
        return new ZonedDateTime(NativeTemporal.zonedSubtract(value, nanos, "nanoseconds"));
    }

    /**
     * Replace the year.
     * 
     * @param year A year.
     * @return A zoned date and time.
     */
    public ZonedDateTime withYear(int year) {
        return withLocal(toLocalDateTime().withYear(year));
    }

    /**
     * Replace the month.
     * 
     * @param month A month.
     * @return A zoned date and time.
     */
    public ZonedDateTime withMonth(int month) {
        return withLocal(toLocalDateTime().withMonth(month));
    }

    /**
     * Replace the day of month.
     * 
     * @param day A day of month.
     * @return A zoned date and time.
     */
    public ZonedDateTime withDayOfMonth(int day) {
        return withLocal(toLocalDateTime().withDayOfMonth(day));
    }

    /**
     * Replace the hour.
     * 
     * @param hour An hour.
     * @return A zoned date and time.
     */
    public ZonedDateTime withHour(int hour) {
        return withLocal(toLocalDateTime().withHour(hour));
    }

    /**
     * Replace the minute.
     * 
     * @param minute A minute.
     * @return A zoned date and time.
     */
    public ZonedDateTime withMinute(int minute) {
        return withLocal(toLocalDateTime().withMinute(minute));
    }

    /**
     * Replace the second.
     * 
     * @param second A second.
     * @return A zoned date and time.
     */
    public ZonedDateTime withSecond(int second) {
        return withLocal(toLocalDateTime().withSecond(second));
    }

    /**
     * Replace the nanosecond.
     * 
     * @param nano A nanosecond.
     * @return A zoned date and time.
     */
    public ZonedDateTime withNano(int nano) {
        return withLocal(toLocalDateTime().withNano(nano));
    }

    /**
     * Replace the local date and time.
     * 
     * @param dateTime A date and time.
     * @return A zoned date and time.
     */
    private ZonedDateTime withLocal(LocalDateTime dateTime) {
        return dateTime.atZone(getZone());
    }

    /**
     * Replace the time zone keeping the instant.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public ZonedDateTime withZoneSameInstant(ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.zonedWithZone(value, zone.temporalId()));
    }

    /**
     * Replace the time zone keeping the local date and time.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public ZonedDateTime withZoneSameLocal(ZoneId zone) {
        return toLocalDateTime().atZone(zone);
    }

    /**
     * Replace the time zone with the fixed offset zone.
     * 
     * @return A zoned date and time.
     */
    public ZonedDateTime withFixedOffsetZone() {
        return withZoneSameInstant(getOffset());
    }

    /**
     * Check whether this zoned date and time is before the other.
     * 
     * @param other A zoned date and time.
     * @return A result.
     */
    public boolean isBefore(java.time.chrono.ChronoZonedDateTime<?> other) {
        return toInstant().isBefore(((ZonedDateTime) (Object) other).toInstant());
    }

    /**
     * Check whether this zoned date and time is after the other.
     * 
     * @param other A zoned date and time.
     * @return A result.
     */
    public boolean isAfter(java.time.chrono.ChronoZonedDateTime<?> other) {
        return toInstant().isAfter(((ZonedDateTime) (Object) other).toInstant());
    }

    /**
     * Check whether this zoned date and time is equal to the other.
     * 
     * @param other A zoned date and time.
     * @return A result.
     */
    public boolean isEqual(java.time.chrono.ChronoZonedDateTime<?> other) {
        return toInstant().equals(((ZonedDateTime) (Object) other).toInstant());
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
    public ZonedDateTime plus(long amount, TemporalUnit unit) {
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
    public ZonedDateTime with(TemporalField field, long value) {
        if (field.isDateBased()) {
            return withLocal(toLocalDateTime().with(field, value));
        }
        if (field.isTimeBased()) {
            return withLocal(toLocalDateTime().with(field, value));
        }
        if (field == ChronoField.INSTANT_SECONDS) {
            return ofInstant(Instant.ofEpochSecond(value, getNano()), getZone());
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal end, TemporalUnit unit) {
        ZonedDateTime other = (ZonedDateTime) end;
        if (unit.isDateBased()) {
            return toLocalDate().until(other.toLocalDate(), unit);
        }
        return toInstant().until(other.toInstant(), unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(TemporalField field) {
        if (field == ChronoField.INSTANT_SECONDS) {
            return toEpochSecond();
        }
        if (field == ChronoField.OFFSET_SECONDS) {
            return getOffset().getTotalSeconds();
        }
        if (field.isDateBased() || field.isTimeBased()) {
            return toLocalDateTime().getLong(field);
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.INSTANT_SECONDS, toEpochSecond()).with(ChronoField.NANO_OF_SECOND, getNano());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(ZonedDateTime other) {
        return NativeTemporal.zonedCompare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ZonedDateTime zoned && NativeTemporal.zonedEquals(value, zoned.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int zone = getZone().hashCode();
        return toLocalDateTime().hashCode() ^ getOffset().hashCode() ^ ((zone << 3) | (zone >>> 29));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        ZoneOffset offset = getOffset();
        StringBuilder builder = new StringBuilder(toLocalDateTime().toString());
        builder.append(offset.getId());
        if (!(getZone() instanceof ZoneOffset)) {
            builder.append('[').append(getZone().getId()).append(']');
        }
        return builder.toString();
    }
}
