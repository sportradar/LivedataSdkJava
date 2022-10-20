/**
 * ************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 * *************************************************************
 */

package com.sportradar.sdk.common.classes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.sift.SiftingAppender;
import ch.qos.logback.core.Appender;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.settings.LoggerSettings;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.timer.TimerListener;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Marker;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File SDK logger implementation.
 */
@SuppressWarnings("JavaDoc")
public class FileSdkLogger extends BaseSdkLogger implements SdkLogger {

    public static final String LOG_COMMON = "Common";
    public static final String LOG_SUFFIX = ".log";
    public static final String ZIP_SUFFIX = ".zip";
    public static final String LOG_PATTERN = "%d [%logger{3}] [%t] [%.5p] %m%n";
    private static final Pattern oddsClear = Pattern.compile("key=\"[^\"]*\"");
    private static final Pattern scoutClear = Pattern.compile("password value=\"[^\"]*\"");
    private static final Duration TIME_SPAN_MIN = Duration.ZERO;
    public static String ROOT_NS = "com.sportradar";
    private final Timer timer;
    private final String logPath;
    private final String commonLogPath;
    private final Duration oldLogCleanupInterval;
    private final Duration oldLogMaxAge;
    private final HashMap<Logger, Marker> markers = new HashMap<>();

    public FileSdkLogger(LoggerSettings loggerSettings, Timer timer) throws SdkException {
        this(loggerSettings, timer, "");
    }

    public FileSdkLogger(LoggerSettings loggerSettings, Timer timer, String markerName) throws SdkException {
        super(ROOT_NS, markerName);

        this.logPath = loggerSettings.getLogPath();
        this.timer = timer;
        this.oldLogCleanupInterval = loggerSettings.getOldLogCleanupInterval();
        this.oldLogMaxAge = loggerSettings.getOldLogMaxAge();

        int maxFileSize = loggerSettings.getMaxFileSize();
        Appender commonAppender = SdkLoggerCfg.createAppender(SdkLogAppenderType.EXECUTION,
                loggerSettings.getLogPath(),
                loggerSettings.getExecutionLogLevel(),
                null,
                true,
                markerName,
                LOG_COMMON,
                maxFileSize);
        if (commonAppender instanceof SiftingAppender) {
            this.rootExecLogger.addAppender(commonAppender);
        }

        File logPathDir = new File(logPath);
        commonLogPath =  logPathDir.getParent() + File.separator + LOG_COMMON + File.separator;

        if (!usingLogback) {
            this.rootExecLogger.warn("Not using logback, please configure logger for 'com.sportradar.sdk' to get execution logs");
        }

        this.invalidMessageLogger.addAppender(SdkLoggerCfg.createAppender(SdkLogAppenderType.INVALID_MSG,
                loggerSettings.getLogPath(),
                loggerSettings.getInvalidMsgLogLevel(),
                null,
                true,
                markerName,
                maxFileSize));
        super.trafficLogger.addAppender(SdkLoggerCfg.createAppender(SdkLogAppenderType.TRAFFIC,
                loggerSettings.getLogPath(),
                loggerSettings.getTrafficLogLevel(),
                null,
                true,
                markerName,
                maxFileSize));
        super.clientInteractionLogger.addAppender(SdkLoggerCfg.createAppender(SdkLogAppenderType.CLIENT_INTERACTION,
                loggerSettings.getLogPath(),
                loggerSettings.getClientInteractionLogLevel(),
                null,
                true,
                markerName,
                maxFileSize));
        this.alertLogger.addAppender(SdkLoggerCfg.createAppender(SdkLogAppenderType.ALERT,
                loggerSettings.getLogPath(),
                loggerSettings.getAlertLogLevel(),
                null,
                true,
                markerName,
                maxFileSize));

        fillMarkers(markerName);
        invalidMessageLogger.setLevel(loggerSettings.getInvalidMsgLogLevel());
        trafficLogger.setLevel(loggerSettings.getTrafficLogLevel());
        clientInteractionLogger.setLevel(loggerSettings.getClientInteractionLogLevel());
        alertLogger.setLevel(loggerSettings.getAlertLogLevel());
        initDeleteOldLogsTask();
    }

    /**
     * Return enum of type {@link SdkLogAppenderType} from a given appender name.
     *
     * @param appenderName specified appender type name
     * @return appender type of type {@link SdkLogAppenderType} from given appender name
     */
    public static SdkLogAppenderType getAppenderType(String appenderName) {
        final String prefix = ROOT_NS;
        if (!appenderName.startsWith(prefix)) {
            return null;
        }
        if (prefix.length() < appenderName.length()) {
            appenderName = appenderName.substring(prefix.length());
        }
        String type = appenderName.replaceAll("[^\\w]+?", "");
        return SdkLogAppenderType.valueOf(type);
    }

    public static Level getLevel(String level) {
        if (level == null || level.isEmpty()) {
            throw new IllegalArgumentException("level");
        }
        Level res = Level.toLevel(level, Level.OFF);
        return res != null ? res : Level.OFF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled(SdkLogAppenderType appenderType) {
        Logger logger = getLogger(appenderType);
        if (logger == null) {
            return false;
        }
        Level level = logger.getLevel();
        if (level == null) {
            level = Level.INFO;
        }
        return Level.DEBUG.isGreaterOrEqual(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled(SdkLogAppenderType appenderType) {
        Logger logger = getLogger(appenderType);
        if (logger == null) {
            return false;
        }
        Level level = logger.getLevel();
        if (level == null) {
            level = Level.INFO;
        }
        return Level.ERROR.isGreaterOrEqual(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled(SdkLogAppenderType appenderType) {
        Logger logger = getLogger(appenderType);
        if (logger == null) {
            return false;
        }
        Level level = logger.getLevel();
        if (level == null) {
            level = Level.INFO;
        }
        return Level.INFO.isGreaterOrEqual(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLevelEnabled(Level level, SdkLogAppenderType appenderType) {
        if (level == Level.DEBUG) {
            return isDebugEnabled(appenderType);
        } else if (level == Level.INFO) {
            return isInfoEnabled(appenderType);
        } else if (level == Level.WARN) {
            return isWarnEnabled(appenderType);
        } else if (level == Level.ERROR) {
            return isErrorEnabled(appenderType);
        } else return level == Level.TRACE && isTraceEnabled(appenderType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled(SdkLogAppenderType appenderType) {
        Logger logger = getLogger(appenderType);
        if (logger == null) {
            return false;
        }
        Level level = logger.getLevel();
        if (level == null) {
            level = Level.INFO;
        }
        return Level.TRACE.isGreaterOrEqual(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled(SdkLogAppenderType appenderType) {
        Logger logger = getLogger(appenderType);
        if (logger == null) {
            return false;
        }
        Level level = logger.getLevel();
        if (level == null) {
            level = Level.INFO;
        }
        return Level.WARN.isGreaterOrEqual(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logAlert(Level level, String message) {
        log(alertLogger, level, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logAlert(Level level, String message, Exception exception) {
        logWithException(alertLogger, level, message, exception);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logClientInteraction(Level level, String message) {
        log(clientInteractionLogger, level, message);
    }

    @Override
    public void logClientInteraction(Level level, String message, Exception exception) {
        logClientInteraction(level, message, TIME_SPAN_MIN, exception);
    }

    @Override
    public void logClientInteraction(Level level, String message, Duration interval, Exception exception) {
        if (level == null) {
            level = clientInteractionLogger.getLevel();
        }
        if (interval == Duration.ZERO || interval == null) {
            logWithException(clientInteractionLogger, level, message, exception);
        } else {
            logWithException(clientInteractionLogger, level,
                    String.format("[Elapsed %s] %s", CommonUtils.durationToString(interval), message),
                    exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void logInvalidMessage(Level level, String message) {
        log(invalidMessageLogger, level, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logTraffic(boolean outgoing, String message) {
        if (message != null) {
            message = message.replaceAll("\r|\n", "");
        }
        if (outgoing) {
            log(trafficLogger, Level.INFO, String.format("-> %s", censor(message)));
        } else {
            log(trafficLogger, Level.INFO, String.format("<- %s", censor(message)));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logTraffic(String message) {
        log(trafficLogger, Level.INFO, String.format("-> %s", censor(message)));
    }


    @Override
    protected void log(Logger logger, Level level, String message) {
        logWithException(logger, level, message, null);
    }

    @Override
    public void logWithException(org.slf4j.Logger logger, Level level, String message, Exception exception) {
        try {
            if (message == null || message.isEmpty()) {
                return;
            }
            if (level == null) {
                level = Level.INFO;
            }
            SdkLogAppenderType appenderType = SdkLogAppenderType.CLIENT_INTERACTION;
            Marker marker = markers.get(logger);
            if (level == Level.DEBUG && isDebugEnabled(appenderType)) {
                if (exception == null) {
                    logger.debug(marker, message);
                } else {
                    logger.debug(marker, message, exception);
                }
            } else if (level == Level.INFO && isInfoEnabled(appenderType)) {
                if (exception == null) {
                    logger.info(marker, message);
                } else {
                    logger.info(marker, message, exception);
                }
            } else if (level == Level.WARN && isWarnEnabled(appenderType)) {
                if (exception == null) {
                    logger.warn(marker, message);
                } else {
                    logger.warn(marker, message, exception);
                }
            } else if (level == Level.ERROR && isErrorEnabled(appenderType)) {
                if (exception == null) {
                    logger.error(marker, message);
                } else {
                    logger.error(marker, message, exception);
                }
            } else if (level == Level.TRACE && isTraceEnabled(appenderType)) {
                if (exception == null) {
                    logger.trace(marker, message);
                } else {
                    logger.trace(marker, message, exception);
                }
            } else {
                // unsupported Log Level should still be logged
                if (isInfoEnabled(appenderType)) {
                    if (exception == null) {
                        logger.info(marker, message);
                    } else {
                        logger.info(marker, message, exception);
                    }
                }
            }
        } catch (Exception e) {
            // ignore all exceptions here
        }
    }

    private static String censor(String message) {
        if (message.contains("type=\"login\"")) {
            Matcher matcher = oddsClear.matcher(message);
            return matcher.replaceAll("key=\"***\"");
        } else if (message.contains("password")) {
            Matcher matcher = scoutClear.matcher(message);
            return matcher.replaceAll("password value=\"***\"");
        } else {
            return message;
        }
    }

    private static void deletePath(String path) {
        try {
            File file = new File(path);
            if (file.isDirectory() && file.listFiles().length > 0) {
                logger.error("Will not delete filepath " + path);
            }
            logger.info("Delete path " + path);
            file.delete();
        } catch (Exception e) {
            logger.warn(String.format("Could not delete path %s", path), e);
        }
    }

    private void fillMarkers(String markerName) {
        for (SdkLogAppenderType appenderType : SdkLogAppenderType.values()) {
            Marker marker = appenderType.getMarker(markerName);
            if (!markers.containsValue(marker)) {
                Logger logger = getLogger(appenderType);
                markers.put(logger, marker);
            }
        }
    }

    private static String[] getFilesOrDirectories(String directoryPath, final boolean isDirectory) {
        File directory = new File(directoryPath);
        File[] matchingFiles = directory.isFile()
                ? new File[]{directory}
                : directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                File file = new File(dir, name);
                if (isDirectory) {
                    return file.isDirectory();
                }
                boolean result = (file.isFile() && (name.endsWith(LOG_SUFFIX) || name.endsWith(ZIP_SUFFIX)
                ));
                if (!result) {
                    logger.info(String.format("Ignoring non-log file named %s%s%s", dir, File.separator, name));
                }
                return result;
            }
        });

        if (matchingFiles != null && matchingFiles.length > 0) {
            ArrayList<String> files = new ArrayList<>(matchingFiles.length);
            for (File file : matchingFiles) {
                files.add(file.getAbsolutePath());
            }
            return files.toArray(new String[files.size()]);
        } else {
            return new String[0];
        }
    }

    private Logger getLogger(SdkLogAppenderType appenderType) {

        switch (appenderType) {
            case ALERT:
                return alertLogger;
            case CLIENT_INTERACTION:
                return clientInteractionLogger;
            case INVALID_MSG:
                return invalidMessageLogger;
            case TRAFFIC:
                return trafficLogger;
            case EXECUTION:
                return rootExecLogger;
            default:
                throw new IndexOutOfBoundsException("appender_type");
        }
    }

    private static DateTime getModifiedDateTime(String filepath) {
        DateTime modifiedDateTime = null;
        try {
            File f = new File(filepath);
            modifiedDateTime = new DateTime(f.lastModified());
        } catch (Exception e) {
            logger.warn(String.format("File modified date exception %s", filepath), e);
        }
        return modifiedDateTime;
    }

    private void initDeleteOldLogsTask() {
        timer.setListener(new TimerListener() {
            @Override
            public void onTick() {
                oldLogsCleanup();
            }
        });
        timer.schedule(Duration.ZERO, oldLogCleanupInterval);
    }

    private void oldCheckDir(String directory, DateTime now, boolean isDirectory) {
        try {
            for (String dir : getFilesOrDirectories(directory, isDirectory)) {
                String[] files = getFilesOrDirectories(dir, false);
                for (String filepath : files) {
                    DateTime modifiedDateTime = getModifiedDateTime(filepath);
                    if (modifiedDateTime == null || now == null || now.compareTo(modifiedDateTime) <= 0) {
                        continue;
                    }
                    Duration duration = new Duration(now.getMillis()).minus(modifiedDateTime.getMillis());
                    if (duration.compareTo(oldLogMaxAge) > 0) {
                        logger.debug(String.format("Will delete outdated log file %s", filepath));
                        deletePath(filepath);
                    }
                }
                if (files.length == 0) {
                    deletePath(dir);
                }
                if(!new File(dir).isFile()) {
                    oldCheckDir(dir, now, isDirectory);
                }
            }
        } catch (Exception e) {
            logger.warn(String.format("oldCheckDir caught exception in %s", directory), e);
        }
    }

    private void oldLogsCleanup() {
        oldCheckDir(commonLogPath, TimeProvider.getCurrent().getCurrentTime(), false);
        oldCheckDir(logPath, TimeProvider.getCurrent().getCurrentTime(), true);
    }
}
