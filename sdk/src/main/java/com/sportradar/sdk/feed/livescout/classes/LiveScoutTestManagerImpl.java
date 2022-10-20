/**
 * ************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 * *************************************************************
 */

package com.sportradar.sdk.feed.livescout.classes;

import com.sportradar.sdk.feed.common.LiveFeedTestManagerBase;
import com.sportradar.sdk.feed.common.TestDelayWithSpeed;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutTestManager;
import com.sportradar.sdk.proto.common.Protocol;

/**
 * Represents a class used to drive the LiveScout test server
 */
public class LiveScoutTestManagerImpl extends LiveFeedTestManagerBase implements LiveScoutTestManager {

    public LiveScoutTestManagerImpl(Protocol protocol) {
        super(protocol);
    }

    /**
     * Is the test manager available?
     *
     * @return - false when not in test mode; true else
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Subscribe to an event
     *
     * @param id    - event id
     * @param delay - start message number can be specified and a speed how fast new events arrive
     */
    @Override
    public void subscribe(EventIdentifier id, TestDelayWithSpeed delay) {
        StringBuilder sb = new StringBuilder();

        sb.append("<match id=\"");
        sb.append(id.getEventId());
        sb.append("\" feedtype=\"delta\" ");

        if (delay != null) {
            sb.append("startmessage=\"");
            sb.append(delay.getMsgNr());
            sb.append("\" ");
        }

        if (delay.getDelay() != null && delay.getDelay().getMillis() > 0) {
            sb.append("messagedelay=\"");
            sb.append(delay.getDelay().getMillis());
            sb.append("\" ");
        }

        sb.append("/>");
        send(sb.toString());
    }
}
