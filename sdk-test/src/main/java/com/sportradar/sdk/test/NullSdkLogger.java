package com.sportradar.sdk.test;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.SdkLogAppenderType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import org.joda.time.Duration;

/**
 * This is a dummy class that implement interface {@link com.sportradar.sdk.common.interfaces.SdkLogger}
 * and does not log events.
 */
public class NullSdkLogger implements SdkLogger {

    /**
     * {@inheritDoc}
     */
    @Override
    public void logInvalidMessage(Level level, String message) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logTraffic(boolean outgoing, String message) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logTraffic(String message) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logClientInteraction(Level level, String message) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logClientInteraction(Level level, String message, Exception exception) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logClientInteraction(Level level, String message, Duration interval, Exception exception) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logAlert(Level level, String message) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logAlert(Level level, String message, Exception exception) {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLevelEnabled(Level level, SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled(SdkLogAppenderType sdkLogAppenderType) {
        return false;
    }
}