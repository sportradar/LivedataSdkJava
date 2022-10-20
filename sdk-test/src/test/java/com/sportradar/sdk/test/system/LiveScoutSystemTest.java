package com.sportradar.sdk.test.system;

import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcher;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.test.system.framework.actions.LiveScoutEntityDispatchAction;
import com.sportradar.sdk.test.system.framework.actions.LoginAction;
import com.sportradar.sdk.test.system.framework.common.SdkDataListener;
import com.sportradar.sdk.test.system.framework.livescout.LiveScoutServerMock;
import com.sportradar.sdk.test.system.framework.livescout.LiveScoutSystemTestFramework;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Ignore
public class LiveScoutSystemTest {

    private Mockery context;
    private List<LiveScoutEntityBase> dispatchedEntities;
    private LiveScoutFeed feed;
    private States loginState;
    private LiveScoutDispatcher mockDispatcher;
    private LiveScoutFeedListener mockListener;
    private States allMsgs;
    private List<OutgoingMessage> requests;
    private SdkDataListener sdkDataListener;
    private Synchroniser synchronizer;
    private LiveScoutSystemTestFramework testFramework;
    private LiveScoutEntityDispatchAction dispatchAction;


    @Before
    public void setUp() throws Exception {
        synchronizer = new Synchroniser();
        context = new JUnit4Mockery() {{
            setThreadingPolicy(synchronizer);
        }};
        loginState = context.states("loginState");
        allMsgs = context.states("allMsgs");
        mockDispatcher = context.mock(LiveScoutDispatcher.class);
        mockListener = context.mock(LiveScoutFeedListener.class);
        dispatchAction = new LiveScoutEntityDispatchAction(allMsgs);
        dispatchedEntities = dispatchAction.getEntities();
        requests = new ArrayList<>();
        context.checking(new Expectations() {{
            oneOf(mockListener).onFeedEvent(with(any(LiveScoutFeed.class)), with(equal(FeedEventType.CONNECTED)));
            oneOf(mockDispatcher).setFeed(with(any(LiveScoutFeed.class)));
            oneOf(mockDispatcher).start(mockListener);
            oneOf(mockListener).onOpened(with(any(LiveScoutFeed.class)));
            will(doAll(new LoginAction(loginState)));
            allowing(mockDispatcher).dispatchEntity((with(any(LiveScoutEntityBase.class))));
            will(doAll(dispatchAction));
        }});
        sdkDataListener = new

                SdkDataListener() {
                    @Override
                    public void sdkEntitySend(OutgoingMessage entity) {
                        requests.add(entity);
                    }
                };
        testFramework = new LiveScoutSystemTestFramework(
                mockDispatcher,
                sdkDataListener);
        testFramework.server().validBookmakers().put("1000", "password");
        testFramework.settings().setUsername("1000");
        testFramework.settings().setPassword("password");
        //send alive message to server every 2 sec
        testFramework.settings().setClientAliveMsgTimeout(Duration.standardSeconds(2));
        //receive alive message from server every 1 sec
        testFramework.settings().setServerAliveMsgTimeout(Duration.standardSeconds(1));

        testFramework.server().setReciveAliveDelay(1500);
        testFramework.server().setSendAliveDelay(1000);

        feed = testFramework.createFeed();
        feed.open(mockListener);
        testFramework.gateway().notifyConnected();
        synchronizer.waitUntil(loginState.is("OK"), 1000);
    }


    @After
    public void TearDown() {
        context.assertIsSatisfied();
    }


    @Test
    public void handle_Full_Match_onInitialized() throws Exception {
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
    public void handle_Logout_If_Ct_Not_Recived() throws Exception {

        assertThat(testFramework.server().isLoggedin(), is(true));
        //wait until server automatic log out user
        Thread.sleep(testFramework.server().getReciveAliveDelay() + 500);
        assertThat(testFramework.server().isLoggedin(), is(false));
    }


    @Test
    public void handle_Invalid_Message_Logging() throws Exception {
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
