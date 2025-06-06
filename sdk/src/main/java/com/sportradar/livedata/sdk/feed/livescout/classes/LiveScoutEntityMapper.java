package com.sportradar.livedata.sdk.feed.livescout.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.livedata.sdk.common.classes.SdkLoggerProvider;
import com.sportradar.livedata.sdk.common.interfaces.SdkLogger;
import com.sportradar.livedata.sdk.feed.common.EntityMapper;
import com.sportradar.livedata.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.livedata.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.livedata.sdk.proto.dto.IncomingMessage;
import com.sportradar.livedata.sdk.proto.dto.incoming.livescout.*;

import jakarta.inject.Inject;

import static com.sportradar.livedata.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link EntityMapper} implementation used to map protocol layer scout messages into feed layer scout entities.
 */
public class LiveScoutEntityMapper implements EntityMapper<IncomingMessage, LiveScoutEntityBase> {

    /**
     * A {@link LiveScoutEntityFactory} used to build live scout entities.
     */
    private final LiveScoutEntityFactory factory;

    /**
     * A {@link SdkLogger} implementation used for structured logging.
     */
    private final SdkLogger sdkLogger = SdkLoggerProvider.get();

    /**
     * Initializes a new instance of the {@link LiveScoutEntityMapper} class.
     *
     * @param factory   A {@link LiveScoutEntityFactory} used to build live scout entities.
     * @throws IllegalArgumentException the {@code factory} is a null reference or
     *                                  {@code sdkLogger} is a null reference
     */
    @Inject
    public LiveScoutEntityMapper(LiveScoutEntityFactory factory) {
        checkNotNull(factory, "factory cannot be a null reference");
        this.factory = factory;
    }


    /**
     * Maps the passed {@link com.sportradar.livedata.sdk.proto.dto.IncomingMessage} derived message to feed entity
     *
     * @param message The {@link com.sportradar.livedata.sdk.proto.dto.IncomingMessage} derived message to be mapped to the feed layer entity.
     * @return The {@link LiveScoutEntityBase} derived class representing the passed message.
     * @throws IllegalArgumentException the {@code message} is a null reference
     */
    @Override
    public LiveScoutEntityBase map(IncomingMessage message) throws InvalidEntityException {
        checkNotNull(message, "message cannot be a null reference");
        if (message instanceof Bookmatch) {
            return factory.buildMatchBookingEntity((Bookmatch) message);
        } else if (message instanceof Matchstop) {
            return factory.buildMatchStopEntity((Matchstop) message);
        } else if (message instanceof Matchlist) {
            return factory.buildMatchListEntity((Matchlist) message);
        } else if (message instanceof Matchlistupdate) {
            return factory.buildMatchListUpdateEntity((Matchlistupdate) message);
        } else if (message instanceof Match) {
            return factory.buildMatchUpdateEntity((Match) message);
        } else if (message instanceof Lineups) {
            return factory.buildLineupsEntity((Lineups) message);
        } else if (message instanceof Matchdata) {
            return factory.buildMatchDataEntity((Matchdata) message);
        } else if (message instanceof Servertime) {
            return factory.buildServerTimeEntity((Servertime) message);
        } else if (message instanceof Ct || message instanceof Infos) {
            // Ignored
            return null;
        } else {
            sdkLogger.logAlert(Level.WARN, "Unknown entity " + message.getClass().getSimpleName());
            return null;
        }
    }
}

