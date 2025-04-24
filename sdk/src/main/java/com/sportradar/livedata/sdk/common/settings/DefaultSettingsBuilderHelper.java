package com.sportradar.livedata.sdk.common.settings;

import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings.LiveScoutSettingsBuilder;
import ch.qos.logback.classic.Level;
import org.joda.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DefaultSettingsBuilderHelper {

    public static LiveScoutSettingsBuilder getLiveScout() {
        LiveScoutSettingsBuilder builder =  LiveScoutSettings.builder();
        fillLiveScoutDefaults(builder);
        builder.hostName("livedata.betradar.com");
        builder.port(2017);
        builder.test(false);
        return builder;
    }

    private static void fillLiveScoutDefaults(LiveScoutSettingsBuilder builder) {
        fillFeedSettingsDefaults(builder);

        builder.useSSL(true);

        builder.requestLimiters(buildLimiters(new LimiterData(450, Duration.standardMinutes(2), "scoutRequestLimit")));

        builder.matchExpireMaxAge(Duration.standardHours(8));

        builder.clientAliveMsgTimeout(Duration.standardSeconds(20));
        builder.serverAliveMsgTimeout(Duration.standardSeconds(8));
    }

    private static void fillFeedSettingsDefaults(LiveScoutSettingsBuilder builder) {
        fillCommonDefaults(builder);
        builder.initialReconnectWait(Duration.standardSeconds(10));
        builder.reconnectWait(Duration.standardSeconds(20));

        builder.receiveBufferSize(1024 * 1024);
        builder.totalBufferSize(20 * 1024 * 1024);

        builder.maxRequestTimeAllowance(Duration.standardMinutes(15));
        builder.maxMatchIdsPerRequest(100);
        builder.disconnectOnParseError(false);
    }

    private static List<LimiterData> buildLimiters(LimiterData... limiters) {
        return Arrays.asList(limiters);
    }

    private static void fillCommonDefaults(LiveScoutSettingsBuilder builder) {
        builder.dispatcherThreadCount(4);
        builder.dispatcherQueueSize(16384);
    }

    public static LoggerSettingsBuilder getLoggerSettings() {
        LoggerSettingsBuilder builder = new LoggerSettingsBuilder();
        builder.setLogPath("logs/livescout/");
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
        builder.hostName("replay.livedata.betradar.com");    //Old "scouttest.betradar.com"
        builder.port(2047); // before 2047
        builder.test(true);
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
