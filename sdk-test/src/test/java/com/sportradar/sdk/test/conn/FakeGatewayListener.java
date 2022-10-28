package com.sportradar.livedata.sdk.test.conn;

public interface FakeGatewayListener {

    public void onConnectedRequested();

    public void onDisconnectedRequested();

    public void onSendDataRequested(byte[] data);
}
