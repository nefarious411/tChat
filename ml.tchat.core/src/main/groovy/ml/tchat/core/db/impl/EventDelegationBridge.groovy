package ml.tchat.core.db.impl

import com.google.common.eventbus.EventBus
import com.google.inject.persist.Transactional
import ml.tchat.core.db.Channel
import ml.tchat.core.db.ChatDao
import ml.tchat.core.db.User
import ml.tchat.core.event.ConnectionReadyEvent
import ml.tchat.core.event.LoginUnsuccessfulEvent
import ml.tchat.core.event.MessageReceivedEvent
import ml.tchat.core.event.PrivateMessageReceivedEvent
import ml.tchat.core.internal.ConnectionImpl
import org.apache.log4j.Logger
import org.pircbotx.PircBotX
import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.hooks.events.MessageEvent
import org.pircbotx.hooks.events.MotdEvent
import org.pircbotx.hooks.events.NoticeEvent
import org.pircbotx.hooks.events.PrivateMessageEvent

import javax.inject.Inject

/**
 * Bridges incoming twitch events to the backing database
 */
public class EventDelegationBridge extends ListenerAdapter<PircBotX> {
  private static final Logger logger = Logger.getLogger(EventDelegationBridge.class);

  /*
  It's ugly, but twitch sends private messages to you right before
  the actual message comes in.  As long as we're receiving these events
  in a synchronous (blocking) way, these should be ordered correctly
   */
  private def specialUserBuffer = [:]

  @Inject
  private EventBus eventBus;
  @Inject
  private ChatDao chatDao;

  private ConnectionImpl connection;

  public void setConnection(ConnectionImpl connection) {
    this.connection = connection;
  }

  @Override
  public void onNotice(NoticeEvent<PircBotX> event) throws Exception {
    if (event.getMessage().contains("Login unsuccessful")) {
      eventBus.post(new LoginUnsuccessfulEvent(connection));
    }
  }

  @Override
  public void onMessage(MessageEvent<PircBotX> event) throws Exception {
    Channel channel = chatDao.findOrCreateChannel(event.getChannel().getName());
    User user = chatDao.findOrCreateUser(event.getUser().getNick());

    def message = chatDao.createMessage(user,
        channel,
        event.getTimestamp(),
        event.getMessage(),
        specialUserBuffer)

    specialUserBuffer.clear()

    eventBus.post(new MessageReceivedEvent(connection, message))
  }

  @Override
  public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
    //return
    def privateMessageParts = event.getMessage().split(/\s+/);

    if (privateMessageParts[0] == "SPECIALUSER") {
      if (!specialUserBuffer[privateMessageParts[1]]) {
        specialUserBuffer[privateMessageParts[1]] = []
      }
      specialUserBuffer[privateMessageParts[1]] << privateMessageParts[2]
    }

    chatDao.storePrivateMessage(event);
    eventBus.post(new PrivateMessageReceivedEvent(connection,
        chatDao.findOrCreateUser(event.getUser().getNick()),
        event.getMessage()));
  }

  @Override
  public void onMotd(MotdEvent<PircBotX> event) throws Exception {
    eventBus.post(new ConnectionReadyEvent(connection));
  }
}
