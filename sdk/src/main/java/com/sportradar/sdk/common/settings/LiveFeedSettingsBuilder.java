package com.sportradar.sdk.common.settings;

import org.joda.time.Duration;

import java.util.List;

/**
 *
 */
public abstract class LiveFeedSettingsBuilder<T extends LiveFeedSettingsBuilder> extends CommonSettingsBuilder<T> {

    protected Duration clientAliveMsgTimeout;
    protected boolean disconnectOnParseError;
    protected String hostName;
    protected Duration initialReconnectWait;
    protected List<LimiterData> loginLimiters;
    protected List<LimiterData> matchRequestLimiters;
    protected int maxMatchIdsPerRequest;
    protected Integer maxMessageSize;
    protected Duration maxRequestTimeAllowance;
    protected String password;
    protected int port;
    protected int receiveBufferSize;
    protected Duration reconnectWait;
    protected List<LimiterData> requestLimiters;
    protected Duration serverAliveMsgTimeout;
    protected boolean test;
    protected boolean useSSL;
    protected String username;

    public Duration getClientAliveMsgTimeout() {
        return clientAliveMsgTimeout;
    }

    public String getHostName() {
        return hostName;
    }

    public Duration getInitialReconnectWait() {
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

    public Integer getMaxMessageSize() {
        return maxMessageSize;
    }

    public Duration getMaxRequestTimeAllowance() {
        return maxRequestTimeAllowance;
    }

    public int getPort() {
        return port;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public Duration getReconnectWait() {
        return reconnectWait;
    }

    public List<LimiterData> getRequestLimiters() {
        return requestLimiters;
    }

    public Duration getServerAliveMsgTimeout() {
        return serverAliveMsgTimeout;
    }

    public boolean isDisconnectOnParseError() {
        return disconnectOnParseError;
    }

    public boolean isTest() {
        return test;
    }

    public boolean isUseSSL() {
        return useSSL;
    }

    @SuppressWarnings("unchecked")
    public T setClientAliveMsgTimeout(Duration clientAliveMsgTimeout) {
        this.clientAliveMsgTimeout = clientAliveMsgTimeout;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setDisconnectOnParseError(boolean disconnectOnParseError) {
        this.disconnectOnParseError = disconnectOnParseError;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setHostName(String hostName) {
        this.hostName = hostName;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setInitialReconnectWait(Duration initialReconnectWait) {
        this.initialReconnectWait = initialReconnectWait;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setLoginLimiters(List<LimiterData> loginLimiters) {
        this.loginLimiters = loginLimiters;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setMatchRequestLimiters(List<LimiterData> matchRequestLimiters) {
        this.matchRequestLimiters = matchRequestLimiters;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setMaxMessageSize(Integer maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setMaxRequestMatchIds(Integer maxMatchIdsPerRequest) {
        this.maxMatchIdsPerRequest = maxMatchIdsPerRequest;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setMaxRequestTimeAllowance(Duration maxRequestTimeAllowance) {
        this.maxRequestTimeAllowance = maxRequestTimeAllowance;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setPassword(String password) {
        this.password = password;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setPort(Integer port) {
        this.port = port;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setReceiveBufferSize(Integer receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setReconnectWait(Duration reconnectWait) {
        this.reconnectWait = reconnectWait;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setRequestLimiters(List<LimiterData> requestLimiters) {
        this.requestLimiters = requestLimiters;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setServerAliveMsgTimeout(Duration serverAliveMsgTimeout) {
        this.serverAliveMsgTimeout = serverAliveMsgTimeout;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setTest(Boolean test) {
        this.test = test;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setUseSSL(Boolean useSSL) {
        this.useSSL = useSSL;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setUsername(String username) {
        this.username = username;
        return (T) this;
    }

}
