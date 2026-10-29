/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.classlib.java.util;

import js.lang.NativeDate;
import reincarnation.classlib.java.io.Serializable;
import reincarnation.classlib.java.lang.Cloneable;
import reincarnation.classlib.java.lang.Comparable;
import reincarnation.classlib.java.time.Instant;
import reincarnation.classlib.java.time.LocalDate;

/**
 * The Java implementation of {@link java.util.Date} which is transpiled into JavaScript.
 */
public class Date implements Serializable, Cloneable, Comparable<Date> {

    /** The epoch milliseconds. */
    private long time;

    /**
     * Create the current date.
     */
    public Date() {
        this.time = java.lang.System.currentTimeMillis();
    }

    /**
     * Create the date.
     * 
     * @param date Epoch milliseconds.
     */
    public Date(long date) {
        this.time = date;
    }

    /**
     * Create the date.
     * 
     * @param year A year minus 1900.
     * @param month A month.
     * @param date A day of month.
     */
    public Date(int year, int month, int date) {
        this(year, month, date, 0, 0, 0);
    }

    /**
     * Create the date.
     * 
     * @param year A year minus 1900.
     * @param month A month.
     * @param date A day of month.
     * @param hrs Hours.
     * @param min Minutes.
     */
    public Date(int year, int month, int date, int hrs, int min) {
        this(year, month, date, hrs, min, 0);
    }

    /**
     * Create the date.
     * 
     * @param year A year minus 1900.
     * @param month A month.
     * @param date A day of month.
     * @param hrs Hours.
     * @param min Minutes.
     * @param sec Seconds.
     */
    public Date(int year, int month, int date, int hrs, int min, int sec) {
        this.time = NativeDate.localTime(year + 1900, month, date, hrs, min, sec);
    }

    /**
     * Create the date.
     * 
     * @param s A date text.
     */
    public Date(String s) {
        this.time = parse(s);
    }

    /**
     * Create the date from the instant.
     * 
     * @param instant An instant.
     * @return A date.
     */
    public static Date from(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    /**
     * Get the epoch milliseconds of the UTC fields.
     * 
     * @param year A year minus 1900.
     * @param month A month.
     * @param date A day of month.
     * @param hrs Hours.
     * @param min Minutes.
     * @param sec Seconds.
     * @return Epoch milliseconds.
     */
    public static long UTC(int year, int month, int date, int hrs, int min, int sec) {
        return NativeDate.utcTime(year + 1900, month, date, hrs, min, sec);
    }

    /**
     * Parse the date text.
     * 
     * @param s A date text.
     * @return Epoch milliseconds.
     */
    public static long parse(String s) {
        String[] tokens = tokenize(s);
        int month = -1;
        int day = -1;
        int year = -1;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int zone = Integer.MIN_VALUE;
        boolean pm = false;
        boolean am = false;

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token == null) {
                break;
            }
            if (token.equalsIgnoreCase("am")) {
                am = true;
                continue;
            }
            if (token.equalsIgnoreCase("pm")) {
                pm = true;
                continue;
            }
            int value = monthIndex(token);
            if (value >= 0) {
                month = value;
                continue;
            }
            int offset = zoneOffset(token);
            if (offset != Integer.MIN_VALUE) {
                zone = offset;
                continue;
            }
            if (token.indexOf(":") >= 0) {
                int first = token.indexOf(":");
                int last = token.indexOf(":", first + 1);
                hour = number(token.substring(0, first));
                if (last < 0) {
                    minute = number(token.substring(first + 1));
                } else {
                    minute = number(token.substring(first + 1, last));
                    second = number(token.substring(last + 1));
                }
                continue;
            }
            if (isNumber(token)) {
                int numeric = number(token);
                if (year < 0 && day < 0 && month >= 0 && token.length() <= 2) {
                    day = numeric;
                } else if (day < 0) {
                    day = numeric;
                } else if (year < 0) {
                    year = numeric;
                }
            }
        }

        if (pm && hour < 12) {
            hour += 12;
        }
        if (am && hour == 12) {
            hour = 0;
        }
        if (month < 0 || day < 0 || year < 0) {
            throw new IllegalArgumentException("Cannot parse date: " + s);
        }
        if (zone == Integer.MIN_VALUE) {
            return NativeDate.localTime(year, month, day, hour, minute, second);
        }
        return NativeDate.utcTime(year, month, day, hour, minute, second) - zone * 60000L;
    }

    /**
     * Get the epoch milliseconds.
     * 
     * @return Epoch milliseconds.
     */
    public long getTime() {
        return time;
    }

    /**
     * Set the epoch milliseconds.
     * 
     * @param time Epoch milliseconds.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Check whether this date is before the other.
     * 
     * @param when A date.
     * @return A result.
     */
    public boolean before(Date when) {
        return time < when.time;
    }

    /**
     * Check whether this date is after the other.
     * 
     * @param when A date.
     * @return A result.
     */
    public boolean after(Date when) {
        return time > when.time;
    }

    /**
     * Get the instant.
     * 
     * @return An instant.
     */
    public Instant toInstant() {
        return Instant.ofEpochMilli(time);
    }

    /**
     * Get the year.
     * 
     * @return A year minus 1900.
     */
    public int getYear() {
        return NativeDate.year(time) - 1900;
    }

    /**
     * Get the month.
     * 
     * @return A month.
     */
    public int getMonth() {
        return NativeDate.month(time);
    }

    /**
     * Get the day of month.
     * 
     * @return A day of month.
     */
    public int getDate() {
        return NativeDate.dayOfMonth(time);
    }

    /**
     * Get the day of week.
     * 
     * @return A day of week.
     */
    public int getDay() {
        return NativeDate.dayOfWeek(time);
    }

    /**
     * Get the hours.
     * 
     * @return Hours.
     */
    public int getHours() {
        return NativeDate.hour(time);
    }

    /**
     * Get the minutes.
     * 
     * @return Minutes.
     */
    public int getMinutes() {
        return NativeDate.minute(time);
    }

    /**
     * Get the seconds.
     * 
     * @return Seconds.
     */
    public int getSeconds() {
        return NativeDate.second(time);
    }

    /**
     * Get the time zone offset.
     * 
     * @return An offset in minutes.
     */
    public int getTimezoneOffset() {
        return NativeDate.timezoneOffset(time);
    }

    /**
     * Set the year.
     * 
     * @param year A year minus 1900.
     */
    public void setYear(int year) {
        this.time = NativeDate.localTime(year + 1900, getMonth(), getDate(), getHours(), getMinutes(), getSeconds());
    }

    /**
     * Set the month.
     * 
     * @param month A month.
     */
    public void setMonth(int month) {
        this.time = NativeDate.localTime(getYear() + 1900, month, getDate(), getHours(), getMinutes(), getSeconds());
    }

    /**
     * Set the day of month.
     * 
     * @param date A day of month.
     */
    public void setDate(int date) {
        this.time = NativeDate.localTime(getYear() + 1900, getMonth(), date, getHours(), getMinutes(), getSeconds());
    }

    /**
     * Set the hours.
     * 
     * @param hours Hours.
     */
    public void setHours(int hours) {
        this.time = NativeDate.localTime(getYear() + 1900, getMonth(), getDate(), hours, getMinutes(), getSeconds());
    }

    /**
     * Set the minutes.
     * 
     * @param minutes Minutes.
     */
    public void setMinutes(int minutes) {
        this.time = NativeDate.localTime(getYear() + 1900, getMonth(), getDate(), getHours(), minutes, getSeconds());
    }

    /**
     * Set the seconds.
     * 
     * @param seconds Seconds.
     */
    public void setSeconds(int seconds) {
        this.time = NativeDate.localTime(getYear() + 1900, getMonth(), getDate(), getHours(), getMinutes(), seconds);
    }

    /**
     * Get the GMT text.
     * 
     * @return A text.
     */
    public String toGMTString() {
        long epochDay = Instant.floorDiv(time, 86400000L);
        long millisOfDay = Instant.floorMod(time, 86400000L);
        int hour = (int) (millisOfDay / 3600000L);
        int minute = (int) ((millisOfDay % 3600000L) / 60000L);
        int second = (int) ((millisOfDay % 60000L) / 1000L);
        LocalDate date = LocalDate.ofEpochDay(epochDay);

        StringBuilder builder = new StringBuilder();
        builder.append(date.getDayOfMonth());
        builder.append(' ');
        builder.append(month(date.getMonthValue() - 1));
        builder.append(' ');
        LocalDate.appendYear(builder, date.getYear());
        builder.append(' ');
        LocalDate.appendTwoDigits(builder, hour);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, minute);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, second);
        builder.append(" GMT");
        return builder.toString();
    }

    /**
     * Get the locale text.
     * 
     * @return A text.
     */
    public String toLocaleString() {
        return toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        return new Date(time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Date other) {
        if (time < other.time) {
            return -1;
        }
        if (time > other.time) {
            return 1;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Date date && time == date.time;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (int) (time ^ (time >>> 32));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return format(time, -NativeDate.timezoneOffset(time));
    }

    /**
     * Format the date.
     * 
     * @param time Epoch milliseconds.
     * @param offset An offset in minutes.
     * @return A text.
     */
    private static String format(long time, int offset) {
        long shifted = time + offset * 60000L;
        long epochDay = Instant.floorDiv(shifted, 86400000L);
        long millisOfDay = Instant.floorMod(shifted, 86400000L);
        int hour = (int) (millisOfDay / 3600000L);
        int minute = (int) ((millisOfDay % 3600000L) / 60000L);
        int second = (int) ((millisOfDay % 60000L) / 1000L);
        LocalDate date = LocalDate.ofEpochDay(epochDay);

        StringBuilder builder = new StringBuilder(weekday(date.getDayOfWeek().getValue() % 7));
        builder.append(' ');
        builder.append(month(date.getMonthValue() - 1));
        builder.append(' ');
        LocalDate.appendTwoDigits(builder, date.getDayOfMonth());
        builder.append(' ');
        LocalDate.appendTwoDigits(builder, hour);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, minute);
        builder.append(':');
        LocalDate.appendTwoDigits(builder, second);
        builder.append(' ');
        builder.append(zone(offset));
        builder.append(' ');
        LocalDate.appendYear(builder, date.getYear());
        return builder.toString();
    }

    /**
     * Get the zone text.
     * 
     * @param offset An offset in minutes.
     * @return A text.
     */
    private static String zone(int offset) {
        if (offset == 0) {
            return "GMT";
        }
        int absolute = offset < 0 ? -offset : offset;
        StringBuilder builder = new StringBuilder("GMT");
        builder.append(offset < 0 ? '-' : '+');
        LocalDate.appendTwoDigits(builder, LocalDate.divide(absolute, 60));
        builder.append(':');
        LocalDate.appendTwoDigits(builder, absolute % 60);
        return builder.toString();
    }

    /**
     * Get the weekday name.
     * 
     * @param day A day of week.
     * @return A name.
     */
    private static String weekday(int day) {
        if (day == 0) {
            return "Sun";
        }
        if (day == 1) {
            return "Mon";
        }
        if (day == 2) {
            return "Tue";
        }
        if (day == 3) {
            return "Wed";
        }
        if (day == 4) {
            return "Thu";
        }
        if (day == 5) {
            return "Fri";
        }
        return "Sat";
    }

    /**
     * Get the month name.
     * 
     * @param month A month.
     * @return A name.
     */
    private static String month(int month) {
        if (month == 0) {
            return "Jan";
        }
        if (month == 1) {
            return "Feb";
        }
        if (month == 2) {
            return "Mar";
        }
        if (month == 3) {
            return "Apr";
        }
        if (month == 4) {
            return "May";
        }
        if (month == 5) {
            return "Jun";
        }
        if (month == 6) {
            return "Jul";
        }
        if (month == 7) {
            return "Aug";
        }
        if (month == 8) {
            return "Sep";
        }
        if (month == 9) {
            return "Oct";
        }
        if (month == 10) {
            return "Nov";
        }
        return "Dec";
    }

    /**
     * Get the month index.
     * 
     * @param token A token.
     * @return A month index or -1.
     */
    private static int monthIndex(String token) {
        if (token.equalsIgnoreCase("jan")) {
            return 0;
        }
        if (token.equalsIgnoreCase("feb")) {
            return 1;
        }
        if (token.equalsIgnoreCase("mar")) {
            return 2;
        }
        if (token.equalsIgnoreCase("apr")) {
            return 3;
        }
        if (token.equalsIgnoreCase("may")) {
            return 4;
        }
        if (token.equalsIgnoreCase("jun")) {
            return 5;
        }
        if (token.equalsIgnoreCase("jul")) {
            return 6;
        }
        if (token.equalsIgnoreCase("aug")) {
            return 7;
        }
        if (token.equalsIgnoreCase("sep")) {
            return 8;
        }
        if (token.equalsIgnoreCase("oct")) {
            return 9;
        }
        if (token.equalsIgnoreCase("nov")) {
            return 10;
        }
        if (token.equalsIgnoreCase("dec")) {
            return 11;
        }
        return -1;
    }

    /**
     * Get the time zone offset of the token.
     * 
     * @param token A token.
     * @return An offset in minutes or {@link Integer#MIN_VALUE}.
     */
    private static int zoneOffset(String token) {
        String value = token.toUpperCase();
        if (value.equals("GMT") || value.equals("UTC") || value.equals("UT") || value.equals("Z")) {
            return 0;
        }
        if (value.startsWith("GMT") || value.startsWith("UTC")) {
            return parseOffset(value, 3);
        }
        if (value.equals("JST")) {
            return 540;
        }
        if (value.equals("EST")) {
            return -300;
        }
        if (value.equals("EDT")) {
            return -240;
        }
        if (value.equals("CST")) {
            return -360;
        }
        if (value.equals("CDT")) {
            return -300;
        }
        if (value.equals("MST")) {
            return -420;
        }
        if (value.equals("MDT")) {
            return -360;
        }
        if (value.equals("PST")) {
            return -480;
        }
        if (value.equals("PDT")) {
            return -420;
        }
        if (value.equals("CET")) {
            return 60;
        }
        if (value.equals("CEST")) {
            return 120;
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Parse the offset text.
     * 
     * @param value A text.
     * @param index A start index.
     * @return An offset in minutes.
     */
    private static int parseOffset(String value, int index) {
        if (index >= value.length()) {
            return 0;
        }
        int sign = value.charAt(index) == '-' ? -1 : 1;
        if (value.charAt(index) == '+' || value.charAt(index) == '-') {
            index++;
        }
        int first = digit(value.charAt(index));
        int second = index + 1 < value.length() ? digit(value.charAt(index + 1)) : -1;
        int hours = (first < 0 ? 0 : first) * 10 + (second < 0 ? 0 : second);
        index += 2;
        int minutes = 0;
        if (index < value.length() && value.charAt(index) == ':') {
            index++;
        }
        if (index + 1 < value.length()) {
            int minuteFirst = digit(value.charAt(index));
            int minuteSecond = digit(value.charAt(index + 1));
            if (minuteFirst >= 0 && minuteSecond >= 0) {
                minutes = minuteFirst * 10 + minuteSecond;
            }
        }
        return sign * (hours * 60 + minutes);
    }

    /**
     * Tokenize the text.
     * 
     * @param text A text.
     * @return Tokens.
     */
    private static String[] tokenize(String text) {
        String[] tokens = new String[10];
        int count = 0;
        int start = -1;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ' || c == '\t' || c == ',') {
                if (start >= 0 && count < tokens.length) {
                    tokens[count++] = text.substring(start, i);
                }
                start = -1;
            } else if (start < 0) {
                start = i;
            }
        }
        if (start >= 0 && count < tokens.length) {
            tokens[count++] = text.substring(start, text.length());
        }
        return tokens;
    }

    /**
     * Check whether the token is a number.
     * 
     * @param token A token.
     * @return A result.
     */
    private static boolean isNumber(String token) {
        if (token.length() == 0) {
            return false;
        }
        for (int i = 0; i < token.length(); i++) {
            if (digit(token.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parse the number.
     * 
     * @param token A token.
     * @return A number.
     */
    private static int number(String token) {
        int value = 0;
        for (int i = 0; i < token.length(); i++) {
            value = value * 10 + digit(token.charAt(i));
        }
        return value;
    }

    /**
     * Get the digit value.
     * 
     * @param value A character.
     * @return A digit or -1.
     */
    private static int digit(char value) {
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
}
