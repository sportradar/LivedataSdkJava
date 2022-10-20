package com.sportradar.sdk.test.conn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class FakeGatewayListenerImpl implements FakeGatewayListener {

    int connectionRequests = 0;
    int disconnectedRequests = 0;
    final int MAX_CONNECTION_REQUESTS;
    final FakeGateway fakeGateway;
    byte[] data;
    private final Logger logger = LoggerFactory.getLogger(FakeGatewayListenerImpl.class);
    public BlockingQueue blockingQueue = new SynchronousQueue();

    FakeGatewayListenerImpl(FakeGateway fakeGateway, int MAX_CONNECTION_REQUESTS) {
        this.fakeGateway = fakeGateway;
        this.MAX_CONNECTION_REQUESTS = MAX_CONNECTION_REQUESTS;
    }

    @Override
    public synchronized void onConnectedRequested() {
        logger.debug("onConnectedRequested");
        if (++connectionRequests <= MAX_CONNECTION_REQUESTS) {
            fakeGateway.notifyConnected();
        }
        try {
            blockingQueue.offer("connected", 10, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onDisconnectedRequested() {
        logger.debug("onDisconnectedRequested");
        ++disconnectedRequests;
        fakeGateway.notifyDisconnected(null);
    }

    @Override
    public synchronized void onSendDataRequested(byte[] data) {
        logger.debug("onSendDataRequested");
        this.data = data;
        fakeGateway.notifyDataReceived(data);
    }

    public synchronized int getConnectionRequests() {
        return connectionRequests;
    }

    public synchronized int getDisconnectedRequests() {
        return disconnectedRequests;
    }

    public synchronized byte[] getData() {
        return data;
    }
}
