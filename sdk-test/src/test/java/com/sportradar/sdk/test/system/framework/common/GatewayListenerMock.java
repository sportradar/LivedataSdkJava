package com.sportradar.sdk.test.system.framework.common;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.test.conn.FakeGatewayListener;

import static org.hamcrest.MatcherAssert.assertThat;

public class GatewayListenerMock implements FakeGatewayListener {

    private SystemTestMessageParser messageParser;

    public GatewayListenerMock(SystemTestMessageParser messageParser) {
        this.messageParser = messageParser;
    }

    @Override
    public void onConnectedRequested() {

    }

    @Override
    public void onDisconnectedRequested() {

    }

    @Override
    public void onSendDataRequested(byte[] data) {
        try {
            messageParser.parseData(data);
        } catch (SdkException e) {
            assertThat("parseData throw an exception", false);
        }
    }
}
