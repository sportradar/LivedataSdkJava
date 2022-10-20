package com.sportradar.sdk.feed.common;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.feed.common.entities.EntityBase;
import com.sportradar.sdk.feed.common.exceptions.InvalidEntityException;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link ProtocolManager} implementation used to manage protocol which connects to a live-feed
 */
public class LiveFeedProtocolManager implements ProtocolManager<OutgoingMessage, EntityBase> {

    /**
     * A {@link Logger} implementation used for logging.
     */
    private final static Logger logger = LoggerFactory.getLogger(LiveFeedProtocolManager.class);
    /**
     * The {@link SdkLogger} implementation used for structured logging.
     */
    protected SdkLogger sdklogger;
    /**
     * The {@link EntityMapper} implementation used to map received messages to feed entities.
     */
    private final EntityMapper<IncomingMessage, EntityBase> entityMapper;
    /**
     * The {@link MessageProcessor} used to process incoming messages
     */
    private final MessageProcessor<EntityBase> messageProcessor;
    /**
     * The {@link Protocol} implementation used to communicate with the target feed.
     */
    private final Protocol<IncomingMessage, OutgoingMessage> protocol;
    /**
     * The {@link RequestProducer} implementation which produces message to be send to the server.
     */
    private final RequestProducer<OutgoingMessage> requestProducer;
    /**
     * The listener used to observe the current {@link ProtocolManager}
     */
    private volatile ProtocolManagerListener<EntityBase> listener;

    /**
     * Initializes a new instance of the {@link LiveFeedProtocolManager} class
     *
     * @param protocol         The {@link Protocol} implementation used to communicate with the target feed.
     * @param entityMapper     The {@link EntityMapper} implementation used to map received messages to feed entities.
     * @param messageProcessor The {@link MessageProcessor} used to process incoming messages
     * @param requestProducer  The {@link RequestProducer} implementation which produces message to be send to the server.
     * @param sdkLogger        The {@link SdkLogger} used to log messages
     */
    public LiveFeedProtocolManager(
            Protocol<IncomingMessage, OutgoingMessage> protocol,
            EntityMapper<IncomingMessage, EntityBase> entityMapper,
            MessageProcessor<EntityBase> messageProcessor,
            RequestProducer<OutgoingMessage> requestProducer,
            SdkLogger sdkLogger) {
        checkNotNull(protocol, "the protocol cannot be a null reference");
        checkNotNull(entityMapper, "entityMapper cannot be a null reference");
        checkNotNull(sdkLogger, "logger cannot be a null reference");

        this.protocol = protocol;
        this.entityMapper = entityMapper;
        this.messageProcessor = messageProcessor;
        this.requestProducer = requestProducer;
        this.sdklogger = sdkLogger;
        setDependencyListeners();
    }

    /**
     * Closes the underlying protocol
     */
    @Override
    public void close() {
        this.requestProducer.stop();
        this.protocol.stop();
    }

    /**
     * Opens the underlying protocol
     */
    @Override
    public void open() {
        this.protocol.start();
    }

    @Override
    public void reconnect() {
        protocol.reconnect();
    }

    /**
     * Sends the passed string representation of the message to the server.
     *
     * @param data - The {@link String} to be send to the server.
     */
    @Override
    public void sendMessage(String data) {
        checkNotNull(data, "message cannot be a null reference");

        try {
            protocol.sendMessage(data);
        } catch (MessageException | ProtocolException e) {
            logger.error("sendMessage caught exception", e);
        }
    }

    /**
     * Sets the listener used to observe the current {@link ProtocolManager}.
     *
     * @param listener The {@link ProtocolManagerListener} used to observe the {@link ProtocolManager}
     */
    @Override
    public void setListener(ProtocolManagerListener<EntityBase> listener) {
        this.listener = listener;
    }

    /**
     * Notifies the observer that the connection to the server was dropped.
     */
    protected void notifyOnDisconnected() {
        ProtocolManagerListener copy = this.listener;
        if (listener != null) {
            copy.onDisconnected();
        }
    }

    /**
     * Notifies the observer that the protocol has successfully logged in to the server.
     */
    protected final void notifyOnLoggedIn() {
        ProtocolManagerListener copy = this.listener;
        if (copy != null) {
            copy.onLoggedIn();
        }
    }

    /**
     * Notifies the observer that new message was received.
     *
     * @param message The {@link EntityBase} derived instance representing the received message.
     */
    protected final void notifyOnMessageReceived(EntityBase message) {
        ProtocolManagerListener<EntityBase> copy = this.listener;
        if (copy != null) {
            try {
                copy.onMessageReceived(message);
            } catch (Exception e) {
                logger.error("Notify message received caught exception", e);
            }
        }
    }

    /**
     * Processes the received message
     *
     * @param message The {@link IncomingMessage} representing the received message.
     */
    protected void processMessage(IncomingMessage message) {
        checkNotNull(message, "message cannot be a null reference");

        // map the received message to feed entity.
        EntityBase entity = null;

        try {
            entity = entityMapper.map(message);
        } catch (InvalidEntityException e) {
            sdklogger.logAlert(Level.ERROR, "processMessage", e);
            logger.error("Invalid entity in entity mapper", e);
            listener.onFeedEvent(FeedEventType.PARSE_ERROR);
            return;
        } catch (Exception e) {
            logger.error("Unhandled exception in entity mapper", e);
            listener.onFeedEvent(FeedEventType.PARSE_ERROR);
        }

        if (entity == null) {
            return;
        }

        try {
            // pass the message through the pipeline
            if (messageProcessor != null) {
                messageProcessor.processMessage(entity);
            } else {
                notifyOnMessageReceived(entity);//TODO AK there is always messageProcessor
            }
        } catch (Exception e) {
            logger.error("Unhandled exception in processor pipeline", e);
        }
    }

    /**
     * Sends the passed {@link OutgoingMessage} instance to the server.
     *
     * @param message the {@link OutgoingMessage} instance to be send to the server.
     * @throws IllegalArgumentException the {@code message} is a null reference.
     * @throws IllegalStateException    the message cannot be send at this time.
     */
    protected boolean sendMessage(OutgoingMessage message, boolean blocking) {
        checkNotNull(message, "message cannot be a null reference");
        try {
            return protocol.sendMessage(message, blocking);
        } catch (MessageException | ProtocolException e) {
            logger.error("sendMessage caught exception", e);
        }
        return false;
    }

    /**
     * Sets the listener to the injected dependencies.
     */
    private void setDependencyListeners() {
        protocol.setListener(new ProtocolListener<IncomingMessage>() {
            @Override
            public void onLoggedIn() {
                requestProducer.start();
                notifyOnLoggedIn();
            }

            @Override
            public void onDisconnected() {
                requestProducer.stop();
                notifyOnDisconnected();
            }

            @Override
            public void onMessageReceived(IncomingMessage message) {
                processMessage(message);
            }

            @Override
            public void onLinkEvent(FeedEventType event, Object data) {
                if (event == FeedEventType.DISCONNECTED) {
                    if (protocol.isStarted()) {
                        onDisconnected();
                    }
                }
                listener.onFeedEvent(event);
            }
        });

        if (requestProducer != null) {
            requestProducer.setListener(new RequestProducerListener<OutgoingMessage>() {
                @Override
                public boolean onRequestReady(OutgoingMessage request, boolean blocking) {
                    return sendMessage(request, blocking);
                }
            });
        }

        if (messageProcessor != null) {
            messageProcessor.setListener(new MessageProcessorListener<EntityBase>() {
                @Override
                public void onMessageProcessed(EntityBase message, MessageProcessor<EntityBase> processor) {
                    notifyOnMessageReceived(message);
                }
            });
        }
    }
}
