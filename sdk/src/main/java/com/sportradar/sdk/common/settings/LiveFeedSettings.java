package com.sportradar.sdk.common.settings;

import org.joda.time.Duration;

import java.util.List;

public abstract class LiveFeedSettings extends CommonSettings {

    private Duration clientAliveMsgTimeout;
    private boolean disconnectOnParseError;
    private String hostName;
    private Duration initialReconnectWait;
    private List<LimiterData> loginLimiters;
    private List<LimiterData> matchRequestLimiters;
    private int maxMatchIdsPerRequest;
    private int maxMessageSize;
    private Duration maxRequestTimeAllowance;
    private String password;
    private int port;
    private int receiveBufferSize;
    private Duration reconnectWait;
    private List<LimiterData> requestLimiters;
    private Duration serverMessageTimeout;
    private boolean test = false;
    private boolean useSSL = true;
    private String username;

    protected LiveFeedSettings(String username,
                               String password,
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
                               boolean enabled,
                               boolean disconnectOnParseError,
                               boolean debugMode) {
        super(enabled, dispatcherThreadCount, dispatcherQueueSize, loggerSettings, debugMode);
        this.username = username;
        this.password = password;
        this.clientAliveMsgTimeout = clientAliveMsgTimeout;
        this.hostName = hostName;
        this.loginLimiters = loginLimiters;
        this.requestLimiters = requestLimiters;
        this.matchRequestLimiters = matchRequestLimiters;
        this.port = port;
        this.serverMessageTimeout = serverAliveMsgTimeout;
        this.reconnectWait = reconnectWait;
        this.maxMatchIdsPerRequest = maxMatchIdsPerRequest;
        this.maxRequestTimeAllowance = maxRequestTimeAllowance;
        this.initialReconnectWait = initialReconnectWait;
        this.receiveBufferSize = receiveBufferSize;
        this.test = test;
        this.maxMessageSize = totalBufferSize;
        this.useSSL = useSSL;
        this.disconnectOnParseError = disconnectOnParseError;

    }

    public Duration getClientAliveMsgTimeout() {
        return clientAliveMsgTimeout;
    }

    public String getHostName() {
        return hostName;
    }

    public Duration getInitialReconnectDelay() {
        return initialReconnectWait;
    }

    public List<LimiterData> getLoginLimiters() {
        return loginLimiters;
    }

    public List<LimiterData> getMatchRequestLimiters() {
        return matchRequestLimiters;
    }

    public int getMaxMatchIdsPerRequest() {
        return maxMatchIdsPerRequest;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public Duration getMaxRequestTimeAllowance() {
        return maxRequestTimeAllowance;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public Duration getReconnectDelay() {
        return reconnectWait;
    }

    public List<LimiterData> getRequestLimiters() {
        return requestLimiters;
    }

    public Duration getServerMessageTimeout() {
        return serverMessageTimeout;
    }

    public String getUsername() {

        return username;
    }

    public boolean disconnectOnParseError() {
        return disconnectOnParseError;
    }

    public boolean isTest() {
        return test;
    }

    public boolean isUseSSL() {
        return useSSL;
    }
}
