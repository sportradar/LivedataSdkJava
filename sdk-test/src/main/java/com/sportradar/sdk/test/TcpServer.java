package com.sportradar.sdk.test;

import com.sportradar.sdk.common.exceptions.SdkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.sportradar.sdk.common.classes.Nulls.checkNotNull;

/**
 * A tcp server
 */
public class TcpServer implements NetworkServer {

    /**
     * The {@link Logger} instance used for logging.
     */
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    /**
     * The {@link ExecutorService} instance used to spawn background threads.
     */
    private final ExecutorService executor;

    /**
     * The port number
     */
    private final int port;

    /**
     * The {@link NetworkServerListener} instance used to observe the current instance.
     */
    private NetworkServerListener listener;

    /**
     * The {@link ServerSocket} instance used to accept client connections.
     */
    private ServerSocket serverSocket;

    /**
     * The {@link Socket} instance used to communicate with the connected client.
     */
    private Socket clientSocket;

    /**
     * The value specifying whether the request to stop the server was received.
     */
    private boolean stopRequested;

    /**
     * The value specifying whether the request to disconnect the client was received.
     */
    private boolean clientDisconnectRequested;

    /**
     * Initializes a new instance of the {@link TcpServer} class.
     *
     * @param executor The {@link ExecutorService} instance used to spawn background threads.
     * @param port     The port number
     * @throws IOException
     */
    public TcpServer(ExecutorService executor, int port) {
        checkNotNull(executor, "The executor cannot be a null reference");
        checkArgument(port > 0 && port < 65535, "The port must be greater than 0 and smaller than 65535");

        this.executor = executor;
        this.port = port;
    }

    /**
     * Starts listening for incoming connections.
     */
    private void startListening() {
        logger.info("Server successfully started");
        listener.onStarted();
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                read();
                if (stopRequested) {
                    break;
                }
            } catch (IOException e) {
                if (stopRequested) {
                    logger.debug("The server was stopped while waiting for connection.");
                } else {
                    logger.error("An error occurred while waiting for a client to connect. Error: {}", e);
                }
                break;
            }
        }
        listener.onStopped();
    }

    /**
     * Reads the incoming data from the client. This method is always running on a background thread.
     */
    private void read() {
        logger.debug("Client has established the connection, notifying the listener");
        listener.onClientConnected();
        this.clientDisconnectRequested = false;
        byte[] buffer = new byte[1024];
        int bufferSize = 1024;
        while (true) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                int readCount = inputStream.read(buffer, 0, bufferSize);

                if (readCount == -1) {
                    logger.debug("The remote side has closed the socket");
                    break;
                } else if (readCount == 0) {
                    logger.debug("nothing read");
                    break;
                } else {
                    logger.debug("data from remote side received. byte count: {}", readCount);
                    byte[] copy = new byte[readCount];
                    System.arraycopy(buffer, 0, copy, 0, readCount);
                    try {
                        listener.onDataReceived(copy);
                    } catch (SdkException e) {
                        logger.error("Caught exception in read", e);
                    }
                }


            } catch (IOException ex) {
                if (clientDisconnectRequested) {
                    logger.debug("The connection to the client closed.");
                } else if (stopRequested) {
                    logger.debug("The connection to the client was closed because the server is stopping.");
                } else {
                    logger.error("There was an error reading from stream", ex);
                    try {
                        clientSocket.close();
                    } catch (IOException ex1) {
                        logger.error("There was an error closing socket", ex1);
                    }
                }
                break;
            }
        }
        logger.debug("Client socket closed. Notifying listener");
        listener.onClientDisconnected();
    }

    /**
     * Sets the {@code NetworkServerListener} instance used to observe the current {@link NetworkServer}.
     *
     * @param listener The {@link NetworkServerListener} used to observe the current {@link NetworkServer}.
     */
    @Override
    public void setListener(NetworkServerListener listener) {
        this.listener = listener;
    }

    /**
     * Asynchronously starts the current {@link NetworkServer}.
     *
     * @throws IOException The exception was thrown while opening the server.
     */
    @Override
    public void start() throws IOException {
        this.stopRequested = false;
        this.serverSocket = new ServerSocket(port);
        Runnable command = new Runnable() {
            @Override
            public void run() {
                startListening();
            }
        };
        executor.submit(command);
    }

    /**
     * Asynchronously stops the current {@link NetworkServer}
     */
    @Override
    public void stop() {
        try {
            this.stopRequested = true;
            this.serverSocket.close();
            this.serverSocket = null;

            if (clientSocket != null) {
                clientSocket.close();
            }

            logger.info("Server successfully stopped");
        } catch (IOException e) {
            logger.warn("There was an error while stopping the server. Error: {}", e);
        }
    }

    /**
     * Forcefully disconnects the currently connected client.
     *
     * @throws IllegalStateException the client is not connected.
     */
    @Override
    public void disconnectClient() {
        logger.info("Forcefully disconnecting client");
        if (clientSocket == null) {
            throw new IllegalStateException("The client is not connected.");
        }
        this.clientDisconnectRequested = true;
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            logger.error("There was an error closing socket", e);
        }
    }

    /**
     * Sends data to the connected client
     *
     * @param data The data to be send.
     * @throws IOException           exception has benn thrown while sending data.
     * @throws IllegalStateException the client is not connected.
     */
    @Override
    public void sendData(byte[] data) throws IOException {

        try {
            logger.debug("Sending data to the client. ByteCount: {}", data.length);
            clientSocket.getOutputStream().write(data);
        } catch (IOException e) {
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e1) {
                    logger.error("There was an error closing socket", e1);
                }
            }
            throw e;
        }
    }
}
