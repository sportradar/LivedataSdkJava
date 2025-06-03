package com.sportradar.livedata.sdk.common.settings;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyFileException;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings.LiveScoutSettingsBuilder;
import com.sportradar.livedata.sdk.common.settings.LoggerSettings.LoggerSettingsBuilder;
import com.sportradar.livedata.sdk.common.settings.JmxSettings.JmxSettingsBuilder;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"JavaDoc"})
public class PropertyFileSettingsLoader implements SettingsLoader {

    public static final String DEFAULT_SETTINGS_FILE_NAME = "/sdk.properties";
    public static final String LIVE_SCOUT_PREFIX = "sdk.livescout.";
    public static final String JMX_PREFIX = "sdk.com.sportradar.livedata.sdk.loginterceptor.jmx.";
    private final static Logger logger = LoggerFactory.getLogger(PropertyFileSettingsLoader.class);
    private PropertiesParser properties;

    public PropertyFileSettingsLoader(Properties properties) throws MissingPropertyFileException {
        if (properties == null) {
            loadProperties();
        }else{
            loadProperties(properties);
        }
    }

    @Override
    public JmxSettings getJmxSettings() throws SdkException {
        JmxSettingsBuilder jmxSettingsBuilder = JmxSettings.builder();
        return readJmxSettings(jmxSettingsBuilder);
    }

    @Override
    public LiveScoutSettings getLiveScoutSettings() throws MissingPropertyException, InvalidPropertyException {
        LiveScoutSettingsBuilder liveScoutSettingsBuilder = LiveScoutSettings.builder(isTest());
        readLiveScout(liveScoutSettingsBuilder);
        return liveScoutSettingsBuilder.build();
    }

    protected final void loadProperties() throws MissingPropertyFileException {
        final InputStream res = getClass().getResourceAsStream(PropertyFileSettingsLoader.DEFAULT_SETTINGS_FILE_NAME);
        if (res == null) {
            throw new MissingPropertyFileException();
        }
        try {
            final Properties props = new Properties();
            props.load(res);
            properties = new PropertiesParser(props);
            logPassedSdkSettings();
        } catch (IOException e) {
            throw new MissingPropertyFileException();
        }
    }

    protected final void loadProperties(Properties properties) {
        this.properties = new PropertiesParser(properties);
        logPassedSdkSettings();
    }

    protected final void logPassedSdkSettings() {
        StringBuilder builder = new StringBuilder("Listing all passed sdk settings : \n");
        for (Object key : properties.entrySet()) {
            String keyString = key.toString();
            if (keyString.startsWith("sdk")) {
                try {
                    String value = properties.getProperty(keyString);
                    if (keyString.contains("_key") || keyString.contains("password")) {
                        value = "***";
                    }
                    builder.append(String.format("%s : %s\n", key, value));
                } catch (MissingPropertyException ignored) {
                }
            }
        }
        logger.info(builder.toString());
    }

    private boolean isTest() throws MissingPropertyException, InvalidPropertyException {
        Boolean test = properties.getBooleanProperty(LIVE_SCOUT_PREFIX + "test");
        if (test == null) {
            return false;
        }
        return test;
    }

    private void readFeedCommon(LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        Boolean debugMode = properties.getBooleanProperty(LIVE_SCOUT_PREFIX + "debug");
        if (debugMode != null) {
            builder.debugMode(debugMode);
        }
        Integer dispatcherThreadCount = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "dispatcher_thread_count");
        Integer dispatcherQueueSize = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "dispatcher_queue_size");
        if (dispatcherThreadCount != null) {
            builder.dispatcherThreadCount(dispatcherThreadCount);
        }
        if (dispatcherQueueSize != null) {
            builder.dispatcherQueueSize(dispatcherQueueSize);
        }
        readLoggerSettings(builder);
    }

    private JmxSettings readJmxSettings(JmxSettingsBuilder jmxSettingsBuilder) throws MissingPropertyException {
        boolean enabled = Boolean.parseBoolean(properties.getProperty(JMX_PREFIX + "enabled"));
        jmxSettingsBuilder.enabled(enabled);

        if (enabled) {
            String jmxHost = properties.getProperty(JMX_PREFIX + "host");
            if (jmxHost != null && !jmxHost.isEmpty()) {
                jmxSettingsBuilder.jmxHost(jmxHost);
            }

            try {
                int jmxPort = Integer.parseInt(properties.getProperty(JMX_PREFIX + "port"));
                jmxSettingsBuilder.jmxPort(jmxPort);
            } catch (Exception e) {
                //use default port
            }

            String passwordFile = properties.getProperty(JMX_PREFIX + "passwordfile");
            if (passwordFile != null && !passwordFile.isEmpty()) {
                jmxSettingsBuilder.passwordFile(passwordFile);
            }

            String accessFile = properties.getProperty(JMX_PREFIX + "accessfile");
            if (accessFile != null && !accessFile.isEmpty()) {
                jmxSettingsBuilder.accessFile(accessFile);
            }
        }
        return jmxSettingsBuilder.build();
    }

    private void readLiveScout(LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        readFeedCommon(builder);
        Duration clientAliveMsgTimeout = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "client_alive_msg_timeout");
        String hostName = properties.getProperty(LIVE_SCOUT_PREFIX + "host_name");
        List<LimiterData> loginLimiters = properties.getLimitersProperty(LIVE_SCOUT_PREFIX + "login_limiters");
        List<LimiterData> matchRequestLimiters = properties.getLimitersProperty(LIVE_SCOUT_PREFIX + "match_request_limiters");
        List<LimiterData> requestLimiters = properties.getLimitersProperty(LIVE_SCOUT_PREFIX + "request_limiters");
        Integer maxMessageSize = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "max_message_size");
        Duration reconnectWait = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "reconnect_wait");
        Integer maxRequestMatchIds = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "max_request_match_ids");
        Duration matchExpireMaxAge = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "match_expire_max_age");
        Duration maxRequestTimeAllowance = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "max_request_time_allowance");
        Duration initialReconnectWait = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "initial_reconnect_wait");
        Integer port = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "port");
        Integer receiveBufferSize = properties.getIntegerProperty(LIVE_SCOUT_PREFIX + "receive_buffer_size");
        Duration serverAliveMsgTimeout = properties.getDurationProperty(LIVE_SCOUT_PREFIX + "server_alive_msg_timeout");
        Boolean useSSL = properties.getBooleanProperty(LIVE_SCOUT_PREFIX + "use_ssl");
        Boolean disconnectOnParseError = properties.getBooleanProperty(LIVE_SCOUT_PREFIX + "disconnect_on_parse_error");
        if (clientAliveMsgTimeout != null) {
            builder.clientAliveMsgTimeout(clientAliveMsgTimeout);
        }
        if (hostName != null) {
            builder.hostName(hostName);
        }
        if (loginLimiters != null) {
            builder.loginLimiters(loginLimiters);
        }
        if (matchRequestLimiters != null) {
            builder.matchRequestLimiters(matchRequestLimiters);
        }
        if (requestLimiters != null) {
            builder.requestLimiters(requestLimiters);
        }
        if (maxMessageSize != null) {
            builder.totalBufferSize(maxMessageSize);
        }
        if (reconnectWait != null) {
            builder.reconnectWait(reconnectWait);
        }
        if (maxRequestMatchIds != null) {
            builder.maxMatchIdsPerRequest(maxRequestMatchIds);
        }
        if (matchExpireMaxAge != null) {
            builder.matchExpireMaxAge(matchExpireMaxAge);
        }
        if (maxRequestTimeAllowance != null) {
            builder.maxRequestTimeAllowance(maxRequestTimeAllowance);
        }
        if (initialReconnectWait != null) {
            builder.initialReconnectWait(initialReconnectWait);
        }
        if (port != null) {
            builder.port(port);
        }
        if (receiveBufferSize != null) {
            builder.receiveBufferSize(receiveBufferSize);
        }
        if (serverAliveMsgTimeout != null) {
            builder.serverAliveMsgTimeout(serverAliveMsgTimeout);
        }
        if (useSSL != null) {
            builder.useSSL(useSSL);
            if(!useSSL) {
                logger.warn("Unsecure connection not supported for LiveScout. It will probably not connect");
            }
        }
        if (disconnectOnParseError != null) {
            builder.disconnectOnParseError(disconnectOnParseError);
        }
        readAuthSettings(builder);
    }

    private void readAuthSettings(LiveScoutSettingsBuilder liveScoutSettingsBuilder) throws MissingPropertyException, InvalidPropertyException {
        try {
            liveScoutSettingsBuilder.authSettings(new AuthSettings(liveScoutSettingsBuilder.isTest(),
                    properties.getProperty(LIVE_SCOUT_PREFIX + "auth0.domain"),
                    properties.getProperty(LIVE_SCOUT_PREFIX + "auth0.audience"),
                    properties.getProperty(LIVE_SCOUT_PREFIX + "auth0.client_id", true),
                    properties.getProperty(LIVE_SCOUT_PREFIX + "auth0.kid", true),
                    properties.parsePrivateKey(LIVE_SCOUT_PREFIX + "auth0.private_key")));
        } catch (MissingPropertyException e) { // if getting token parameters fails, fallback to legacy login
            logger.warn("SSO required property \"{}\" not found. Using legacy login", e.getProperty());
            liveScoutSettingsBuilder.authSettings(new AuthSettings(
                    properties.getProperty(LIVE_SCOUT_PREFIX + "username", true),
                    properties.getProperty(LIVE_SCOUT_PREFIX + "password", true )));
        }
    }

    private void readLoggerSettings(LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        String internalPrefix = LIVE_SCOUT_PREFIX + "logger.";
        String logPath = properties.getProperty(internalPrefix + "log_path");
        Integer maxFileSize = properties.getIntegerProperty(internalPrefix + "max_file_size");
        Duration oldLogCleanupInterval = properties.getDurationProperty(internalPrefix + "old_log_cleanup_interval");
        Duration oldLogMaxAge = properties.getDurationProperty(internalPrefix + "old_log_max_age");
        Level alertLogLevel = properties.getLevelProperty(internalPrefix + "alert_log_level");
        Level clientInteractionLogLevel = properties.getLevelProperty(internalPrefix + "client_interaction_log_level");
        Level executionLogLevel = properties.getLevelProperty("sdk.common.logger.execution_log_level");
        Level invalidMsgLogLevel = properties.getLevelProperty(internalPrefix + "invalid_msg_log_level");
        Level trafficLogLevel = properties.getLevelProperty(internalPrefix + "traffic_log_level");

        LoggerSettingsBuilder logBuilder = LoggerSettings.builder();
        if (logPath != null) {
            logBuilder.logPath(logPath);
        }
        if (oldLogCleanupInterval != null) {
            logBuilder.oldLogCleanupInterval(oldLogCleanupInterval);
        }
        if (oldLogMaxAge != null) {
            logBuilder.oldLogMaxAge(oldLogMaxAge);
        }
        if (alertLogLevel != null) {
            logBuilder.alertLogLevel(alertLogLevel);
        }
        if (clientInteractionLogLevel != null) {
            logBuilder.clientInteractionLogLevel(clientInteractionLogLevel);
        }
        if (executionLogLevel != null) {
            logBuilder.executionLogLevel(executionLogLevel);
        }
        if (invalidMsgLogLevel != null) {
            logBuilder.invalidMsgLogLevel(invalidMsgLogLevel);
        }
        if (trafficLogLevel != null) {
            logBuilder.trafficLogLevel(trafficLogLevel);
        }
        if (maxFileSize != null) {
            logBuilder.maxFileSize(maxFileSize);
        }
        builder.loggerSettings(logBuilder.build());
    }
}
