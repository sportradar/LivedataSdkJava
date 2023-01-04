package com.sportradar.livedata.sdk.common.classes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SdkContextDiscriminatorTest {

    SdkContextDiscriminator sdkContextDiscriminator;
    Field key;
    Field defaultValue;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        sdkContextDiscriminator = new SdkContextDiscriminator();

        key = sdkContextDiscriminator.getClass().getDeclaredField("key");
        key.setAccessible(true);
        key.set(sdkContextDiscriminator, "Initial key");

        defaultValue = sdkContextDiscriminator.getClass().getDeclaredField("defaultValue");
        defaultValue.setAccessible(true);
        defaultValue.set(sdkContextDiscriminator, "Initial default value");
    }

    @DisplayName("getDiscriminatingValue() test")
    @Test
    void getDiscriminatingValue_test() {
        assertThat(sdkContextDiscriminator.getDiscriminatingValue(new ILoggingEventMock()))
                .isEqualTo("Initial default value");
    }

    @DisplayName("getKey() test")
    @Test
    void getKey_test() {
        assertThat(sdkContextDiscriminator.getKey()).isEqualTo("Initial key");
    }

    @DisplayName("setKey() test")
    @Test
    void setKey_test() throws IllegalAccessException {
        sdkContextDiscriminator.setKey("New key");

        assertThat(key.get(sdkContextDiscriminator)).isEqualTo("New key");
    }

    @DisplayName("getDefaultValue() test")
    @Test
    void getDefaultValue_test() {
        assertThat(sdkContextDiscriminator.getDefaultValue()).isEqualTo("Initial default value");
    }

    @DisplayName("setDefaultValue() test -> if initial value is null")
    @Test
    void setDefaultValue_initialValueNull_test() throws IllegalAccessException {
        defaultValue.set(sdkContextDiscriminator, null);

        sdkContextDiscriminator.setDefaultValue("New default value");

        assertThat(defaultValue.get(sdkContextDiscriminator)).isEqualTo("New default value");
    }

    @DisplayName("setDefaultValue() test -> if initial value is empty")
    @Test
    void setDefaultValue_initialValueEmpty_test() throws IllegalAccessException {
        defaultValue.set(sdkContextDiscriminator, "");

        sdkContextDiscriminator.setDefaultValue("New default value");

        assertThat(defaultValue.get(sdkContextDiscriminator)).isEqualTo("New default value");
    }

    @DisplayName("setDefaultValue() test -> if initial value is normal")
    @Test
    void setDefaultValue_initialValueNormal_test() throws IllegalAccessException {
        sdkContextDiscriminator.setDefaultValue("New default value");

        assertThat(defaultValue.get(sdkContextDiscriminator)).isEqualTo("Initial default value");
    }


}

class ILoggingEventMock implements ILoggingEvent {

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public Level getLevel() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object[] getArgumentArray() {
        return new Object[0];
    }

    @Override
    public String getFormattedMessage() {
        return null;
    }

    @Override
    public String getLoggerName() {
        return null;
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return null;
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return null;
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return new StackTraceElement[0];
    }

    @Override
    public boolean hasCallerData() {
        return false;
    }

    @Override
    public List<Marker> getMarkerList() {
        return null;
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return null;
    }

    @Override
    public Map<String, String> getMdc() {
        return null;
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public int getNanoseconds() {
        return 0;
    }

    @Override
    public long getSequenceNumber() {
        return 0;
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return null;
    }

    @Override
    public void prepareForDeferredProcessing() {

    }
}