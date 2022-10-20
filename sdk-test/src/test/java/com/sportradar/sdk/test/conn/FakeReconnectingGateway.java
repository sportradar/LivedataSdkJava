package com.sportradar.sdk.test.conn;

import ch.qos.logback.classic.Logger;
import com.sportradar.sdk.common.networking.Gateway;
import com.sportradar.sdk.common.networking.GatewayListener;
import com.sportradar.sdk.common.networking.ReconnectingGateway;
import com.sportradar.sdk.common.timer.Timer;
import org.joda.time.Duration;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class FakeReconnectingGateway extends ReconnectingGateway {

    private GatewayListener listener;
    private FakeGatewayListener fakeGatewayListener;
    private boolean isConnected;
    private final Logger logger = (Logger) LoggerFactory.getLogger(FakeReconnectingGateway.class);
    public BlockingQueue blockingQueue = new SynchronousQueue();

    public FakeReconnectingGateway(
            final Gateway actualGateway,
            final Timer connectTimer,
            final Duration initialReconnectInterval,
            final Duration reconnectInterval) {
        super(actualGateway, connectTimer, initialReconnectInterval, reconnectInterval);
        logger.debug("FakeReconnectinGateway init");
    }

    public void setFakeGatewayListener(FakeGatewayListener fakeGatewayListener) {
        this.fakeGatewayListener = fakeGatewayListener;
    }

    @Override
    public void setListener(GatewayListener listener) {
        logger.debug("setListener");
        super.setListener(listener);
        this.listener = listener;
    }

    @Override
    public void sendData(byte[] data) throws IOException {
        logger.debug("sendData");
        super.sendData(data);
        fakeGatewayListener.onSendDataRequested(data);
    }

    @Override
    public void connect() throws IOException {
        logger.debug("connect");
        super.connect();
        this.listener.onConnected();
        this.fakeGatewayListener.onConnectedRequested();
    }

    @Override
    public void disconnect(boolean dueToError) {
        logger.debug("disconnect");
        super.disconnect(dueToError);
        this.listener.onDisconnected(null);
        this.fakeGatewayListener.onDisconnectedRequested();
    }

    @Override
    public boolean isConnected() {
        logger.debug("isConnected");
        return super.isConnected();
    }

    public void notifyConnected() {
        logger.debug("notifyConnected");
        isConnected = true;
        /*try {
            blockingQueue.offer("notifyConnected", 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        listener.onConnected();
    }

    public void notifyDisconnected(Exception ex) {
        logger.debug("notifyDisconnected");
        isConnected = false;
        /*try {
            blockingQueue.offer("notifyDisconnected", 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        listener.onDisconnected(ex);
    }

    public void notifyDataReceived(byte[] data) {
        logger.debug("notifyDataReceived");
        /*try {
            blockingQueue.offer("notifyDataReceived", 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        this.listener.onDataReceived(new ByteArrayInputStream(data));
    }

    /*public int getSetGatewayListenerCounter() {
        return this.setGatewayListenerCounter;
    }*/

    /*public int getDisconnectCounter() {
        return this.disconnectCounter;
    }*/
}
