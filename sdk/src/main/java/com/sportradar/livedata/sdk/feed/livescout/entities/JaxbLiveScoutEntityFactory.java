package com.sportradar.livedata.sdk.feed.livescout.entities;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.exceptions.UnknownEnumException;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.livedata.sdk.feed.livescout.enums.BookMatchResult;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;
import jakarta.inject.Inject;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

@SuppressWarnings("JavaDoc")
public class JaxbLiveScoutEntityFactory implements LiveScoutEntityFactory {

    @Override
    public MatchBookingEntity buildMatchBookingEntity(Bookmatch bookMatch) throws InvalidEntityException {
        MatchBookingEntity result = new MatchBookingEntity();
        result.setMatchId(bookMatch.getMatchid());
        result.setMessage(bookMatch.getMessage());
        try {
            result.setResult(BookMatchResult.getBookMatchResultFromLiteralValue(bookMatch.getResult()));
        } catch (UnknownEnumException e) {
            throw new InvalidEntityException(e, "Bookmatch.getScore()", bookMatch.getResult());
        }
        return result;
    }

    @Override
    public MatchStopEntity buildMatchStopEntity(Matchstop matchStop) {
        MatchStopEntity result = new MatchStopEntity();
        result.setMatchId(matchStop.getMatchid());
        result.setReason(matchStop.getReason());
        return result;
    }

    @Override
    public MatchListEntity buildMatchListEntity(Matchlist matchList) throws InvalidEntityException {
        checkNotNull(matchList.getMatch());
        MatchListEntity result = new MatchListEntity();
        ArrayList<MatchUpdateEntity> matches = new ArrayList<>(matchList.getMatch().size());
        for (Match match : matchList.getMatch()) {
            matches.add(buildMatchUpdateEntity(match));
        }
        result.setMatches(matches);
        return result;
    }

    @Override
    public MatchListUpdateEntity buildMatchListUpdateEntity(Matchlistupdate matchListUpdate) throws InvalidEntityException {
        MatchListUpdateEntity result = new MatchListUpdateEntity();
        ArrayList<MatchUpdateEntity> matches = new ArrayList<>(matchListUpdate.getMatch().size());
        for (Match match : matchListUpdate.getMatch()) {
            matches.add(buildMatchUpdateEntity(match));
        }
        result.setMatches(matches);
        return result;
    }

    @Override
    public MatchUpdateEntity buildMatchUpdateEntity(Match match) throws InvalidEntityException {
        MatchUpdateEntity result = new MatchUpdateEntity();
        result.setMatchHeader(JaxbLiveScoutEntityFactoryHelper.buildMatchHeaderEntity(match));
        for (IncomingMessage item : match.getStatusOrScoreOrRed()) {
            JaxbLiveScoutEntityFactoryHelper.applyStatusOrScoreOrRed(result, item);
        }
        return result;
    }

    @Override
    public LineupsEntity buildLineupsEntity(Lineups lineups) throws InvalidEntityException {
        LineupsEntity result = new LineupsEntity();
        result.setMatchId(lineups.getMatchid());
        result.setPreliminary(lineups.getPreliminary() != null ? 1 == lineups.getPreliminary() : null);
        if (lineups.getPlayer() != null) {//always not null
            for (Player player : lineups.getPlayer()) {
                result.getPlayers().add(JaxbLiveScoutEntityFactoryHelper.buildPlayerEntity(player));
            }
        }
        List<Manager> managerList = lineups.getManager();
        if (managerList != null) {//always not null
            for (Manager manager : managerList) {
                result.getManagers().add(JaxbLiveScoutEntityFactoryHelper.buildManagerEntity(manager));
            }
        }
        List<Teamofficial> teamOfficialList = lineups.getTeamofficial();
        if (teamOfficialList != null) {//always not null
            for (Teamofficial teamofficial : teamOfficialList) {
                result.getTeamOfficials().add(JaxbLiveScoutEntityFactoryHelper.buildTeamOfficial(teamofficial));
            }
        }
        return result;
    }

    @Override
    public MatchDataEntity buildMatchDataEntity(Matchdata matchdata) throws InvalidEntityException {
        MatchDataEntity result = new MatchDataEntity();
        result.setMatchId(matchdata.getMatchid());
        result.setMatchTime(matchdata.getMtime());
        result.setRemainingTimeInPeriod(matchdata.getRemainingtimeperiod());
        return result;
    }

    @Override
    public ServerTimeEntity buildServerTimeEntity(Servertime servertime) throws InvalidEntityException {
        ServerTimeEntity entity = new ServerTimeEntity();
        entity.setServerTime(new DateTime(servertime.getValue()));
        return entity;
    }
}
