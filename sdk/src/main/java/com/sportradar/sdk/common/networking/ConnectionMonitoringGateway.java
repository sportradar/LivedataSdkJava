/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/

package com.sportradar.sdk.common.networking;

import com.sportradar.sdk.common.classes.DevHelper;
import com.sportradar.sdk.common.timer.Timer;
import com.sportradar.sdk.common.timer.TimerListener;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Gateway} implementation which observes the received messages to determine when the connection is unstable
 */
public class ConnectionMonitoringGateway implements Gateway {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionMonitoringGateway.class);

    /**
     * The wrapped {@link Gateway} implementation used to communicate with the server.
     */
    private final Gateway actualGateway;

    /**
     * The timer used to periodically monitor the connection status.
     */
    private final Timer monitorTimer;

    /**
     * The {@link Duration} specifying the interval between two consecutive connection checks.
     */
    private final Duration checkInterval;

    /**
     * The {@link Duration specifying the time window in milliseconds after which the connection will be considered unstable if the gateway does not receive any data.
     */
    private final long noActivityTimeout;

    /**
     * The {@link Object} used to synchronize access to shared members.
     */
    private final Object lock = new Object();

    /**
     * The {@link GatewayListener} instance used to observe the current {@link Gateway}.
     */
    private volatile GatewayListener listener;

    /**
     * The value specifying whether the connection should be automatically re-established after disconnect.
     */
    private volatile boolean autoReconnect;

    private boolean debugMode;

    /**
     * Initializes a new instance of the {@link ConnectionMonitoringGateway} class.
     *
     * @param actualGateway     the wrapped {@link Gateway} implementation used to communicate with the server.
     * @param monitorTimer      The timer used to periodically monitor the connection status.
     * @param checkInterval     Check interval for socket connection.
     * @param noActivityTimeout The {@link Duration} specifying the time window in milliseconds after which the connection will be considered unstable if the gateway does not receive any data.
     * @param debugMode         Adds debug logging.
     * @throws IllegalArgumentException the {@code actualGateway} is a null reference or
     *                                  {@code monitorTimer} is a null reference or
     *                                  {@code checkInterval} is a null reference or
     *                                  {@code noActivityTimeout} is a null reference.
     */
    public ConnectionMonitoringGateway(Gateway actualGateway,
                                       Timer monitorTimer,
                                       Duration checkInterval,
                                       Duration noActivityTimeout,
                                       boolean debugMode) {
        checkNotNull(actualGateway, "The actualGateway cannot be a null reference");
        checkNotNull(monitorTimer, "The monitorTimer cannot be a null reference");
        checkNotNull(checkInterval, "checkInterval cannot be a null reference");
        checkNotNull(noActivityTimeout, "noActivityTimeout cannot be a null reference");

        this.actualGateway = actualGateway;
        this.monitorTimer = monitorTimer;
        this.checkInterval = checkInterval;
        this.noActivityTimeout = noActivityTimeout.getMillis();
        this.debugMode = debugMode;
        setDependencyListeners();
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
                monitorTimer.schedule(checkInterval, checkInterval);

                GatewayListener copy = listener;
                if (copy != null) {
                    copy.onConnected();
                }
            }

            @Override
            public void onDisconnected(Exception ex) {
                monitorTimer.stop();

                GatewayListener copy = listener;
                if (copy != null) {
                    copy.onDisconnected(ex);
                }

                if (autoReconnect) {
                    try {
                        actualGateway.connect();
                    } catch (IOException e) {
                        logger.error("There was an error at reconnecting {}", actualGateway.getId(), e);
                    }
                }
            }
        });

        this.monitorTimer.setListener(new TimerListener() {
            @Override
            public void onTick() {
                synchronized (lock) {
                    long now = System.currentTimeMillis();

                    if ((actualGateway.getLastReceivedMsgTimestamp() + noActivityTimeout) < now) {
                        if (debugMode && DevHelper.isDebuggerAttached()) {
                            logger.info("Would disconnect the socket {} due to message timeout, but will not since we are running in debug mode",
                                    actualGateway.getId());
                            return;
                        }

                        logger.warn("Disconnecting the socket {} because the message timeout was reached", actualGateway.getId());

                        actualGateway.disconnect(true);
                        try {
                            actualGateway.connect();
                        } catch (IOException e) {
                            logger.error("onTick caught exception", e);
                        }
                    }
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
        actualGateway.sendData(data);
    }

    /**
     * Attempts to establish a connection to the remote site.
     *
     * @throws IOException                            There was an error establishing the connection.
     */
    @Override
    public void connect() throws IOException {
        synchronized (lock) {
            autoReconnect = true;
            actualGateway.connect();
        }
    }

    /**
     * Drops the established connection.
     */
    @Override
    public void disconnect(boolean dueToError) {
        synchronized (lock) {
            autoReconnect = false;
            actualGateway.disconnect(dueToError);
        }
    }

    /**
     * Gets a value specifying whether the connection to the remote site is established.
     *
     * @return Value specifying whether the connection to the remote site is established.
     */
    @Override
    public boolean isConnected() {
        return actualGateway.isConnected();
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
