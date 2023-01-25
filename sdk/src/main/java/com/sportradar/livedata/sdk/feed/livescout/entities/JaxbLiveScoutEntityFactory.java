package com.sportradar.livedata.sdk.feed.livescout.entities;

import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;

@SuppressWarnings("JavaDoc")
public class JaxbLiveScoutEntityFactory implements LiveScoutEntityFactory {

    @Override
    public MatchBookingEntity buildMatchBookingEntity(Bookmatch bookMatch) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchBookingEntity(bookMatch);
    }

    @Override
    public MatchStopEntity buildMatchStopEntity(Matchstop matchStop) {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchStopEntity(matchStop);
    }

    @Override
    public MatchListEntity buildMatchListEntity(Matchlist matchList) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchListEntity(matchList);
    }

    @Override
    public MatchListUpdateEntity buildMatchListUpdateEntity(Matchlistupdate matchListUpdate) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchListUpdateEntity(matchListUpdate);
    }

    @Override
    public MatchUpdateEntity buildMatchUpdateEntity(Match match) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchUpdateEntity(match);
    }

    @Override
    public LineupsEntity buildLineupsEntity(Lineups lineups) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildLineupsEntity(lineups);
    }

    @Override
    public MatchDataEntity buildMatchDataEntity(Matchdata matchdata) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildMatchDataEntity(matchdata);
    }

    @Override
    public ServerTimeEntity buildServerTimeEntity(Servertime servertime) throws InvalidEntityException {
        return JaxbLiveScoutEntityFactoryHelper.buildServerTimeEntity(servertime);
    }
}
