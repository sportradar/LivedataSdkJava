package com.sportradar.livedata.sdk.system.framework.livescout;

import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings;
import com.sportradar.livedata.sdk.common.settings.LiveScoutSettings.LiveScoutSettingsBuilder;
import com.sportradar.livedata.sdk.common.networking.FakeGateway;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.system.framework.common.*;

import jakarta.xml.bind.JAXBException;

public class LiveScoutSystemTestFramework {

    private final LiveScoutSettingsBuilder liveScoutSettingsBuilder;
    private final LiveScoutDispatcher mockDispatcher;
    private final FakeGateway mockGateway;
    private final LiveScoutServerMock liveScoutServer;

    public LiveScoutSystemTestFramework(
            final LiveScoutDispatcher mockDispatcher,
            final SdkDataListener sdkDataListener) throws JAXBException {
        this.mockDispatcher = mockDispatcher;
        mockGateway = new FakeGateway();
        SystemTestMessageParser messageParser = new SystemTestMessageParser(
                new OutgoingMessageListener() {
                    @Override
                    public <T extends OutgoingMessage> void messageSendToServer(T msg) {
                        liveScoutServer.handleBookmakerStatus(msg);
                        sdkDataListener.sdkEntitySend(msg);
                    }
                },
                "com.sportradar.livedata.sdk.proto.dto.incoming.livescout",
                "com.sportradar.livedata.sdk.proto.dto.outgoing.livescout");
        mockGateway.setFakeGatewayListener(new GatewayListenerMock(messageParser));
        liveScoutServer = new LiveScoutServerMock(mockGateway, messageParser);

        liveScoutSettingsBuilder = LiveScoutSettings.builder(false);
    }

    public LiveScoutFeed createFeed() {
        return SystemTestHelper.getLiveScoutFeed(mockGateway, mockDispatcher, liveScoutSettingsBuilder);
    }

    public LiveScoutSettingsBuilder settings() {
        return liveScoutSettingsBuilder;
    }

    public LiveScoutServerMock server() {
        return liveScoutServer;
    }

    public FakeGateway gateway() {
        return mockGateway;
    }
}
