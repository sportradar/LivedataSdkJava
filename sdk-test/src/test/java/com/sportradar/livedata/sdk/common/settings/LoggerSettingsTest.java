package com.sportradar.livedata.sdk.common.settings;

import ch.qos.logback.classic.Level;
import org.joda.time.Duration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggerSettingsTest {

    @Test
    void testDefaultValues() {
        LoggerSettings settings = LoggerSettings.builder().build();

        assertEquals("logs/livescout/", settings.getLogPath());
        assertEquals(Duration.standardHours(1), settings.getOldLogCleanupInterval());
        assertEquals(Duration.standardDays(7), settings.getOldLogMaxAge());
        assertEquals(Level.WARN, settings.getAlertLogLevel());
        assertEquals(Level.INFO, settings.getClientInteractionLogLevel());
        assertEquals(Level.INFO, settings.getExecutionLogLevel());
        assertEquals(Level.INFO, settings.getInvalidMsgLogLevel());
        assertEquals(Level.INFO, settings.getTrafficLogLevel());
        assertEquals(10485760, settings.getMaxFileSize());
    }

    @Test
    void testCustomValues() {
        LoggerSettings settings = LoggerSettings.builder()
                .logPath("custom/logs/")
                .oldLogCleanupInterval(Duration.standardMinutes(30))
                .oldLogMaxAge(Duration.standardDays(14))
                .alertLogLevel(Level.ERROR)
                .clientInteractionLogLevel(Level.DEBUG)
                .executionLogLevel(Level.TRACE)
                .invalidMsgLogLevel(Level.WARN)
                .trafficLogLevel(Level.ERROR)
                .maxFileSize(20971520)
                .build();

        assertEquals("custom/logs/", settings.getLogPath());
        assertEquals(Duration.standardMinutes(30), settings.getOldLogCleanupInterval());
        assertEquals(Duration.standardDays(14), settings.getOldLogMaxAge());
        assertEquals(Level.ERROR, settings.getAlertLogLevel());
        assertEquals(Level.DEBUG, settings.getClientInteractionLogLevel());
        assertEquals(Level.TRACE, settings.getExecutionLogLevel());
        assertEquals(Level.WARN, settings.getInvalidMsgLogLevel());
        assertEquals(Level.ERROR, settings.getTrafficLogLevel());
        assertEquals(20971520, settings.getMaxFileSize());
    }
}
