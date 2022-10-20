package com.sportradar.sdk.common.settings;

import ch.qos.logback.classic.Level;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.List;

public class DefaultSettingsBuilderHelper {

    public static LiveScoutSettingsBuilder getLiveScout() {
        LiveScoutSettingsBuilder builder = new LiveScoutSettingsBuilder();
        fillLiveScoutDefaults(builder);
        builder.setHostName("livedata.betradar.com");
        builder.setPort(2017);
        builder.setTest(false);
        return builder;
    }

    private static void fillLiveScoutDefaults(LiveScoutSettingsBuilder builder) {
        fillFeedSettingsDefaults(builder);

        builder.setUseSSL(true);

        builder.setRequestLimiters(buildLimiters(new LimiterData(450, Duration.standardMinutes(2), "scoutRequestLimit")));

        builder.setMaxMatchListInterval(Duration.standardHours(300));

        builder.setMatchExpireCheckInterval(Duration.standardMinutes(5));
        builder.setMatchExpireMaxAge(Duration.standardHours(8));

        builder.setClientAliveMsgTimeout(Duration.standardSeconds(20));
        builder.setServerAliveMsgTimeout(Duration.standardSeconds(8));

        builder.getLoggerSettingsBuilder().setLogPath("logs/livescout/");
    }

    private static void fillFeedSettingsDefaults(LiveFeedSettingsBuilder builder) {
        fillCommonDefaults(builder);
        builder.setInitialReconnectWait(Duration.standardSeconds(10));
        builder.setReconnectWait(Duration.standardSeconds(20));

        builder.setReceiveBufferSize(1024 * 1024);
        builder.setMaxMessageSize(20 * 1024 * 1024);

        builder.setMaxRequestTimeAllowance(Duration.standardMinutes(15));
        builder.setMaxRequestMatchIds(100);
        builder.setDisconnectOnParseError(false);
    }

    private static List<LimiterData> buildLimiters(LimiterData... limiters) {
        return Arrays.asList(limiters);
    }

    private static void fillCommonDefaults(CommonSettingsBuilder builder) {
        builder.setEnabled(false);
        builder.setLoggerSettings(getLoggerSettings());
        builder.setDispatcherThreadCount(4);
        builder.setDispatcherQueueSize(16384);
    }

    public static LoggerSettingsBuilder getLoggerSettings() {
        LoggerSettingsBuilder builder = new LoggerSettingsBuilder();
        builder.setOldLogCleanupInterval(Duration.standardHours(1));
        builder.setOldLogMaxAge(Duration.standardDays(7));
        builder.setAlertLogLevel(Level.WARN);
        builder.setClientInteractionLogLevel(Level.INFO);
        builder.setExecutionLogLevel(Level.INFO);
        builder.setInvalidMsgLogLevel(Level.INFO);
        builder.setTrafficLogLevel(Level.INFO);
        builder.setMaxFileSize(10485760);
        return builder;
    }

    public static LiveScoutSettingsBuilder getLiveScoutTest() {
        LiveScoutSettingsBuilder builder = new LiveScoutSettingsBuilder();
        fillLiveScoutDefaults(builder);
        builder.setHostName("replay.livedata.betradar.com");    //Old "scouttest.betradar.com"
        builder.setPort(2047); // before 2047
        builder.setTest(true);
        return builder;
    }

    public static JmxSettingsBuilder getJmxSettingsBuilder() {
        JmxSettingsBuilder builder = new JmxSettingsBuilder();
        fillJmxDefaults(builder);
        return builder;
    }

    private static void fillJmxDefaults(JmxSettingsBuilder builder) {
        builder.setEnabled(false);
        builder.setJmxHost("localhost");
        builder.setJmxPort(13370);
    }
}
