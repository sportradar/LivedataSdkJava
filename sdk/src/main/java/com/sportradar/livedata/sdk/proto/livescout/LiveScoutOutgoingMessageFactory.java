/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.livedata.sdk.proto.livescout;

import com.sportradar.livedata.sdk.common.interfaces.Version;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.proto.dto.outgoing.livescout.*;

import jakarta.inject.Inject;
import java.util.Collection;
import java.util.UUID;

import static com.sportradar.livedata.sdk.common.classes.Nulls.nte;

/**
 * A factory used to generate {@link OutgoingMessage} representing live-scout server requests.
 */
public class LiveScoutOutgoingMessageFactory {

    private final Version version;

    @Inject
    public LiveScoutOutgoingMessageFactory(final Version version) {
        this.version = version;
    }

    /**
     * Constructs and returns a {@link OutgoingMessage} representing a log-in request.
     *
     * @param username The username
     * @param password The password.
     * @return a {@link OutgoingMessage} representing a log-in request.
     */
    public OutgoingMessage buildLoginRequest(String username, String password, String accessToken) {
        Clientversion clientVersion = new Clientversion();
        clientVersion.setValue(version.getVersion());

        Credential credential = new Credential();
        if(accessToken == null) {
            Loginname loginname = new Loginname();
            loginname.setValue(username);
            Password pass = new Password();
            pass.setValue(password);

            credential.setLoginname(loginname);
            credential.setPassword(pass);
        } else {
            Jwttoken token = new Jwttoken();
            token.setValue(accessToken);
            credential.setJwttoken(token);
        }
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
    public OutgoingMessage buildLogOutRequest() {
        return new Logout();
    }

    public OutgoingMessage buildMatchBooking(long matchId) {
        Bookmatch ret = new Bookmatch();
        ret.setMatchid((int) matchId);

        return ret;
    }

    public OutgoingMessage buildMatchSubscribe(Iterable<Long> matchIds) {
        Matchsubscription subscription = new Matchsubscription();
        for (Long matchId : matchIds) {
            Match ret = new Match();
            ret.setMatchid(matchId);
            ret.setFeedtype("delta");
            subscription.getMatch().add(ret);
        }
        return subscription;
    }

    public OutgoingMessage buildMatchUnsubscribe(Iterable<Long> matchIds) {
        Matchunsubscription unsubscription = new Matchunsubscription();
        for(Long matchId : matchIds){
            Match ret = new Match();
            ret.setMatchid(matchId);
            ret.setFeedtype("delta");
            unsubscription.getMatch().add(ret);
        }
        return unsubscription;
    }

    public OutgoingMessage buildMatchList(int hoursBack, int hoursForward, boolean includeAvailable, Collection<Integer> sportIds, Collection<Long> matchIds, UUID requestId) {
        Matchlist ret = new Matchlist();

        ret.setHoursback(hoursBack);
        ret.setHoursforward(hoursForward);
        if (includeAvailable) {
            ret.setIncludeavailable("yes");
        }

        for(int sportId : nte(sportIds)){
            Matchlist.Sport sport = new Matchlist.Sport();
            sport.setSportid(sportId);
            ret.getSport().add(sport);
        }

        for(long matchId : nte(matchIds)){
            Matchlist.Match match = new Matchlist.Match();
            match.setMatchid(matchId);
            ret.getMatch().add(match);
        }

        if (requestId != null) {
            ret.setRequestid(requestId.toString());
        }

        return ret;
    }

    public OutgoingMessage buildAlive() {
        return new Ct();
    }


    public OutgoingMessage buildServerTimeRequest() {
        return new Servertime();
    }


}
