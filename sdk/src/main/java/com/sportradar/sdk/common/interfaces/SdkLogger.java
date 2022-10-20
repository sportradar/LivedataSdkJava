/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.interfaces;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import org.joda.time.Duration;

/**
 * Represents a logger used to log events
 */
public interface SdkLogger {

    /**
     * Return true when logger with given appender type have current log level equal to debug,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to debug, otherwise false
     */
    boolean isDebugEnabled(SdkLogAppenderType sdkLogAppenderType);

    /**
     * Return true when logger with given appender type have current log level equal to error,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to error, otherwise false
     */
    boolean isErrorEnabled(SdkLogAppenderType sdkLogAppenderType);

    /**
     * Return true when logger with given appender type have current log level equal to info,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to info, otherwise false
     */
    boolean isInfoEnabled(SdkLogAppenderType sdkLogAppenderType);

    /**
     * Return true when logger with given appender type have current log level equal to  given level,
     * otherwise false.
     *
     * @param level              specified level
     * @param sdkLogAppenderType specified appender type
     * @return {@code true} when logger with appender type have
     * current level equal to level, otherwise {@code false}
     */
    boolean isLevelEnabled(Level level, SdkLogAppenderType sdkLogAppenderType);

    /**
     * Return true when logger with given appender type have current log level equal to trace,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to trace, otherwise false
     */
    boolean isTraceEnabled(SdkLogAppenderType sdkLogAppenderType);

    /**
     * Return true when logger with given appender type have current log level equal to warn,
     * otherwise return false.
     *
     * @param sdkLogAppenderType specified appender type
     * @return true when logger with appender type have current level equal to warn, otherwise false
     */
    boolean isWarnEnabled(SdkLogAppenderType sdkLogAppenderType);

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
    void logAlert(Level level, String message);

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
    void logAlert(Level level, String message, Exception exception);

    /**
     * All interaction with the client software on the SDK interface level will be logged in a separate client communication log.
     * This includes all method invocations on the SDK interface including parameter values, request parameter validation errors
     * and other errors returned by the SDK, unhandled client exceptions, errors reported by the client handlers, timings of each call, etc.
     *
     * @param level   Log level
     * @param message String message
     */
    void logClientInteraction(Level level, String message);

    /**
     * All interaction with the client software on the SDK interface level will be logged in a separate client communication log.
     * This includes all method invocations on the SDK interface including parameter values, request parameter validation errors
     * and other errors returned by the SDK, unhandled client exceptions, errors reported by the client handlers, timings of each call, etc.
     *
     * @param level     Log level
     * @param message   String message
     * @param exception Optional exception
     */
    void logClientInteraction(Level level, String message, Exception exception);

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
    void logClientInteraction(Level level, String message, Duration interval, Exception exception);

    /**
     * Corrupt input feed data will be dumped into log files dedicated to corrupt input data (could be garbage and not even text, exceeding max buffer size).
     * Invalid input messages will be dumped into log files dedicated to invalid input messages (text, but not necessarily XML).
     *
     * @param level   Log level
     * @param message Invalid message
     */
    void logInvalidMessage(Level level, String message);

    /**
     * Depending on configuration SDK will be able to dump all feed XML traffic into dedicated traffic logs.
     * This data is already logged at the feed server level but here the client will have possibility
     * to configure traffic log filters based on the type of message, level of detail, match id, etc.
     *
     * @param outgoing when true it specifies a message the SDK has sent; false means
     *                 that a message SDK received
     * @param message  Message that was either read or written to the communication channel
     */
    void logTraffic(boolean outgoing, String message);

    /**
     * Dump given message to traffic log.
     *
     * @param message Message that was either read or written to the channel
     */
    void logTraffic(String message);
}
