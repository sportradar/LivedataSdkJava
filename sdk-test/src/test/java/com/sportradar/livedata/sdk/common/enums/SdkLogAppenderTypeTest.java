package com.sportradar.livedata.sdk.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SdkLogAppenderTypeTest {

    @DisplayName("getAppenderName() test -> if appenderType and marker are not null")
    @Test
    public void getAppenderName_appenderTypeIsNotNull_test() {
        SdkLogAppenderType appenderTypeAlert = SdkLogAppenderType.ALERT;
        final String markerName = "marker";

        String appenderName = SdkLogAppenderType.getAppenderName(appenderTypeAlert, markerName);

        assertThat(appenderName).isEqualTo(
                SdkLogAppenderType.resolveAppenderName(appenderTypeAlert, markerName)
        );
    }

    @DisplayName("getAppenderName() test -> if appenderType is null")
    @Test
    public void getAppenderName_appenderTypeIsNull_test() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> SdkLogAppenderType.getAppenderName(null, "marker")
        );
        assertThat(ex.getMessage()).isEqualTo("appenderType");
    }

    @DisplayName("getAppenderName() test -> if marker is null")
    @Test
    public void getAppenderName_markerIsNull_test() {
        SdkLogAppenderType appenderTypeAlert = SdkLogAppenderType.ALERT;
        String expectedAppenderName = appenderTypeAlert.getAppenderName();

        String actualAppenderName = SdkLogAppenderType.getAppenderName(appenderTypeAlert, null);

        assertThat(actualAppenderName).isEqualTo(expectedAppenderName);
    }

    @DisplayName("resolveAppenderName() test -> ok parameters")
    @Test
    public void getMarker_test() {
        SdkLogAppenderType appenderTypeAlert = SdkLogAppenderType.ALERT;
        final String markerName = "marker";

        String actualAppenderName = SdkLogAppenderType.resolveAppenderName(appenderTypeAlert, markerName);

        assertThat(actualAppenderName).isEqualTo(
                String.format("%s-%s", markerName, appenderTypeAlert.getAppenderName())
        );
    }

    @DisplayName("resolveAppenderName() test -> appender is null")
    @Test
    public void getMarker_appenderTypeIsNull_test() {
        assertThrows(
                IllegalArgumentException.class,
                () -> SdkLogAppenderType.resolveAppenderName(null, "marker")
        );
    }

    @DisplayName("resolveAppenderName() test -> marker is null")
    @Test
    public void getMarker_markerIsNull_test() {
        SdkLogAppenderType appenderType = SdkLogAppenderType.ALERT;

        String actualAppenderName = SdkLogAppenderType.resolveAppenderName(appenderType, null);

        assertThat(actualAppenderName).isEqualTo(appenderType.getAppenderName());
    }

    @DisplayName("resolveAppenderName() test -> marker is empty")
    @Test
    public void getMarker_markerIsEmpty_test() {
        SdkLogAppenderType appenderType = SdkLogAppenderType.ALERT;

        String actualAppenderName = SdkLogAppenderType.resolveAppenderName(appenderType, "");

        assertThat(actualAppenderName).isEqualTo(appenderType.getAppenderName());
    }

    @DisplayName("resolveAppenderName() test -> if appenderType == EXECUTION")
    @Test
    public void getMarker_appenderTypeExecution_test() {
        SdkLogAppenderType appenderType = SdkLogAppenderType.EXECUTION;

        String actualAppenderName = SdkLogAppenderType.resolveAppenderName(appenderType, "marker");

        assertThat(actualAppenderName).isEqualTo(appenderType.getAppenderName());
    }
}