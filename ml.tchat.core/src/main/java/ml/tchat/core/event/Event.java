package ml.tchat.core.event;

import ml.tchat.core.Connection;

public abstract class Event<E extends org.pircbotx.hooks.Event> {
  private final Connection connection;
  private final E origEvent;

  public Event(Connection connection) {
    this(connection, null);
  }

  public Event(Connection connection, E origEvent) {
    this.connection = connection;
    this.origEvent = origEvent;
  }

  /**
   * Gets the associated connection to this event
   *
   * @return Connection
   */
  public Connection getConnection() {
    return connection;
  }

  protected E getOrigEvent() {
    return origEvent;
  }
}
