package ml.tchat.core.event;

import ml.tchat.core.Connection;
import ml.tchat.core.model.Channel;
import ml.tchat.core.model.User;

public class MessageReceivedEvent extends Event {

  private final Connection connection;
  private final Channel channel;
  private final User user;
  private final String message;

  public MessageReceivedEvent(Connection connection, Channel channel, User user, String message) {
    super(connection);
    this.connection = connection;
    this.channel = channel;
    this.user = user;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public User getUser() {
    return user;
  }

  public Channel getChannel() {
    return channel;
  }
}
