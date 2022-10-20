package com.sportradar.sdk.test.system.framework.livescout;


import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.dto.outgoing.livescout.*;
import com.sportradar.sdk.test.conn.FakeGateway;
import com.sportradar.sdk.test.system.framework.common.SystemTestMessageParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

public class LiveScoutServerMock {

    protected FakeGateway gateway;
    protected boolean handleRequests;
    protected Map<Long, LiveScoutMatch> matches;
    protected SystemTestMessageParser parser;
    protected Map<String, String> validBookmakers;
    protected boolean loggedin = false;

    protected final Timer sendAliveTimer;
    protected long sendAliveDelay = 1000;

    protected Timer reciveAliveTimer;

    public void setReciveAliveDelay(long reciveAliveDelay) {
        this.reciveAliveDelay = reciveAliveDelay;
    }

    public void setSendAliveDelay(long sendAliveDelay) {
        this.sendAliveDelay = sendAliveDelay;
    }

    public long getReciveAliveDelay() {

        return reciveAliveDelay;
    }

    public long getSendAliveDelay() {
        return sendAliveDelay;
    }

    protected long reciveAliveDelay = 1500;


    public LiveScoutServerMock(FakeGateway gateway,
                               SystemTestMessageParser parser) {
        checkNotNull(gateway);
        checkNotNull(parser);
        handleRequests = true;
        this.gateway = gateway;
        this.parser = parser;
        this.matches = new HashMap<>();
        this.validBookmakers = new HashMap<>();

        sendAliveTimer = new Timer();
        reciveAliveTimer = new Timer();
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    private class SendAlive extends TimerTask {

        @Override
        public void run() {
            try {
                sendAlive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LogoutNotRecivingAlive extends TimerTask {

        @Override
        public void run() {
            try {
                handleLogout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleBookmakerStatus(final OutgoingMessage request) {
        if (handleRequests) {
            Runnable task = new Runnable() {
                public void run() {
                    try {
                        handleRequests(request);
                    } catch (Exception e) {
                        //TODO fail
                    }
                }
            };
            new Thread(task).start();
        }
    }

    protected void handleRequests(OutgoingMessage request) throws Exception {

        if (handleRequests) {

            if (request instanceof Bookmatch) {//match booking
                handleBookmatch((Bookmatch) request);
            } else if (request instanceof Ct) {//alive
                handleCt();
            } else if (request instanceof Login) {//login
                handleLogin((Login) request);
            } else if (request instanceof Logout) {//logout
                handleLogout();
            } else if (request instanceof Match) {//match subscription
                handleMatch((Match) request);
            } else if (request instanceof Matchlist) {//match list
                handleMatchList((Matchlist) request);
            } else if (request instanceof Matchstop) {//matchstop
                handleMatchStop((Matchstop) request);
            } else if (request instanceof Servertime) {// Server time request.
                handleServertime();
            }
        }
    }

    protected void handleBookmatch(Bookmatch request) throws Exception {
        sendData(LiveScoutServerResponseFactory.buildBookmatch(request.getMatchid()));
    }

    protected void handleCt() throws Exception {
        //reset time first then send alive
        reciveAliveTimer.cancel();
        reciveAliveTimer.scheduleAtFixedRate(new LogoutNotRecivingAlive(), reciveAliveDelay, reciveAliveDelay);
        sendAlive();
    }

    protected void handleLogin(Login request) throws Exception {
        Credential credential = request.getCredential();
        if (credential != null) {
            Loginname loginname = credential.getLoginname();
            Password password = credential.getPassword();
            if (loginname != null && password != null) {
                if (validBookmakers().containsKey(loginname.getValue())) {
                    if (validBookmakers().get(loginname.getValue()).equals(password.getValue())) {
                        loggedin = true;
                        try {
                            sendAliveTimer.scheduleAtFixedRate(new SendAlive(), sendAliveDelay, sendAliveDelay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            reciveAliveTimer.scheduleAtFixedRate(new LogoutNotRecivingAlive(), reciveAliveDelay, reciveAliveDelay);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        sendData(LiveScoutServerResponseFactory.buildLogin(12345, loggedin));
    }

    protected void handleLogout() {
        loggedin = false;
        sendAliveTimer.cancel();
    }

    protected void handleMatch(Match request) throws Exception {
        String feedType = request.getFeedtype();

        if (feedType == null) {
            sendData(LiveScoutServerResponseFactory.buildMatch(request.getMatchid(), null));
        } else {
            switch (feedType) {
                case "full":
                    sendData(LiveScoutServerResponseFactory.buildMatch(request.getMatchid(), "full"));
                    break;
                case "delta":
                case "deltaupdate":
                    sendData(LiveScoutServerResponseFactory.buildMatchUpdate(request.getMatchid()));
                    break;
                default:
                    System.err.println("Unknown type of the ScoutFeedType encountered. type: " + feedType);
                    //logger.warn("Unknown type of the ScoutFeedType encountered. type: {}", feedType);

            }
        }
    }

    protected void handleMatchList(Matchlist request) throws Exception {
        sendData(LiveScoutServerResponseFactory.buildMatchList());
    }

    protected void handleMatchStop(Matchstop request) throws Exception {
        sendData(LiveScoutServerResponseFactory.buildMatchStop(request.getMatchid()));
    }

    protected void handleServertime() {
        com.sportradar.sdk.proto.dto.incoming.livescout.Servertime servertime = LiveScoutServerResponseFactory.buildServertime();
    }


    public boolean handleRequests() {
        return handleRequests;
    }

    public boolean isMatchBookedAndSubscribed(long matchId) {
        if (matches.containsKey(matchId)) {
            LiveScoutMatch match = matches.get(matchId);
            if (match.isBooked() && match.isSubscribed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Current matches and their msgnr that should be in alive
     *
     * @return curent matches
     */
    public Map<Long, LiveScoutMatch> matches() {
        return matches;
    }

    public void sendAlive() throws Exception {
        if (loggedin) {
            sendData(LiveScoutServerResponseFactory.buildCt());
        } else {
            System.out.println("LoginFirst");
        }
    }

    public void sendCustomMessage(byte[] bytes) throws Exception {
        gateway.notifyDataReceived(bytes);
    }

    public void sendFullMatch() throws Exception {
        sendData(LiveScoutServerResponseFactory.buildMatch(233545, "full"));
    }

    protected synchronized void sendData(IncomingMessage data) throws Exception {
        gateway.notifyDataReceived(parser.toByteArray(data));
    }

    public void setHandleRequests(boolean handleRequests) {
        this.handleRequests = handleRequests;
    }

    public Map<String, String> validBookmakers() {
        return validBookmakers;
    }

}
