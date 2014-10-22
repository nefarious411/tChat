package ml.tchat.core.event;

import ml.tchat.core.Connection;

public class LoginUnsuccessfulEvent extends Event {

  public LoginUnsuccessfulEvent(Connection connection) {
    super(connection);
  }
}
