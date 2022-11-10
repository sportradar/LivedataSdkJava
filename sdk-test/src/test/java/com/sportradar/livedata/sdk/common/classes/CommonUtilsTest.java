package com.sportradar.livedata.sdk.common.classes;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommonUtilsTest {

    @DisplayName("timeStringToDateTime() test -> with ok parameters")
    @Test
    void timeStringToDateTime_ok_test() {
        DateTime expected = new DateTime(2013, 9, 26, 23, 11, 52, DateTimeZone.forID("Europe/Oslo"));
        String time = "2013-9-26T23:11:52";
        String zone = "Europe/Oslo";

        DateTime result = CommonUtils.timeStringToDateTime(time, zone);

        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("timeStringToDateTime() test -> with invalid timezone")
    @Test
    void timeStringToDateTime_invalidTimezone_test() {
        String time = "2013-9-26T23:11:52";
        String zone = "Mars/Hellas Planitia";

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.timeStringToDateTime(time, zone)
        );
    }

    @DisplayName("timeStringToDateTime() test -> with invalid createdTime")
    @Test
    void timeStringToDateTime_invalidCreatedTime_test() {
        String time = "2013-9-26T25:11:52";
        String zone = "Europe/Oslo";

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.timeStringToDateTime(time, zone)
        );
    }

    @DisplayName("timeStringToDateTime() test -> with null timezone")
    @Test
    void timeStringToDateTime_nullTimezone_test() {
        String time = "2013-9-26T23:11:52";

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.timeStringToDateTime(time, null)
        );
    }

    @DisplayName("timeStringToDateTime() test -> with null createdTime")
    @Test
    void timeStringToDateTime_nullCreatedTime_test() {
        String zone = "Europe/Oslo";

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.timeStringToDateTime(null, zone)
        );
    }

    @DisplayName("toTimestamp() test -> with null")
    @Test
    void toTimestamp_null_test() {
        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.toTimestamp(null)
        );
    }

    @DisplayName("toTimestamp() test -> with ok parameters (UTC zone)")
    @Test
    void toTimestamp_UTCTimeZone_ok_Test() {
        DateTime dateTime = new DateTime(2000, 1, 1, 1, 1, 1, DateTimeZone.UTC);

        long result = CommonUtils.toTimestamp(dateTime);

        assertThat(result).isEqualTo(946688461000L);
    }

    @DisplayName("toTimestamp() test -> with ok parameters (non-UTC zone)")
    @Test
    void toTimestamp_notUTCTimeZone_ok_Test() {
        DateTime dateTime = new DateTime(2000, 1, 1, 2, 1, 1, DateTimeZone.forID("Europe/Ljubljana"));

        long result = CommonUtils.toTimestamp(dateTime);

        assertThat(result).isEqualTo(946688461000L);
    }

    @DisplayName("durationToString() test -> with null")
    @Test
    void durationToString_null_test() {
        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.durationToString(null)
        );
    }

    @DisplayName("durationToString() test -> with duration zero")
    @Test
    void durationToString_durationZero_test() {
        Duration duration = Duration.ZERO;

        String result = CommonUtils.durationToString(duration);

        assertThat(result).isEqualTo("0");
    }

    @DisplayName("durationToString() test -> with millis")
    @Test
    void durationToString_millis_test() {
        DateTime dateTime = new DateTime(2013, 11, 15, 12, 34, 45, 67);
        Duration duration = new Duration(dateTime.getMillisOfDay());

        String result = CommonUtils.durationToString(duration);

        assertThat(result).isEqualTo("12:34:45.67");
    }

    @DisplayName("durationToString() test -> with days")
    @Test
    void durationToString_days_test() {
        Duration duration = Duration.millis(2 * 24 * 60 * 60 * 1000 - 1);

        String result = CommonUtils.durationToString(duration);

        assertThat(result).isEqualTo("47:59:59.999");
    }

    @DisplayName("randomDuration() test -> with OK parameters")
    @Test
    void randomDuration_ok_test() {
        Duration min = Duration.millis(1667944800000L);
        Duration max = Duration.millis(1668087993343L);

        Duration result = CommonUtils.randomDuration(min, max);

        assertThat(result).isGreaterThanOrEqualTo(min);
        assertThat(result).isLessThanOrEqualTo(max);
    }

    @DisplayName("randomDuration() test -> with min is null parameter")
    @Test
    void randomDuration_minIsNull_test() {
        Duration max = Duration.millis(1668087993343L);

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.randomDuration(null, max)
        );
    }

    @DisplayName("randomDuration() test -> with max is null parameter")
    @Test
    void randomDuration_maxIsNull_test() {
        Duration min = Duration.millis(1667944800000L);

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.randomDuration(min, null)
        );
    }

    @DisplayName("randomDuration() test -> with min > max")
    @Test
    void randomDuration_minGreaterThanMax_test() {
        Duration min = Duration.millis(1668087993343L);
        Duration max = Duration.millis(1667944800000L);

        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.randomDuration(min, max)
        );
    }
}