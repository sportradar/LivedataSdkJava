package com.sportradar.sdk.common.settings;

import com.sportradar.sdk.common.exceptions.SdkException;

/**
 *
 */
public interface SettingsLoader {

    JmxSettings getJmxSettings() throws SdkException;

    LiveScoutSettings getLiveScoutSettings() throws SdkException;
}
