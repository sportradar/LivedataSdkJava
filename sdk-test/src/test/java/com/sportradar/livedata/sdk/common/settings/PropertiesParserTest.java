package com.sportradar.livedata.sdk.common.settings;

import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import org.joda.time.Duration;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertiesParserTest {

    @Test
    void getBooleanProperty_ValidTrueValue_Test() throws MissingPropertyException, InvalidPropertyException {
        Properties properties = new Properties();
        properties.setProperty("testBoolean", "true");
        PropertiesParser parser = new PropertiesParser(properties);

        Boolean result = parser.getBooleanProperty("testBoolean");
        assertThat(result, is(true));
    }

    @Test
    void getBooleanProperty_InvalidValue_Test() {
        Properties properties = new Properties();
        properties.setProperty("testBoolean", "invalid");
        PropertiesParser parser = new PropertiesParser(properties);

        assertThrows(InvalidPropertyException.class, () -> parser.getBooleanProperty("testBoolean"));
    }

    @Test
    void getDurationProperty_ValidValue_Test() throws MissingPropertyException {
        Properties properties = new Properties();
        properties.setProperty("testDuration", "1:02:03:04.500");
        PropertiesParser parser = new PropertiesParser(properties);

        Duration result = parser.getDurationProperty("testDuration");
        assertThat(result.getMillis(), is(93784500L));
    }

    @Test
    void getDurationProperty_MissingMandatory_Test() {
        Properties properties = new Properties();
        PropertiesParser parser = new PropertiesParser(properties);

        assertThrows(MissingPropertyException.class, () -> parser.getDurationProperty("missingKey", true));
    }

    @Test
    void getLimitersProperty_ValidInput_Test() throws MissingPropertyException, InvalidPropertyException {
        Properties properties = new Properties();
        properties.setProperty("limiters", "\"10;00:00:00:01\",\"100;00:00:00:05\"");
        PropertiesParser parser = new PropertiesParser(properties);

        List<LimiterData> result = parser.getLimitersProperty("limiters");
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getLimit(), is(10));
        assertThat(result.get(0).getDuration(), is(Duration.standardSeconds(1)));
    }

    @Test
    void parseCSVProperty_ValidInput_Test() throws MissingPropertyException {
        Properties properties = new Properties();
        properties.setProperty("csvKey", "value1,value2,value3");
        PropertiesParser parser = new PropertiesParser(properties);

        Set<String> result = parser.parseCSVProperty("csvKey");
        assertThat(result, containsInAnyOrder("value1", "value2", "value3"));
    }

    @Test
    void parsePrivateKey_ValidKey_Test() throws MissingPropertyException, InvalidPropertyException {
        String validKey = PropertyConstants.VALID_RSA_PRIVATE_KEY;
        Properties properties = new Properties();
        properties.setProperty("privateKey", validKey);
        PropertiesParser parser = new PropertiesParser(properties);

        RSAPrivateKey privateKey = parser.parsePrivateKey("privateKey");
        assertThat(privateKey, is(notNullValue()));
        assertThat(privateKey.getAlgorithm(), is("RSA"));
    }

    @Test
    void parsePrivateKey_MissingKey_Test() {
        Properties properties = new Properties();
        PropertiesParser parser = new PropertiesParser(properties);

        assertThrows(MissingPropertyException.class, () -> parser.parsePrivateKey("missingKey"));
    }

    @Test
    void parsePrivateKey_InvalidKey_Test() {
        Properties properties = new Properties();
        properties.setProperty("privateKey", "invalid-key");
        PropertiesParser parser = new PropertiesParser(properties);

        assertThrows(InvalidPropertyException.class, () -> parser.parsePrivateKey("privateKey"));
    }
}
