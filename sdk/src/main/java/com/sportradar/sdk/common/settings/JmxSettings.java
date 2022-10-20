package com.sportradar.sdk.common.settings;

public class JmxSettings {

    protected boolean enabled;
    protected String jmxHost = "localhost";
    protected int jmxPort = 12345;
    protected String passwordFile;
    protected String accessFile;

    public JmxSettings(boolean enabled, String jmxHost, int jmxPort, String passwordFile, String accessFile) {
        this.enabled = enabled;
        this.jmxHost = jmxHost;
        this.jmxPort = jmxPort;
        this.passwordFile = passwordFile;
        this.accessFile = accessFile;
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

    public boolean isEnabled() {
        return enabled;
    }
}
