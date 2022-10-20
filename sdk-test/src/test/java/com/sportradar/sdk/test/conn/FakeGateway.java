package com.sportradar.sdk.test.conn;

import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * A {@link Gateway} implementation used for testing.
 */
public class FakeGateway implements Gateway {

    private GatewayListener listener;
    private FakeGatewayListener fakeGatewayListener;
    private boolean isConnected;
    private int setGatewayListenerCounter = 0;
    private int disconnectCounter = 0;

    public FakeGateway() {
    }


    /**
     * Sets the {@link com.sportradar.sdk.common.networking.GatewayListener} used to observe the current {@link com.sportradar.sdk.common.networking.Gateway} implementation.
     *
     * @param listener the {@link com.sportradar.sdk.common.networking.GatewayListener} used to observe the current {@link com.sportradar.sdk.common.networking.Gateway} implementation or a null reference if observation is not required.
     */
    @Override
    public void setListener(GatewayListener listener) {
        ++setGatewayListenerCounter;
        this.listener = listener;
    }

    public void setFakeGatewayListener(FakeGatewayListener listener) {
        this.fakeGatewayListener = listener;
    }

    /**
     * Sends data through the current {@link com.sportradar.sdk.common.networking.Gateway}.
     *
     * @param data data to send.
     * @throws IllegalArgumentException The {@code data} is a null reference or an empty array
     */
    @Override
    public void sendData(byte[] data) throws IOException {
        this.fakeGatewayListener.onSendDataRequested(data);
    }


    /**
     * Attempts to establish a connection to the remote site.
     *
     * @throws java.io.IOException There was an error establishing the connection.
     */
    @Override
    public void connect() throws IOException {
        this.fakeGatewayListener.onConnectedRequested();
    }

    /**
     * Drops the established connection.
     *
     * @param dueToError - when false user wished to close connection; when true there was a problem and we had
     *                   to close it
     */
    @Override
    public void disconnect(boolean dueToError) {
        ++disconnectCounter;
        this.fakeGatewayListener.onDisconnectedRequested();
    }

    /**
     * Gets a value specifying whether the connection to the remote site is established.
     *
     * @return Value specifying whether the connection to the remote site is established.
     */
    @Override
    public boolean isConnected() {
        return isConnected;
    }

    public void notifyConnected() {
        isConnected = true;
        if(listener!=null) {
            listener.onConnected();
        }
    }

    public void notifyDisconnected(Exception ex) {
        isConnected = false;
        if(listener!=null) {
            listener.onDisconnected(ex);
        }
    }

    public void notifyDataReceived(byte[] data) {
        if(listener!=null) {
            this.listener.onDataReceived(new ByteArrayInputStream(data));
        }
    }


    public int getSetGatewayListenerCounter() {
        return this.setGatewayListenerCounter;
    }

    public int getDisconnectCounter() {
        return this.disconnectCounter;
    }

    /**
     * Gets last timestamp in unix millis when the last msg was received
     *
     * @return timestamp when the last msg was received
     */
    @Override
    public long getLastReceivedMsgTimestamp() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }
}
