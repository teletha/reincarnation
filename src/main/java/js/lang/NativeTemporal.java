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
 * The JavaScript Temporal API.
 */
@Native
public final class NativeTemporal {

    /**
     * Hide constructor.
     */
    private NativeTemporal() {
    }

    /**
     * Get the current instant.
     * 
     * @return An instant.
     */
    @Native("Temporal.Now.instant()")
    public static Object instantNow() {
        return null;
    }

    /**
     * Create the instant from the epoch nanoseconds.
     * 
     * @param nanos Epoch nanoseconds.
     * @return An instant.
     */
    @Native("Temporal.Instant.fromEpochNanoseconds($0)")
    public static Object instantOfEpochNano(long nanos) {
        return null;
    }

    /**
     * Get the epoch nanoseconds of the instant.
     * 
     * @param instant An instant.
     * @return Epoch nanoseconds.
     */
    @Native("$rt_l($0.epochNanoseconds)")
    public static long instantEpochNano(Object instant) {
        return 0L;
    }

    /**
     * Parse the instant.
     * 
     * @param text A text.
     * @return An instant.
     */
    @Native("Temporal.Instant.from($0)")
    public static Object instantFrom(String text) {
        return null;
    }

    /**
     * Add the duration to the instant.
     * 
     * @param instant An instant.
     * @param amount An amount.
     * @param unit A time unit.
     * @return An instant.
     */
    @Native("$0.add({[$2]: Number($1)})")
    public static Object instantAdd(Object instant, long amount, String unit) {
        return null;
    }

    /**
     * Subtract the duration from the instant.
     * 
     * @param instant An instant.
     * @param amount An amount.
     * @param unit A time unit.
     * @return An instant.
     */
    @Native("$0.subtract({[$2]: Number($1)})")
    public static Object instantSubtract(Object instant, long amount, String unit) {
        return null;
    }

    /**
     * Compare the instants.
     * 
     * @param left A left instant.
     * @param right A right instant.
     * @return A compared result.
     */
    @Native("Temporal.Instant.compare($0, $1)")
    public static int instantCompare(Object left, Object right) {
        return 0;
    }

    /**
     * Check whether the instants are equal.
     * 
     * @param left A left instant.
     * @param right A right instant.
     * @return A result.
     */
    @Native("$0.epochNanoseconds === $1.epochNanoseconds")
    public static boolean instantEquals(Object left, Object right) {
        return false;
    }

    /**
     * Create the zoned date and time from the instant.
     * 
     * @param instant An instant.
     * @param zone A time zone id.
     * @return A zoned date and time.
     */
    @Native("$0.toZonedDateTimeISO($1)")
    public static Object instantAtZone(Object instant, String zone) {
        return null;
    }

    /**
     * Get the current date.
     * 
     * @return A date.
     */
    @Native("Temporal.Now.plainDateISO()")
    public static Object dateNow() {
        return null;
    }

    /**
     * Create the date.
     * 
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @return A date.
     */
    @Native("Temporal.PlainDate.from({year: Number($0), month: Number($1), day: Number($2)})")
    public static Object dateOf(int year, int month, int day) {
        return null;
    }

    /**
     * Parse the date.
     * 
     * @param text A text.
     * @return A date.
     */
    @Native("Temporal.PlainDate.from($0)")
    public static Object dateFrom(String text) {
        return null;
    }

    /**
     * Get the year.
     * 
     * @param date A date.
     * @return A year.
     */
    @Native("$0.year")
    public static int dateYear(Object date) {
        return 0;
    }

    /**
     * Get the month.
     * 
     * @param date A date.
     * @return A month.
     */
    @Native("$0.month")
    public static int dateMonth(Object date) {
        return 0;
    }

    /**
     * Get the day.
     * 
     * @param date A date.
     * @return A day.
     */
    @Native("$0.day")
    public static int dateDay(Object date) {
        return 0;
    }

    /**
     * Get the day of week.
     * 
     * @param date A date.
     * @return A day of week.
     */
    @Native("$0.dayOfWeek")
    public static int dateDayOfWeek(Object date) {
        return 0;
    }

    /**
     * Get the day of year.
     * 
     * @param date A date.
     * @return A day of year.
     */
    @Native("$0.dayOfYear")
    public static int dateDayOfYear(Object date) {
        return 0;
    }

    /**
     * Get the length of the month.
     * 
     * @param date A date.
     * @return A length.
     */
    @Native("$0.daysInMonth")
    public static int dateDaysInMonth(Object date) {
        return 0;
    }

    /**
     * Get the length of the year.
     * 
     * @param date A date.
     * @return A length.
     */
    @Native("$0.daysInYear")
    public static int dateDaysInYear(Object date) {
        return 0;
    }

    /**
     * Check whether the year is a leap year.
     * 
     * @param date A date.
     * @return A result.
     */
    @Native("$0.inLeapYear")
    public static boolean dateInLeapYear(Object date) {
        return false;
    }

    /**
     * Add the duration to the date.
     * 
     * @param date A date.
     * @param amount An amount.
     * @param unit A date unit.
     * @return A date.
     */
    @Native("$0.add({[$2]: Number($1)})")
    public static Object dateAdd(Object date, long amount, String unit) {
        return null;
    }

    /**
     * Subtract the duration from the date.
     * 
     * @param date A date.
     * @param amount An amount.
     * @param unit A date unit.
     * @return A date.
     */
    @Native("$0.subtract({[$2]: Number($1)})")
    public static Object dateSubtract(Object date, long amount, String unit) {
        return null;
    }

    /**
     * Replace the date fields.
     * 
     * @param date A date.
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @return A date.
     */
    @Native("$0.with({year: Number($1), month: Number($2), day: Number($3)})")
    public static Object dateWith(Object date, int year, int month, int day) {
        return null;
    }

    /**
     * Get the epoch day.
     * 
     * @param date A date.
     * @return An epoch day.
     */
    @Native("$rt_l($0.since('1970-01-01', {largestUnit: 'days'}).days)")
    public static long dateEpochDay(Object date) {
        return 0L;
    }

    /**
     * Create the date from the epoch day.
     * 
     * @param day An epoch day.
     * @return A date.
     */
    @Native("Temporal.PlainDate.from('1970-01-01').add({days: Number($0)})")
    public static Object dateOfEpochDay(long day) {
        return null;
    }

    /**
     * Create the date and time from the date and the time.
     * 
     * @param date A date.
     * @param time A time.
     * @return A date and time.
     */
    @Native("$0.toPlainDateTime($1)")
    public static Object dateAtTime(Object date, Object time) {
        return null;
    }

    /**
     * Create the date and time at the start of the day.
     * 
     * @param date A date.
     * @return A date and time.
     */
    @Native("$0.toPlainDateTime({hour: 0, minute: 0, second: 0})")
    public static Object dateAtStartOfDay(Object date) {
        return null;
    }

    /**
     * Compare the dates.
     * 
     * @param left A left date.
     * @param right A right date.
     * @return A compared result.
     */
    @Native("Temporal.PlainDate.compare($0, $1)")
    public static int dateCompare(Object left, Object right) {
        return 0;
    }

    /**
     * Check whether the dates are equal.
     * 
     * @param left A left date.
     * @param right A right date.
     * @return A result.
     */
    @Native("$0.equals($1)")
    public static boolean dateEquals(Object left, Object right) {
        return false;
    }

    /**
     * Get the current time.
     * 
     * @return A time.
     */
    @Native("Temporal.Now.plainTimeISO()")
    public static Object timeNow() {
        return null;
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
    @Native("Temporal.PlainTime.from({hour: Number($0), minute: Number($1), second: Number($2), millisecond: Math.trunc(Number($3) / 1000000), microsecond: Math.trunc(Number($3) / 1000) % 1000, nanosecond: Number($3) % 1000})")
    public static Object timeOf(int hour, int minute, int second, int nano) {
        return null;
    }

    /**
     * Parse the time.
     * 
     * @param text A text.
     * @return A time.
     */
    @Native("Temporal.PlainTime.from($0)")
    public static Object timeFrom(String text) {
        return null;
    }

    /**
     * Get the hour.
     * 
     * @param time A time.
     * @return An hour.
     */
    @Native("$0.hour")
    public static int timeHour(Object time) {
        return 0;
    }

    /**
     * Get the minute.
     * 
     * @param time A time.
     * @return A minute.
     */
    @Native("$0.minute")
    public static int timeMinute(Object time) {
        return 0;
    }

    /**
     * Get the second.
     * 
     * @param time A time.
     * @return A second.
     */
    @Native("$0.second")
    public static int timeSecond(Object time) {
        return 0;
    }

    /**
     * Get the nanosecond.
     * 
     * @param time A time.
     * @return A nanosecond.
     */
    @Native("$0.millisecond * 1000000 + $0.microsecond * 1000 + $0.nanosecond")
    public static int timeNano(Object time) {
        return 0;
    }

    /**
     * Add the duration to the time.
     * 
     * @param time A time.
     * @param amount An amount.
     * @param unit A time unit.
     * @return A time.
     */
    @Native("$0.add({[$2]: Number($1)})")
    public static Object timeAdd(Object time, long amount, String unit) {
        return null;
    }

    /**
     * Subtract the duration from the time.
     * 
     * @param time A time.
     * @param amount An amount.
     * @param unit A time unit.
     * @return A time.
     */
    @Native("$0.subtract({[$2]: Number($1)})")
    public static Object timeSubtract(Object time, long amount, String unit) {
        return null;
    }

    /**
     * Replace the time fields.
     * 
     * @param time A time.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @param nano A nanosecond.
     * @return A time.
     */
    @Native("$0.with({hour: Number($1), minute: Number($2), second: Number($3), millisecond: Math.trunc(Number($4) / 1000000), microsecond: Math.trunc(Number($4) / 1000) % 1000, nanosecond: Number($4) % 1000})")
    public static Object timeWith(Object time, int hour, int minute, int second, int nano) {
        return null;
    }

    /**
     * Compare the times.
     * 
     * @param left A left time.
     * @param right A right time.
     * @return A compared result.
     */
    @Native("Temporal.PlainTime.compare($0, $1)")
    public static int timeCompare(Object left, Object right) {
        return 0;
    }

    /**
     * Check whether the times are equal.
     * 
     * @param left A left time.
     * @param right A right time.
     * @return A result.
     */
    @Native("$0.equals($1)")
    public static boolean timeEquals(Object left, Object right) {
        return false;
    }

    /**
     * Get the current date and time.
     * 
     * @return A date and time.
     */
    @Native("Temporal.Now.plainDateTimeISO()")
    public static Object dateTimeNow() {
        return null;
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
    @Native("Temporal.PlainDateTime.from({year: Number($0), month: Number($1), day: Number($2), hour: Number($3), minute: Number($4), second: Number($5), millisecond: Math.trunc(Number($6) / 1000000), microsecond: Math.trunc(Number($6) / 1000) % 1000, nanosecond: Number($6) % 1000})")
    public static Object dateTimeOf(int year, int month, int day, int hour, int minute, int second, int nano) {
        return null;
    }

    /**
     * Parse the date and time.
     * 
     * @param text A text.
     * @return A date and time.
     */
    @Native("Temporal.PlainDateTime.from($0)")
    public static Object dateTimeFrom(String text) {
        return null;
    }

    /**
     * Get the year.
     * 
     * @param dateTime A date and time.
     * @return A year.
     */
    @Native("$0.year")
    public static int dateTimeYear(Object dateTime) {
        return 0;
    }

    /**
     * Get the month.
     * 
     * @param dateTime A date and time.
     * @return A month.
     */
    @Native("$0.month")
    public static int dateTimeMonth(Object dateTime) {
        return 0;
    }

    /**
     * Get the day.
     * 
     * @param dateTime A date and time.
     * @return A day.
     */
    @Native("$0.day")
    public static int dateTimeDay(Object dateTime) {
        return 0;
    }

    /**
     * Get the day of week.
     * 
     * @param dateTime A date and time.
     * @return A day of week.
     */
    @Native("$0.dayOfWeek")
    public static int dateTimeDayOfWeek(Object dateTime) {
        return 0;
    }

    /**
     * Get the day of year.
     * 
     * @param dateTime A date and time.
     * @return A day of year.
     */
    @Native("$0.dayOfYear")
    public static int dateTimeDayOfYear(Object dateTime) {
        return 0;
    }

    /**
     * Get the hour.
     * 
     * @param dateTime A date and time.
     * @return An hour.
     */
    @Native("$0.hour")
    public static int dateTimeHour(Object dateTime) {
        return 0;
    }

    /**
     * Get the minute.
     * 
     * @param dateTime A date and time.
     * @return A minute.
     */
    @Native("$0.minute")
    public static int dateTimeMinute(Object dateTime) {
        return 0;
    }

    /**
     * Get the second.
     * 
     * @param dateTime A date and time.
     * @return A second.
     */
    @Native("$0.second")
    public static int dateTimeSecond(Object dateTime) {
        return 0;
    }

    /**
     * Get the nanosecond.
     * 
     * @param dateTime A date and time.
     * @return A nanosecond.
     */
    @Native("$0.millisecond * 1000000 + $0.microsecond * 1000 + $0.nanosecond")
    public static int dateTimeNano(Object dateTime) {
        return 0;
    }

    /**
     * Get the date part.
     * 
     * @param dateTime A date and time.
     * @return A date.
     */
    @Native("$0.toPlainDate()")
    public static Object dateTimeDate(Object dateTime) {
        return null;
    }

    /**
     * Get the time part.
     * 
     * @param dateTime A date and time.
     * @return A time.
     */
    @Native("$0.toPlainTime()")
    public static Object dateTimeTime(Object dateTime) {
        return null;
    }

    /**
     * Add the duration to the date and time.
     * 
     * @param dateTime A date and time.
     * @param amount An amount.
     * @param unit A unit.
     * @return A date and time.
     */
    @Native("$0.add({[$2]: Number($1)})")
    public static Object dateTimeAdd(Object dateTime, long amount, String unit) {
        return null;
    }

    /**
     * Subtract the duration from the date and time.
     * 
     * @param dateTime A date and time.
     * @param amount An amount.
     * @param unit A unit.
     * @return A date and time.
     */
    @Native("$0.subtract({[$2]: Number($1)})")
    public static Object dateTimeSubtract(Object dateTime, long amount, String unit) {
        return null;
    }

    /**
     * Replace the date fields of the date and time.
     * 
     * @param dateTime A date and time.
     * @param year A year.
     * @param month A month.
     * @param day A day.
     * @return A date and time.
     */
    @Native("$0.with({year: Number($1), month: Number($2), day: Number($3)})")
    public static Object dateTimeWithDate(Object dateTime, int year, int month, int day) {
        return null;
    }

    /**
     * Replace the time fields of the date and time.
     * 
     * @param dateTime A date and time.
     * @param hour An hour.
     * @param minute A minute.
     * @param second A second.
     * @param nano A nanosecond.
     * @return A date and time.
     */
    @Native("$0.with({hour: Number($1), minute: Number($2), second: Number($3), millisecond: Math.trunc(Number($4) / 1000000), microsecond: Math.trunc(Number($4) / 1000) % 1000, nanosecond: Number($4) % 1000})")
    public static Object dateTimeWithTime(Object dateTime, int hour, int minute, int second, int nano) {
        return null;
    }

    /**
     * Create the zoned date and time from the date and time.
     * 
     * @param dateTime A date and time.
     * @param zone A time zone id.
     * @return A zoned date and time.
     */
    @Native("$0.toZonedDateTime($1)")
    public static Object dateTimeAtZone(Object dateTime, String zone) {
        return null;
    }

    /**
     * Compare the dates and times.
     * 
     * @param left A left date and time.
     * @param right A right date and time.
     * @return A compared result.
     */
    @Native("Temporal.PlainDateTime.compare($0, $1)")
    public static int dateTimeCompare(Object left, Object right) {
        return 0;
    }

    /**
     * Check whether the dates and times are equal.
     * 
     * @param left A left date and time.
     * @param right A right date and time.
     * @return A result.
     */
    @Native("$0.equals($1)")
    public static boolean dateTimeEquals(Object left, Object right) {
        return false;
    }

    /**
     * Get the current zoned date and time in the system time zone.
     * 
     * @return A zoned date and time.
     */
    @Native("Temporal.Now.zonedDateTimeISO()")
    public static Object zonedNow() {
        return null;
    }

    /**
     * Get the current zoned date and time in the time zone.
     * 
     * @param zone A time zone id.
     * @return A zoned date and time.
     */
    @Native("Temporal.Now.zonedDateTimeISO($0)")
    public static Object zonedNow(String zone) {
        return null;
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
     * @param zone A time zone id.
     * @return A zoned date and time.
     */
    @Native("Temporal.ZonedDateTime.from({year: Number($0), month: Number($1), day: Number($2), hour: Number($3), minute: Number($4), second: Number($5), millisecond: Math.trunc(Number($6) / 1000000), microsecond: Math.trunc(Number($6) / 1000) % 1000, nanosecond: Number($6) % 1000, timeZone: $7})")
    public static Object zonedOf(int year, int month, int day, int hour, int minute, int second, int nano, String zone) {
        return null;
    }

    /**
     * Parse the zoned date and time.
     * 
     * @param text A text.
     * @return A zoned date and time.
     */
    @Native("Temporal.ZonedDateTime.from($0)")
    public static Object zonedFrom(String text) {
        return null;
    }

    /**
     * Get the year.
     * 
     * @param zoned A zoned date and time.
     * @return A year.
     */
    @Native("$0.year")
    public static int zonedYear(Object zoned) {
        return 0;
    }

    /**
     * Get the month.
     * 
     * @param zoned A zoned date and time.
     * @return A month.
     */
    @Native("$0.month")
    public static int zonedMonth(Object zoned) {
        return 0;
    }

    /**
     * Get the day.
     * 
     * @param zoned A zoned date and time.
     * @return A day.
     */
    @Native("$0.day")
    public static int zonedDay(Object zoned) {
        return 0;
    }

    /**
     * Get the day of week.
     * 
     * @param zoned A zoned date and time.
     * @return A day of week.
     */
    @Native("$0.dayOfWeek")
    public static int zonedDayOfWeek(Object zoned) {
        return 0;
    }

    /**
     * Get the day of year.
     * 
     * @param zoned A zoned date and time.
     * @return A day of year.
     */
    @Native("$0.dayOfYear")
    public static int zonedDayOfYear(Object zoned) {
        return 0;
    }

    /**
     * Get the hour.
     * 
     * @param zoned A zoned date and time.
     * @return An hour.
     */
    @Native("$0.hour")
    public static int zonedHour(Object zoned) {
        return 0;
    }

    /**
     * Get the minute.
     * 
     * @param zoned A zoned date and time.
     * @return A minute.
     */
    @Native("$0.minute")
    public static int zonedMinute(Object zoned) {
        return 0;
    }

    /**
     * Get the second.
     * 
     * @param zoned A zoned date and time.
     * @return A second.
     */
    @Native("$0.second")
    public static int zonedSecond(Object zoned) {
        return 0;
    }

    /**
     * Get the nanosecond.
     * 
     * @param zoned A zoned date and time.
     * @return A nanosecond.
     */
    @Native("$0.millisecond * 1000000 + $0.microsecond * 1000 + $0.nanosecond")
    public static int zonedNano(Object zoned) {
        return 0;
    }

    /**
     * Get the time zone id.
     * 
     * @param zoned A zoned date and time.
     * @return A time zone id.
     */
    @Native("$0.timeZoneId")
    public static String zonedZone(Object zoned) {
        return null;
    }

    /**
     * Get the offset text.
     * 
     * @param zoned A zoned date and time.
     * @return An offset text.
     */
    @Native("$0.offset")
    public static String zonedOffset(Object zoned) {
        return null;
    }

    /**
     * Get the instant.
     * 
     * @param zoned A zoned date and time.
     * @return An instant.
     */
    @Native("$0.toInstant()")
    public static Object zonedInstant(Object zoned) {
        return null;
    }

    /**
     * Get the date part.
     * 
     * @param zoned A zoned date and time.
     * @return A date.
     */
    @Native("$0.toPlainDate()")
    public static Object zonedDate(Object zoned) {
        return null;
    }

    /**
     * Get the time part.
     * 
     * @param zoned A zoned date and time.
     * @return A time.
     */
    @Native("$0.toPlainTime()")
    public static Object zonedTime(Object zoned) {
        return null;
    }

    /**
     * Get the date and time part.
     * 
     * @param zoned A zoned date and time.
     * @return A date and time.
     */
    @Native("$0.toPlainDateTime()")
    public static Object zonedDateTime(Object zoned) {
        return null;
    }

    /**
     * Add the duration to the zoned date and time.
     * 
     * @param zoned A zoned date and time.
     * @param amount An amount.
     * @param unit A unit.
     * @return A zoned date and time.
     */
    @Native("$0.add({[$2]: Number($1)})")
    public static Object zonedAdd(Object zoned, long amount, String unit) {
        return null;
    }

    /**
     * Subtract the duration from the zoned date and time.
     * 
     * @param zoned A zoned date and time.
     * @param amount An amount.
     * @param unit A unit.
     * @return A zoned date and time.
     */
    @Native("$0.subtract({[$2]: Number($1)})")
    public static Object zonedSubtract(Object zoned, long amount, String unit) {
        return null;
    }

    /**
     * Replace the time zone keeping the instant.
     * 
     * @param zoned A zoned date and time.
     * @param zone A time zone id.
     * @return A zoned date and time.
     */
    @Native("$0.withTimeZone($1)")
    public static Object zonedWithZone(Object zoned, String zone) {
        return null;
    }

    /**
     * Compare the zoned dates and times.
     * 
     * @param left A left zoned date and time.
     * @param right A right zoned date and time.
     * @return A compared result.
     */
    @Native("Temporal.ZonedDateTime.compare($0, $1)")
    public static int zonedCompare(Object left, Object right) {
        return 0;
    }

    /**
     * Check whether the zoned dates and times are equal.
     * 
     * @param left A left zoned date and time.
     * @param right A right zoned date and time.
     * @return A result.
     */
    @Native("$0.equals($1)")
    public static boolean zonedEquals(Object left, Object right) {
        return false;
    }

    /**
     * Get the text.
     * 
     * @param value A value.
     * @return A text.
     */
    @Native("$0.toString()")
    public static String text(Object value) {
        return null;
    }

    /**
     * Get the system time zone id.
     * 
     * @return A time zone id.
     */
    @Native("Temporal.Now.timeZoneId()")
    public static String systemZone() {
        return null;
    }

    /**
     * Check whether the time zone id is valid.
     * 
     * @param zone A time zone id.
     * @return A result.
     */
    @Native("(() => { try { Temporal.ZonedDateTime.from({year: 2000, month: 1, day: 1, timeZone: $0}); return true; } catch (e) { return false; } })()")
    public static boolean validZone(String zone) {
        return false;
    }
}
