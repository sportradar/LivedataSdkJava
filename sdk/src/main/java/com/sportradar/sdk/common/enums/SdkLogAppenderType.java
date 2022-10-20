/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.enums;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public enum SdkLogAppenderType {
    /**
     * Invalid message appender.
     */
    INVALID_MSG("InvalidMessageAppender", "InvalidMsg"),
    /**
     * Traffic appender.
     */
    TRAFFIC("TrafficAppender", "Traffic"),
    /**
     * Client interaction appender.
     */
    CLIENT_INTERACTION("ClientInteractionAppender", "ClientInteraction"),
    /**
     * Alert appender.
     */
    ALERT("AlertAppender", "Alert"),
    /**
     * Execution appender.
     */
    EXECUTION("Execution", "Execution");

    final private String appenderName;
    final private String fileName;

    SdkLogAppenderType(String appenderName, String fileName) {
        this.appenderName = appenderName;
        this.fileName = fileName;
    }

    public String getAppenderName() {
        return this.appenderName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Marker getMarker(String markerName) {
        return MarkerFactory.getMarker(resolveAppenderName(this, markerName));
    }

    public static String getAppenderName(SdkLogAppenderType appenderType, String markerName) {
        if (!(appenderType instanceof SdkLogAppenderType)) {
            throw new IllegalArgumentException("appenderType");
        }
        if (markerName == null ||
                !(markerName instanceof String)) {
            return appenderType.getAppenderName();
        }
        for (SdkLogAppenderType sdkLogAppenderType : SdkLogAppenderType.values()) {
            if (appenderType == sdkLogAppenderType) {
                return resolveAppenderName(sdkLogAppenderType, markerName);
            }
        }
        return appenderType.getAppenderName();
    }

    public static String resolveAppenderName(SdkLogAppenderType appenderType, String markerName) {
        if (!(appenderType instanceof SdkLogAppenderType)) {
            throw new IllegalArgumentException("appenderType");
        }
        if (!(markerName instanceof String) || markerName.isEmpty() || appenderType == EXECUTION) {
            return appenderType.appenderName;
        }
        return String.format("%s-%s", markerName, appenderType.appenderName);
    }
}
