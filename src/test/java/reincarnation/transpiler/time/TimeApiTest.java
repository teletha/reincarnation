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

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@code java.time} API.
 */
class TimeApiTest extends JavascriptCodeVerifier {

    @Test
    void instant() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Instant instant = Instant.parse("2020-01-02T03:04:05.123456789Z");
                return instant.getEpochSecond() + ":" + instant.getNano() + ":" + instant.toEpochMilli() + ":" + instant;
            }
        });
    }

    @Test
    void instantArithmetic() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Instant instant = Instant.ofEpochSecond(1000L, 500L);
                Instant later = instant.plusSeconds(60L).minusNanos(500L);
                return later + ":" + later.isAfter(instant) + ":" + instant.plus(Duration.ofMillis(250L));
            }
        });
    }

    @Test
    void localDate() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDate date = LocalDate.of(2020, 2, 29);
                return date + ":" + date.getYear() + ":" + date.getMonth() + ":" + date.getMonthValue() + ":" + date.getDayOfMonth() + ":" + date.getDayOfYear() + ":" + date.getDayOfWeek() + ":" + date.isLeapYear() + ":" + date.lengthOfMonth() + ":" + date.toEpochDay();
            }
        });
    }

    @Test
    void localDateArithmetic() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDate date = LocalDate.parse("2020-01-31");
                return date.plusMonths(1L) + ":" + date.plusDays(1L) + ":" + date.plusYears(1L) + ":" + date.minusMonths(2L) + ":" + date.withDayOfMonth(15) + ":" + LocalDate.ofEpochDay(0L);
            }
        });
    }

    @Test
    void localTime() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalTime time = LocalTime.of(23, 59, 58, 123456789);
                return time + ":" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + ":" + time.getNano() + ":" + time.toSecondOfDay() + ":" + time.toNanoOfDay() + ":" + time.plusSeconds(5L) + ":" + time.minusHours(1L) + ":" + LocalTime.of(1, 2, 3) + ":" + LocalTime.of(1, 2);
            }
        });
    }

    @Test
    void localDateTime() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDateTime dateTime = LocalDateTime.of(2020, 2, 29, 12, 30, 15, 123000000);
                return dateTime + ":" + dateTime.getYear() + ":" + dateTime.getHour() + ":" + dateTime.toLocalDate() + ":" + dateTime.toLocalTime() + ":" + dateTime.plusDays(1L) + ":" + dateTime.plusHours(12L) + ":" + dateTime.minusMinutes(30L);
            }
        });
    }

    @Test
    void zonedDateTime() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                ZoneId zone = ZoneId.of("Asia/Tokyo");
                ZonedDateTime zoned = ZonedDateTime.of(2020, 2, 29, 12, 30, 15, 123000000, zone);
                return zoned + ":" + zoned.getZone() + ":" + zoned.getOffset() + ":" + zoned.toEpochSecond() + ":" + zoned.toInstant() + ":" + zoned.toLocalDate() + ":" + zoned.plusHours(1L) + ":" + zoned.withZoneSameInstant(ZoneOffset.UTC);
            }
        });
    }

    @Test
    void duration() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Duration duration = Duration.ofSeconds(3661L, 500000000L);
                Duration parsed = Duration.parse("PT1H1M1.5S");
                return duration + ":" + duration.getSeconds() + ":" + duration.getNano() + ":" + duration.toMillis() + ":" + duration.toNanos() + ":" + duration.plusSeconds(10L) + ":" + duration.multipliedBy(2L) + ":" + parsed + ":" + parsed.equals(duration) + ":" + Duration.ofMillis(-1500L);
            }
        });
    }

    @Test
    void period() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Period period = Period.between(LocalDate.of(2020, 1, 31), LocalDate.of(2021, 3, 1));
                return period + ":" + period.getYears() + ":" + period.getMonths() + ":" + period.getDays() + ":" + period.plusMonths(1L) + ":" + LocalDate.of(2020, 1, 31).plus(period);
            }
        });
    }

    @Test
    void zoneOffset() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                ZoneOffset offset = ZoneOffset.of("+09:30");
                return offset + ":" + offset.getTotalSeconds() + ":" + ZoneOffset.UTC + ":" + ZoneOffset.ofHours(-2) + ":" + offset.equals(ZoneOffset.ofHoursMinutes(9, 30)) + ":" + ZoneId.of("+09:30").normalized();
            }
        });
    }

    @Test
    void temporalInterface() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                java.time.temporal.Temporal temporal = LocalDate.of(2020, 1, 31);
                java.time.temporal.Temporal later = temporal.plus(1L, java.time.temporal.ChronoUnit.MONTHS);
                return later + ":" + temporal.get(java.time.temporal.ChronoField.YEAR) + ":" + java.time.temporal.ChronoUnit.DAYS.between(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));
            }
        });
    }

    @Test
    void monthAndDayOfWeek() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return Month.of(2) + ":" + Month.JANUARY.plus(12L) + ":" + Month.FEBRUARY.length(true) + ":" + DayOfWeek.of(1) + ":" + DayOfWeek.SUNDAY.plus(1L) + ":" + LocalDate.of(2020, 1, 1).getDayOfWeek().getValue();
            }
        });
    }

    @Test
    void instantEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Instant instant = Instant.ofEpochSecond(-1L, 500000000L);
                return instant.getEpochSecond() + ":" + instant.getNano() + ":" + instant.toEpochMilli() + ":" + instant + ":" + Instant.EPOCH + ":" + instant.isBefore(Instant.EPOCH) + ":" + instant.until(Instant.EPOCH, java.time.temporal.ChronoUnit.MILLIS);
            }
        });
    }

    @Test
    void localDateEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDate date = LocalDate.of(2021, 1, 31);
                return date.plusMonths(1L) + ":" + date.minusMonths(1L) + ":" + date.withYear(2020) + ":" + LocalDate.of(2020, 2, 29).withYear(2021) + ":" + date.until(LocalDate.of(2022, 3, 2), java.time.temporal.ChronoUnit.DAYS) + ":" + LocalDate.of(2021, 1, 31).until(LocalDate.of(2021, 2, 28), java.time.temporal.ChronoUnit.MONTHS) + ":" + LocalDate.ofEpochDay(-1L) + ":" + date.with(java.time.temporal.ChronoField.DAY_OF_YEAR, 1L) + ":" + date.getLong(java.time.temporal.ChronoField.EPOCH_DAY);
            }
        });
    }

    @Test
    void localTimeEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalTime time = LocalTime.of(23, 59, 59, 999999999);
                LocalTime midnight = LocalTime.of(0, 0);
                return time.plusNanos(1L) + ":" + midnight.minusNanos(1L) + ":" + LocalTime.ofNanoOfDay(3661000000000L) + ":" + LocalTime.ofSecondOfDay(3661L) + ":" + time.until(midnight, java.time.temporal.ChronoUnit.NANOS) + ":" + time.withNano(0) + ":" + time.compareTo(midnight);
            }
        });
    }

    @Test
    void localDateTimeEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDateTime dateTime = LocalDateTime.of(2020, 1, 31, 23, 30);
                return dateTime.plusHours(1L) + ":" + dateTime.plusMonths(1L) + ":" + dateTime.until(LocalDateTime.of(2020, 2, 1, 0, 30), java.time.temporal.ChronoUnit.HOURS) + ":" + dateTime.withHour(0) + ":" + dateTime.withDayOfMonth(1) + ":" + dateTime.atZone(ZoneId.of("Asia/Tokyo")).toInstant();
            }
        });
    }

    @Test
    void zonedDateTimeEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                ZonedDateTime zoned = ZonedDateTime.of(2020, 1, 31, 23, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
                ZonedDateTime fixed = zoned.withFixedOffsetZone();
                return zoned.plusHours(2L) + ":" + zoned.plusDays(1L) + ":" + zoned.withZoneSameLocal(ZoneOffset.UTC) + ":" + fixed + ":" + fixed.getZone() + ":" + zoned.getOffset() + ":" + zoned.isEqual(fixed) + ":" + zoned.toInstant().atZone(ZoneOffset.UTC).isEqual(zoned);
            }
        });
    }

    @Test
    void durationEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Duration duration = Duration.ofSeconds(90061L, 500000000L);
                Duration parsed = Duration.parse("P2DT3H4M5.6S");
                return duration.dividedBy(3L) + ":" + duration.negated() + ":" + duration.abs() + ":" + Duration.ofSeconds(-1L, 500000000L) + ":" + parsed + ":" + duration.toDays() + ":" + duration.toHours() + ":" + duration.toMinutes() + ":" + duration.compareTo(Duration.ofSeconds(90061L)) + ":" + duration.get(java.time.temporal.ChronoUnit.SECONDS) + ":" + Duration.between(LocalTime.of(1, 0), LocalTime.of(2, 30));
            }
        });
    }

    @Test
    void periodEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                Period period = Period.of(1, 13, 32);
                return period.normalized() + ":" + period.toTotalMonths() + ":" + period.multipliedBy(2) + ":" + period.negated() + ":" + Period.ZERO + ":" + Period.ofWeeks(2) + ":" + period.get(java.time.temporal.ChronoUnit.YEARS) + ":" + period.get(java.time.temporal.ChronoUnit.MONTHS) + ":" + period.get(java.time.temporal.ChronoUnit.DAYS);
            }
        });
    }

    @Test
    void zoneEdge() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                ZoneOffset offset = ZoneOffset.ofHoursMinutesSeconds(1, 2, 3);
                return offset + ":" + offset.getTotalSeconds() + ":" + ZoneOffset.MIN + ":" + ZoneOffset.MAX + ":" + ZoneOffset.of("Z").getId() + ":" + ZoneId.of("UTC").normalized().getId() + ":" + ZoneId.ofOffset("UTC", ZoneOffset.ofHours(2)).getId();
            }
        });
    }

    @Test
    void chronoAccess() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                LocalDate date = LocalDate.of(2020, 12, 31);
                LocalTime time = LocalTime.of(23, 59, 58, 123456789);
                return date.getLong(java.time.temporal.ChronoField.DAY_OF_WEEK) + ":" + date.getLong(java.time.temporal.ChronoField.DAY_OF_YEAR) + ":" + date.getLong(java.time.temporal.ChronoField.PROLEPTIC_MONTH) + ":" + time.getLong(java.time.temporal.ChronoField.SECOND_OF_DAY) + ":" + time.getLong(java.time.temporal.ChronoField.MILLI_OF_SECOND) + ":" + time.getLong(java.time.temporal.ChronoField.MICRO_OF_SECOND) + ":" + java.time.temporal.ChronoUnit.DAYS.between(date, LocalDate.of(2021, 1, 1)) + ":" + java.time.temporal.ChronoUnit.HOURS.between(LocalTime.of(0, 0), LocalTime.of(23, 0));
            }
        });
    }
}
