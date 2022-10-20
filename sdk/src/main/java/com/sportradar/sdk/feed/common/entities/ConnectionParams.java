package com.sportradar.sdk.feed.common.entities;

import java.io.Serializable;

/**
 * Holds data about underlying connection parameters
 */
public class ConnectionParams implements Serializable {

    private static final long serialVersionUID = -5278173689989410907L;
    private String hostName;
    private Integer port;
    private String url;

    public ConnectionParams(String hostName, Integer port, String url) {
        this.hostName = hostName;
        this.port = port;
        this.url = url;
    }

    /**
     * Hostname we are connecting to
     *
     * @return hostname or null if using HTTP based feed
     */
    public String getHostName() {

        return hostName;
    }

    /**
     * Port we are connecting to
     *
     * @return port or null if using HTTP based feed
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Url we are fetching
     *
     * @return url or null if using Socket based feed
     */
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ConnectionParams{" +
                "hostName='" + hostName + '\'' +
                ", port=" + port +
                ", url='" + url + '\'' +
                '}';
    }

}
