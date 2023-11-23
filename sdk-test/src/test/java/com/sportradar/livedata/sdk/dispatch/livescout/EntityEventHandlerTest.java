package com.sportradar.livedata.sdk.dispatch.livescout;

import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.livescout.entities.*;
import com.sportradar.livedata.sdk.feed.livescout.enums.ScoutFeedType;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Test;


public class EntityEventHandlerTest {

    private final Mockery context = new JUnit5Mockery();
    private final LiveScoutFeedListener listenerMock = context.mock(LiveScoutFeedListener.class);
    private final LiveScoutFeed feed = context.mock(LiveScoutFeed.class);
    private EntityEventHandler handler = new TestEntityEventHandler(
            2,
            2,
            listenerMock,
            feed);


    @Test
    void matchListUpdateEntityIsDispatched() {
        final MatchListUpdateEntity entity = new TestMatchListUpdateEntity(EventIdentifier.id(1L));
        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchListUpdateReceived(with(feed), with(entity));
        }});

        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity)), 1L, false);
        final MatchListUpdateEntity notHandledEntity = new TestMatchListUpdateEntity(EventIdentifier.id(2L));
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1, false);
        context.assertIsSatisfied();
    }

    @Test
    void matchListEntityIsDispatched() {
        final MatchListEntity entity = new TestMatchListEntity(EventIdentifier.id(1L));
        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchListReceived(with(feed), with(entity));
        }});

        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity)), 1L, false);
        final MatchListEntity notHandledEntity = new TestMatchListEntity(EventIdentifier.id(2L));
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1L, false);
        context.assertIsSatisfied();
    }

    @Test
    void matchStopEntityIsDispatched() {
        final MatchStopEntity entity = new TestMatchStopEntity(EventIdentifier.id(1L));
        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchStopped(with(feed), with(entity));
        }});

        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity)), 1L, false);
        final MatchStopEntity notHandledEntity = new TestMatchStopEntity(EventIdentifier.id(2L));
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1L, false);
        context.assertIsSatisfied();
    }

    @Test
    void matchUpdateEntityIsDispatched() {
        final MatchUpdateEntity matchUpdateEntity = new TestMatchUpdateEntity(EventIdentifier.id(1L), null);
        final MatchUpdateEntity fullUpdateEntity = new TestMatchUpdateEntity(EventIdentifier.id(1L), new TestMatchHeaderEntity(ScoutFeedType.FULL));
//        final MatchUpdateEntity partialUpdateEntity = new TestMatchUpdateEntity(EventIdentifier.id(1L), new TestMatchHeaderEntity(ScoutFeedType.PARTIAL));
        final MatchUpdateEntity deltaUpdateEntity = new TestMatchUpdateEntity(EventIdentifier.id(1L), new TestMatchHeaderEntity(ScoutFeedType.DELTA));
        final MatchUpdateEntity deltaUpdateDeltaEntity = new TestMatchUpdateEntity(EventIdentifier.id(1L), new TestMatchHeaderEntity(ScoutFeedType.DELTAUPDATE));

        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchUpdateReceived(with(feed), with(matchUpdateEntity));
            oneOf(listenerMock).onFullMatchUpdateReceived(with(feed), with(fullUpdateEntity));
//            oneOf(listenerMock).onPartialMatchUpdateReceived(with(feed), with(partialUpdateEntity));
            oneOf(listenerMock).onMatchDeltaUpdateReceived(with(feed), with(deltaUpdateEntity));
            oneOf(listenerMock).onMatchDeltaUpdateUpdateReceived(with(feed), with(deltaUpdateDeltaEntity));
        }});

        final MatchUpdateEntity notHandledEntity = new TestMatchUpdateEntity(EventIdentifier.id(2L), null);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(matchUpdateEntity)), 1, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(fullUpdateEntity)), 1, false);
//        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(partialUpdateEntity)), 1, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(deltaUpdateEntity)), 1, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(deltaUpdateDeltaEntity)), 1, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1, false);

        context.assertIsSatisfied();
    }

    @Test
    void matchBookEntityIsDispatched() {
        final MatchBookingEntity entity = new TestMatchBookingEntity(EventIdentifier.id(1L));
        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchBooked(with(feed), with(entity));
        }});

        final MatchBookingEntity notHandledEntity = new TestMatchBookingEntity(EventIdentifier.id(2L));
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity)), 1, true);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1, true);
        context.assertIsSatisfied();
    }

    @Test
    void lineupsEntityIsDispatched() {
        final LineupsEntity entity = new TestLineupsEntity(EventIdentifier.id(1L));
        context.checking(new Expectations() {{
            oneOf(listenerMock).onLineupsReceived(with(feed), with(entity));
        }});

        final LineupsEntity notHandledEntity = new TestLineupsEntity(EventIdentifier.id(2L));
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity)), 1, true);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(notHandledEntity)), 1, true);

        context.assertIsSatisfied();
    }

    @Test
    void entityIsDispatchedExactlyOneTime() {
        EntityEventHandler handler1 = new TestEntityEventHandler(
                1,
                2,
                listenerMock,
                feed);

        final MatchStopEntity entity1 = new TestMatchStopEntity(EventIdentifier.id(1L));
        final MatchStopEntity entity4 = new TestMatchStopEntity(EventIdentifier.id(4L));
        final MatchStopEntity entity5 = new TestMatchStopEntity(EventIdentifier.id(5L));
        final MatchStopEntity entity17 = new TestMatchStopEntity(EventIdentifier.id(17L));

        context.checking(new Expectations() {{
            oneOf(listenerMock).onMatchStopped(with(feed), with(entity1));
            oneOf(listenerMock).onMatchStopped(with(feed), with(entity4));
            oneOf(listenerMock).onMatchStopped(with(feed), with(entity5));
            oneOf(listenerMock).onMatchStopped(with(feed), with(entity17));
        }});

        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity1)), 1, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity4)), 4, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity5)), 5, false);
        handler.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity17)), 17, false);

        handler1.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity1)), 1, false);
        handler1.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity4)), 4, false);
        handler1.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity5)), 5, false);
        handler1.onEvent(markAsValid(new LiveScoutDispatcherContainer(entity17)), 17, false);

        context.assertIsSatisfied();


    }

    class TestEntityEventHandler extends EntityEventHandler {

        TestEntityEventHandler(int id, int dispatcherCount, LiveScoutFeedListener listener, LiveScoutFeed feed) {
            super(id, dispatcherCount, listener, feed);
        }
    }


    class TestMatchListUpdateEntity extends MatchListUpdateEntity {

        private final EventIdentifier eventId;

        TestMatchListUpdateEntity(EventIdentifier eventId) {
            this.eventId = eventId;
        }

        /**
         * Get the unique event id.
         *
         * @return - an event id or null
         */
        @Override
        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    class TestMatchListEntity extends MatchListEntity {

        private final EventIdentifier eventId;

        TestMatchListEntity(EventIdentifier eventId) {
            this.eventId = eventId;
        }

        @Override
        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    class TestMatchStopEntity extends MatchStopEntity {

        private final EventIdentifier eventId;

        TestMatchStopEntity(EventIdentifier eventId) {
            this.eventId = eventId;
        }

        @Override
        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    class TestMatchUpdateEntity extends MatchUpdateEntity {

        private final EventIdentifier eventId;
        private final MatchHeaderEntity matchHeader;

        TestMatchUpdateEntity(EventIdentifier eventId, MatchHeaderEntity matchHeader) {
            super();
            this.eventId = eventId;
            this.matchHeader = matchHeader;
        }

        @Override
        public MatchHeaderEntity getMatchHeader() {
            return this.matchHeader;
        }

        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    class TestMatchHeaderEntity extends MatchHeaderEntity {

        private final ScoutFeedType feedType;

        TestMatchHeaderEntity(ScoutFeedType feedType) {
            this.feedType = feedType;
        }

        @Override
        public ScoutFeedType getFeedType() {
            return feedType;
        }
    }

    class TestMatchBookingEntity extends MatchBookingEntity {

        private final EventIdentifier eventId;

        TestMatchBookingEntity(EventIdentifier eventId) {
            super();
            this.eventId = eventId;
        }

        @Override
        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    class TestLineupsEntity extends LineupsEntity {

        private final EventIdentifier eventId;

        TestLineupsEntity(EventIdentifier eventId) {
            this.eventId = eventId;
        }

        @Override
        public EventIdentifier getEventId() {
            return eventId;
        }
    }

    private static LiveScoutDispatcherContainer markAsValid(LiveScoutDispatcherContainer obj) {
        obj.copy(obj);
        return obj;
    }
}
