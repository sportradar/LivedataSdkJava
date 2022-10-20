package com.sportradar.sdk.test;

import com.sportradar.sdk.common.classes.BlockingList;
import com.sportradar.sdk.common.classes.SdkVersion;
import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.common.settings.LiveScoutSettings;
import com.sportradar.sdk.proto.common.*;
import com.sportradar.sdk.proto.dto.IncomingMessage;
import com.sportradar.sdk.proto.dto.LiveScoutLoginType;
import com.sportradar.sdk.proto.dto.OutgoingMessage;
import com.sportradar.sdk.proto.dto.incoming.livescout.Ct;
import com.sportradar.sdk.proto.dto.outgoing.livescout.Credential;
import com.sportradar.sdk.proto.dto.outgoing.livescout.Login;
import com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Live-odds server used for end-to-end and integration tests.
 */
public class FakeOddsServer implements NetworkServerListener, MessageParserListener<OutgoingMessage> {

    /**
     * Specifies the time interval the server will wait for an async operation to complete.
     */
    private static final long TIMEOUT = 2000L;

    /**
     * A combination of bytes used as a separator when sending multiple messages across the wire.
     */
    private static final byte[] MESSAGE_SEPARATOR = new byte[]{0x0D, 0x0A, 0x0D, 0x0A};

    /**
     * A logger implementation.
     */
    private static final Logger logger = LoggerFactory.getLogger(FakeOddsServer.class);

    /**
     * A tcp server used to accept incoming client connections.
     */
    private final TcpServer server;

    /**
     * A {@link com.sportradar.sdk.proto.common.MessageParser} used to parse messages from the clients.
     */
    private final MessageParser<OutgoingMessage> messageParser;

    /**
     * A {@link com.sportradar.sdk.proto.common.MessageWriter} implementation used to write messages to byte[]
     */
    private final MessageWriter<IncomingMessage> messageWriter;

    /**
     * A {@link com.sportradar.sdk.proto.livescout.LiveScoutStatusFactory}
     */
    private final LiveScoutStatusFactory requestFactory = new LiveScoutStatusFactory(new SdkVersion());

    /**
     * The {@link com.sportradar.sdk.common.settings.LiveScoutSettings} representing the application's liveOddsSettings.
     */
    private final LiveScoutSettings settings;

    /**
     * A {@link BlockingList} used to synchronize threads.
     */
    private BlockingList<String> events = new BlockingList<>();

    /**
     * A {@link Comparator} used to compare {@link OutgoingMessage} instances.
     */
    private Comparator<OutgoingMessage> requestComparator = new LiveScoutStatusComparator();

    /**
     * A {@link BlockingList} used to synchronize the main thread and the one receiving messages.
     */
    private BlockingList<OutgoingMessage> messages = new BlockingList<>();

    /**
     * A {@link ScheduledExecutorService} used to schedule an event which starts the server.
     */
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * A {@link ScheduledExecutorService} used to schedule event to send "alive" messages to the client.
     */
    private ScheduledExecutorService aliveScheduler = Executors.newScheduledThreadPool(1);

    /**
     * Initializes a new instance of the {@link FakeOddsServer} class.
     *
     * @param server        The {@link TcpServer} used to accept client connections.
     * @param messageParser The {@link MessageWriter} used to parse client messages
     * @param messageWriter The {@link MessageWriter} used to write messages send to the clients.
     */
    public FakeOddsServer(TcpServer server, MessageParser<OutgoingMessage> messageParser, MessageWriter<IncomingMessage> messageWriter, LiveScoutSettings settings) {
        this.server = server;
        this.server.setListener(this);
        this.messageParser = messageParser;
        this.messageWriter = messageWriter;
        this.settings = settings;
        this.messageParser.setListener(this);
    }

    /**
     * Sends the message to the connected client.
     *
     * @param message The {@link IncomingMessage} representing the message to be send.
     */
    private void sendData(IncomingMessage message) throws MessageException, ProtocolException, IOException {
        byte[] messageData = messageWriter.write(message);
        byte[] data = new byte[messageData.length + 4];
        System.arraycopy(messageData, 0, data, 0, messageData.length);
        System.arraycopy(MESSAGE_SEPARATOR, 0, data, messageData.length, MESSAGE_SEPARATOR.length);

        server.sendData(data);
    }

    private IncomingMessage buildLoginResponse(LiveScoutLoginType type){
        com.sportradar.sdk.proto.dto.incoming.livescout.Login login = new com.sportradar.sdk.proto.dto.incoming.livescout.Login();
        login.setResult(type.getLiteralValue());
        return login;
    }

    /**
     * Invoked when the observed {@link com.sportradar.sdk.proto.common.MessageParser} parsed data which represented a whole message.
     *
     * @param message Message constructed from the received data.
     */
    @Override
    public void onMessageReady(OutgoingMessage message) {
        messages.add(message);

        if (message != null && Login.class.equals(message.getClass())) {
            Credential cred = ((Login)message).getCredential();
            if (cred.getLoginname().equals(settings.getUsername()) && settings.getPassword().equals(cred.getPassword())) {
                try {
                    sendData(buildLoginResponse(LiveScoutLoginType.VALID));
                } catch (Exception e) {
                    logger.error("There was at sending data", e);
                }
            } else {
                try {
                    sendData(buildLoginResponse(LiveScoutLoginType.INVALID));
                } catch (Exception e) {
                    logger.error("There was at sending data", e);
                }
            }
        }
    }

    /**
     * Invoked when the async method used to start the observed {@link TcpServer} has completed
     */
    @Override
    public void onStarted() {
        logger.info("The server started");
        events.add("start");
    }

    /**
     * Invoked when the async method used to stop the observed {@link TcpServer} has completed.
     */
    @Override
    public void onStopped() {
        logger.info("The server stopped");
        events.add("stop");
    }

    /**
     * Invoked by the observed {@link TcpServer} when the client connects to it.
     */
    @Override
    public void onClientConnected() {
        events.add("connect");
    }

    /**
     * Invoked by the observed {@link TcpServer} when the client closes the connection.
     */
    @Override
    public void onClientDisconnected() {
        events.add("disconnect");
    }

    /**
     * Invoked by the observed {@code TcpServer} when it receives data from the remote site
     *
     * @param data The received data
     */
    @Override
    public void onDataReceived(byte[] data) throws SdkException {
        logger.info("Message from server received");
        try {
            messageParser.processData(new ByteArrayInputStream(data));
        } catch (ProtocolException e) {
            logger.error("An error occurred while creating message from received data. Error: {}", e);
        }
    }

    /**
     * Starts the server. The method blocks until the the server is started.
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void start() throws InterruptedException, IOException {
        server.start();
        events.compareAndRemove("start", TIMEOUT);
    }

    /**
     * Schedules a server start.
     *
     * @param delay Specifies the delay after which the server will be started.
     */
    public void scheduleStart(long delay) {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    server.start();
                } catch (IOException e) {
                }
            }
        };
        scheduler.schedule(command, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops the server and block the calling thread until the stop is completed.
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        server.stop();
        events.compareAndRemove("stop", TIMEOUT);
    }

    /**
     * Sends the message to the connected client.
     *
     * @throws MessageException
     * @throws ProtocolException
     * @throws IOException
     */
    public void sendMessage() throws MessageException, ProtocolException, IOException {
        sendData(new Ct());
    }

    /**
     * Disconnects the currently connected client.
     *
     * @throws IOException
     */
    public void disconnectClient() throws IOException {
        server.disconnectClient();
    }

    /**
     * Starts to repeatedly send "alive" messages to the connected client.
     */
    public void startSendingAliveMessage() {
        aliveScheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable command = new Runnable() {
            @Override
            public void run() {
                try {
                    sendData(new Ct());
                } catch (Exception e) {
                }
            }
        };
        aliveScheduler.scheduleWithFixedDelay(command, 100, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops sending "alive" messages to the connected client.
     */
    public void stopSendingAliveMessage() {
        aliveScheduler.shutdown();
    }

    /**
     * Waits (blocks the current thread) until the server is started.
     *
     * @param timeout The timeout to wait until throwing {@link InterruptedException}
     * @throws InterruptedException The timeout has elapsed.
     */
    public void waitForServerToStart(long timeout) throws InterruptedException {
        events.compareAndRemove("start", timeout);
    }

    /**
     * Blocks the calling thread until the the client connects or throws {@link InterruptedException} if the timeout elapses.
     */
    public void hasClientConnected() throws InterruptedException {
        events.compareAndRemove("connect", TIMEOUT);
    }

    /**
     * Blocks the calling thread until the the client disconnects or throws {@link InterruptedException} if the timeout elapses.
     */
    public void hasClientDisconnected() throws InterruptedException {
        events.compareAndRemove("disconnect", TIMEOUT);
    }

    /**
     * Blocks the calling thread until the a "login" request is received from the client or throws {@link InterruptedException}
     */
    public void hasReceivedLoginRequest() throws InterruptedException {
        messages.compareAndRemove((OutgoingMessage) requestFactory.buildLoginRequest("0", "0"), requestComparator, TIMEOUT);
    }

}


