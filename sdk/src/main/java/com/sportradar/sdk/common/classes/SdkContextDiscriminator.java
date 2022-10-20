package com.sportradar.sdk.common.classes;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.AbstractDiscriminator;

public class SdkContextDiscriminator extends AbstractDiscriminator<ILoggingEvent> {

    private String key;
    private String defaultValue;

    public String getDiscriminatingValue(ILoggingEvent event) {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        if (this.defaultValue == null || this.defaultValue.isEmpty()) {
            this.defaultValue = defaultValue;
        }
    }
}
