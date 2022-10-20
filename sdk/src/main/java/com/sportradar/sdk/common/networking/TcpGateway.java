/***************************************************************
 * Copyright (c) 2013, Sportradar AG                           *
 ***************************************************************/
package com.sportradar.sdk.common.networking;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A {@link Gateway} implementation which uses TCP protocol stack to communicate.
 *
 * @author uros.bregar
 */
public class TcpGateway implements Gateway {

    /**
     * The logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(TcpGateway.class);
    /**
     * The size of the read buffer in bytes.
     */
    private final int bufferSize;
    /**
     * Gets the value indicating whether the disconnect was requested by the user.
     */
    private volatile boolean disconnectRequested;
    /**
     * The {@link InetSocketAddress} instance specifying the remote endpoint of the socket.
     */
    private final InetSocketAddress endpoint;
    /**
     * The {@link ExecutorService} used to execute tasks.
     */
    private final ExecutorService executor;
    /**
     * Gets the last timestamp of any received msg
     */
    private volatile long lastReceivedMsgTimestamp = 0L;
    /**
     * The {@link GatewayListener} instance used to observer the current {@link TcpGateway}
     */
    private GatewayListener listener = null;
    /**
     * The lock used to synchronize access to members shared between multiple threads.
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    /**
     * The lock used to synchronize read access to members shared between multiple threads.
     */
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    /**
     * Gets the read loop thread id
     */
    private volatile long readLoopThreadId = 0;
    /**
     * The {@link Socket} used to connect to the remote site.
     */
    private Socket socket = null;
    /**
     * The {@link SocketFactory} instance used to build the {@link Socket}
     */
    private final SocketFactory socketFactory;

    // ZB: fields below are not thread safe by itself
    // and are protected by readLock and writeLock
    /**
     * Future of read runnable...
     */
    private Future<?> socketReadLoop = null;
    private final Object socketWriteStreamLock = new Object();
    /**
     * The lock used to synchronise write access to members shared between multiple threads.
     */
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    /**
     * Initializes a new instance of the {@link TcpGateway} class.
     *
     * @param executor      The {@link ExecutorService} instance used to execute tasks.
     * @param socketFactory The {@link SocketFactory} instance used to build the {@link Socket}
     * @param endpoint      The {@link InetSocketAddress} instance specifying the remote endpoint of the socket.
     * @param bufferSize    The size of the read buffer in bytes
     * @throws IllegalArgumentException The {@code executor} is a null reference or {@code socketFactory} is a null reference or {@code endpoint} is a null reference or {@code bufferSize} is smaller than zero.
     */
    public TcpGateway(
            ExecutorService executor,
            SocketFactory socketFactory,
            InetSocketAddress endpoint,
            int bufferSize) {
        checkNotNull(executor, "The executor cannot be a null reference");
        checkNotNull(socketFactory, "The socketFactory cannot be a null reference");
        checkNotNull(endpoint, "The endpoint cannot be a null reference");
        checkArgument(bufferSize > 0, "The buffer size must be greater than zero");

        this.executor = executor;
        this.socketFactory = socketFactory;
        this.endpoint = endpoint;
        this.bufferSize = bufferSize;
    }

    /**
     * Sets the {@link GatewayListener} used to observe the current {@link Gateway} implementation.
     *
     * @param listener the {@link GatewayListener} used to observe the current {@link Gateway} implementation or a null reference if observation is not required.
     */
    @Override
    public void setListener(GatewayListener listener) {
        writeLock.lock();
        try {
            this.listener = listener;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Sends data through the current {@link Gateway}.
     *
     * @param data data to send.
     * @throws IllegalArgumentException The {@code data} is a null reference or an empty array
     */
    @Override
    public void sendData(byte[] data) throws IOException {
        checkArgument(data != null && data.length > 0, "The data cannot be a null reference or an empty array");

        Socket s = null;
        readLock.lock();
        try {
            s = socket;
            checkState(s != null && s.isConnected(), "The connection to the remote site is not established");
        } finally {
            readLock.unlock();
        }

        // ZB: serialize writings to socket
        synchronized (socketWriteStreamLock) {
            OutputStream oStream = s.getOutputStream();
            oStream.write(data);
            oStream.flush();
            // logger.debug("Data send to the server. Data length: {}", data.length);
        }
    }

    /**
     * Attempts to establish a connection to the remote site.
     *
     * @throws IOException There was an error establishing the connection.
     */
    @Override
    public void connect() throws IOException {
        writeLock.lock();
        try {
            disconnectRequested = false;

            safelyCloseSocket();

            try {
                socket = socketFactory.createSocket(endpoint.getHostName(), endpoint.getPort());
                logger.info("Connection to the server {}:{} established",
                        endpoint.getHostName(),
                        endpoint.getPort());

            } catch (Exception ex) {
                safelyCloseSocket();
                logger.error("An error occurred while establishing connection to the remote site {}:{}",
                        endpoint.getHostName(), endpoint.getPort(), ex);
                throw ex;
            }

            this.socketReadLoop = executor.submit(new Runnable() {
                @Override
                public void run() {
                    readLoopThreadId = Thread.currentThread().getId();
                    read();
                }
            });

        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Drops the established connection.
     *
     * @param dueToError - when false user wished to close connection; when true there was a problem and we had
     *                   to close it
     */
    @Override
    public void disconnect(boolean dueToError) {

        Future<?> readLoop = null;

        writeLock.lock();
        try {
            if (!dueToError) {
                disconnectRequested = true;
            }
            readLoop = this.socketReadLoop;

            safelyCloseSocket();
        } finally {
            writeLock.unlock();
        }

        if ((readLoopThreadId != Thread.currentThread().getId()) && (readLoop != null)) {
            try {
                // wait till listener.onDisconnected is done
                if (!readLoop.isDone()) {
                    readLoop.get(2000, TimeUnit.MILLISECONDS);
                    readLoop.cancel(true);
                }
            } catch (TimeoutException | InterruptedException | ExecutionException | CancellationException ignored) {
            }
        }
        readLoopThreadId = 0;
    }


    /**
     * Gets a value specifying whether the connection to the remote site is established.
     *
     * @return Value specifying whether the connection to the remote site is established.
     */
    @Override
    public boolean isConnected() {
        readLock.lock();
        try {
            return (socket != null && socket.isConnected());
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Gets last timestamp in unix millis when the last msg was received
     *
     * @return timestamp when the last msg was received
     */
    @Override
    public long getLastReceivedMsgTimestamp() {
        return this.lastReceivedMsgTimestamp;
    }

    @Override
    public String getId() {
        if (socket != null) {
            return String.format("%s:%s", socket.getInetAddress(), socket.getPort());
        }
        return null;
    }

    /**
     * continuously waits for data from remote site until the read operation causes an exception.
     */
    private void read() {
        boolean raiseConnectedEvent = true;
        Exception error = null;
        GatewayListener localListener = null;
        InputStream inputStream = null;
        byte[] buffer = new byte[bufferSize];

        try {

            while (true) {
                try {
                    logger.trace("Started reading for {}:{}", socket.getInetAddress(), socket.getPort());
                    readLock.lock();
                    try {
                        inputStream = socket.getInputStream();
                        localListener = this.listener;
                    } finally {
                        readLock.unlock();
                    }
                    logger.trace("Ended reading for {}:{}", socket.getInetAddress(), socket.getPort());
                    if (raiseConnectedEvent) {
                        raiseConnectedEvent = false;
                        if (localListener != null) {
                            localListener.onConnected();
                        }
                    }

                    if (inputStream == null) {
                        logger.error("The server {}:{} dropped the connection", endpoint.getHostName(), endpoint.getPort());
                        error = new IOException("The server closed the connection");
                        break;
                    }

                    // The call blocks indefinitely - or throws an exception if connection is closed.
                    int readCount = inputStream.read(buffer, 0, bufferSize);
                    if (readCount != -1) {
                        this.lastReceivedMsgTimestamp = System.currentTimeMillis();
                        logger.trace("Received data from the server {}:{} ByteCount: {}",
                                socket.getInetAddress(),
                                socket.getPort(),
                                readCount);
                        if (localListener != null) {
                            byte[] copy = new byte[readCount];
                            System.arraycopy(buffer, 0, copy, 0, readCount);
                            try {
                                localListener.onDataReceived(new ByteArrayInputStream(copy));
                            } catch (Exception e) {
                                logger.error("Caught exception while invoking onDataReceived {}:{}",
                                        endpoint.getHostName(), endpoint.getPort(), e);
                            }

                        }
                    } else {
                        logger.error("The server {}:{} dropped the connection", endpoint.getHostName(), endpoint.getPort());
                        error = new IOException("The server closed the connection");
                        break;
                    }
                } catch (NullPointerException ex) {
                    logger.debug("Cannot read from the socket because it has been closed");
                    break;
                } catch (IOException ex) {
                    if (disconnectRequested) {
                        logger.debug("The gateway was disconnected while waiting for input stream. Aborting read operation");
                    } else {
                        logger.error("An error occurred while waiting for the input stream.", ex);
                        error = ex;
                    }
                    break;
                } catch (Exception ex) {
                    logger.error("An error occurred while waiting for the input stream", ex);
                    error = ex;
                    break;
                }
            }
        } finally {
            try {
                writeLock.lock();
                try {
                    safelyCloseSocket();
                } finally {
                    writeLock.unlock();
                }
            } finally {
                if (localListener != null) {
                    localListener.onDisconnected(disconnectRequested ? null : error);
                }
            }
        }
    }

    /**
     * Closes the socket and ignores possible {@link IOException}
     */
    private synchronized void safelyCloseSocket() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception ex) {
            logger.debug(String.format(
                            "An error occurred while closing socket %s:%s",
                            socket.getInetAddress(),
                            socket.getPort()),
                    ex);
        } finally {
            socket = null;
        }
    }
}
