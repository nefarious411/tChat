package ml.tchat.core.event;

import ml.tchat.core.Connection;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class MessageReceivedEvent extends Event<MessageEvent<?>> {

  public MessageReceivedEvent(Connection connection, MessageEvent event) {
    super(connection, event);
  }

  public String getMessage() {
    return getOrigEvent().getMessage();
  }

  public User getUser() {
    return getOrigEvent().getUser();
  }

  public Channel getChannel() {
    return getOrigEvent().getChannel();
  }
}
