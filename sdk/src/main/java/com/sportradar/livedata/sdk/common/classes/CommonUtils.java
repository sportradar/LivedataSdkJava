/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common.classes;

import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import org.apache.commons.lang3.BooleanUtils;
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
     * During parsing properties it is better to get error on wrong value than null.
     *
     * @param value value
     * @param propertyName property name to show in error message
     * @return parsed boolean value
     * @throws InvalidEntityException if could not parse non-null value
     */
    public static Boolean parseBooleanProperty(String value, String propertyName) throws InvalidEntityException {
        if(value == null) {
            return null;
        }
        Boolean result = BooleanUtils.toBooleanObject(value);
        if(result == null){
            throw new InvalidEntityException(propertyName, "Cannot parse boolean from: " + value);
        }
        return result;
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
        /*
        From docs:
        public Period toPeriod() Converts this duration to a Period instance using the standard period type and the ISO chronology.
        Only precise fields in the period type will be used.
        Thus, only the hour, minute, second and millisecond fields on the period will be used.
        The year, month, week and day fields will not be populated.

        In other words: Period instance can't be calculated properly without start date (or end date),
        because some days can be 24h or 23h (due to DST)
         */
        Period period = new Period(duration.getMillis());
        return period.toString(builder.toFormatter());
    }
}
