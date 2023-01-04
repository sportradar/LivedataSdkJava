package com.sportradar.livedata.sdk.common.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.enums.SdkLogAppenderType;
import org.joda.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullSdkLoggerTest {

    NullSdkLogger logger;

    @BeforeEach
    void setUp() {
        logger = new NullSdkLogger();
    }

    @DisplayName("logInvalidMessage(level, message) test")
    @Test
    void logInvalidMessage_test() {
        assertDoesNotThrow(() -> logger.logInvalidMessage(Level.ALL, "message"));
    }

    @DisplayName("logTraffic(outgoing, message) test")
    @Test
    void logTraffic_twoParams_test() {
        assertDoesNotThrow(() -> logger.logTraffic(true, "message"));
    }

    @DisplayName("logTraffic(message) test")
    @Test
    void logTraffic_oneParam_test() {
        assertDoesNotThrow(() -> logger.logTraffic("message"));
    }

    @DisplayName("logClientInteraction(level, message) test")
    @Test
    void logClientInteraction_twoParams_test() {
        assertDoesNotThrow(() -> logger.logClientInteraction(Level.ALL, "message"));
    }

    @DisplayName("logClientInteraction(level, message, exception) test")
    @Test
    void logClientInteraction_threeParams_test() {
        assertDoesNotThrow(() -> logger.logClientInteraction(Level.ALL, "message", new Exception()));
    }

    @DisplayName("logClientInteraction(level, message, interval, exception) test")
    @Test
    void logClientInteraction_fourParams_test() {
        assertDoesNotThrow(() -> logger.logClientInteraction(Level.ALL, "message", Duration.ZERO, new Exception()));
    }

    @DisplayName("logAlert(level, message) test")
    @Test
    void logAlert_twoParams_test() {
        assertDoesNotThrow(() -> logger.logAlert(Level.ALL, "message"));
    }

    @DisplayName("logAlert(level, message, exception) test")
    @Test
    void logAlert_threeParams_test() {
        assertDoesNotThrow(() -> logger.logAlert(Level.ALL, "message", new Exception()));
    }

    @DisplayName("isLevelEnabled() test")
    @Test
    void isLevelEnabled_test() {
        assertFalse(logger.isLevelEnabled(Level.ALL, SdkLogAppenderType.ALERT));
    }

    @DisplayName("isDebugEnabled() test")
    @Test
    void isDebugEnabled_test() {
        assertFalse(logger.isDebugEnabled(SdkLogAppenderType.ALERT));
    }

    @DisplayName("isInfoEnabled() test")
    @Test
    void isInfoEnabled_test() {
        assertFalse(logger.isInfoEnabled(SdkLogAppenderType.ALERT));
    }

    @DisplayName("isWarnEnabled() test")
    @Test
    void isWarnEnabled_test() {
        assertFalse(logger.isWarnEnabled(SdkLogAppenderType.ALERT));
    }

    @DisplayName("isErrorEnabled() test")
    @Test
    void isErrorEnabled_test() {
        assertFalse(logger.isErrorEnabled(SdkLogAppenderType.ALERT));
    }

    @DisplayName("isTraceEnabled() test")
    @Test
    void isTraceEnabled_test() {
        assertFalse(logger.isTraceEnabled(SdkLogAppenderType.ALERT));
    }
}