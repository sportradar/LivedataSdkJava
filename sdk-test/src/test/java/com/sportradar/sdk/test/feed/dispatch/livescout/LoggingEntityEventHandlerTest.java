package com.sportradar.sdk.test.feed.dispatch.livescout;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.dispatch.livescout.EntityEventHandler;
import com.sportradar.sdk.dispatch.livescout.LiveScoutDispatcherContainer;
import com.sportradar.sdk.dispatch.livescout.LoggingEntityEventHandler;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.entities.*;
import com.sportradar.sdk.feed.livescout.enums.BookMatchResult;
import com.sportradar.sdk.feed.livescout.enums.ScoutFeedType;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.test.feed.dispatch.CommonTestUtils;
import com.sportradar.sdk.test.feed.dispatch.LoggingEntityEventHandlerBase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggingEntityEventHandlerTest extends LoggingEntityEventHandlerBase {

    private static long eventId = 1;
    private static boolean isEndOfBatch = false;
    private static long sequenceNumber = 123;
    private static long virtualGameId = 1;
    private final int id = 1;
    private final int dispatcherCount = 1;
    private final Mockery context = new JUnit4Mockery();
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private EntityEventHandler entityEventHandler;
    private LiveScoutFeedListener feedListenerMock = context.mock(LiveScoutFeedListener.class);
    private LiveScoutFeed feedMock = context.mock(LiveScoutFeed.class);

    public LoggingEntityEventHandlerTest() {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        entityEventHandler = new LoggingEntityEventHandler(
                id,
                dispatcherCount,
                feedListenerMock,
                feedMock,
                sdkLogger);

        context.checking(new Expectations() {{
            ignoring(feedListenerMock);
        }});
    }


    @Test
    public void testOnEvent_DispatchMatchList_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchListEntity entity = EntityMock.createFake(virtualGameId, eventId, sequenceNumber, MatchListEntity.class);
        List<MatchUpdateEntity> matchUpdateEntities = new ArrayList<>();
        MatchUpdateEntity matchUpdateEntity = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(0L), null);
        matchUpdateEntities.add(matchUpdateEntity);
        CommonTestUtils.setFieldValue(MatchListEntity.class, "matches", entity, matchUpdateEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchListReceived : %s", entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    private static LiveScoutDispatcherContainer markAsValid(LiveScoutDispatcherContainer obj) {
        obj.copy(obj);
        return obj;
    }

    @Test
    public void testOnEvent_DispatchMatchList_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchListEntity entity = EntityMock.createFake(virtualGameId, eventId, sequenceNumber, MatchListEntity.class);
        List<MatchUpdateEntity> matchUpdateEntities = new ArrayList<>();
        MatchUpdateEntity matchUpdateEntity = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(0L), null);
        matchUpdateEntities.add(matchUpdateEntity);
        CommonTestUtils.setFieldValue(MatchListEntity.class, "matches", entity, matchUpdateEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchListReceived #%s", matchUpdateEntities.size());
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_DispatchMatchList_InfoLevel_EmptyMatchList() throws Exception {
        logger.setLevel(Level.INFO);
        MatchListEntity entity = EntityMock.<MatchListEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchListEntity.class);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = "[Elapsed 0] Discarding MatchList client event notify: empty match list";
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_LineupsEntity_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        LineupsEntity entity = EntityMock.<LineupsEntity>createFake(virtualGameId, eventId, sequenceNumber, LineupsEntity.class);
        List<PlayerEntity> players = new ArrayList<>();
        PlayerEntity playerEntity = new PlayerEntity();
        players.add(playerEntity);
        CommonTestUtils.setFieldValue(LineupsEntity.class, "players", entity, players);
        CommonTestUtils.setFieldValue(LineupsEntity.class, "matchId", entity, 1);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] Lineups match id : %s", 1);
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchBookingEntity_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchBookingEntity entity = EntityMock.<MatchBookingEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchBookingEntity.class);
        BookMatchResult result = BookMatchResult.INVALID;
        String entityMessage = "match booking message";
        CommonTestUtils.setFieldValue(MatchBookingEntity.class, "result", entity, result);
        CommonTestUtils.setFieldValue(MatchBookingEntity.class, "message", entity, entityMessage);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] OnMatchBookingReply : %s", entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_MatchBookingEntity_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchBookingEntity entity = EntityMock.<MatchBookingEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchBookingEntity.class);
        BookMatchResult result = BookMatchResult.VALID;
        String entityMessage = "match booking message";
        CommonTestUtils.setFieldValue(MatchBookingEntity.class, "result", entity, result);
        CommonTestUtils.setFieldValue(MatchBookingEntity.class, "message", entity, entityMessage);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] OnMatchBookingReply %s %s", result, entityMessage);
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchListUpdateEntity_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchListUpdateEntity entity = EntityMock.<MatchListUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchListUpdateEntity.class);
        List<MatchUpdateEntity> matchUpdateEntities = new ArrayList<>();
        MatchUpdateEntity matchUpdateEntity = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(0L), null);
        matchUpdateEntities.add(matchUpdateEntity);
        CommonTestUtils.setFieldValue(MatchListUpdateEntity.class, "matches", entity, matchUpdateEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchListUpdateReceived : %s", entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_MatchListUpdateEntity_DebugLevel_EmptyMatchOdds() throws Exception {
        logger.setLevel(Level.INFO);
        MatchListUpdateEntity entity = EntityMock.<MatchListUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchListUpdateEntity.class);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = "[Elapsed 0] Discarding MatchListUpdate client event notify: empty match list";
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchListUpdateEntity_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchListUpdateEntity entity = EntityMock.<MatchListUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchListUpdateEntity.class);
        List<MatchUpdateEntity> matchUpdateEntities = new ArrayList<>();
        MatchUpdateEntity matchUpdateEntity = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(0L), null);
        matchUpdateEntities.add(matchUpdateEntity);
        CommonTestUtils.setFieldValue(MatchListUpdateEntity.class, "matches", entity, matchUpdateEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchListUpdateReceived #%s", matchUpdateEntities.size());
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchStopEntity_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchStopEntity entity = EntityMock.<MatchStopEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchStopEntity.class);
        long matchId = 123;
        String reason = "stopped reason";
        CommonTestUtils.setFieldValue(MatchStopEntity.class, "matchId", entity, matchId);
        CommonTestUtils.setFieldValue(MatchStopEntity.class, "reason", entity, reason);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchStopped %s %s", matchId, reason);
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchStopEntity_InfoLevel_EmptyValues() throws Exception {
        logger.setLevel(Level.INFO);
        MatchStopEntity entity = EntityMock.<MatchStopEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchStopEntity.class);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = "[Elapsed 0] onMatchStopped 0 null";
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeDeltaUpdate_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchUpdateEntity entity = EntityMock.<MatchUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.DELTAUPDATE;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchDeltaUpdateUpdateReceived [%s] : %s", entity.getMatchHeader().getMatchId(), entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeDeltaUpdate_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchUpdateEntity entity = EntityMock.createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.DELTAUPDATE;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchDeltaUpdateUpdateReceived %s %s", entity.getMatchHeader().getMatchId(), entity.getMatchStatus());
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeDelta_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchUpdateEntity entity = EntityMock.<MatchUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.DELTA;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchDeltaUpdateReceived [%s] : %s", entity.getMatchHeader().getMatchId(), entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeDelta_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchUpdateEntity entity = EntityMock.<MatchUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.DELTA;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onMatchDeltaUpdateReceived %s %s", entity.getMatchHeader().getMatchId(), entity.getMatchStatus());
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeFull_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        MatchUpdateEntity entity = EntityMock.<MatchUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.FULL;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onFullMatchUpdateReceived [%s] : %s", matchId, entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_MatchUpdateEntity_FeedTypeFull_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        MatchUpdateEntity entity = EntityMock.<MatchUpdateEntity>createFake(virtualGameId, eventId, sequenceNumber, MatchUpdateEntity.class);
        long matchId = 123;
        MatchHeaderEntity matchHeader = ScoutFakeBetStopFactory.generateBetStop(EventIdentifier.id(matchId), null).getMatchHeader();
        ScoutFeedType feedType = ScoutFeedType.FULL;
        CommonTestUtils.setFieldValue(MatchHeaderEntity.class, "feedType", matchHeader, feedType);
        CommonTestUtils.setFieldValue(MatchUpdateEntity.class, "matchHeader", entity, matchHeader);
        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] onFullMatchUpdateReceived %s %s", matchId, entity.getMatchStatus());
        assertEqualMessage(Level.INFO, message);
    }

    @Test
    public void testOnEvent_OddsSuggestionsEntity_DebugLevel() throws Exception {
        logger.setLevel(Level.DEBUG);
        OddsSuggestionsEntity entity = EntityMock.createFake(virtualGameId, eventId, sequenceNumber, OddsSuggestionsEntity.class);
        List<ScoutOddsEntity> scoutOddsEntities = new ArrayList<>();
        ScoutOddsEntity scoutOddsEntity = new ScoutOddsEntity();
        scoutOddsEntities.add(scoutOddsEntity);
        CommonTestUtils.setFieldValue(OddsSuggestionsEntity.class, "odds", entity, scoutOddsEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] OnOddsSuggestion [%s] : %s", entity.getMatchId(), entity);
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_OddsSuggestionsEntity_DebugLevel_EmptyMatchOdds() throws Exception {
        logger.setLevel(Level.DEBUG);
        OddsSuggestionsEntity entity = EntityMock.<OddsSuggestionsEntity>createFake(virtualGameId, eventId, sequenceNumber, OddsSuggestionsEntity.class);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] Discarding OddsSuggestions client event notify: empty odds list");
        assertEqualMessage(Level.DEBUG, message);
    }

    @Test
    public void testOnEvent_OddsSuggestionsEntity_InfoLevel() throws Exception {
        logger.setLevel(Level.INFO);
        OddsSuggestionsEntity entity = EntityMock.<OddsSuggestionsEntity>createFake(virtualGameId, eventId, sequenceNumber, OddsSuggestionsEntity.class);
        List<ScoutOddsEntity> scoutOddsEntities = new ArrayList<>();
        ScoutOddsEntity scoutOddsEntity = new ScoutOddsEntity();
        scoutOddsEntities.add(scoutOddsEntity);
        CommonTestUtils.setFieldValue(OddsSuggestionsEntity.class, "odds", entity, scoutOddsEntities);

        LiveScoutDispatcherContainer entityContainer = new LiveScoutDispatcherContainer(entity);
        entityEventHandler.onEvent(markAsValid(entityContainer), sequenceNumber, isEndOfBatch);
        String message = String.format("[Elapsed 0] OnOddsSuggestion %s #%s", entity.getMatchId(), scoutOddsEntities.size());
        assertEqualMessage(Level.INFO, message);
    }

    static class EntityMock<T> {

        public static <T> T createFake(
                final Long virtualGameId,
                final Long eventId,
                final long sequenceNumber,
                Class<T> clazz) throws Exception {
            Constructor<T> ctor = clazz.getDeclaredConstructor(Map.class);
            ctor.setAccessible(true);
            T ret = ctor.newInstance(new HashMap<String, String>());
            CommonTestUtils.setFieldValue(clazz, "timeOffset", ret, 0L);
            CommonTestUtils.setFieldValue(clazz, "timeStamp", ret, TimeProvider.getCurrent().getCurrentTime());
            CommonTestUtils.setFieldValue(clazz, "sequenceNumber", ret, sequenceNumber);

            if (virtualGameId != null) {
                CommonTestUtils.setFieldValue(clazz, "virtualGameId", ret, virtualGameId);
            }
            if (eventId != null) {
                CommonTestUtils.setFieldValue(clazz, "eventId", ret, eventId);
            }
            return ret;
        }
    }
}
