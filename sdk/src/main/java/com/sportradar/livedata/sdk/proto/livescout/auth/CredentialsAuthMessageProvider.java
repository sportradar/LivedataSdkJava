package com.sportradar.livedata.sdk.proto.livescout.auth;

import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageFactory;

/**
 * Represents a class used to build credential authentication lifecycle requests to be sent to the betradar feed.
 */
public class CredentialsAuthMessageProvider extends AuthMessageProvider {

    public CredentialsAuthMessageProvider(LiveScoutOutgoingMessageFactory factory, AuthSettings authSettings) {
        super(factory, authSettings);
    }

    @Override
    public OutgoingMessage buildLoginRequest() {
        return factory.buildLoginRequest(settings.getUsername(), settings.getPassword(), null);
    }
}
