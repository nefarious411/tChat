package ml.tchat.core.event;

import ml.tchat.core.Connection;

public abstract class Event {
  private final Connection connection;
  private final long timestamp;

  public Event(Connection connection) {
    this.connection = connection;
    this.timestamp = System.currentTimeMillis();
  }

  /**
   * Gets the associated connection to this event
   *
   * @return Connection
   */
  public Connection getConnection() {
    return connection;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
