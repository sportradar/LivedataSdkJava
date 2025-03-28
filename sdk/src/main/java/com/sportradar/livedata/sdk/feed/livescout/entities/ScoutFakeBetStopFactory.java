package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.common.enums.Team;
import com.sportradar.livedata.sdk.feed.livescout.enums.MatchBetStatus;
import com.sportradar.livedata.sdk.feed.livescout.enums.ScoutFeedType;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JavaDoc")
public class ScoutFakeBetStopFactory {

    public static MatchUpdateEntity generateBetStop(EventIdentifier id, DateTime time) {

        MatchHeaderEntity matchHeader = new MatchHeaderEntity();
        matchHeader.setMatchId(id.getEventId());
        matchHeader.setBetStatus(MatchBetStatus.BETSTOP);
        matchHeader.setFeedType(ScoutFeedType.DELTA);
        ScoutEventEntity event = new ScoutEventEntity();
        event.setId(0);
        event.setTypeId(1011);
        event.setServerTime(time);
        event.setSide(Team.NONE);
        event.setInfo("Automatically generated betstop");
        List<ScoutEventEntity> events = new ArrayList<>(1);
        events.add(event);

        MatchUpdateEntity ret = new MatchUpdateEntity();
        ret.setMatchHeader(matchHeader);
        ret.setEvents(events);

        return ret;
    }

}
