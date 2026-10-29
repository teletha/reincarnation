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
 * The Java implementation of {@link java.time.LocalTime} which is transpiled into JavaScript.
 */
public final class LocalTime implements Temporal, TemporalAdjuster, Comparable<LocalTime>, Serializable {

    /** The midnight. */
    public static final LocalTime MIDNIGHT = of(0, 0);

    /** The noon. */
    public static final LocalTime NOON = of(12, 0);

    /** The minimum time. */
    public static final LocalTime MIN = of(0, 0);

    /** The maximum time. */
    public static final LocalTime MAX = of(23, 59, 59, 999999999);

    /** The native Temporal time. */
    private final Object value;

    /**
     * Create the time.
     * 
     * @param value A native value.
     */
    LocalTime(Object value) {
        this.value = value;
    }

    /**
     * Get the current time.
     * 
     * @return A time.
     */
    public static LocalTime now() {
        return new LocalTime(NativeTemporal.timeNow());
    }

    /**
     * Create the time.
     * 
     * @param hour An hour.
     * @param minute A minute.
     * @return A time.
     */
    public static LocalTime of(int hour, int minute) {
        return of(hour, minute, 0, 0);
    }

    /**
     * Create the time.
     * 
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @return A time.
     */
    public static LocalTime of(int hour, int minute, int second) {
        return of(hour, minute, second, 0);
    }

    /**
     * Create the time.
     * 
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @param nano A nanosecond.
     * @return A time.
     */
    public static LocalTime of(int hour, int minute, int second, int nano) {
        return new LocalTime(NativeTemporal.timeOf(hour, minute, second, nano));
    }

    /**
     * Create the time from the second of day.
     * 
     * @param secondOfDay A second of day.
     * @return A time.
     */
    public static LocalTime ofSecondOfDay(long secondOfDay) {
        return ofNanoOfDay(secondOfDay * 1000000000L);
    }

    /**
     * Create the time from the nanosecond of day.
     * 
     * @param nanoOfDay A nanosecond of day.
     * @return A time.
     */
    public static LocalTime ofNanoOfDay(long nanoOfDay) {
        long seconds = Instant.floorDiv(nanoOfDay, 1000000000L);
        int nano = (int) Instant.floorMod(nanoOfDay, 1000000000L);
        return of((int) (seconds / 3600L), (int) ((seconds % 3600L) / 60L), (int) (seconds % 60L), nano);
    }

    /**
     * Parse the time.
     * 
     * @param text A text.
     * @return A time.
     */
    public static LocalTime parse(CharSequence text) {
        return new LocalTime(NativeTemporal.timeFrom(text.toString()));
    }

    /**
     * Get the hour.
     * 
     * @return An hour.
     */
    public int getHour() {
        return NativeTemporal.timeHour(value);
    }

    /**
     * Get the minute.
     * 
     * @return A minute.
     */
    public int getMinute() {
        return NativeTemporal.timeMinute(value);
    }

    /**
     * Get the second.
     * 
     * @return A second.
     */
    public int getSecond() {
        return NativeTemporal.timeSecond(value);
    }

    /**
     * Get the nanosecond.
     * 
     * @return A nanosecond.
     */
    public int getNano() {
        return NativeTemporal.timeNano(value);
    }

    /**
     * Get the second of day.
     * 
     * @return A second of day.
     */
    public int toSecondOfDay() {
        return getHour() * 3600 + getMinute() * 60 + getSecond();
    }

    /**
     * Get the nanosecond of day.
     * 
     * @return A nanosecond of day.
     */
    public long toNanoOfDay() {
        return toSecondOfDay() * 1000000000L + getNano();
    }

    /**
     * Add the hours.
     * 
     * @param hours Hours.
     * @return A time.
     */
    public LocalTime plusHours(long hours) {
        return hours == 0L ? this : new LocalTime(NativeTemporal.timeAdd(value, hours, "hours"));
    }

    /**
     * Add the minutes.
     * 
     * @param minutes Minutes.
     * @return A time.
     */
    public LocalTime plusMinutes(long minutes) {
        return minutes == 0L ? this : new LocalTime(NativeTemporal.timeAdd(value, minutes, "minutes"));
    }

    /**
     * Add the seconds.
     * 
     * @param seconds Seconds.
     * @return A time.
     */
    public LocalTime plusSeconds(long seconds) {
        return seconds == 0L ? this : new LocalTime(NativeTemporal.timeAdd(value, seconds, "seconds"));
    }

    /**
     * Add the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A time.
     */
    public LocalTime plusNanos(long nanos) {
        return nanos == 0L ? this : new LocalTime(NativeTemporal.timeAdd(value, nanos, "nanoseconds"));
    }

    /**
     * Subtract the hours.
     * 
     * @param hours Hours.
     * @return A time.
     */
    public LocalTime minusHours(long hours) {
        return hours == 0L ? this : new LocalTime(NativeTemporal.timeSubtract(value, hours, "hours"));
    }

    /**
     * Subtract the minutes.
     * 
     * @param minutes Minutes.
     * @return A time.
     */
    public LocalTime minusMinutes(long minutes) {
        return minutes == 0L ? this : new LocalTime(NativeTemporal.timeSubtract(value, minutes, "minutes"));
    }

    /**
     * Subtract the seconds.
     * 
     * @param seconds Seconds.
     * @return A time.
     */
    public LocalTime minusSeconds(long seconds) {
        return seconds == 0L ? this : new LocalTime(NativeTemporal.timeSubtract(value, seconds, "seconds"));
    }

    /**
     * Subtract the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A time.
     */
    public LocalTime minusNanos(long nanos) {
        return nanos == 0L ? this : new LocalTime(NativeTemporal.timeSubtract(value, nanos, "nanoseconds"));
    }

    /**
     * Replace the hour.
     * 
     * @param hour An hour.
     * @return A time.
     */
    public LocalTime withHour(int hour) {
        return new LocalTime(NativeTemporal.timeWith(value, hour, getMinute(), getSecond(), getNano()));
    }

    /**
     * Replace the minute.
     * 
     * @param minute A minute.
     * @return A time.
     */
    public LocalTime withMinute(int minute) {
        return new LocalTime(NativeTemporal.timeWith(value, getHour(), minute, getSecond(), getNano()));
    }

    /**
     * Replace the second.
     * 
     * @param second A second.
     * @return A time.
     */
    public LocalTime withSecond(int second) {
        return new LocalTime(NativeTemporal.timeWith(value, getHour(), getMinute(), second, getNano()));
    }

    /**
     * Replace the nanosecond.
     * 
     * @param nano A nanosecond.
     * @return A time.
     */
    public LocalTime withNano(int nano) {
        return new LocalTime(NativeTemporal.timeWith(value, getHour(), getMinute(), getSecond(), nano));
    }

    /**
     * Create the date and time.
     * 
     * @param date A date.
     * @return A date and time.
     */
    public LocalDateTime atDate(LocalDate date) {
        return new LocalDateTime(NativeTemporal.dateAtTime(date.nativeValue(), value));
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
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        return unit == ChronoUnit.NANOS || unit == ChronoUnit.MICROS || unit == ChronoUnit.MILLIS || unit == ChronoUnit.SECONDS || unit == ChronoUnit.MINUTES || unit == ChronoUnit.HOURS || unit == ChronoUnit.HALF_DAYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime plus(long amount, TemporalUnit unit) {
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
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalTime with(TemporalField field, long value) {
        if (field == ChronoField.HOUR_OF_DAY) {
            return withHour((int) value);
        }
        if (field == ChronoField.MINUTE_OF_HOUR) {
            return withMinute((int) value);
        }
        if (field == ChronoField.SECOND_OF_MINUTE) {
            return withSecond((int) value);
        }
        if (field == ChronoField.NANO_OF_SECOND) {
            return withNano((int) value);
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal end, TemporalUnit unit) {
        long difference = ((LocalTime) end).toNanoOfDay() - toNanoOfDay();
        if (unit == ChronoUnit.NANOS) {
            return difference;
        }
        if (unit == ChronoUnit.MICROS) {
            return difference / 1000L;
        }
        if (unit == ChronoUnit.MILLIS) {
            return difference / 1000000L;
        }
        if (unit == ChronoUnit.SECONDS) {
            return difference / 1000000000L;
        }
        if (unit == ChronoUnit.MINUTES) {
            return difference / 60000000000L;
        }
        if (unit == ChronoUnit.HOURS) {
            return difference / 3600000000000L;
        }
        if (unit == ChronoUnit.HALF_DAYS) {
            return difference / 43200000000000L;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(TemporalField field) {
        if (field == ChronoField.HOUR_OF_DAY) {
            return getHour();
        }
        if (field == ChronoField.MINUTE_OF_HOUR) {
            return getMinute();
        }
        if (field == ChronoField.SECOND_OF_MINUTE) {
            return getSecond();
        }
        if (field == ChronoField.NANO_OF_SECOND) {
            return getNano();
        }
        if (field == ChronoField.SECOND_OF_DAY) {
            return toSecondOfDay();
        }
        if (field == ChronoField.NANO_OF_DAY) {
            return toNanoOfDay();
        }
        if (field == ChronoField.MILLI_OF_SECOND) {
            return getNano() / 1000000L;
        }
        if (field == ChronoField.MICRO_OF_SECOND) {
            return getNano() / 1000L;
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, toNanoOfDay());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(LocalTime other) {
        return NativeTemporal.timeCompare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof LocalTime time && NativeTemporal.timeEquals(value, time.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        long nano = toNanoOfDay();
        return (int) (nano ^ (nano >>> 32));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        LocalDate.appendTwoDigits(builder, getHour());
        builder.append(':');
        LocalDate.appendTwoDigits(builder, getMinute());
        if (getSecond() > 0 || getNano() > 0) {
            builder.append(':');
            LocalDate.appendTwoDigits(builder, getSecond());
            if (getNano() > 0) {
                builder.append('.');
                int nano = getNano();
                if (nano % 1000000 == 0) {
                    LocalDate.pad(builder, nano / 1000000, 3);
                } else if (nano % 1000 == 0) {
                    LocalDate.pad(builder, nano / 1000, 6);
                } else {
                    LocalDate.pad(builder, nano, 9);
                }
            }
        }
        return builder.toString();
    }
}
