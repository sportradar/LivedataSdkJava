package com.sportradar.sdk.example;

import com.sportradar.sdk.common.exceptions.SdkException;
import com.sportradar.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.sdk.feed.sdk.Sdk;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * The main entry point for the sdk-example
     *
     * @param args provided command line arguments
     */
    public static void main(String[] args) throws SdkException {
        logger.info("Current JVM version - " + System.getProperty("java.version"));

        final Sdk sdk = Sdk.getInstance();
        final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();

        final LiveScoutFeedListener scoutFeedListener = new LiveScoutFeedListenerImpl();

        if (liveScoutFeed != null) {
            liveScoutFeed.open(scoutFeedListener);
        }

        logger.info("The sdk is running. Hit any key to exit");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            boolean close = false;
            while(!close && liveScoutFeed != null) {
                String input = reader.readLine();
                if("close".equals(input)) {
                    close = true;
                } else if("matchlist".equals(input)) {
                    logger.info("Sending matchlist request");
                    liveScoutFeed.getMatchList(13,13,true);
                } else if(input.matches("(\\d+,?)+")){
                    String[] ids = StringUtils.split(input, ',');
                    EventIdentifier[] toSubscribe = Arrays.stream(ids).map(i -> EventIdentifier.id(Long.parseLong(i)))
                            .toArray(EventIdentifier[]::new);
                    liveScoutFeed.subscribe(toSubscribe);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Closing the sdk");
        sdk.close();
        logger.info("Sdk successfully closed. Main thread will now exit");
    }
}