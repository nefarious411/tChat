package ml.tchat.core.internal;


import com.google.common.eventbus.EventBus;
import ml.tchat.core.event.ConnectionReadyEvent;
import ml.tchat.core.event.MessageReceivedEvent;
import ml.tchat.core.model.factory.ModelTypeConverter;
import org.apache.log4j.Logger;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.MotdEvent;

public class EventDelegationBridge extends ListenerAdapter<PircBotX> {
  private static final Logger logger = Logger.getLogger(EventDelegationBridge.class);
  private final EventBus eventBus;
  private final ConnectionImpl connection;

  EventDelegationBridge(EventBus eventBus, ConnectionImpl connection) {
    this.eventBus = eventBus;
    this.connection = connection;
  }


  @Override
  public void onMessage(MessageEvent<PircBotX> event) throws Exception {
    eventBus.post(new MessageReceivedEvent(connection,
        ModelTypeConverter.convert(event.getChannel()),
        ModelTypeConverter.convert(event.getUser()),
        event.getMessage()));
  }

  @Override
  public void onMotd(MotdEvent<PircBotX> event) throws Exception {
    eventBus.post(new ConnectionReadyEvent(connection));
  }
}
