/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.PeriodFormatterBuilder;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class CommonUtils {

    /**
     * Convert from UNIX timestamp to DateTime with UTC DateTimeZone
     *
     * @param time Time in UNIX timestamp
     * @return DateTime value of UNIX timestamp with UTC DateTimeZone
     */
    public static DateTime fromTimestamp(long time) {
        return fromTimestamp(time, DateTimeZone.UTC);
    }

    /**
     * Convert from UNIX timestamp to DateTime with specified DateTimeZone
     *
     * @param time Time in UNIX timestamp
     * @param zone Convert to which DateTimeZone
     * @return DateTime value of UNIX timestamp with specified DateTimeZone
     */
    public static DateTime fromTimestamp(long time, DateTimeZone zone) {
        return new DateTime(time, zone);
    }

    /**
     * Converts int to boolean
     *
     * @param value Input int
     * @return False if value 0, else true
     */
    public static boolean intToBoolean(int value) {
        return value != 0;
    }

    /**
     * Converts Integer to Boolean
     *
     * @param value Input Integer
     * @return False if value 0, else true
     */
    public static Boolean integerToBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return value != 0;
    }

    /**
     * Converts input string and timezone string to DateTime instance
     * @param createdTime time in ISO format string
     * @param zoneString timezone string
     * @return datetime instance
     */
    public static DateTime timeStringToDateTime(String createdTime, String zoneString) {
        Nulls.checkNotNull(createdTime);
        Nulls.checkNotNull(zoneString);
        checkArgument(DateTimeZone.getAvailableIDs().contains(zoneString), "Unknown time zone!");
        DateTimeZone zone = DateTimeZone.forID(zoneString);
        return ISODateTimeFormat.dateTimeParser().withZone(zone).parseDateTime(createdTime);
    }

    /**
     * Transforms DateTime timestamp to UNIX timestamp
     *
     * @param time Time in DateTime format
     * @return Time as UNIX timestamp
     */
    public static long toTimestamp(final DateTime time) {
        Nulls.checkNotNull(time);
        return time.getMillis();
    }

    /**
     * Return a random duration between the given bounds.
     *
     * @param minDuration - minimum duration
     * @param maxDuration - maximum duration
     * @return a random duration in interval [minDuration, maxDuration]
     */
    public static Duration randomDuration(
            final Duration minDuration,
            final Duration maxDuration) {
        Nulls.checkNotNull(minDuration, "minDuration cannot be a null reference");
        Nulls.checkNotNull(maxDuration, "maxDuration cannot be a null reference");

        long min = minDuration.getMillis();
        long max = maxDuration.getMillis();

        checkArgument(min <= max, "minDuration must be less than maxDuration");

        long spread = max - min;

        return Duration.millis(min + Math.round(Math.random() * spread));
    }

    /**
     * Convert given Duration to String.
     *
     * @param duration - input Duration
     * @return String
     */
    public static String durationToString(Duration duration) {
        Nulls.checkNotNull(duration);
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        builder.appendDays()
                .appendSeparator(":")
                .appendHours()
                .appendSeparator(":")
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .appendSeparator(".")
                .appendMillis();
        Period period = new Period(duration.getMillis());
        return period.toString(builder.toFormatter());
    }
}
