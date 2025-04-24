package com.sportradar.livedata.sdk.common.settings;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.exceptions.InvalidPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyException;
import com.sportradar.livedata.sdk.common.exceptions.MissingPropertyFileException;
import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings.LiveScoutSettingsBuilder;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"unchecked", "JavaDoc"})
public class PropertyFileSettingsLoader implements SettingsLoader {

    public static final String DEFAULT_SETTINGS_FILE_NAME = "/sdk.properties";
    private final static Logger logger = LoggerFactory.getLogger(PropertyFileSettingsLoader.class);
    private PropertiesParser properties;

    public PropertyFileSettingsLoader(Properties properties) throws MissingPropertyFileException {
        if (properties == null) {
            loadProperties(DEFAULT_SETTINGS_FILE_NAME);
        }else{
            loadProperties(properties);
        }
    }

    @Override
    public JmxSettings getJmxSettings() throws SdkException {
        JmxSettingsBuilder jmxSettingsBuilder = DefaultSettingsBuilderHelper.getJmxSettingsBuilder();
        return readJmxSettings("sdk.com.sportradar.livedata.sdk.loginterceptor.jmx.", jmxSettingsBuilder);
    }

    @Override
    public LiveScoutSettings getLiveScoutSettings() throws MissingPropertyException, InvalidPropertyException {
        final String PREFIX = "sdk.livescout.";
        LiveScoutSettingsBuilder liveScoutSettingsBuilder;


        if (!useSSL(PREFIX, true)) {
            logger.warn("Unsecure connection not supported for LiveScout. It will probably not connect", PREFIX + ".use_ssl", "false");
        }

        if (isTest(PREFIX)) {
            liveScoutSettingsBuilder = DefaultSettingsBuilderHelper.getLiveScoutTest();
        } else {
            liveScoutSettingsBuilder = DefaultSettingsBuilderHelper.getLiveScout();
        }

        return commonLiveScoutSettingsBuild(PREFIX, liveScoutSettingsBuilder);
    }

    protected final void loadProperties(String file) throws MissingPropertyFileException {
        final InputStream res = getClass().getResourceAsStream(file);
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

    private LiveScoutSettings commonLiveScoutSettingsBuild(String prefix, LiveScoutSettingsBuilder liveScoutSettingsBuilder) throws MissingPropertyException, InvalidPropertyException {
        readLiveScout(prefix, liveScoutSettingsBuilder);
        return liveScoutSettingsBuilder.build();
    }

    private boolean isTest(String prefix) throws MissingPropertyException, InvalidPropertyException {
        Boolean test = properties.getBooleanProperty(prefix + "test");
        if (test == null) {
            return false;
        }

        return test;
    }

    private void readFeedCommon(String prefix, LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        Boolean debugMode = properties.getBooleanProperty(prefix + "debug");
        if (debugMode != null) {
            builder.debugMode(debugMode);
        }
        Integer dispatcherThreadCount = properties.getIntegerProperty(prefix + "dispatcher_thread_count");
        Integer dispatcherQueueSize = properties.getIntegerProperty(prefix + "dispatcher_queue_size");
        if (dispatcherThreadCount != null) {
            builder.dispatcherThreadCount(dispatcherThreadCount);
        }
        if (dispatcherQueueSize != null) {
            builder.dispatcherQueueSize(dispatcherQueueSize);
        }
        readLoggerSettings(prefix, builder);
    }

    private JmxSettings readJmxSettings(String prefix, JmxSettingsBuilder jmxSettingsBuilder) throws MissingPropertyException, InvalidPropertyException {
        boolean enabled = Boolean.parseBoolean(properties.getProperty(prefix + "enabled"));
        jmxSettingsBuilder.setEnabled(enabled);

        if (jmxSettingsBuilder.isEnabled()) {
            String jmxHost = properties.getProperty(prefix + "host");
            if (jmxHost != null && jmxHost.length() > 0) {
                jmxSettingsBuilder.setJmxHost(jmxHost);
            }

            try {
                int jmxPort = Integer.parseInt(properties.getProperty(prefix + "port"));
                jmxSettingsBuilder.setJmxPort(jmxPort);
            } catch (Exception e) {
                //use default port
            }

            String passwordFile = properties.getProperty(prefix + "passwordfile");
            if (passwordFile != null && passwordFile.length() > 0) {
                jmxSettingsBuilder.setPasswordFile(passwordFile);
            }

            String accessFile = properties.getProperty(prefix + "accessfile");
            if (accessFile != null && accessFile.length() > 0) {
                jmxSettingsBuilder.setAccessFile(accessFile);
            }
        }
        return jmxSettingsBuilder.build();
    }

    private void readLiveFeed(String prefix, LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        readFeedCommon(prefix, builder);
        Duration clientAliveMsgTimeout = properties.getDurationProperty(prefix + "client_alive_msg_timeout");
        String hostName = properties.getProperty(prefix + "host_name");
        List<LimiterData> loginLimiters = properties.getLimitersProperty(prefix + "login_limiters");
        List<LimiterData> matchRequestLimiters = properties.getLimitersProperty(prefix + "match_request_limiters");
        List<LimiterData> requestLimiters = properties.getLimitersProperty(prefix + "request_limiters");
        Integer maxMessageSize = properties.getIntegerProperty(prefix + "max_message_size");
        Duration reconnectWait = properties.getDurationProperty(prefix + "reconnect_wait");
        Integer maxRequestMatchIds = properties.getIntegerProperty(prefix + "max_request_match_ids");
        Duration maxRequestTimeAllowance = properties.getDurationProperty(prefix + "max_request_time_allowance");
        Duration initialReconnectWait = properties.getDurationProperty(prefix + "initial_reconnect_wait");
        Integer port = properties.getIntegerProperty(prefix + "port");
        Integer receiveBufferSize = properties.getIntegerProperty(prefix + "receive_buffer_size");
        Duration serverAliveMsgTimeout = properties.getDurationProperty(prefix + "server_alive_msg_timeout");
        Boolean useSSL = properties.getBooleanProperty(prefix + "use_ssl");
        Boolean disconnectOnParseError = properties.getBooleanProperty(prefix + "disconnect_on_parse_error");
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
        }
        if (disconnectOnParseError != null) {
            builder.disconnectOnParseError(disconnectOnParseError);
        }
    }

    private void readLiveScout(String prefix, LiveScoutSettingsBuilder liveScoutSettingsBuilder) throws MissingPropertyException, InvalidPropertyException {
        readLiveFeed(prefix, liveScoutSettingsBuilder);
        String username = properties.getProperty(prefix + "username", true);
        String password = properties.getProperty(prefix + "password", true);
        Duration matchExpireCheckInterval = properties.getDurationProperty(prefix + "match_expire_check_interval");
        Duration matchExpireMaxAge = properties.getDurationProperty(prefix + "match_expire_max_age");
        Duration maxMatchListInterval = properties.getDurationProperty(prefix + "max_match_list_interval");
        AuthSettings authSettings = new AuthSettings(username, password, null, null, null, null, null);
        liveScoutSettingsBuilder.authSettings(authSettings);

        // by default set use_ssl to true
        Boolean useSSL = properties.getBooleanProperty(prefix + "use_ssl");
        if (useSSL == null) {
            liveScoutSettingsBuilder.useSSL(true);
        }
        if (matchExpireMaxAge != null) {
            liveScoutSettingsBuilder.matchExpireMaxAge(matchExpireMaxAge);
        }
    }

    private void readLoggerSettings(final String prefix, LiveScoutSettingsBuilder builder) throws MissingPropertyException, InvalidPropertyException {
        String internalPrefix = prefix + "logger.";
        String logPath = properties.getProperty(internalPrefix + "log_path");
        Integer maxFileSize = properties.getIntegerProperty(internalPrefix + "max_file_size");
        Duration oldLogCleanupInterval = properties.getDurationProperty(internalPrefix + "old_log_cleanup_interval");
        Duration oldLogMaxAge = properties.getDurationProperty(internalPrefix + "old_log_max_age");
        Level alertLogLevel = properties.getLevelProperty(internalPrefix + "alert_log_level");
        Level clientInteractionLogLevel = properties.getLevelProperty(internalPrefix + "client_interaction_log_level");
        Level executionLogLevel = properties.getLevelProperty("sdk.common.logger.execution_log_level");
        Level invalidMsgLogLevel = properties.getLevelProperty(internalPrefix + "invalid_msg_log_level");
        Level trafficLogLevel = properties.getLevelProperty(internalPrefix + "traffic_log_level");

        LoggerSettingsBuilder logBuilder = DefaultSettingsBuilderHelper.getLoggerSettings();
        if (logPath != null) {
            logBuilder.setLogPath(logPath);
        }
        if (oldLogCleanupInterval != null) {
            logBuilder.setOldLogCleanupInterval(oldLogCleanupInterval);
        }
        if (oldLogMaxAge != null) {
            logBuilder.setOldLogMaxAge(oldLogMaxAge);
        }
        if (alertLogLevel != null) {
            logBuilder.setAlertLogLevel(alertLogLevel);
        }
        if (clientInteractionLogLevel != null) {
            logBuilder.setClientInteractionLogLevel(clientInteractionLogLevel);
        }
        if (executionLogLevel != null) {
            logBuilder.setExecutionLogLevel(executionLogLevel);
        }
        if (invalidMsgLogLevel != null) {
            logBuilder.setInvalidMsgLogLevel(invalidMsgLogLevel);
        }
        if (trafficLogLevel != null) {
            logBuilder.setTrafficLogLevel(trafficLogLevel);
        }
        if (maxFileSize != null) {
            logBuilder.setMaxFileSize(maxFileSize);
        }
        builder.loggerSettings(logBuilder.build());
    }

    private boolean useSSL(String prefix, boolean def) throws MissingPropertyException, InvalidPropertyException {
        Boolean test = properties.getBooleanProperty(prefix + "use_ssl");
        if (test == null) {
            return def;
        }

        return test;
    }
}
