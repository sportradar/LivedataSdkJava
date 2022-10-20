/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.livescout;

import com.sportradar.sdk.common.interfaces.Version;
import com.sportradar.sdk.proto.common.StatusFactory;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.dto.outgoing.livescout.*;

import javax.inject.Inject;
import java.util.Collection;

import static com.sportradar.sdk.common.classes.Nulls.nte;

/**
 * A factory used to generate {@link OutgoingMessage} representing live-scout server requests.
 */
public class LiveScoutStatusFactory implements StatusFactory {

    private final Version version;

    @Inject
    public LiveScoutStatusFactory(final Version version) {
        this.version = version;
    }

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-in request.
     *
     * @param username The username
     * @param password The password.
     * @return a {@link OutgoingMessage} representing a log-in request.
     */
    @Override
    public OutgoingMessage buildLoginRequest(String username, String password) {
        Loginname loginname = new Loginname();
        loginname.setValue(username);

        Password pass = new Password();
        pass.setValue(password);

        Clientversion clientVersion = new Clientversion();
        clientVersion.setValue(version.getVersion());

        Credential credential = new Credential();
        credential.setLoginname(loginname);
        credential.setPassword(pass);
        credential.setClientversion(clientVersion);

        Login login = new Login();
        login.setCredential(credential);

        return login;
    }

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-out request.
     *
     * @return a {@link OutgoingMessage} representing a log-out request or a null reference if protocol associate with
     * the current factory does not support log-out
     */
    @Override
    public OutgoingMessage buildLogOutRequest() {
        return new Logout();
    }

    public OutgoingMessage buildMatchBooking(long matchId) {
        Bookmatch ret = new Bookmatch();
        ret.setMatchid((int) matchId);

        return ret;
    }

    public OutgoingMessage buildMatchSubscribe(Iterable<Long> matchIds) {
        Matchsubscription matchsubscription = new Matchsubscription();
        for (Long matchId : matchIds) {
            Match ret = new Match();
            ret.setMatchid(matchId);
            ret.setFeedtype("delta");
            matchsubscription.getMatch().add(ret);
        }
        return matchsubscription;
    }

    public OutgoingMessage buildMatchUnsubscribe(long matchId) {
        Matchstop ret = new Matchstop();
        ret.setMatchid((int) matchId);

        return ret;
    }

    public OutgoingMessage buildMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Long> sportIds, Collection<Long> matchIds) {
        Matchlist ret = new Matchlist();

        ret.setHoursback(hoursBack);
        ret.setHoursforward(hoursForward);
        if (includeAvailable) {
            ret.setIncludeavailable("yes");
        }

        for(long sportId : nte(sportIds)){
            Matchlist.Sport sport = new Matchlist.Sport();
            sport.setSportid(sportId);
            ret.getSport().add(sport);
        }

        for(long matchId : nte(matchIds)){
            Matchlist.Match match = new Matchlist.Match();
            match.setMatchid(matchId);
            ret.getMatch().add(match);
        }

//        if(sportIds != null && !sportIds.isEmpty()) {
//            List<Matchlist.Sport> sports = sportIds.stream().map(i -> {
//                Matchlist.Sport sport = new Matchlist.Sport();
//                sport.setSportid(i);
//                return sport;
//            }).collect(Collectors.toList());
//        }
//        if(matchIds != null && !matchIds.isEmpty()) {
//            List<Matchlist.Match> matches = matchIds.stream().map(i -> {
//                Matchlist.Match match = new Matchlist.Match();
//                match.setMatchid(i);
//                return match;
//            }).collect(Collectors.toList());
//        }

        return ret;
    }

    public OutgoingMessage buildAlive() {
        return new Ct();
    }


    public OutgoingMessage buildServerTimeRequest() {
        return new Servertime();
    }


}
