/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;

/**
 * Null SDK logger implementation.
 */
public class NullSdkLogger implements SdkLogger {

    /**
     * Corrupt input feed data will be dumped into log files dedicated to corrupt input data (could be garbage and not even text, exceeding max buffer size).
     * Invalid input messages will be dumped into log files dedicated to invalid input messages (text, but not necessarily XML).
     *
     * @param level   Log level
     * @param message Invalid message
     */
    @Override
    public void logInvalidMessage(Level level, String message) {

    }

    /**
     * Depending on configuration SDK will be able to dump all feed XML traffic into dedicated traffic logs.
     * This data is already logged at the feed server level but here the client will have possibility
     * to configure traffic log filters based on the type of message, level of detail, match id, etc.
     *
     * @param outgoing when true it specifies a message the SDK has sent; false means
     *                 that a message SDK received
     * @param message  Message that was either read or written to the communication channel
     */
    @Override
    public void logTraffic(boolean outgoing, String message) {

    }

    /**
     * Dump given message to traffic log.
     *
     * @param message Message that was either read or written to the channel
     */
    @Override
    public void logTraffic(String message) {

    }

    /**
     * All interaction with the client software on the SDK interface level will be logged in a separate client communication log.
     * This includes all method invocations on the SDK interface including parameter values, request parameter validation errors
     * and other errors returned by the SDK, unhandled client exceptions, errors reported by the client handlers, timings of each call, etc.
     *
     * @param level   Log level
     * @param message String message
     */
    @Override
    public void logClientInteraction(Level level, String message) {

    }

    /**
     * All interaction with the client software on the SDK interface level will be logged in a separate client communication log.
     * This includes all method invocations on the SDK interface including parameter values, request parameter validation errors
     * and other errors returned by the SDK, unhandled client exceptions, errors reported by the client handlers, timings of each call, etc.
     *
     * @param level     Log level
     * @param message   String message
     * @param exception Optional exception
     */
    @Override
    public void logClientInteraction(Level level, String message, Exception exception) {

    }

    /**
     * All interaction with the client software on the SDK interface level will be logged in a separate client communication log.
     * This includes all method invocations on the SDK interface including parameter values, request parameter validation errors
     * and other errors returned by the SDK, unhandled client exceptions, errors reported by the client handlers, timings of each call, etc.
     *
     * @param level     Log level
     * @param message   String message
     * @param interval  Elapsed time while processing client request or client handler invocation (optional)
     * @param exception Optional exception
     */
    @Override
    public void logClientInteraction(Level level, String message, Duration interval, Exception exception) {

    }

    /**
     * SDK will generate and signal alerts in case of any special events.
     * This could happen when network connection is dropped, rate-limiting thresholds are exceeded,
     * when going over queue low/high watermarks, when feed error recovery is in progress,
     * if persistent state store gets out-of-sync, when event dispatching is put on pause,
     * when SDK library is reloaded, current status of login sequence, etc. Alerts will be dumped into dedicated alert logs.
     * Special alerts (configurable) could be logged to other log appenders, like SMTP appender, NT Event Log appender or JmxLogger appender.
     *
     * @param level   Log level
     * @param message Alert message
     */
    @Override
    public void logAlert(Level level, String message) {

    }

    /**
     * SDK will generate and signal alerts in case of any special events.
     * This could happen when network connection is dropped, rate-limiting thresholds are exceeded,
     * when going over queue low/high watermarks, when feed error recovery is in progress,
     * if persistent state store gets out-of-sync, when event dispatching is put on pause,
     * when SDK library is reloaded, current status of login sequence, etc. Alerts will be dumped into dedicated alert logs.
     * Special alerts (configurable) could be logged to other log appenders, like SMTP appender, NT Event Log appender or JmxLogger appender.
     *
     * @param level     - Log level
     * @param message   Alert message
     * @param exception Optional exception
     */
    @Override
    public void logAlert(Level level, String message, Exception exception) {

    }




    /**
     * Return true when logger with given appender type have current log level equal to  given level,
     * otherwise false.
     *
     * @param level              specified level
     * @param sdkLogAppenderType specified appender type
     * @return {@code true} when logger with appender type have
     * current level equal to level, otherwise {@code false}
     */
    @Override
    public boolean isLevelEnabled(Level level, SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * Return true when logger with given appender type have current log level equal to debug,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to debug, otherwise false
     */
    @Override
    public boolean isDebugEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * Return true when logger with given appender type have current log level equal to info,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to info, otherwise false
     */
    @Override
    public boolean isInfoEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * Return true when logger with given appender type have current log level equal to warn,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to warn, otherwise false
     */
    @Override
    public boolean isWarnEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * Return true when logger with given appender type have current log level equal to error,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to error, otherwise false
     */
    @Override
    public boolean isErrorEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * Return true when logger with given appender type have current log level equal to trace,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to trace, otherwise false
     */
    @Override
    public boolean isTraceEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }
}
