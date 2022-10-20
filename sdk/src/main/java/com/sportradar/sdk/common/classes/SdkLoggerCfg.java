/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.sift.SiftingAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.sift.AppenderFactory;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.Duration;
import ch.qos.logback.core.util.FileSize;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

public class SdkLoggerCfg {

    public static final String DISCRIMINATOR_KEY = "loggerid";

    private final static ConcurrentHashMap<String, Appender> appenderMap = new ConcurrentHashMap<>();


    private static <T extends Appender> void addAppender(String appenderName, T appender) {
        appenderMap.put(appenderName, appender);
    }

    private static <T extends Appender> Appender getAppender(String appenderName) {
        return appenderMap.get(appenderName);
    }

    public static class MatchingFilter extends TurboFilter {

        private String marker;
        private Marker markerToAccept;
        private boolean isWhitelist;

        public MatchingFilter() {
            isWhitelist = false;
        }

        @Override
        public FilterReply decide(Marker marker, Logger logger, Level level,
                                  String format, Object[] params, Throwable t) {
            if (!isStarted()) {
                return FilterReply.NEUTRAL;
            }

            if (isWhitelist) {
                if (markerToAccept.equals(marker)) {
                    return FilterReply.ACCEPT;
                }
                return FilterReply.DENY;
            } else {
                if (!markerToAccept.equals(marker)) {
                    return FilterReply.ACCEPT;
                }
                return FilterReply.DENY;
            }
        }

        public String getMarker() {
            return this.marker;
        }

        public void setMarker(String marker) {
            this.marker = marker;
        }

        public void setIsWhitelist(boolean isWhitelist) {
            this.isWhitelist = isWhitelist;
        }

        @Override
        public void start() {
            if (marker != null && marker.trim().length() > 0) {
                super.start();
                markerToAccept = MarkerFactory.getMarker(marker);
            }
        }
    }

    private static RollingFileAppender<ILoggingEvent> createRollingFileAppender(
            final String appenderName,
            final String logPath,
            final String fileName,
            Level threshold,
            String logPattern,
            LoggerContext loggerContext,
            String logSubdirectory,
            int maxFileSize
    ) {
        final String logDir;
        if ((logSubdirectory == null || logSubdirectory.isEmpty())) {
            logDir = logPath + File.separator + fileName + File.separator;
        } else {
            File logPathDir = new File(logPath);
            logDir = logPathDir.getParent() + File.separator + logSubdirectory + File.separator;
        }

        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(loggerContext);
        patternLayoutEncoder.setPattern(logPattern);
        patternLayoutEncoder.setCharset(Charset.forName("UTF-8"));
        patternLayoutEncoder.start();

        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setAppend(true);
        appender.setPrudent(false);
        appender.setContext(loggerContext);
        appender.setName(appenderName);
        appender.setFile(logDir + fileName + FileSdkLogger.LOG_SUFFIX);
        appender.setEncoder(patternLayoutEncoder);

        ThresholdFilter thresholdFilter = new ThresholdFilter();
        thresholdFilter.setContext(loggerContext);
        thresholdFilter.setLevel(threshold.levelStr);
        thresholdFilter.start();

        SizeAndTimeBasedFNATP<ILoggingEvent> sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP<>();
        sizeAndTimeBasedFNATP.setMaxFileSize(new FileSize(new Long(maxFileSize)));
        TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<>();
        timeBasedRollingPolicy.setContext(loggerContext);
        timeBasedRollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(sizeAndTimeBasedFNATP);
        timeBasedRollingPolicy.setFileNamePattern(logDir + "%d{yyyy-MM-dd}.%i." + fileName + FileSdkLogger.ZIP_SUFFIX);
        timeBasedRollingPolicy.setParent(appender);
        appender.setRollingPolicy(timeBasedRollingPolicy);
        timeBasedRollingPolicy.start();
        appender.start();
        addAppender(appenderName, appender);
        return appender;
    }

    public static Appender<ILoggingEvent> createAppender(
            final SdkLogAppenderType appenderType,
            final String logPath,
            final Level threshold,
            final String logPattern,
            boolean whitelistFilter,
            String markerName,
            int maxFileSize) {
        return createAppender(appenderType, logPath, threshold, logPattern, whitelistFilter, markerName, null, maxFileSize);
    }


    public static Appender<ILoggingEvent> createAppender(
            final SdkLogAppenderType appenderType,
            final String logPath,
            final Level threshold,
            final String logPattern,
            boolean whitelistFilter,
            String markerName,
            final String logSubdirectory,
            int maxFileSize) {
        if (appenderType == null) {
            throw new IllegalArgumentException("appenderType");
        }
        if (logPath == null || logPath.isEmpty()) {
            throw new IllegalArgumentException("logPath");
        }
        final String appenderName = SdkLogAppenderType.getAppenderName(appenderType, markerName);
        final String fileName = appenderType.getFileName();
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName");
        }
        final Level actualThreshold;
        if (threshold == null) {
            actualThreshold = Level.DEBUG;
        } else {
            actualThreshold = threshold;
        }
        final String actualLogPattern;
        if (logPattern == null || logPattern.isEmpty()) {
            actualLogPattern = FileSdkLogger.LOG_PATTERN;
        } else {
            actualLogPattern = logPattern;
        }

        // return existing appender if exists
        Appender existingAppender = getAppender(appenderName);
        if (existingAppender instanceof SiftingAppender) {
            return existingAppender;
        } else if (existingAppender instanceof Appender) {
            return existingAppender;
        }

        final LoggerContext loggerContext = new LoggerContext();

        MatchingFilter matchingFilter = new MatchingFilter();
        matchingFilter.setContext(loggerContext);
        if (whitelistFilter) {
            // whitelist by logger name (deny everything else)
            matchingFilter.setIsWhitelist(true);
            matchingFilter.setMarker(appenderName);
        } else {
            // blacklist all specific SDK logger appenders
            matchingFilter.setMarker(appenderName);
        }
        matchingFilter.start();
        loggerContext.addTurboFilter(matchingFilter);

        final RollingFileAppender<ILoggingEvent> appender = createRollingFileAppender(
                appenderName,
                logPath,
                fileName,
                actualThreshold,
                actualLogPattern,
                loggerContext,
                logSubdirectory,
                maxFileSize);
        SiftingAppender siftingAppender = new SiftingAppender();
        final String actualAppenderName = getActualAppenderName(appenderName, fileName);

        SdkContextDiscriminator sdkContextDiscriminator = new SdkContextDiscriminator();
        sdkContextDiscriminator.setKey(DISCRIMINATOR_KEY);
        sdkContextDiscriminator.setDefaultValue(appenderName);
        sdkContextDiscriminator.setContext(loggerContext);
        sdkContextDiscriminator.start();
        siftingAppender.setDiscriminator(sdkContextDiscriminator);
        siftingAppender.setContext(loggerContext);
        siftingAppender.setName(actualAppenderName);
        siftingAppender.setTimeout(Duration.buildByMinutes(1.0));

        AppenderFactory<ILoggingEvent> appenderFactory = new AppenderFactory<ILoggingEvent>() {
            @Override
            public Appender<ILoggingEvent> buildAppender(Context context, String discriminatingValue) throws JoranException {
                // get rolling file appender if exists; otherwise return execution rolling file appender
                Appender existingAppender = getAppender(discriminatingValue);
                if (existingAppender instanceof Appender) {
                    return existingAppender;
                }
                return appender;
            }
        };
        siftingAppender.setAppenderFactory(appenderFactory);
        siftingAppender.start();
        addAppender(actualAppenderName, siftingAppender);
        return siftingAppender;
    }

    private static String getActualAppenderName(String appenderName, String fileName) {
        return appenderName + "-" + fileName;
    }
}
