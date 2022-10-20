/**
 * ************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 * *************************************************************
 */
package com.sportradar.sdk.common.networking;

import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.timer.TimerListener;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Gateway} which  periodically attempts to connect to the server until the connection is established
 */
public class ReconnectingGateway implements Gateway {

    /**
     * The {@link org.slf4j.Logger} implementation used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(ReconnectingGateway.class);

    /**
     * The {@link Gateway} used for actual communication.
     */
    private final Gateway actualGateway;

    /**
     * The {@link Timer} used to periodically attempt to establish a connection.
     */
    private final Timer connectTimer;

    /**
     * The {@link Duration} specifying the initial reconnect interval when the connection is closed by remote side.
     */
    private final Duration initialReconnectDelay;

    /**
     * The {@link Duration} specifying the reconnect interval when the connection is closed by remote side connecting
     * and did not reconnect after initial delay.
     */
    private final Duration reconnectInterval;

    /**
     * The {@link Object} used to synchronize access to shared variables.
     */
    private final Object lock = new Object();

    // ZB: marked as volatile because it is forwarded
    // to actualGateway which can trigger events in it own
    // thread
    /**
     * Will this be the first call to connect
     */
    private boolean firstConnect;
    /**
     * The listener used to observe this {@link Gateway} instance.
     */
    private volatile GatewayListener listener;


    /**
     * Initializes a new instance of the {@link ReconnectingGateway} class.
     *
     * @param actualGateway         The wrapped {@link Gateway} instance used for actual communication
     * @param connectTimer          The {@link Timer} used to periodically attempt to establish a connection.
     * @param initialReconnectDelay The {@link Duration} specifying the initial reconnect interval.
     * @param reconnectInterval     The {@link Duration} specifying the reconnect interval.
     * @throws IllegalArgumentException The {@code actualGateway} is a null reference or a
     *                                  {@code connectTimer} is a null reference or a
     *                                  {@code reconnectInterval} is a null reference.
     */
    @Inject
    public ReconnectingGateway(
            final Gateway actualGateway,
            final Timer connectTimer,
            final Duration initialReconnectDelay,
            final Duration reconnectInterval) {

        checkNotNull(actualGateway, "The actualGateway cannot be a null reference");
        checkNotNull(connectTimer, "The connectTimer cannot be a null reference");
        checkNotNull(initialReconnectDelay, "initialReconnectDelay cannot be a null reference");
        checkNotNull(reconnectInterval, "reconnectInterval cannot be a null reference");

        this.actualGateway = actualGateway;
        this.connectTimer = connectTimer;
        this.initialReconnectDelay = initialReconnectDelay;
        this.reconnectInterval = reconnectInterval;

        setDependencyListeners();
        firstConnect = true;
    }

    /**
     * Sets listeners to injected dependencies
     */
    private void setDependencyListeners() {
        this.actualGateway.setListener(new GatewayListener() {
            @Override
            public void onDataReceived(InputStream stream) {
                GatewayListener copy = listener;

                if (copy != null) {
                    copy.onDataReceived(stream);
                }
            }

            @Override
            public void onConnected() {
                connectTimer.stop();

                GatewayListener copy = listener;
                if (copy != null) {
                    copy.onConnected();
                }
            }

            @Override
            public void onDisconnected(Exception ex) {
                GatewayListener copy = listener;
                if (copy != null) {
                    copy.onDisconnected(ex);
                }
            }
        });

        this.connectTimer.setListener(new TimerListener() {
            @Override
            public void onTick() {
                try {
                    logger.info("attempting to connect..");
                    actualGateway.connect();
                } catch (IOException e) {
                    //Exception was already logged by the TcpGateway class. No need to log it again.
                }
            }
        });
    }


    /**
     * Sets the {@link GatewayListener} used to observe the current {@link Gateway} implementation.
     *
     * @param listener the {@link GatewayListener} used to observe the current {@link Gateway} implementation or a null reference if observation is not required.
     */
    @Override
    public void setListener(GatewayListener listener) {
        this.listener = listener;
    }

    /**
     * Sends data through the current {@link Gateway}.
     *
     * @param data data to send.
     * @throws IllegalArgumentException The {@code data} is a null reference or an empty array
     */
    @Override
    public void sendData(byte[] data) throws IOException {
        //synchronized (lock) { // ZB: underlying implementation should take care of "atomic" operations
        actualGateway.sendData(data);
        //}
    }

    /**
     * Attempts to establish a connection to the remote site.
     *
     * @throws IOException There was an error establishing the connection.
     */
    @Override
    public void connect() throws IOException {
        synchronized (lock) {
            if (!actualGateway.isConnected()) {
                if (firstConnect) {
                    firstConnect = false;
                    try {
                        actualGateway.connect();
                    } catch (Exception e) {
                        logger.error("First connect failed, setting reconnecting timer");
                        connect();
                        throw e;
                    }
                } else {
                    connectTimer.schedule(initialReconnectDelay, reconnectInterval);
                }
            }
        }
    }

    /**
     * Drops the established connection.
     */
    @Override
    public void disconnect(boolean dueToError) {
        synchronized (lock) {
            connectTimer.stop();

            if (actualGateway.isConnected()) {
                actualGateway.disconnect(dueToError);
            }
        }
    }

    /**
     * Gets a value specifying whether the connection to the remote site is established.
     *
     * @return Value specifying whether the connection to the remote site is established.
     */
    @Override
    public boolean isConnected() {
        //synchronized (lock) { // ZB: underlying implementation should take care of "atomic" operations
        return actualGateway.isConnected();
        //}
    }

    /**
     * Gets last timestamp in unix millis when the last msg was received
     *
     * @return timestamp when the last msg was received
     */
    @Override
    public long getLastReceivedMsgTimestamp() {
        return actualGateway.getLastReceivedMsgTimestamp();
    }

    @Override
    public String getId() {
        return actualGateway.getId();
    }
}
