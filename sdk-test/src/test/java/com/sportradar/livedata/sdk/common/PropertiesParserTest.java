/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.common;

import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import com.sportradar.livedata.sdk.common.settings.LimiterData;
import com.sportradar.livedata.sdk.common.settings.PropertiesParser;
import org.joda.time.Duration;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
public class PropertiesParserTest {

    @Test
    void getDurationProperty_Full_String_Test() throws MissingPropertyException {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        propertiesParser.setProperty("duration", "11:23:13:05.337");
        Duration duration = propertiesParser.getDurationProperty("duration");
        assertThat(duration.getMillis(), is(1033985337L));
    }


    @Test
    void getDurationProperty_No_Miliseconds_Test() throws MissingPropertyException {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        propertiesParser.setProperty("duration", "11:23:13:05");
        Duration duration = propertiesParser.getDurationProperty("duration");
        assertThat(duration.getMillis(), is(1033985000L));
    }


    @Test
    void getLimitersProperty_Multiple_Test() throws MissingPropertyException, InvalidPropertyException {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        propertiesParser.setProperty("limiters", "\"10;00:00:00:01\",\"100;00:00:00:05\",\"1000;00:00:01:00\"");
        List<LimiterData> result = propertiesParser.getLimitersProperty("limiters");
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(3));
        assertThat(result.get(0), is(equalTo(new LimiterData(10, Duration.standardSeconds(1), "name"))));
        assertThat(result.get(1), is(equalTo(new LimiterData(100, Duration.standardSeconds(5), "name"))));
        assertThat(result.get(2), is(equalTo(new LimiterData(1000, Duration.standardMinutes(1), "name"))));
    }

    @Test
    void getLimitersProperty_Single_Test() throws MissingPropertyException, InvalidPropertyException {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        propertiesParser.setProperty("limiters", "\"10;00:00:00:01\"");
        List<LimiterData> result = propertiesParser.getLimitersProperty("limiters");
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(equalTo(new LimiterData(10, Duration.standardSeconds(1), "name"))));
    }

    @Test
    void parseCSVProperty_Null_Test() throws Exception {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        String key = null;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            propertiesParser.parseCSVProperty(key);
        });
    }

    @Test
    void parseCSVProperty_Empty_Test() throws Exception {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        String value = "";
        propertiesParser.setProperty("languages", value);
        Set<String> result = propertiesParser.parseCSVProperty("languages");
        assertThat(result, not(equalTo(null)));
        assertThat(result.size(), not(equalTo(0)));
    }

    @Test
    void parseCSVProperty_OK_Test() throws Exception {
        PropertiesParser propertiesParser = new PropertiesParser(new Properties());
        String value = "en,de,ru,de,en";
        propertiesParser.setProperty("languages", value);
        Set<String> result = propertiesParser.parseCSVProperty("languages");
        assertThat(result, not(equalTo(null)));
        assertThat(result.size(), equalTo(3));
        assertThat(result.contains("en"), equalTo(true));
        assertThat(result.contains("de"), equalTo(true));
        assertThat(result.contains("ru"), equalTo(true));
    }
}
