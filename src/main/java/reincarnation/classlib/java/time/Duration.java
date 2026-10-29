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
import reincarnation.classlib.java.lang.Comparable;
import reincarnation.classlib.java.util.List;
import reincarnation.classlib.java.time.temporal.ChronoUnit;
import reincarnation.classlib.java.time.temporal.Temporal;
import reincarnation.classlib.java.time.temporal.TemporalAmount;
import reincarnation.classlib.java.time.temporal.TemporalUnit;

/**
 * The Java implementation of {@link java.time.Duration} which is transpiled into JavaScript.
 */
public final class Duration implements TemporalAmount, Comparable<Duration>, Serializable {

    /** The zero duration. */
    public static final Duration ZERO = new Duration(0L, 0);

    /** The seconds. */
    private final long seconds;

    /** The nanoseconds. */
    private final int nanos;

    /**
     * Create the duration.
     * 
     * @param seconds Seconds.
     * @param nanos Nanoseconds.
     */
    private Duration(long seconds, int nanos) {
        this.seconds = seconds;
        this.nanos = nanos;
    }

    /**
     * Create the duration.
     * 
     * @param seconds Seconds.
     * @param nanoAdjustment Nanoseconds.
     * @return A duration.
     */
    private static Duration create(long seconds, long nanoAdjustment) {
        return new Duration(seconds + Instant.floorDiv(nanoAdjustment, 1000000000L), (int) Instant.floorMod(nanoAdjustment, 1000000000L));
    }

    /**
     * Create the duration of days.
     * 
     * @param days Days.
     * @return A duration.
     */
    public static Duration ofDays(long days) {
        return create(days * 86400L, 0L);
    }

    /**
     * Create the duration of hours.
     * 
     * @param hours Hours.
     * @return A duration.
     */
    public static Duration ofHours(long hours) {
        return create(hours * 3600L, 0L);
    }

    /**
     * Create the duration of minutes.
     * 
     * @param minutes Minutes.
     * @return A duration.
     */
    public static Duration ofMinutes(long minutes) {
        return create(minutes * 60L, 0L);
    }

    /**
     * Create the duration of seconds.
     * 
     * @param seconds Seconds.
     * @return A duration.
     */
    public static Duration ofSeconds(long seconds) {
        return create(seconds, 0L);
    }

    /**
     * Create the duration of seconds.
     * 
     * @param seconds Seconds.
     * @param nanoAdjustment Nanoseconds.
     * @return A duration.
     */
    public static Duration ofSeconds(long seconds, long nanoAdjustment) {
        return create(seconds, nanoAdjustment);
    }

    /**
     * Create the duration of milliseconds.
     * 
     * @param millis Milliseconds.
     * @return A duration.
     */
    public static Duration ofMillis(long millis) {
        return create(Instant.floorDiv(millis, 1000L), Instant.floorMod(millis, 1000L) * 1000000L);
    }

    /**
     * Create the duration of nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A duration.
     */
    public static Duration ofNanos(long nanos) {
        return create(Instant.floorDiv(nanos, 1000000000L), Instant.floorMod(nanos, 1000000000L));
    }

    /**
     * Compute the duration between the temporals.
     * 
     * @param start A start temporal.
     * @param end An end temporal.
     * @return A duration.
     */
    public static Duration between(Temporal start, Temporal end) {
        return ofNanos(start.until(end, ChronoUnit.NANOS));
    }

    /**
     * Parse the duration.
     * 
     * @param text A text.
     * @return A duration.
     */
    public static Duration parse(CharSequence text) {
        String value = text.toString();
        int length = value.length();
        int index = 0;
        if (length == 0) {
            throw new IllegalArgumentException("Text cannot be parsed to a Duration");
        }

        boolean negative = false;
        char first = value.charAt(0);
        if (first == '-') {
            negative = true;
            index++;
        } else if (first == '+') {
            index++;
        }
        if (index >= length || value.charAt(index) != 'P') {
            throw new IllegalArgumentException("Text cannot be parsed to a Duration: " + value);
        }
        index++;

        long days = 0L;
        long hours = 0L;
        long minutes = 0L;
        long seconds = 0L;
        int nanos = 0;
        boolean seen = false;

        int start = index;
        while (index < length && isDigit(value.charAt(index))) {
            index++;
        }
        if (index < length && value.charAt(index) == 'D') {
            days = number(value, start, index);
            index++;
            seen = true;
        }

        if (index < length && value.charAt(index) == 'T') {
            index++;
            boolean time = false;

            start = index;
            while (index < length && isDigit(value.charAt(index))) {
                index++;
            }
            if (index < length && value.charAt(index) == 'H') {
                hours = number(value, start, index);
                index++;
                time = true;
            }

            start = index;
            while (index < length && isDigit(value.charAt(index))) {
                index++;
            }
            if (index < length && value.charAt(index) == 'M') {
                minutes = number(value, start, index);
                index++;
                time = true;
            }

            start = index;
            while (index < length && isDigit(value.charAt(index))) {
                index++;
            }
            int secondEnd = index;
            if (index < length && (value.charAt(index) == '.' || value.charAt(index) == ',')) {
                index++;
                int fractionStart = index;
                while (index < length && isDigit(value.charAt(index))) {
                    index++;
                }
                nanos = fraction(value, fractionStart, index);
            }
            if (index < length && value.charAt(index) == 'S') {
                seconds = number(value, start, secondEnd);
                index++;
                time = true;
            }
            seen = seen || time;
        }

        if (!seen || index != length) {
            throw new IllegalArgumentException("Text cannot be parsed to a Duration: " + value);
        }

        long total = days * 86400L + hours * 3600L + minutes * 60L + seconds;
        Duration duration = create(total, nanos);
        return negative ? duration.negated() : duration;
    }

    /**
     * Get the seconds.
     * 
     * @return Seconds.
     */
    public long getSeconds() {
        return seconds;
    }

    /**
     * Get the nanoseconds.
     * 
     * @return Nanoseconds.
     */
    public int getNano() {
        return nanos;
    }

    /**
     * Check whether this duration is zero.
     * 
     * @return A result.
     */
    public boolean isZero() {
        return seconds == 0L && nanos == 0;
    }

    /**
     * Check whether this duration is negative.
     * 
     * @return A result.
     */
    public boolean isNegative() {
        return seconds < 0L;
    }

    /**
     * Add the duration.
     * 
     * @param other A duration.
     * @return A duration.
     */
    public Duration plus(Duration other) {
        return create(seconds + other.seconds, nanos + other.nanos);
    }

    /**
     * Add the days.
     * 
     * @param days Days.
     * @return A duration.
     */
    public Duration plusDays(long days) {
        return plus(ofDays(days));
    }

    /**
     * Add the hours.
     * 
     * @param hours Hours.
     * @return A duration.
     */
    public Duration plusHours(long hours) {
        return plus(ofHours(hours));
    }

    /**
     * Add the minutes.
     * 
     * @param minutes Minutes.
     * @return A duration.
     */
    public Duration plusMinutes(long minutes) {
        return plus(ofMinutes(minutes));
    }

    /**
     * Add the seconds.
     * 
     * @param seconds Seconds.
     * @return A duration.
     */
    public Duration plusSeconds(long seconds) {
        return plus(ofSeconds(seconds));
    }

    /**
     * Add the milliseconds.
     * 
     * @param millis Milliseconds.
     * @return A duration.
     */
    public Duration plusMillis(long millis) {
        return plus(ofMillis(millis));
    }

    /**
     * Add the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A duration.
     */
    public Duration plusNanos(long nanos) {
        return plus(ofNanos(nanos));
    }

    /**
     * Subtract the duration.
     * 
     * @param other A duration.
     * @return A duration.
     */
    public Duration minus(Duration other) {
        return create(seconds - other.seconds, nanos - other.nanos);
    }

    /**
     * Subtract the days.
     * 
     * @param days Days.
     * @return A duration.
     */
    public Duration minusDays(long days) {
        return minus(ofDays(days));
    }

    /**
     * Subtract the hours.
     * 
     * @param hours Hours.
     * @return A duration.
     */
    public Duration minusHours(long hours) {
        return minus(ofHours(hours));
    }

    /**
     * Subtract the minutes.
     * 
     * @param minutes Minutes.
     * @return A duration.
     */
    public Duration minusMinutes(long minutes) {
        return minus(ofMinutes(minutes));
    }

    /**
     * Subtract the seconds.
     * 
     * @param seconds Seconds.
     * @return A duration.
     */
    public Duration minusSeconds(long seconds) {
        return minus(ofSeconds(seconds));
    }

    /**
     * Subtract the milliseconds.
     * 
     * @param millis Milliseconds.
     * @return A duration.
     */
    public Duration minusMillis(long millis) {
        return minus(ofMillis(millis));
    }

    /**
     * Subtract the nanoseconds.
     * 
     * @param nanos Nanoseconds.
     * @return A duration.
     */
    public Duration minusNanos(long nanos) {
        return minus(ofNanos(nanos));
    }

    /**
     * Multiply this duration.
     * 
     * @param multiplicand A multiplicand.
     * @return A duration.
     */
    public Duration multipliedBy(long multiplicand) {
        return create(seconds * multiplicand, nanos * multiplicand);
    }

    /**
     * Divide this duration.
     * 
     * @param divisor A divisor.
     * @return A duration.
     */
    public Duration dividedBy(long divisor) {
        if (divisor == 0L) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return create(seconds / divisor, (seconds % divisor) * 1000000000L / divisor + nanos / divisor);
    }

    /**
     * Negate this duration.
     * 
     * @return A duration.
     */
    public Duration negated() {
        return create(-seconds, -nanos);
    }

    /**
     * Get the absolute duration.
     * 
     * @return A duration.
     */
    public Duration abs() {
        return isNegative() ? negated() : this;
    }

    /**
     * Get the days.
     * 
     * @return Days.
     */
    public long toDays() {
        return seconds / 86400L;
    }

    /**
     * Get the hours.
     * 
     * @return Hours.
     */
    public long toHours() {
        return seconds / 3600L;
    }

    /**
     * Get the minutes.
     * 
     * @return Minutes.
     */
    public long toMinutes() {
        return seconds / 60L;
    }

    /**
     * Get the seconds.
     * 
     * @return Seconds.
     */
    public long toSeconds() {
        return seconds;
    }

    /**
     * Get the milliseconds.
     * 
     * @return Milliseconds.
     */
    public long toMillis() {
        return seconds * 1000L + nanos / 1000000L;
    }

    /**
     * Get the nanoseconds.
     * 
     * @return Nanoseconds.
     */
    public long toNanos() {
        return seconds * 1000000000L + nanos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long get(TemporalUnit unit) {
        if (unit == ChronoUnit.SECONDS) {
            return seconds;
        }
        if (unit == ChronoUnit.NANOS) {
            return nanos;
        }
        throw new UnsupportedOperationException("Unsupported unit: " + unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TemporalUnit> getUnits() {
        return List.of(ChronoUnit.SECONDS, ChronoUnit.NANOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal addTo(Temporal temporal) {
        return temporal.plus(seconds, ChronoUnit.SECONDS).plus(nanos, ChronoUnit.NANOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal subtractFrom(Temporal temporal) {
        return temporal.minus(seconds, ChronoUnit.SECONDS).minus(nanos, ChronoUnit.NANOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Duration other) {
        if (seconds != other.seconds) {
            return seconds < other.seconds ? -1 : 1;
        }
        return nanos - other.nanos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Duration duration && seconds == duration.seconds && nanos == duration.nanos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (int) (seconds ^ (seconds >>> 32)) + 51 * nanos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (isZero()) {
            return "PT0S";
        }
        long hours = seconds / 3600L;
        long minutes = (seconds % 3600L) / 60L;
        long secs = seconds % 60L;
        StringBuilder builder = new StringBuilder("PT");
        if (hours != 0L) {
            builder.append(hours).append('H');
        }
        if (minutes != 0L) {
            builder.append(minutes).append('M');
        }
        if (secs == 0L && nanos == 0) {
            if (builder.length() > 2) {
                return builder.toString();
            }
            secs = 0L;
        }
        if (secs < 0L && nanos > 0) {
            if (secs == -1L) {
                builder.append("-0");
            } else {
                builder.append(secs + 1L);
            }
        } else {
            builder.append(secs);
        }
        if (nanos == 0) {
            builder.append('S');
        } else {
            int fraction = secs < 0L ? 1000000000 - nanos : nanos;
            int scale;
            if (fraction % 1000000 == 0) {
                fraction = LocalDate.divide(fraction, 1000000);
                scale = 3;
            } else if (fraction % 1000 == 0) {
                fraction = LocalDate.divide(fraction, 1000);
                scale = 6;
            } else {
                scale = 9;
            }
            while (scale > 1 && fraction % 10 == 0) {
                fraction = LocalDate.divide(fraction, 10);
                scale--;
            }
            builder.append('.');
            LocalDate.pad(builder, fraction, scale);
            builder.append('S');
        }
        return builder.toString();
    }

    /**
     * Check whether the character is a digit.
     * 
     * @param value A character.
     * @return A result.
     */
    private static boolean isDigit(char value) {
        return LocalDate.digit(value) >= 0;
    }

    /**
     * Parse the number.
     * 
     * @param text A text.
     * @param start A start index.
     * @param end An end index.
     * @return A number.
     */
    private static long number(String text, int start, int end) {
        long value = 0L;
        for (int i = start; i < end; i++) {
            value = value * 10L + LocalDate.digit(text.charAt(i));
        }
        return value;
    }

    /**
     * Parse the fraction.
     * 
     * @param text A text.
     * @param start A start index.
     * @param end An end index.
     * @return A nanosecond.
     */
    private static int fraction(String text, int start, int end) {
        int value = 0;
        int digits = 0;
        for (int i = start; i < end && digits < 9; i++) {
            value = value * 10 + LocalDate.digit(text.charAt(i));
            digits++;
        }
        while (digits < 9) {
            value *= 10;
            digits++;
        }
        return value;
    }
}
