package ml.tchat.core.event;

import ml.tchat.core.Connection;
import ml.tchat.core.db.Message;

public class MessageReceivedEvent extends Event {

  private final Message message;

  public MessageReceivedEvent(Connection connection, Message message) {
    super(connection);
    this.message = message;
  }

  public Message getMessage() {
    return message;
  }
}
