/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.feed.common;

import com.sportradar.sdk.proto.common.MessageException;
import com.sportradar.sdk.proto.common.Protocol;
import com.sportradar.sdk.proto.common.ProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * Base class for feed test managers
 */
public class LiveFeedTestManagerBase {

    private final static Logger logger = LoggerFactory.getLogger(LiveFeedTestManagerBase.class);

    protected final Protocol protocol;

    public LiveFeedTestManagerBase(Protocol protocol) {
        checkNotNull(protocol, "Protocol cannot be a null reference");
        this.protocol = protocol;
    }

    protected boolean send(String msg) {
        try {
            protocol.sendMessage(msg);
        } catch (MessageException e) {
            logger.warn("Send caught exception", e);
            return false;
        } catch (ProtocolException e) {
            logger.warn("Send caught exception", e);
            return false;
        }

        return true;
    }
}
