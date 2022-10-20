package com.sportradar.sdk.common.settings;

public class JmxSettingsBuilder {

    protected String jmxHost;
    protected int jmxPort;
    protected String passwordFile;
    protected String accessFile;
    protected boolean enabled;

    public JmxSettings build() {
        return new JmxSettings(enabled, jmxHost, jmxPort, passwordFile, accessFile);
    }

    public String getJmxHost() {
        return jmxHost;
    }

    public int getJmxPort() {
        return jmxPort;
    }

    public String getPasswordFile() {
        return passwordFile;
    }

    public String getAccessFile() {
        return accessFile;
    }

    public JmxSettingsBuilder setJmxHost(String jmxHost) {
        this.jmxHost = jmxHost;
        return this;
    }

    public JmxSettingsBuilder setJmxPort(int jmxPort) {
        this.jmxPort = jmxPort;
        return this;
    }

    public JmxSettingsBuilder setPasswordFile(String passwordFile) {
        this.passwordFile = passwordFile;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public JmxSettingsBuilder setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public JmxSettingsBuilder setAccessFile(String accessFile) {
        this.accessFile = accessFile;
        return this;

    }
}
