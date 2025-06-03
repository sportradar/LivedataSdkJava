package com.sportradar.livedata.sdk.proto.livescout.auth;

import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.livescout.LiveScoutOutgoingMessageFactory;

/**
 * Represents a class used to build authentication lifecycle requests to be sent to the betradar feed.
 */
public abstract class AuthMessageProvider {
    protected final LiveScoutOutgoingMessageFactory factory;
    protected final AuthSettings settings;

    /**
     *
     * @param factory The factory used to build requests send to the feed
     * @param authSettings The {@link AuthSettings} instance containing the application's settings concerning the authentication
     */
    public AuthMessageProvider(LiveScoutOutgoingMessageFactory factory, AuthSettings authSettings) {
        this.factory = factory;
        this.settings = authSettings;
    }

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-in request.
     *
     * @return a {@link OutgoingMessage} representing a log-in request.
     */
    public abstract OutgoingMessage buildLoginRequest();

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-out request.
     *
     * @return a {@link OutgoingMessage} representing a log-out request or a null reference if protocol associate with
     * the current factory does not support log-out
     */
    public OutgoingMessage buildLogOutRequest() {
        return factory.buildLogOutRequest();
    }
}
