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
 * The Java implementation of {@link java.time.Instant} which is transpiled into JavaScript.
 */
public final class Instant implements Temporal, TemporalAdjuster, Comparable<Instant>, Serializable {

    /** The epoch. */
    public static final Instant EPOCH = new Instant(NativeTemporal.instantOfEpochNano(0L));

    /** The native Temporal instant. */
    private final Object value;

    /**
     * Create the instant.
     * 
     * @param value A native value.
     */
    Instant(Object value) {
        this.value = value;
    }

    /**
     * Get the current instant.
     * 
     * @return An instant.
     */
    public static Instant now() {
        return new Instant(NativeTemporal.instantNow());
    }

    /**
     * Create the instant from epoch seconds.
     * 
     * @param seconds Epoch seconds.
     * @return An instant.
     */
    public static Instant ofEpochSecond(long seconds) {
        return new Instant(NativeTemporal.instantOfEpochNano(seconds * 1000000000L));
    }

    /**
     * Create the instant from epoch seconds and a nanosecond adjustment.
     * 
     * @param seconds Epoch seconds.
     * @param nanoAdjustment A nanosecond adjustment.
     * @return An instant.
     */
    public static Instant ofEpochSecond(long seconds, long nanoAdjustment) {
        return new Instant(NativeTemporal.instantOfEpochNano(seconds * 1000000000L + nanoAdjustment));
    }

    /**
     * Create the instant from epoch milliseconds.
     * 
     * @param millis Epoch milliseconds.
     * @return An instant.
     */
    public static Instant ofEpochMilli(long millis) {
        return new Instant(NativeTemporal.instantOfEpochNano(millis * 1000000L));
    }

    /**
     * Parse the instant.
     * 
     * @param text A text.
     * @return An instant.
     */
    public static Instant parse(CharSequence text) {
        return new Instant(NativeTemporal.instantFrom(text.toString()));
    }

    /**
     * Get epoch seconds.
     * 
     * @return Epoch seconds.
     */
    public long getEpochSecond() {
        return floorDiv(epochNano(), 1000000000L);
    }

    /**
     * Get the nanosecond.
     * 
     * @return A nanosecond.
     */
    public int getNano() {
        return (int) floorMod(epochNano(), 1000000000L);
    }

    /**
     * Get epoch milliseconds.
     * 
     * @return Epoch milliseconds.
     */
    public long toEpochMilli() {
        return floorDiv(epochNano(), 1000000L);
    }

    /**
     * Get the epoch nanoseconds.
     * 
     * @return Epoch nanoseconds.
     */
    private long epochNano() {
        return NativeTemporal.instantEpochNano(value);
    }

    /**
     * Check whether this instant is before the other.
     * 
     * @param other An instant.
     * @return A result.
     */
    public boolean isBefore(Instant other) {
        return epochNano() < other.epochNano();
    }

    /**
     * Check whether this instant is after the other.
     * 
     * @param other An instant.
     * @return A result.
     */
    public boolean isAfter(Instant other) {
        return epochNano() > other.epochNano();
    }

    /**
     * Add seconds.
     * 
     * @param seconds Seconds.
     * @return An instant.
     */
    public Instant plusSeconds(long seconds) {
        return new Instant(NativeTemporal.instantAdd(value, seconds, "seconds"));
    }

    /**
     * Add milliseconds.
     * 
     * @param millis Milliseconds.
     * @return An instant.
     */
    public Instant plusMillis(long millis) {
        return new Instant(NativeTemporal.instantAdd(value, millis, "milliseconds"));
    }

    /**
     * Add nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return An instant.
     */
    public Instant plusNanos(long nanos) {
        return new Instant(NativeTemporal.instantAdd(value, nanos, "nanoseconds"));
    }

    /**
     * Subtract seconds.
     * 
     * @param seconds Seconds.
     * @return An instant.
     */
    public Instant minusSeconds(long seconds) {
        return new Instant(NativeTemporal.instantSubtract(value, seconds, "seconds"));
    }

    /**
     * Subtract milliseconds.
     * 
     * @param millis Milliseconds.
     * @return An instant.
     */
    public Instant minusMillis(long millis) {
        return new Instant(NativeTemporal.instantSubtract(value, millis, "milliseconds"));
    }

    /**
     * Subtract nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return An instant.
     */
    public Instant minusNanos(long nanos) {
        return new Instant(NativeTemporal.instantSubtract(value, nanos, "nanoseconds"));
    }

    /**
     * Add the duration.
     * 
     * @param duration A duration.
     * @return An instant.
     */
    public Instant plus(Duration duration) {
        return new Instant(NativeTemporal.instantAdd(value, duration.getSeconds(), "seconds"))
                .plusNanos(duration.getNano());
    }

    /**
     * Subtract the duration.
     * 
     * @param duration A duration.
     * @return An instant.
     */
    public Instant minus(Duration duration) {
        return new Instant(NativeTemporal.instantSubtract(value, duration.getSeconds(), "seconds"))
                .minusNanos(duration.getNano());
    }

    /**
     * Create the zoned date and time in the zone.
     * 
     * @param zone A time zone.
     * @return A zoned date and time.
     */
    public ZonedDateTime atZone(ZoneId zone) {
        return new ZonedDateTime(NativeTemporal.instantAtZone(value, zone.temporalId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        return unit == ChronoUnit.NANOS || unit == ChronoUnit.MICROS || unit == ChronoUnit.MILLIS || unit == ChronoUnit.SECONDS || unit == ChronoUnit.MINUTES || unit == ChronoUnit.HOURS || unit == ChronoUnit.HALF_DAYS || unit == ChronoUnit.DAYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instant plus(long amount, TemporalUnit unit) {
        if (unit == ChronoUnit.NANOS) {
            return plusNanos(amount);
        }
        if (unit == ChronoUnit.MICROS) {
            return plusNanos(amount * 1000L);
        }
        if (unit == ChronoUnit.MILLIS) {
            return plusMillis(amount);
        }
        if (unit == ChronoUnit.SECONDS) {
            return plusSeconds(amount);
        }
        if (unit == ChronoUnit.MINUTES) {
            return plusSeconds(amount * 60L);
        }
        if (unit == ChronoUnit.HOURS) {
            return plusSeconds(amount * 3600L);
        }
        if (unit == ChronoUnit.HALF_DAYS) {
            return plusSeconds(amount * 43200L);
        }
        if (unit == ChronoUnit.DAYS) {
            return plusSeconds(amount * 86400L);
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal end, TemporalUnit unit) {
        long difference = ((Instant) end).epochNano() - epochNano();
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
        if (unit == ChronoUnit.DAYS) {
            return difference / 86400000000000L;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLong(TemporalField field) {
        if (field == ChronoField.INSTANT_SECONDS) {
            return getEpochSecond();
        }
        if (field == ChronoField.NANO_OF_SECOND) {
            return getNano();
        }
        if (field == ChronoField.MICRO_OF_SECOND) {
            return getNano() / 1000L;
        }
        if (field == ChronoField.MILLI_OF_SECOND) {
            return getNano() / 1000000L;
        }
        throw new UnsupportedOperationException("Unsupported field: " + field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.plus(getEpochSecond(), ChronoUnit.SECONDS).plus(getNano(), ChronoUnit.NANOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Instant other) {
        return NativeTemporal.instantCompare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Instant instant && NativeTemporal.instantEquals(value, instant.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        long seconds = getEpochSecond();
        return (int) (seconds ^ (seconds >>> 32)) + 51 * getNano();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        long seconds = getEpochSecond();
        int nano = getNano();
        long epochDay = floorDiv(seconds, 86400L);
        long secondOfDay = floorMod(seconds, 86400L);
        int hour = (int) (secondOfDay / 3600L);
        int minute = (int) ((secondOfDay % 3600L) / 60L);
        int second = (int) (secondOfDay % 60L);
        StringBuilder builder = new StringBuilder(LocalDate.ofEpochDay(epochDay).toString());
        builder.append('T');
        LocalDate.appendTwoDigits(builder, hour);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, minute);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, second);
        if (nano != 0) {
            builder.append('.');
            if (nano % 1000000 == 0) {
                LocalDate.pad(builder, nano / 1000000, 3);
            } else if (nano % 1000 == 0) {
                LocalDate.pad(builder, nano / 1000, 6);
            } else {
                LocalDate.pad(builder, nano, 9);
            }
        }
        builder.append('Z');
        return builder.toString();
    }

    /**
     * Compute the floor division.
     * 
     * @param value A value.
     * @param divisor A divisor.
     * @return A quotient.
     */
    public static long floorDiv(long value, long divisor) {
        long quotient = value / divisor;
        if ((value % divisor != 0L) && ((value < 0L) != (divisor < 0L))) {
            quotient--;
        }
        return quotient;
    }

    /**
     * Compute the floor modulus.
     * 
     * @param value A value.
     * @param divisor A divisor.
     * @return A remainder.
     */
    public static long floorMod(long value, long divisor) {
        long remainder = value % divisor;
        if (remainder != 0L && ((value < 0L) != (divisor < 0L))) {
            remainder += divisor;
        }
        return remainder;
    }
}
