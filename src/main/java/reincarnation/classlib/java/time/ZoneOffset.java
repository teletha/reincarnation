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

/**
 * The Java implementation of {@link java.time.ZoneOffset} which is transpiled into JavaScript.
 */
public final class ZoneOffset extends ZoneId implements Comparable<ZoneOffset>, Serializable {

    /** The maximum total seconds. */
    private static final int MAX_SECONDS = 18 * 3600;

    /** The UTC offset. */
    public static final ZoneOffset UTC = new ZoneOffset(0);

    /** The minimum offset. */
    public static final ZoneOffset MIN = new ZoneOffset(-MAX_SECONDS);

    /** The maximum offset. */
    public static final ZoneOffset MAX = new ZoneOffset(MAX_SECONDS);

    /** The total seconds. */
    private final int totalSeconds;

    /**
     * Create the offset.
     * 
     * @param totalSeconds Total seconds.
     */
    private ZoneOffset(int totalSeconds) {
        super(format(totalSeconds));
        this.totalSeconds = totalSeconds;
    }

    /**
     * Get the offset of the id.
     * 
     * @param offsetId An offset id.
     * @return An offset.
     */
    public static ZoneOffset of(String offsetId) {
        if (offsetId == null) {
            throw new NullPointerException("offsetId");
        }
        int length = offsetId.length();
        if (offsetId.equals("Z") || offsetId.equals("UTC") || offsetId.equals("GMT") || offsetId.equals("UT")) {
            return UTC;
        }
        if (length < 2) {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + offsetId);
        }

        char sign = offsetId.charAt(0);
        if (sign != '+' && sign != '-') {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + offsetId);
        }

        int index = 1;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int digits = 0;
        while (index < length && digits < 2) {
            int digit = LocalDate.digit(offsetId.charAt(index));
            if (digit < 0) {
                break;
            }
            hours = hours * 10 + digit;
            index++;
            digits++;
        }
        if (digits == 0) {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + offsetId);
        }
        if (index < length && offsetId.charAt(index) == ':') {
            index++;
            minutes = twoDigits(offsetId, index);
            index += 2;
            if (index < length && offsetId.charAt(index) == ':') {
                index++;
                seconds = twoDigits(offsetId, index);
                index += 2;
            }
        } else if (index < length) {
            minutes = twoDigits(offsetId, index);
            index += 2;
            if (index < length) {
                seconds = twoDigits(offsetId, index);
                index += 2;
            }
        }
        if (index != length) {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + offsetId);
        }

        int total = hours * 3600 + minutes * 60 + seconds;
        if (sign == '-') {
            total = -total;
        }
        return ofTotalSeconds(total);
    }

    /**
     * Get the offset of the hours.
     * 
     * @param hours Hours.
     * @return An offset.
     */
    public static ZoneOffset ofHours(int hours) {
        return ofHoursMinutesSeconds(hours, 0, 0);
    }

    /**
     * Get the offset of the hours and the minutes.
     * 
     * @param hours Hours.
     * @param minutes Minutes.
     * @return An offset.
     */
    public static ZoneOffset ofHoursMinutes(int hours, int minutes) {
        return ofHoursMinutesSeconds(hours, minutes, 0);
    }

    /**
     * Get the offset of the hours, the minutes and the seconds.
     * 
     * @param hours Hours.
     * @param minutes Minutes.
     * @param seconds Seconds.
     * @return An offset.
     */
    public static ZoneOffset ofHoursMinutesSeconds(int hours, int minutes, int seconds) {
        if (hours < 0) {
            return ofTotalSeconds(hours * 3600 - minutes * 60 - seconds);
        }
        return ofTotalSeconds(hours * 3600 + minutes * 60 + seconds);
    }

    /**
     * Get the offset of the total seconds.
     * 
     * @param totalSeconds Total seconds.
     * @return An offset.
     */
    public static ZoneOffset ofTotalSeconds(int totalSeconds) {
        if (totalSeconds < -MAX_SECONDS || totalSeconds > MAX_SECONDS) {
            throw new IllegalArgumentException("Zone offset not in valid range: " + totalSeconds);
        }
        if (totalSeconds == 0) {
            return UTC;
        }
        return new ZoneOffset(totalSeconds);
    }

    /**
     * Get the total seconds.
     * 
     * @return Total seconds.
     */
    public int getTotalSeconds() {
        return totalSeconds;
    }

    /**
     * Check whether this offset is negative.
     * 
     * @return A result.
     */
    public boolean isNegative() {
        return totalSeconds < 0;
    }

    /**
     * Parse the two digits.
     * 
     * @param text A text.
     * @param index An index.
     * @return A value.
     */
    private static int twoDigits(String text, int index) {
        if (index + 1 >= text.length()) {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + text);
        }
        int first = LocalDate.digit(text.charAt(index));
        int second = LocalDate.digit(text.charAt(index + 1));
        if (first < 0 || second < 0) {
            throw new IllegalArgumentException("Invalid ID for ZoneOffset: " + text);
        }
        return first * 10 + second;
    }

    /**
     * Format the offset.
     * 
     * @param totalSeconds Total seconds.
     * @return An offset id.
     */
    private static String format(int totalSeconds) {
        if (totalSeconds == 0) {
            return "Z";
        }
        int absolute = totalSeconds < 0 ? -totalSeconds : totalSeconds;
        StringBuilder builder = new StringBuilder();
        builder.append(totalSeconds < 0 ? '-' : '+');
        LocalDate.appendTwoDigits(builder, LocalDate.divide(absolute, 3600));
        builder.append(':');
        LocalDate.appendTwoDigits(builder, LocalDate.divide(absolute, 60) % 60);
        if (absolute % 60 != 0) {
            builder.append(':');
            LocalDate.appendTwoDigits(builder, absolute % 60);
        }
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(ZoneOffset other) {
        return totalSeconds - other.totalSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ZoneOffset offset && totalSeconds == offset.totalSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return totalSeconds;
    }
}
