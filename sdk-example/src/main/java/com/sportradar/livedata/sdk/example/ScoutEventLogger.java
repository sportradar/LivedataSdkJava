package com.sportradar.livedata.sdk.example;

import com.sportradar.livedata.sdk.feed.livescout.entities.MatchUpdateEntity;
import com.sportradar.livedata.sdk.feed.livescout.entities.ScoutEventEntity;
import java.util.List;
import org.slf4j.Logger;

/**
 * Logs parsed scout events from match updates for manual QA.
 */
final class ScoutEventLogger {

  private ScoutEventLogger() {
  }

  /**
   * Logs scout events from a match update when logging is enabled.
   *
   * @param logger application logger
   * @param matchUpdate received match update
   * @param settings event logging settings
   */
  static void logEvents(
      Logger logger,
      MatchUpdateEntity matchUpdate,
      ExampleEventLoggingSettings settings) {
    if (!settings.isEnabled()) {
      return;
    }

    final List<ScoutEventEntity> events = matchUpdate.getEvents();
    if (events == null || events.isEmpty()) {
      return;
    }

    final long matchId = matchUpdate.getEventId().getEventId();
    for (final ScoutEventEntity event : events) {
      if (!settings.matchesType(event.getTypeId())) {
        continue;
      }
      logger.info(
          "Scout event: match={} type={} id={} {}",
          matchId,
          event.getTypeId(),
          event.getId(),
          event);
    }
  }
}
