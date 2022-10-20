package com.sportradar.sdk.common.settings;

import org.joda.time.Duration;

/**
 *
 */
public class LiveScoutSettingsBuilder extends LiveFeedSettingsBuilder<LiveScoutSettingsBuilder> {

    private Duration matchExpireCheckInterval;
    private Duration matchExpireMaxAge;
    private Duration maxMatchListInterval;

    public LiveScoutSettings build() {
        return new LiveScoutSettings(
                username,
                password,
                maxMatchListInterval,
                matchExpireCheckInterval,
                matchExpireMaxAge,
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
                getDispatcherThreadCount(),
                getDispatcherQueueSize(),
                loggerSettingsBuilder.build(),
                enabled,
                disconnectOnParseError,
                debugMode);
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


    public LiveScoutSettingsBuilder setMatchExpireCheckInterval(Duration matchExpireCheckInterval) {
        this.matchExpireCheckInterval = matchExpireCheckInterval;
        return this;
    }

    public LiveScoutSettingsBuilder setMatchExpireMaxAge(Duration matchExpireMaxAge) {
        this.matchExpireMaxAge = matchExpireMaxAge;
        return this;
    }

    public LiveScoutSettingsBuilder setMaxMatchListInterval(Duration maxMatchListInterval) {
        this.maxMatchListInterval = maxMatchListInterval;
        return this;
    }

}
