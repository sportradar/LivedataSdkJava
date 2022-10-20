package com.sportradar.sdk.feed.livescout.classes;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.feed.common.EntityMapper;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.sdk.feed.livescout.entities.LiveScoutEntityBase;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutEntityFactory;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.incoming.livescout.*;

import javax.inject.Inject;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link EntityMapper} implementation used to map protocol layer scout messages into feed layer scout entities.
 */
public class LiveScoutEntityMapper implements EntityMapper<IncomingMessage, EntityBase> {

    /**
     * A {@link LiveScoutEntityFactory} used to build live scout entities.
     */
    private final LiveScoutEntityFactory factory;

    /**
     * A {@link SdkLogger} implementation used for structured logging.
     */
    private final SdkLogger sdkLogger;

    /**
     * Initializes a new instance of the {@link LiveScoutEntityMapper} class.
     *
     * @param factory A {@link LiveScoutEntityFactory} used to build live scout entities.
     * @throws IllegalArgumentException the {@code factory} is a null reference or
     *                                  {@code sdkLogger} is a null reference
     */
    @Inject
    public LiveScoutEntityMapper(LiveScoutEntityFactory factory,
                                 SdkLogger sdkLogger) {
        checkNotNull(factory, "factory cannot be a null reference");
        checkNotNull(sdkLogger, "sdkLogger cannot be a null reference");
        this.factory = factory;
        this.sdkLogger = sdkLogger;
    }


    /**
     * Maps the passed {@link com.sportradar.sdk.proto.dto.IncomingMessage} derived message to feed entity
     *
     * @param message The {@link com.sportradar.sdk.proto.dto.IncomingMessage} derived message to be mapped to the feed layer entity.
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
        } else if (message instanceof OddsSuggestions) {
            return factory.buildOddsSuggestionsEntity((OddsSuggestions) message);
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

