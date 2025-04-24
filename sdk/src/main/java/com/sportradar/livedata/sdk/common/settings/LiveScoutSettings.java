package com.sportradar.livedata.sdk.common.settings;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joda.time.Duration;

import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
public class LiveScoutSettings extends CommonSettings {

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

    @Builder
    protected LiveScoutSettings(AuthSettings authSettings,
                               String hostName,
                               int port,
                               boolean useSSL,
                               boolean test,
                               int receiveBufferSize,
                               int totalBufferSize,
                               Duration initialReconnectWait,
                               Duration reconnectWait,
                               int maxMatchIdsPerRequest,
                               List<LimiterData> loginLimiters,
                               List<LimiterData> requestLimiters,
                               List<LimiterData> matchRequestLimiters,
                               Duration maxRequestTimeAllowance,
                               Duration serverAliveMsgTimeout,
                               Duration clientAliveMsgTimeout,
                               LoggerSettings loggerSettings,
                               int dispatcherThreadCount,
                               int dispatcherQueueSize,
                               boolean disconnectOnParseError,
                               boolean debugMode,
                               Duration matchExpireMaxAge) {
        super(dispatcherThreadCount, dispatcherQueueSize, loggerSettings, debugMode);
        this.authSettings = authSettings;
        this.clientAliveMsgTimeout = clientAliveMsgTimeout;
        this.hostName = hostName;
        this.loginLimiters = loginLimiters;
        this.requestLimiters = requestLimiters;
        this.matchRequestLimiters = matchRequestLimiters;
        this.port = port;
        this.serverAliveMsgTimeout = serverAliveMsgTimeout;
        this.reconnectWait = reconnectWait;
        this.maxMatchIdsPerRequest = maxMatchIdsPerRequest;
        this.maxRequestTimeAllowance = maxRequestTimeAllowance;
        this.initialReconnectWait = initialReconnectWait;
        this.receiveBufferSize = receiveBufferSize;
        this.test = test;
        this.totalBufferSize = totalBufferSize;
        this.useSSL = useSSL;
        this.disconnectOnParseError = disconnectOnParseError;
        this.matchExpireMaxAge = matchExpireMaxAge;
    }
}
