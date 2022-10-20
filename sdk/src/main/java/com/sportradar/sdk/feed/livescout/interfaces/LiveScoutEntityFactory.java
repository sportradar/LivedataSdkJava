package com.sportradar.sdk.feed.livescout.interfaces;

import com.sportradar.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.proto.dto.incoming.livescout.*;

/**
 * Classes that implement this interface should build
 * feed layer entities based on input protocol layer entities
 */
public interface LiveScoutEntityFactory {

    public LineupsEntity buildLineupsEntity(Lineups lineups) throws InvalidEntityException;

    public MatchBookingEntity buildMatchBookingEntity(Bookmatch bookMatch) throws InvalidEntityException;

    public MatchDataEntity buildMatchDataEntity(Matchdata matchdata) throws InvalidEntityException;

    public MatchListEntity buildMatchListEntity(Matchlist matchList) throws InvalidEntityException;

    public MatchListUpdateEntity buildMatchListUpdateEntity(Matchlistupdate matchListUpdate) throws InvalidEntityException;

    public MatchStopEntity buildMatchStopEntity(Matchstop matchStop);

    public MatchUpdateEntity buildMatchUpdateEntity(Match matchUpdate) throws InvalidEntityException;

    public OddsSuggestionsEntity buildOddsSuggestionsEntity(OddsSuggestions oddsSuggestions);

    public ServerTimeEntity buildServerTimeEntity(Servertime servertime) throws InvalidEntityException;


}
