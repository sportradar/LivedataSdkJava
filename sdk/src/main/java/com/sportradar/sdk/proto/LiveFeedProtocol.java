package com.sportradar.sdk.proto;

import ch.qos.logback.classic.Level;
import com.sportradar.sdk.common.interfaces.SdkLogger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.rategate.RateGate;
import com.sportradar.sdk.common.rategate.RateGateContinuation;
import com.sportradar.sdk.common.rategate.RateLimiter;
import com.sportradar.sdk.common.settings.LiveFeedSettings;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.LiveScoutLoginType;
import com.sportradar.sdk.proto.dto.incoming.livescout.Login;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sportradar.sdk.proto.dto.OutgoingMessage;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Protocol} implementation used to connect to the live-feed.
 */
public class LiveFeedProtocol extends ProtocolBase<IncomingMessage, OutgoingMessage> {

    /**
     * The {@link Logger} implementation used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(LiveFeedProtocol.class);

    /**
     * When having to wait longer than that amount of time for logging-in, just disconnect.
     */
    protected final Duration DISCONNECT_WHEN_HAVING_TO_WAIT_LONGER_THAN = Duration.standardSeconds(2);
    /**
     * The {@link MessageWriter used to write messages to {@link byte[]}}.
     */
    private final MessageWriter<OutgoingMessage> messageWriter;
    /**
     * The {@link OutgoingMessageInspector} implementation used to inspect send messages.
     */
    private final OutgoingMessageInspector<OutgoingMessage> outgoingMessageInspector;
    /**
     * The {@link RateLimiter} implementation used to rate-limit send messages
     */
    private final RateLimiter rateLimiter;
    /**
     * The {@link LiveFeedSettings} instance containing the application's settings concerning the live-feed
     */
    private final LiveFeedSettings settings;
    /**
     * The factory used to build requests send to the feed.
     */
    private final StatusFactory statusFactory;

    /**
     * Initializes a new instance of the {@link LiveFeedProtocol} class.
     *
     * @param gateway                  The {@link Gateway} implementation used to establish a connection to the live-odds server.
     * @param messageParser            The {@link MessageParser} implementation used to construct {@link IncomingMessage} instances from data received from the server.
     * @param messageWriter            The {@link MessageWriter used to write messages to {@link byte[]}}.
     * @param rateLimiter              The {@link RateLimiter} implementation used to rate-limit send messages
     * @param outgoingMessageInspector The {@link OutgoingMessageInspector} implementation used to inspect send messages.
     * @param statusFactory            The factory used to build requests send to the feed.
     * @param settings                 The {@link LiveFeedSettings} instance containing the application's settings concerning the live-feed
     * @param sdkLogger                The {@link SdkLogger} used for structured logging.
     */
    public LiveFeedProtocol(
            final Gateway gateway,
            final MessageParser<IncomingMessage> messageParser,
            final MessageWriter<OutgoingMessage> messageWriter,
            final RateLimiter rateLimiter,
            final OutgoingMessageInspector<OutgoingMessage> outgoingMessageInspector,
            final StatusFactory statusFactory,
            final LiveFeedSettings settings,
            final SdkLogger sdkLogger) {

        super(sdkLogger, gateway, messageParser);

        checkNotNull(messageWriter, "messageWriter cannot be a null reference");
        checkNotNull(rateLimiter, "rateLimiter cannot be a null reference");
        checkNotNull(outgoingMessageInspector, "outgoingMessageInspector cannot be a null reference");
        checkNotNull(statusFactory, "statusFactory cannot be a null reference");
        checkNotNull(settings, "settings cannot be a null reference");

        this.messageWriter = messageWriter;
        this.rateLimiter = rateLimiter;
        this.outgoingMessageInspector = outgoingMessageInspector;
        this.statusFactory = statusFactory;
        this.settings = settings;
    }

    /**
     * Sends the passed {@code OutgoingMessage} instance to the server.
     *
     * @param message The message derived from {@link OutgoingMessage} to be send to the server.
     */
    @Override
    public boolean sendMessage(OutgoingMessage message, boolean blocking) throws MessageException, ProtocolException {

        boolean success = rateLimit(
                outgoingMessageInspector.isRateLimited(message),
                outgoingMessageInspector.getEventRequestRateLimitCount(message),
                blocking);

        if (!success) {
            return false;
        }

        byte[] messageData = messageWriter.write(message);
        try {
            gateway.sendData(messageData);
            sdkLogger.logTraffic(true, new String(messageData, Charset.forName(messageWriter.getEncoding())));
        } catch (IOException e) {
            throw new ProtocolException("The message could not be send", e);
        }
        return true;
    }

    /**
     * Sends the passed data to the server as is.
     */
    @Override
    public void sendMessage(String data) throws MessageException, ProtocolException {
        rateLimit(
                outgoingMessageInspector.isRateLimited(data),
                outgoingMessageInspector.getEventRequestRateLimitCount(data),
                true);

        String withNewLine = data + "\n";

        try {
            logger.debug("Using the direct sendMessage");
            gateway.sendData(withNewLine.getBytes(Charset.forName(messageWriter.getEncoding())));
            sdkLogger.logTraffic(true, withNewLine);
        } catch (IOException e) {
            throw new ProtocolException("The message could not be send", e);
        }
    }

    /**
     * When necessary blocks the current thread in order to enforce the rate limits of the protocol
     *
     * @param isRequestLimited           value indicating whether to rate-limit send requests
     * @param eventRequestRateLimitCount value indicating the number of events associated with the operation causing the rate-limiting
     * @throws {@link ProtocolException} T rate limiter was interrupted while blocking
     */
    private boolean rateLimit(boolean isRequestLimited, int eventRequestRateLimitCount, boolean blocking) throws ProtocolException {
        try {
            if (isRequestLimited) {
                final RateGate requestLimiter = rateLimiter.getRequestLimiter();
                if (!blocking) {
                    Duration duration = requestLimiter.howLongToWait();
                    if (duration.getMillis() > 0) {
                        return false;
                    }
                } else {
                    final Duration requestWaitTime = requestLimiter.howLongToWait();
                    if (requestWaitTime.isLongerThan(Duration.millis(0))) {
                        logger.info("Will block request for around {} ms", requestWaitTime.getMillis());
                    }
                    requestLimiter.waitToProceed();
                }
                if (eventRequestRateLimitCount > 0) {
                    final RateGate matchRequestLimiter = rateLimiter.getMatchRequestLimiter();
                    if (!blocking) {
                        Duration duration = matchRequestLimiter.howLongToWait(eventRequestRateLimitCount);
                        if (duration.getMillis() > 0) {
                            return false;
                        }
                    } else {
                        final Duration matchRequestWaitTime = matchRequestLimiter.howLongToWait(eventRequestRateLimitCount);
                        if (matchRequestWaitTime.isLongerThan(Duration.millis(0))) {
                            logger.info("Will block match request for around {} ms", matchRequestWaitTime.getMillis());
                        }
                        matchRequestLimiter.waitToProceed(eventRequestRateLimitCount);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new ProtocolException("Limiter was interrupted", e);
        }
        return true;
    }

    /**
     * Stops the protocol - i.e. disables the communication with the server.
     */
    @Override
    public void stop() {
        OutgoingMessage logout = statusFactory.buildLogOutRequest();

        //Some protocols do not require/support log-out
        if (logout != null) {
            try {
                sendMessage(logout, true);
            } catch (Exception e) {
                logger.warn("Failed to send logout request. Exception:", e);
            }
        }
        super.stop();
    }

    /**
     * Invoked by the observed {@link Gateway} instance when it establishes a connection to the server.
     */
    @Override
    protected void onGatewayConnected() {
        super.onGatewayConnected();
        try {
            if (rateLimiter.getLoginLimiter().howLongToWait().isLongerThan(DISCONNECT_WHEN_HAVING_TO_WAIT_LONGER_THAN)) {
                // Eventually we will arrive in onConnected() again and do what we have to do
                sdkLogger.logAlert(Level.WARN, "Login limit reached, disconnecting feed");
                gateway.disconnect(true);
                RateGateContinuation.after(rateLimiter.getLoginLimiter()).continueWith(new Runnable() {
                    @Override
                    public void run() {
                        sdkLogger.logAlert(Level.WARN, "Login limit wait over, connecting to feed");
                        connect();
                    }
                });
                return;
            }
            if (rateLimiter.getLoginLimiter().howLongToWait().getMillis() > 0) {
                sdkLogger.logAlert(Level.WARN, "Login limit reached, waiting to proceed");
                rateLimiter.getLoginLimiter().waitToProceed();
            }

            try {
                sendMessage(statusFactory.buildLoginRequest(settings.getUsername(), settings.getPassword()), false);
            } catch (Exception e) {
                logger.error("onConnected caught exception", e);
            }
        } catch (InterruptedException e) {
            logger.error("onConnected caught exception", e);

            // Make sure we eventually reconnect
            connect();
        }
    }

    /**
     * Invoked when the internally used {@link Gateway} drops connection to the remote host.
     */
    @Override
    protected void onGatewayDisconnected() {
        notifyOnDisconnected();
        super.onGatewayDisconnected();
    }

    /**
     * Invoked when the observed {@link MessageParser} parsed data which represented a whole message.
     *
     * @param message Message constructed from the received data.
     */
    @Override
    protected void onMessageParsed(IncomingMessage message) {
        checkNotNull(message, "message cannot be a null reference");
        if(message instanceof Login){
            Login login = (Login) message;
            LiveScoutLoginType type = LiveScoutLoginType.getTypeFromLiteralValue(login.getResult());
            if(type == LiveScoutLoginType.VALID){
                logger.debug("LOGIN_OK response received");
                notifyOnLoggedIn();
            }else{
                logger.debug("LOGIN_FAILED response received.");
            }
        }else{
            super.onMessageParsed(message);
        }
    }

    /**
     * Notifies the observer that the current {@link LiveFeedProtocol} has disconnected.
     */
    protected void notifyOnDisconnected() {
        ProtocolListener<IncomingMessage> copy = this.listener;
        if (copy != null) {
            copy.onDisconnected();
        }
    }

    /**
     * Notifies the observer that the current {@link LiveFeedProtocol} has logged in.
     */
    protected void notifyOnLoggedIn() {
        ProtocolListener<IncomingMessage> copy = this.listener;
        if (copy != null) {
            copy.onLoggedIn();
        }
    }
}
