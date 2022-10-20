package com.sportradar.sdk.test;


import com.sportradar.sdk.common.exceptions.SdkException;

/**
 * Represents a listener used to observe the {@link TcpServer} instance.
 */
public interface NetworkServerListener {

    /**
     * Invoked when the async method used to start the observed {@link TcpServer} has completed
     */
    public void onStarted();

    /**
     * Invoked when the async method used to stop the observed {@link TcpServer} has completed.
     */
    public void onStopped();

    /**
     * Invoked by the observed {@link TcpServer} when the client connects to it.
     */
    public void onClientConnected();

    /**
     * Invoked by the observed {@link TcpServer} when the client closes the connection.
     */
    public void onClientDisconnected();

    /**
     * Invoked by the observed {@code TcpServer} when it receives data from the remote site
     *
     * @param data The received data
     */
    public void onDataReceived(byte[] data) throws SdkException;
}
