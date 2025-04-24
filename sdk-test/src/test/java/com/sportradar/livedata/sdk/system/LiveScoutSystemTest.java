package com.sportradar.livedata.sdk.system;

import com.sportradar.livedata.sdk.common.enums.FeedEventType;
import com.sportradar.livedata.sdk.common.settings.AuthSettings;
import com.sportradar.livedata.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.livedata.sdk.proto.dto.OutgoingMessage;
import com.sportradar.livedata.sdk.system.framework.actions.LiveScoutEntityDispatchAction;
import com.sportradar.livedata.sdk.system.framework.actions.LoginAction;
import com.sportradar.livedata.sdk.system.framework.common.SdkDataListener;
import com.sportradar.livedata.sdk.system.framework.livescout.LiveScoutServerMock;
import com.sportradar.livedata.sdk.system.framework.livescout.LiveScoutSystemTestFramework;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Disabled
class LiveScoutSystemTest {

    private Mockery context;
    private List<LiveScoutEntityBase> dispatchedEntities;
    private LiveScoutFeed feed;
    private States loginState;
    private LiveScoutDispatcher mockDispatcher;
    private LiveScoutFeedListener mockListener;
    private States allMsgs;
    private List<OutgoingMessage> requests;
    private SdkDataListener sdkDataListener;//test
    private Synchroniser synchronizer;
    private LiveScoutSystemTestFramework testFramework;//test
    private LiveScoutEntityDispatchAction dispatchAction;//test


    @BeforeEach
    public void setUp() throws Exception {
        synchronizer = new Synchroniser();
        context = new JUnit5Mockery() {{
            setThreadingPolicy(synchronizer);
        }};
        loginState = context.states("loginState");
        allMsgs = context.states("allMsgs");
        mockDispatcher = context.mock(LiveScoutDispatcher.class);
        mockListener = context.mock(LiveScoutFeedListener.class);
        dispatchAction = new LiveScoutEntityDispatchAction(allMsgs);
        dispatchedEntities = dispatchAction.getEntities();
        requests = new ArrayList<>();
        context.checking(new Expectations() {{//order is important
            oneOf(mockDispatcher).dispatchOnFeedEvent(with(any(LiveScoutFeed.class)), with(equal(FeedEventType.CONNECTED)));
            //runs inside Dispatcher realisation, as it was mocked, listener was not called
            oneOf(mockListener).onFeedEvent(with(any(LiveScoutFeed.class)), with(equal(FeedEventType.CONNECTED)));
            oneOf(mockDispatcher).setFeed(with(any(LiveScoutFeed.class)));
            oneOf(mockDispatcher).start(mockListener);
            //should come as login event from server
            oneOf(mockListener).onOpened(with(any(LiveScoutFeed.class)));
            //he action is triggered by the mockListener.onOpened method is called, LoginAction is executed.
            will(doAll(new LoginAction(loginState)));
            allowing(mockDispatcher).dispatchEntity((with(any(LiveScoutEntityBase.class))));
            will(doAll(dispatchAction));
        }});
        sdkDataListener = entity -> requests.add(entity);
        testFramework = new LiveScoutSystemTestFramework(
                mockDispatcher,
                sdkDataListener);
        testFramework.server().validBookmakers().put("1000", "password");
        AuthSettings authSettings = new AuthSettings("1000", "password",
                null, null, null, null, null);
        testFramework.settings().authSettings(authSettings);
        //send alive message to server every 2 sec
        testFramework.settings().clientAliveMsgTimeout(Duration.standardSeconds(2));
        //receive alive message from server every 1 sec
        testFramework.settings().serverAliveMsgTimeout(Duration.standardSeconds(1));

        testFramework.server().setReciveAliveDelay(1500);
        testFramework.server().setSendAliveDelay(1000);

        feed = testFramework.createFeed();
        feed.open(mockListener);//shoots connected message
        testFramework.gateway().notifyConnected();//normal listener is null, so event does not shoot
        synchronizer.waitUntil(loginState.is("OK"), 1000);
    }


    @AfterEach
    public void TearDown() {
        context.assertIsSatisfied();
    }


    @Test
    void handle_Full_Match_onInitialized() throws Exception {
        context.checking(new Expectations() {{
            oneOf(mockListener).onInitialized(with(any(LiveScoutFeed.class)));
        }});
        dispatchAction.setExpectedNumberOfMessages(1);
        testFramework.server().sendFullMatch();

        synchronizer.waitUntil(allMsgs.is("OK"), 500);
        Thread.sleep(100); //if any more msgs should arrive
        assertThat(dispatchedEntities.size(), is(1));
        assertThat(dispatchedEntities.get(0), is(instanceOf(MatchUpdateEntity.class)));
    }


    @Test
    void handle_Logout_If_Ct_Not_Recived() throws Exception {

        assertThat(testFramework.server().isLoggedin(), is(true));
        //wait until server automatic log out user
        Thread.sleep(testFramework.server().getReciveAliveDelay() + 500);
        assertThat(testFramework.server().isLoggedin(), is(false));
    }


    @Test
    void handle_Invalid_Message_Logging() throws Exception {
        context.checking(new Expectations() {{
            oneOf(mockListener).onInitialized(with(any(LiveScoutFeed.class)));
        }});
        testFramework.server().sendFullMatch();
        LiveScoutServerMock server = testFramework.server();
        server.sendAlive();
        server.sendCustomMessage("Some not valid msg!".getBytes());
        server.sendAlive();
        dispatchedEntities.size();
    }


}
