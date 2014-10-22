package ml.tchat.core.event;

import ml.tchat.core.Connection;
import ml.tchat.core.db.User;

/**
 *
 */
public class PrivateMessageReceivedEvent extends Event {

  private final User user;
  private final String message;

  public PrivateMessageReceivedEvent(Connection connection, User user, String message) {
    super(connection);
    this.user = user;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public User getUser() {
    return user;
  }

}
