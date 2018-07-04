package com.github.corneil.cloud_foundry.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.data.util.Pair;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
public class TimeRange {
    private TimeRange() {
    }

    private static final PeriodFormatterBuilder builder = new PeriodFormatterBuilder()
        .printZeroNever()
        .appendYears().appendSuffix("y")
        .appendMonths().appendSuffix("M")
        .appendDays().appendSuffix("d")
        .appendHours().appendSuffix("h")
        .appendMinutes().appendSuffix("m")
        .appendSeconds().appendSuffix("s");

    public static PeriodFormatter formatter() {
        return builder.toFormatter();
    }

    public static Pair<Date, Date> createStartAndEndDates(Date now, final String input) {
        if (input.contains(",")) {
            String[] parts = StringUtils.split(input, ",");
            if (parts.length != 2) {
                throw new IllegalArgumentException(String.format("Input %s may only have one , and should separate to date periods", input));
            }
            Date startDate = createDateBefore(now, parts[0]);
            Date endDate = createDateBefore(now, parts[1]);
            return Pair.of(startDate, endDate);
        }
        return Pair.of(createDateBefore(now, input), now);
    }

    public static Date createDateBefore(final Date anchor, final String period) {
        Assert.notNull(anchor, "anchor is required");
        Assert.notNull(period, "period is required");
        Period before = Period.parse(period.trim(), builder.toFormatter());
        log.info("Period:{}", period);
        return LocalDateTime.fromDateFields(anchor).minus(before).toDate();
    }
}
