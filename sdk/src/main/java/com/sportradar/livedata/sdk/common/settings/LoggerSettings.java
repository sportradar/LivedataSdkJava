package com.sportradar.livedata.sdk.common.settings;


import ch.qos.logback.classic.Level;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.Duration;

@Getter
@EqualsAndHashCode
@Builder
public class LoggerSettings {

    private final Level alertLogLevel;
    private final Level clientInteractionLogLevel;
    private final Level executionLogLevel;
    private final Level invalidMsgLogLevel;
    private final String logPath;
    private final int maxFileSize;
    private final Duration oldLogCleanupInterval;
    private final Duration oldLogMaxAge;
    private final Level trafficLogLevel;

    @SuppressWarnings("all") // Suppressing warnings for Lombok generated code
    public static class LoggerSettingsBuilder {
        private String logPath = "logs/livescout/";
        private Duration oldLogCleanupInterval = Duration.standardHours(1);
        private Duration oldLogMaxAge = Duration.standardDays(7);
        private Level alertLogLevel = Level.WARN;
        private Level clientInteractionLogLevel = Level.INFO;
        private Level executionLogLevel = Level.INFO;
        private Level invalidMsgLogLevel = Level.INFO;
        private Level trafficLogLevel = Level.INFO;
        private int maxFileSize = 10485760;
    }
}
