/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.test.common;

import com.sportradar.sdk.common.classes.CommonUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CommonUtilsTest {


    @Test
    public void timestampToDateTime_OK_Test() {
        DateTime expected = new DateTime(2013, 9, 26, 23, 11, 52, DateTimeZone.forID("Europe/Oslo"));
        String time = "2013-9-26T23:11:52";
        String zone = "Europe/Oslo";
        DateTime result = CommonUtils.timeStringToDateTime(time, zone);
        assertThat(expected, is(result));
    }


    @Test(expected = IllegalArgumentException.class)
    public void timestampToDateTime_Invalid_TimeZone_Test() {
        String time = "2013-9-26T23:11:52";
        String zone = "Mars/Hellas Planitia";
        assertNull(CommonUtils.timeStringToDateTime(time, zone));
    }

    @Test(expected = IllegalArgumentException.class)
    public void timestampToDateTime_Invalid_CreatedTime_Test() {
        String time = "2013-9-26T25:11:52";
        String zone = "Europe/Oslo";
        assertNull(CommonUtils.timeStringToDateTime(time, zone));
    }

    @Test(expected = IllegalArgumentException.class)
    public void timestampToDateTime_Null_CreatedTime_Test() {
        String zone = "Europe/Oslo";
        CommonUtils.timeStringToDateTime(null, zone);
    }

    @Test(expected = IllegalArgumentException.class)
    public void timestampToDateTime_Null_TimeZone_Test() {
        String time = "2013-9-26T23:11:52";
        CommonUtils.timeStringToDateTime(time, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toTimestamp_Null_DateTime_Test() {
        CommonUtils.toTimestamp(null);
    }

    @Test
    public void toTimestamp_OK_UTC_TimeZone_Test() {
        DateTime dateTime = new DateTime(2000, 1, 1, 1, 1, 1, DateTimeZone.UTC);
        assertThat(CommonUtils.toTimestamp(dateTime), is(946688461000L));
    }

    @Test
    public void toTimestamp_OK_Not_UTC_TimeZone_Test() {
        DateTime dateTime = new DateTime(2000, 1, 1, 2, 1, 1, DateTimeZone.forID("Europe/Ljubljana"));
        assertThat(CommonUtils.toTimestamp(dateTime), is(946688461000L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void durationToString_Null_Test() {
        CommonUtils.durationToString(null);
    }

    @Test
    public void durationToString_DurationIsZero_Test() {
        Duration duration = Duration.ZERO;
        String result = CommonUtils.durationToString(duration);
        assertThat(result, equalTo("0"));
    }

    @Test
    public void durationToString_Millis_Test() {
        DateTime dateTime = new DateTime(2013, 11, 15, 12, 34, 45, 67);
        Duration duration = new Duration(dateTime.getMillisOfDay());
        String result = CommonUtils.durationToString(duration);
        assertThat(result, equalTo("12:34:45.67"));
    }

    @Test
    public void durationToString_Days_Test() {
        Duration duration = Duration.millis(2 * 24 * 60 * 60 * 1000 - 1);
        String result = CommonUtils.durationToString(duration);
        assertThat(result, equalTo("47:59:59.999"));
    }
}
