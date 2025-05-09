package com.sportradar.livedata.sdk.common.settings;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.Duration;

import java.util.List;

@Getter
@EqualsAndHashCode
@Builder
public class LiveScoutSettings {
    // Common settings
    protected int dispatcherThreadCount;
    protected int dispatcherQueueSize;
    protected LoggerSettings loggerSettings;
    protected boolean debugMode;
    // LiveScout specific settings
    private final Duration clientAliveMsgTimeout;
    private final boolean disconnectOnParseError;
    private final String hostName;
    private final Duration initialReconnectWait;
    private final List<LimiterData> loginLimiters;
    private final List<LimiterData> matchRequestLimiters;
    private final int maxMatchIdsPerRequest;
    private final int totalBufferSize;
    private final Duration maxRequestTimeAllowance;
    private final int port;
    private final int receiveBufferSize;
    private final Duration reconnectWait;
    private final List<LimiterData> requestLimiters;
    private final Duration serverAliveMsgTimeout;
    private final boolean test;
    private final boolean useSSL;
    private final AuthSettings authSettings;
    private final Duration matchExpireMaxAge;

    public static LiveScoutSettingsBuilder builder(boolean isTest) {
        LiveScoutSettingsBuilder builder = new LiveScoutSettingsBuilder().test(isTest);
        if(isTest){ // setting test mode values
            builder.hostName("replay.livedata.betradar.com");
            builder.port(2047);
        } else { // could be set with other defaults, but this way is easier to read
            builder.hostName("livedata.betradar.com");
            builder.port(2017);
        }
        return builder;
    }

    @SuppressWarnings("all") // Suppressing warnings for Lombok generated code
    public static class LiveScoutSettingsBuilder {
        // Builder default values. Lombok will generate setters for all missing LiveScoutSettings fields as well
        private int dispatcherThreadCount = 4;
        private int dispatcherQueueSize = 16384;

        private Duration initialReconnectWait = Duration.standardSeconds(10);
        private Duration reconnectWait = Duration.standardSeconds(20);
        private int receiveBufferSize = 1024 * 1024;
        private int totalBufferSize = 20 * 1024 * 1024;
        private Duration maxRequestTimeAllowance = Duration.standardMinutes(15);
        private int maxMatchIdsPerRequest = 100;
        private boolean disconnectOnParseError = false;

        private boolean useSSL = true;
        private List<LimiterData> requestLimiters = List.of(new LimiterData(450, Duration.standardMinutes(2), "scoutRequestLimit"));
        private Duration matchExpireMaxAge = Duration.standardHours(8);
        private Duration clientAliveMsgTimeout = Duration.standardSeconds(20);
        private Duration serverAliveMsgTimeout = Duration.standardSeconds(8);

        public boolean isTest() {
            return test;
        }
    }
}
