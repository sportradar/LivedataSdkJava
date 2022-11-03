package example;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.livedata.sdk.feed.sdk.Sdk;
import example.listeners.*;
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
        try {
            boolean close = false;
            while(!close) {
                String input = reader.readLine();
                if("close".equals(input)) {
                    close = true;
                } else if("matchlist".equals(input)) {
                    if(liveScoutFeed != null) {
                        logger.info("Sending matchlist request");
                        liveScoutFeed.getMatchList(13,13,true);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
