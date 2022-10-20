/**
 * ************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 * *************************************************************
 */

package com.sportradar.sdk.common.classes;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.exceptions.SdkException;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

/**
 * Represents a base class for all SDK loggers.
 */
public abstract class BaseSdkLogger {

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseSdkLogger.class);

    protected final Logger rootExecLogger;
    protected final Logger invalidMessageLogger;
    protected final Logger trafficLogger;
    protected final Logger clientInteractionLogger;
    protected final Logger alertLogger;

    /**
     * Is the default logging facility of the app logback?
     */
    protected final boolean usingLogback;

    protected BaseSdkLogger(final String rootName, final String markerName) throws SdkException {
        LoggerContext lc;
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        if (iLoggerFactory instanceof LoggerContext) {
            lc = (LoggerContext) iLoggerFactory;
        } else {
            lc = new LoggerContext();
        }

        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(rootName);

        if (slf4jLogger instanceof Logger) {
            this.rootExecLogger = (Logger) LoggerFactory.getLogger(rootName);
            usingLogback = true;
        } else {
            this.rootExecLogger = lc.getLogger(rootName);
            usingLogback = false;
        }

        final String prefix;
        if (markerName != null && markerName.isEmpty()) {
            prefix = rootName + ".logger" + ".";
        } else {
            prefix = rootName  + ".logger" + "." + markerName + ".";
        }

        this.invalidMessageLogger = lc.getLogger(prefix + SdkLogAppenderType.INVALID_MSG.getFileName());
        this.invalidMessageLogger.setAdditive(false);
        if (invalidMessageLogger.getLevel() == null) {
            invalidMessageLogger.setLevel(Level.INFO);
        }
        this.trafficLogger = lc.getLogger(prefix + SdkLogAppenderType.TRAFFIC.getFileName());
        this.trafficLogger.setAdditive(false);
        if (trafficLogger.getLevel() == null) {
            trafficLogger.setLevel(Level.INFO);
        }
        this.clientInteractionLogger = lc.getLogger(prefix + SdkLogAppenderType.CLIENT_INTERACTION.getFileName());
        this.clientInteractionLogger.setAdditive(false);
        if (clientInteractionLogger.getLevel() == null) {
            clientInteractionLogger.setLevel(Level.INFO);
        }
        this.alertLogger = lc.getLogger(prefix + SdkLogAppenderType.ALERT.getFileName());
        this.alertLogger.setAdditive(false);
        if (alertLogger.getLevel() == null) {
            alertLogger.setLevel(Level.INFO);
        }
    }

    protected abstract void log(Logger logger, Level level, String message);

    protected abstract void logWithException(org.slf4j.Logger logger, Level level, String message, Exception exception);
}
