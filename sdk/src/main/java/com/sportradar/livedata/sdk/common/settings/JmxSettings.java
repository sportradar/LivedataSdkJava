package com.sportradar.livedata.sdk.common.settings;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class JmxSettings {

    private final boolean enabled;
    private final String jmxHost;
    private final int jmxPort;
    private final String passwordFile;
    private final String accessFile;

    @SuppressWarnings("all") // Suppressing warnings for Lombok generated code
    public static class JmxSettingsBuilder {
        private boolean enabled = false;
        private String jmxHost = "localhost";
        private int jmxPort = 13370;
    }
}
