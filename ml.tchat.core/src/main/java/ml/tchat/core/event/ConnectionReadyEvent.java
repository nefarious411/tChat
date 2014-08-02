package ml.tchat.core.event;

import ml.tchat.core.Connection;

public class ConnectionReadyEvent extends Event {

  public ConnectionReadyEvent(Connection connection) {
    super(connection);
  }
}
