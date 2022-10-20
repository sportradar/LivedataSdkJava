package com.sportradar.sdk.common.settings;

import org.joda.time.Duration;

import java.util.List;

/**
 * Represents a part of the application's configuration file related to the live-scout
 */
public class LiveScoutSettings extends LiveFeedSettings {

    private Duration matchExpireCheckInterval;
    private Duration matchExpireMaxAge;
    private Duration maxMatchListInterval;
    public LiveScoutSettings(String username,
                             String password,
                             Duration maxMatchListInterval,
                             Duration matchExpireCheckInterval,
                             Duration matchExpireMaxAge,
                             String hostName,
                             int port,
                             boolean useSSL,
                             boolean test,
                             int receiveBufferSize,
                             int maxMessageSize,
                             Duration initialReconnectWait,
                             Duration reconnectWait,
                             int maxMatchIdsPerRequest,
                             List<LimiterData> loginLimiters,
                             List<LimiterData> requestLimiters,
                             List<LimiterData> matchRequestLimiters,
                             Duration maxRequestTimeAllowance,
                             Duration serverAliveMsgTimeout,
                             Duration clientAliveMsgTimeout,
                             int dispatcherThreadCount,
                             int dispatcherQueueSize,
                             LoggerSettings loggerSettings,
                             boolean enabled,
                             boolean disconnectOnParseError,
                             boolean debugMode) {
        super(username,
                password,
                hostName,
                port,
                useSSL,
                test,
                receiveBufferSize,
                maxMessageSize,
                initialReconnectWait,
                reconnectWait,
                maxMatchIdsPerRequest,
                loginLimiters,
                requestLimiters,
                matchRequestLimiters,
                maxRequestTimeAllowance,
                serverAliveMsgTimeout,
                clientAliveMsgTimeout,
                loggerSettings,
                dispatcherThreadCount,
                dispatcherQueueSize,
                enabled,
                disconnectOnParseError,
                debugMode);

        this.maxMatchListInterval = maxMatchListInterval;
        this.matchExpireCheckInterval = matchExpireCheckInterval;
        this.matchExpireMaxAge = matchExpireMaxAge;
    }

    public Duration getMatchExpireCheckInterval() {
        return matchExpireCheckInterval;
    }

    public Duration getMatchExpireMaxAge() {
        return matchExpireMaxAge;
    }

    public Duration getMaxMatchListInterval() {
        return maxMatchListInterval;
    }
}
