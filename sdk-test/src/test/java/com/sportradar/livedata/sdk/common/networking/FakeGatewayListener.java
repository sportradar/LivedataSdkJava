package com.sportradar.livedata.sdk.common.networking;

public interface FakeGatewayListener {

    public void onConnectedRequested();

    public void onDisconnectedRequested();

    public void onSendDataRequested(byte[] data);
}
