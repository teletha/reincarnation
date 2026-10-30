/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.time;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for {@link Date}.
 */
class DateTest extends JavascriptCodeVerifier {

    @Test
    void time() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(1234567890123L);
                Date same = new Date(1234567890123L);
                Date later = new Date(1234567891123L);
                return date.getTime() + ":" + date.equals(same) + ":" + date.before(later) + ":" + date.after(later) + ":" + date.compareTo(later) + ":" + date.hashCode();
            }
        });
    }

    @Test
    void instant() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = Date.from(Instant.ofEpochMilli(1577934245000L));
                return date.getTime() + ":" + date.toInstant().toEpochMilli() + ":" + date.toInstant();
            }
        });
    }

    @Test
    void fields() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(2020 - 1900, 0, 2, 3, 4, 5);
                return date.getYear() + ":" + date.getMonth() + ":" + date.getDate() + ":" + date.getDay() + ":" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ":" + date.getTimezoneOffset() + ":" + date.toString();
            }
        });
    }

    @Test
    void setters() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(0L);
                date.setYear(120);
                date.setMonth(5);
                date.setDate(15);
                date.setHours(12);
                date.setMinutes(30);
                date.setSeconds(45);
                date.setTime(1000L);
                return date.getTime() + ":" + date.toString();
            }
        });
    }

    @Test
    void utc() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Date.UTC(2020 - 1900, 0, 2, 3, 4, 5) + ":" + Date.UTC(1970 - 1900, 0, 1, 0, 0, 0);
            }
        });
    }

    @Test
    void parse() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Date.parse("Thu, 02 Jan 2020 03:04:05 GMT") + ":" + new Date("Jan 2, 2020 3:04:05 AM").getTime();
            }
        });
    }

    @Test
    void epochText() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return new Date(0L).toString() + ":" + new Date(0L).toGMTString();
            }
        });
    }

    @Test
    void constructors() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(2020 - 1900, 11, 31);
                Date dateTime = new Date(2020 - 1900, 11, 31, 23, 59);
                Date dateTimeSecond = new Date(2020 - 1900, 11, 31, 23, 59, 58);
                return date.getTime() + ":" + dateTime.getTime() + ":" + dateTimeSecond.getTime() + ":" + date + ":" + dateTime + ":" + dateTimeSecond;
            }
        });
    }

    @Test
    void cloneAndBoundaries() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(1000L);
                Date clone = (Date) date.clone();
                Date before = new Date(999L);
                return clone.equals(date) + ":" + (clone == date ? "same" : "different") + ":" + before.before(date) + ":" + date.after(before) + ":" + date.compareTo(clone) + ":" + date.hashCode() + ":" + before.getTime();
            }
        });
    }

    @Test
    void settersIndividual() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(0L);
                date.setYear(121);
                date.setMonth(11);
                date.setDate(31);
                date.setHours(23);
                date.setMinutes(59);
                date.setSeconds(58);
                return date.getYear() + ":" + date.getMonth() + ":" + date.getDate() + ":" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ":" + date;
            }
        });
    }

    @Test
    void negativeTime() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Date date = new Date(-1000L);
                return date.getTime() + ":" + date.toInstant() + ":" + date;
            }
        });
    }
}
