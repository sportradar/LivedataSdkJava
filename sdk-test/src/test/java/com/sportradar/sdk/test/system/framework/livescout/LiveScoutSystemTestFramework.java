package com.sportradar.sdk.test.system.framework.livescout;

import com.sportradar.sdk.common.settings.DefaultSettingsBuilderHelper;
import com.sportradar.sdk.common.settings.LiveScoutSettingsBuilder;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.test.NullSdkLogger;
import com.sportradar.sdk.test.conn.FakeGateway;
import com.sportradar.sdk.test.system.framework.common.*;

import jakarta.xml.bind.JAXBException;

public class LiveScoutSystemTestFramework {

    private LiveScoutSettingsBuilder liveScoutSettingsBuilder;
    private LiveScoutDispatcher mockDispatcher;
    private FakeGateway mockGateway;
    private LiveScoutServerMock liveScoutServer;
    private SystemTestMessageParser messageParser;

    public LiveScoutSystemTestFramework(
            final LiveScoutDispatcher mockDispatcher,
            final SdkDataListener sdkDataListener) {
        this.mockDispatcher = mockDispatcher;
        mockGateway = new FakeGateway();
        try {
            messageParser = new SystemTestMessageParser(
                    new NullSdkLogger(),
                    new OutgoingMessageListener() {
                        @Override
                        public <T extends OutgoingMessage> void messageSendToServer(T msg) throws Exception {
                            liveScoutServer.handleBookmakerStatus(msg);
                            sdkDataListener.sdkEntitySend(msg);
                        }
                    },
                    "com.sportradar.sdk.proto.dto.incoming.livescout",
                    "com.sportradar.sdk.proto.dto.outgoing.livescout");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        mockGateway.setFakeGatewayListener(new GatewayListenerMock(messageParser));
        liveScoutServer = new LiveScoutServerMock(mockGateway, messageParser);

        liveScoutSettingsBuilder = DefaultSettingsBuilderHelper.getLiveScout();
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
