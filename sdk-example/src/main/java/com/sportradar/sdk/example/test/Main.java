package com.sportradar.sdk.example.test;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.feed.sdk.Sdk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * The main entry point for the sdk-example
     * @param args provided command line arguments
     */
    public static void main(String[] args) throws SdkException {

        final Sdk sdk = Sdk.getInstance();
        final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();

        final LiveScoutFeedListener scoutFeedListener = new LiveScoutFeedListenerImpl();

        if (liveScoutFeed != null) {
            liveScoutFeed.open(scoutFeedListener);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        logger.info("The sdk is running. Hit any key to exit");
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Closing the sdk");
        sdk.close();
        logger.info("Sdk successfully closed. Main thread will now exit");
    }
}
