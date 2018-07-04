package com.github.corneil.cloud_foundry.demo.util;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeRangeTest {
    @Test
    public void test10minutes() {
        PeriodFormatter formatter = TimeRange.formatter();
        System.out.println("10 minutes = " + formatter.print(Period.minutes(10)));

        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMin = now.minusMinutes(10);
        // when
        Pair<Date, Date> range = TimeRange.createStartAndEndDates(now.toDate(), "10m");
        // then
        assertEquals(tenMin, LocalDateTime.fromDateFields(range.getFirst()));
        assertEquals(now, LocalDateTime.fromDateFields(range.getSecond()));
    }

    @Test
    public void test10Hours() {
        PeriodFormatter formatter = TimeRange.formatter();
        System.out.println("10 hours = " + formatter.print(Period.hours(10)));

        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime hours = now.minusHours(10);
        // when
        Pair<Date, Date> range = TimeRange.createStartAndEndDates(now.toDate(), "10h");
        // then
        assertEquals(hours, LocalDateTime.fromDateFields(range.getFirst()));
        assertEquals(now, LocalDateTime.fromDateFields(range.getSecond()));
    }

    @Test
    public void test3010Days() {
        PeriodFormatter formatter = TimeRange.formatter();
        System.out.println("3010 days = " + formatter.print(Period.days(3010)));

        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime days = now.minusDays(3010);
        // when
        Pair<Date, Date> range = TimeRange.createStartAndEndDates(now.toDate(), "3010d");
        // then
        assertEquals(days, LocalDateTime.fromDateFields(range.getFirst()));
        assertEquals(now, LocalDateTime.fromDateFields(range.getSecond()));
    }

    @Test
    public void testMix() {
        PeriodFormatter formatter = TimeRange.formatter();
        System.out.println("2 years 3 months, 4 days 5 hours 6 minutes 7 seconds = " +
            formatter.print(Period.years(2).plusMonths(3).plusDays(4).plusHours(5).plusMinutes(6).plusSeconds(7)));

        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusYears(2).minusMonths(3).minusDays(4).minusHours(5).minusMinutes(6).minusSeconds(7);
        // when
        Pair<Date, Date> range = TimeRange.createStartAndEndDates(now.toDate(), "2y3M4d5h6m7s");
        // then
        assertEquals(past, LocalDateTime.fromDateFields(range.getFirst()));
        assertEquals(now, LocalDateTime.fromDateFields(range.getSecond()));
    }

    @Test
    public void testSplit() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMonths(1);
        LocalDateTime end = now.minusMinutes(2);
        // when
        Pair<Date, Date> range = TimeRange.createStartAndEndDates(now.toDate(), "1M,2m");
        assertEquals(start, LocalDateTime.fromDateFields(range.getFirst()));
        assertEquals(end, LocalDateTime.fromDateFields(range.getSecond()));
    }
}
