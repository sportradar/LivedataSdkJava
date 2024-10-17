package com.sportradar.livedata.sdk.common.classes;

import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilsTest {

    @Test
    void testFromTimestampWithDefaultZone() {
        long unixTimestamp = 1609459200000L; // 2021-01-01T00:00:00Z
        DateTime dateTime = CommonUtils.fromTimestamp(unixTimestamp);
        assertNotNull(dateTime);
        assertEquals(DateTimeZone.UTC, dateTime.getZone());
        assertEquals(new DateTime(unixTimestamp, DateTimeZone.UTC), dateTime);
    }

    @Test
    void testFromTimestampWithSpecifiedZone() {
        long unixTimestamp = 1609459200000L; // 2021-01-01T00:00:00Z
        DateTimeZone zone = DateTimeZone.forID("America/New_York");
        DateTime dateTime = CommonUtils.fromTimestamp(unixTimestamp, zone);
        assertNotNull(dateTime);
        assertEquals(zone, dateTime.getZone());
        assertEquals(new DateTime(unixTimestamp, zone), dateTime);
    }

    @Test
    void testParseBooleanPropertyValidTrue() throws InvalidEntityException {
        assertTrue(CommonUtils.parseBooleanProperty("true", "someProperty"));
    }

    @Test
    void testParseBooleanPropertyValidFalse() throws InvalidEntityException {
        assertFalse(CommonUtils.parseBooleanProperty("false", "someProperty"));
    }

    @Test
    void testParseBooleanPropertyInvalidValue() {
        InvalidEntityException exception = assertThrows(InvalidEntityException.class, () ->
                CommonUtils.parseBooleanProperty("notABoolean", "someProperty"));
        assertTrue(exception.getMessage().contains("Cannot parse boolean from: notABoolean"));
    }

    @Test
    void testParseBooleanPropertyNullValue() throws InvalidEntityException {
        assertNull(CommonUtils.parseBooleanProperty(null, "someProperty"));
    }

    @Test
    void testTimeStringToDateTimeValid() {
        String isoString = "2021-01-01T00:00:00Z";
        DateTime dateTime = CommonUtils.timeStringToDateTime(isoString, "UTC");
        assertNotNull(dateTime);
        assertEquals(new DateTime(isoString, DateTimeZone.UTC), dateTime);
    }

    @Test
    void testTimeStringToDateTimeInvalidZone() {
        String isoString = "2021-01-01T00:00:00Z";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.timeStringToDateTime(isoString, "InvalidZone"));
        assertTrue(exception.getMessage().contains("Unknown time zone!"));
    }

    @Test
    void testToTimestamp() {
        DateTime dateTime = new DateTime(1609459200000L, DateTimeZone.UTC);
        long unixTimestamp = CommonUtils.toTimestamp(dateTime);
        assertEquals(1609459200000L, unixTimestamp);
    }

    @Test
    void testRandomDurationWithinBounds() {
        Duration min = Duration.standardSeconds(5);
        Duration max = Duration.standardSeconds(10);
        Duration result = CommonUtils.randomDuration(min, max);
        assertTrue(result.getMillis() >= min.getMillis() && result.getMillis() <= max.getMillis());
    }

    @Test
    void testRandomDurationMinGreaterThanMax() {
        Duration min = Duration.standardSeconds(10);
        Duration max = Duration.standardSeconds(5);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.randomDuration(min, max));
        assertTrue(exception.getMessage().contains("minDuration must be less than maxDuration"));
    }

    @Test
    void testDurationToString() {
        Duration duration = Duration.standardDays(1)
                .plus(Duration.standardHours(2))
                .plus(Duration.standardMinutes(30))
                .plus(Duration.standardSeconds(15))
                .plus(Duration.millis(500));
        String result = CommonUtils.durationToString(duration);
        // don't know why there is no days separation. But it was working so and tested before me.
        assertEquals("26:30:15.500", result);
    }
}