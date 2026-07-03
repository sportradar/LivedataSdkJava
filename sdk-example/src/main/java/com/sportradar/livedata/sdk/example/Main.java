package com.sportradar.livedata.sdk.example;

import com.sportradar.livedata.sdk.common.exceptions.SdkException;
import com.sportradar.livedata.sdk.feed.common.entities.EventIdentifier;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeed;
import com.sportradar.livedata.sdk.feed.livescout.interfaces.LiveScoutFeedListener;
import com.sportradar.livedata.sdk.feed.sdk.Sdk;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interactive example for the Livedata SDK.
 *
 * <p>Configure {@code sdk.properties} (see README.md), then run:
 * {@code mvn -pl sdk-example exec:java -Dgpg.skip=true}
 */
public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  /**
   * The main entry point for the sdk-example.
   *
   * @param args optional; ignored — commands are read from stdin
   */
  public static void main(String[] args) throws SdkException {
    logger.info("JVM version: {}", System.getProperty("java.version"));

    final Sdk sdk = Sdk.getInstance();
    final LiveScoutFeed liveScoutFeed = sdk.getLiveScout();
    logger.info("SDK version: {}", sdk.getVersion());

    final LiveScoutFeedListener listener = new LiveScoutFeedListenerImpl();
    liveScoutFeed.open(listener);
    printHelp();

    final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!handleCommand(line.trim(), liveScoutFeed)) {
          break;
        }
      }
    } catch (IOException e) {
      logger.error("Failed to read command input", e);
    } finally {
      logger.info("Closing SDK");
      sdk.close();
    }
  }

  private static void printHelp() {
    logger.info("Commands: matchlist | subscribe <id[,id...]> | book <id[,id...]> | help | close");
  }

  private static boolean handleCommand(String command, LiveScoutFeed liveScoutFeed) {
    if (command.isEmpty()) {
      return true;
    }
    if ("close".equalsIgnoreCase(command)) {
      return false;
    }
    if ("help".equalsIgnoreCase(command)) {
      printHelp();
      return true;
    }
    if ("matchlist".equalsIgnoreCase(command)) {
      logger.info("Requesting match list (13 hours back/forward, include available)");
      liveScoutFeed.getMatchList(13, 13, true);
      return true;
    }
    if (command.toLowerCase().startsWith("subscribe ")) {
      final EventIdentifier[] eventIds = parseEventIds(command.substring("subscribe ".length()));
      if (eventIds.length == 0) {
        logger.warn("Usage: subscribe <id[,id...]>");
        return true;
      }
      logger.info("Subscribing to {} match(es)", eventIds.length);
      liveScoutFeed.subscribe(eventIds);
      return true;
    }
    if (command.toLowerCase().startsWith("book ")) {
      final EventIdentifier[] eventIds = parseEventIds(command.substring("book ".length()));
      if (eventIds.length == 0) {
        logger.warn("Usage: book <id[,id...]>");
        return true;
      }
      logger.info("Booking {} match(es)", eventIds.length);
      liveScoutFeed.bookMatches(eventIds);
      return true;
    }

    logger.warn("Unknown command: {}. Type 'help' for available commands.", command);
    return true;
  }

  private static EventIdentifier[] parseEventIds(String rawIds) {
    return Arrays.stream(StringUtils.split(rawIds, ','))
        .map(String::trim)
        .filter(id -> !id.isEmpty())
        .mapToLong(Long::parseLong)
        .mapToObj(EventIdentifier::id)
        .toArray(EventIdentifier[]::new);
  }
}
