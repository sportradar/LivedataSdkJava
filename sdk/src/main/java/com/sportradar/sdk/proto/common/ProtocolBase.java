/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.proto.common;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.enums.FeedEventType;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.exceptions.SdkFatalException;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.timer.TimeProvider;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;


/**
 * Base class for all {@link Protocol} implementations
 */
public abstract class ProtocolBase<I extends IncomingMessage, O extends OutgoingMessage> implements Protocol<I, O> {

    /**
     * The {@link Logger} used for event logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProtocolBase.class);

    /**
     * The {@link Gateway} implementation used to establish a connection to the server.
     */
    protected final Gateway gateway;

    /**
     * The {@link MessageParser} used to parse data received from the server.
     */
    protected final MessageParser<I> messageParser;
    /**
     * Sdk logging facility.
     */
    protected final SdkLogger sdkLogger;
    /**
     * The value specifying whether the protocol is started - i.e. is communication with the server enabled.
     */
    protected volatile boolean isStarted;
    /**
     * The listener used to observe the current {@link ProtocolBase}
     */
    protected volatile ProtocolListener<I> listener;

    /**
     * Initializes a new instance of the {@link ProtocolBase} class.
     *
     * @param sdkLogger     The {@link SdkLogger} implementation used for structured logging.
     * @param gateway       The {@link Gateway} implementation used to establish a connection to the server.
     * @param messageParser The {@link MessageParser} used to parse data received from the server.
     * @throws IllegalArgumentException The {@code sdkLogger} is a null reference or {@code gateway} is a null reference or {@code messageParser} is a null reference.
     */
    public ProtocolBase(
            final SdkLogger sdkLogger,
            final Gateway gateway,
            final MessageParser<I> messageParser) {
        checkNotNull(sdkLogger, "The sdkLogger cannot be a null reference");
        checkNotNull(gateway, "The gateway cannot be a null reference");
        checkNotNull(messageParser, "The messageParser cannot be a null reference");


        this.sdkLogger = sdkLogger;
        this.messageParser = messageParser;
        this.gateway = gateway;

        setDependencyListeners();
    }

    /**
     * Starts the protocol - i.e. enables(starts) the communication with the server.
     */
    @Override
    public void start() {
        isStarted = true;
        connect();
    }

    @Override
    public void reconnect() {
        stop();
        start();
    }

    /**
     * Gets the value indicating whether the protocol is started - i.e if the communication with server is enabled.
     *
     * @return True if protocol is started. False otherwise.
     */
    @Override
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Sets the {@link ProtocolListener} used to observe the current {@link Protocol}.
     *
     * @param listener the {@link ProtocolListener} used to observe the current {@link Protocol} or a null reference.
     */
    @Override
    public void setListener(ProtocolListener<I> listener) {
        this.listener = listener;
    }

    /**
     * Stops the protocol - i.e. disables the communication with the server.
     */
    @Override
    public void stop() {
        isStarted = false;
        gateway.disconnect(false);
    }

    /**
     * Sets the listeners on the injected dependencies
     */
    protected void setDependencyListeners() {
        this.gateway.setListener(new GatewayListener() {
            @Override
            public void onDataReceived(InputStream stream) {
                onGatewayDataReceived(stream);
            }

            @Override
            public void onConnected() {
                messageParser.clear();
                onGatewayConnected();
            }

            @Override
            public void onDisconnected(Exception ex) {
                if (ex == null) {
                    onGatewayDisconnected();
                } else {
                    onGatewayError(ex);
                }
                messageParser.clear();
            }
        });

        this.messageParser.setListener(new MessageParserListener<I>() {
            @Override
            public void onMessageReady(I message) {
                onMessageParsed(message);
            }
        });
    }

    /**
     * Invoked by the observed {@link Gateway} instance when it receives new data from the server.
     *
     * @param stream The {@link InputStream} containing the received data.
     */
    protected void onGatewayDataReceived(InputStream stream) {
        try {
            this.messageParser.processData(stream);
        } catch (SdkException e) {
            logger.error("Could not tokenize incoming data", e);
            notifyOnEvent(FeedEventType.PARSE_ERROR, null);
            if (e instanceof SdkFatalException) {
                stop();
            }
        }
    }

    /**
     * Invoked when the internally used gateway establishes a connection to the remote host.
     */
    protected void onGatewayConnected() {
        notifyOnEvent(FeedEventType.CONNECTED, TimeProvider.getCurrent().getCurrentTime());
    }

    /**
     * Invoked when the internally used {@link Gateway} drops connection to the remote host.
     */
    protected void onGatewayDisconnected() {
        notifyOnEvent(FeedEventType.DISCONNECTED, TimeProvider.getCurrent().getCurrentTime());
    }

    /**
     * Invoked by the observed {@link Gateway} when the connection to the server is closed due to some error -
     * connection closed by the server, missed connection alive message, invalid data ...
     *
     * @param ex The exception which occurred on the observed gateway.
     */
    protected void onGatewayError(Exception ex) {
        notifyOnEvent(FeedEventType.DISCONNECTED, TimeProvider.getCurrent().getCurrentTime());
    }

    /**
     * Invoked when the observed {@link MessageParser} parsed data which represented a whole message.
     *
     * @param message Message constructed from the received data.
     */
    protected void onMessageParsed(I message) {
        notifyOnMessageReceived(message);
    }

    /**
     * Notifies the attached observer that an error occurred on the observed protocol
     *
     * @param eventType The {@link FeedEventType} member specifying the type of the occurred error.
     * @param data      Opaque additional data
     */
    protected void notifyOnEvent(FeedEventType eventType, Object data) {
        checkNotNull(eventType, "error cannot be a null reference");
        ProtocolListener<I> copy = this.listener;
        if (copy != null) {
            copy.onLinkEvent(eventType, data);
        }
    }

    /**
     * Notifies the attached observer the protocol has received message from the server.
     *
     * @param message the message received from the server.
     */
    protected void notifyOnMessageReceived(I message) {
        ProtocolListener<I> copy = this.listener;
        if (copy != null) {
            copy.onMessageReceived(message);
        }
    }

    /**
     * Instructs the internally used {@link Gateway} to connect to the host.
     */
    protected void connect() {
        try {
            gateway.connect();
        } catch (IOException e) {
            sdkLogger.logAlert(Level.ERROR, "failed to connect", e);
            logger.error("failed to connect", e);
        }
    }
}
