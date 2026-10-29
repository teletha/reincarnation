/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

/**
 * The JavaScript date and time functions.
 */
@Native
public final class NativeDate {

    /**
     * Hide constructor.
     */
    private NativeDate() {
    }

    /**
     * Get the current time in milliseconds.
     * 
     * @return A time.
     */
    @Native("$rt_l(Date.now())")
    public static long now() {
        return java.lang.System.currentTimeMillis();
    }

    /**
     * Get the current time in nanoseconds.
     * 
     * @return A time.
     */
    @Native("$rt_l($rt_nanoTime())")
    public static long nanoTime() {
        return java.lang.System.nanoTime();
    }

    /**
     * Get the year of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A year.
     */
    @Native("new Date(Number($0)).getFullYear()")
    public static int year(long time) {
        return 0;
    }

    /**
     * Get the month of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A month.
     */
    @Native("new Date(Number($0)).getMonth()")
    public static int month(long time) {
        return 0;
    }

    /**
     * Get the day of month of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A day of month.
     */
    @Native("new Date(Number($0)).getDate()")
    public static int dayOfMonth(long time) {
        return 0;
    }

    /**
     * Get the day of week of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A day of week.
     */
    @Native("new Date(Number($0)).getDay()")
    public static int dayOfWeek(long time) {
        return 0;
    }

    /**
     * Get the hour of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return An hour.
     */
    @Native("new Date(Number($0)).getHours()")
    public static int hour(long time) {
        return 0;
    }

    /**
     * Get the minute of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A minute.
     */
    @Native("new Date(Number($0)).getMinutes()")
    public static int minute(long time) {
        return 0;
    }

    /**
     * Get the second of the local time.
     * 
     * @param time Epoch milliseconds.
     * @return A second.
     */
    @Native("new Date(Number($0)).getSeconds()")
    public static int second(long time) {
        return 0;
    }

    /**
     * Get the time zone offset.
     * 
     * @param time Epoch milliseconds.
     * @return An offset in minutes.
     */
    @Native("new Date(Number($0)).getTimezoneOffset()")
    public static int timezoneOffset(long time) {
        return 0;
    }

    /**
     * Create the epoch milliseconds of the local time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @return Epoch milliseconds.
     */
    @Native("(() => { const d = new Date(0); d.setHours(Number($3), Number($4), Number($5), 0); d.setFullYear(Number($0), Number($1), Number($2)); return $rt_l(d.getTime()); })()")
    public static long localTime(int year, int month, int day, int hour, int minute, int second) {
        return 0L;
    }

    /**
     * Create the epoch milliseconds of the UTC time.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @return Epoch milliseconds.
     */
    @Native("(() => { const d = new Date(0); d.setUTCHours(Number($3), Number($4), Number($5), 0); d.setUTCFullYear(Number($0), Number($1), Number($2)); return $rt_l(d.getTime()); })()")
    public static long utcTime(int year, int month, int day, int hour, int minute, int second) {
        return 0L;
    }
}
