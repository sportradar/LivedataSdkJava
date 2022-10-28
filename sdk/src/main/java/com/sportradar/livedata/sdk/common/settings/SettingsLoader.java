package com.sportradar.livedata.sdk.common.settings;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;

/**
 *
 */
public interface SettingsLoader {

    JmxSettings getJmxSettings() throws SdkException;

    LiveScoutSettings getLiveScoutSettings() throws SdkException;
}
