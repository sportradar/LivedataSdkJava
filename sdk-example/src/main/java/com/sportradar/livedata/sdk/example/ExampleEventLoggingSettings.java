package com.sportradar.livedata.sdk.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Runtime settings for scout event logging in the example listener.
 */
public class ExampleEventLoggingSettings {

  private volatile boolean enabled = true;

  private final Set<Integer> typeFilter = new HashSet<>();

  /**
   * Returns whether scout event logging is enabled.
   *
   * @return true when scout events should be logged
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Enables or disables scout event logging.
   *
   * @param enabled new enabled state
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Returns whether the given event type should be logged.
   *
   * @param typeId scout event type id
   * @return true when the event type passes the current filter
   */
  public synchronized boolean matchesType(int typeId) {
    return typeFilter.isEmpty() || typeFilter.contains(typeId);
  }

  /**
   * Clears any type filter so all event types are logged.
   */
  public synchronized void clearTypeFilter() {
    typeFilter.clear();
  }

  /**
   * Restricts logging to the given event type ids.
   *
   * @param typeIds event type ids to log
   */
  public synchronized void setTypeFilter(Set<Integer> typeIds) {
    typeFilter.clear();
    typeFilter.addAll(typeIds);
  }

  /**
   * Returns the active type filter for display purposes.
   *
   * @return unmodifiable view of filtered type ids; empty when all types are logged
   */
  public synchronized Set<Integer> getTypeFilter() {
    return Collections.unmodifiableSet(new HashSet<>(typeFilter));
  }
}
