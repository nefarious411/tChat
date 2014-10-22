package ml.tchat.core.db

import org.pircbotx.PircBotX
import org.pircbotx.hooks.events.MessageEvent
import org.pircbotx.hooks.events.PrivateMessageEvent


interface ChatDao {
  void storePrivateMessage(PrivateMessageEvent<PircBotX> event)

  User findOrCreateUser(String nick)

  Channel findOrCreateChannel(String name)

  Message createMessage(User user, Channel channel, long timestamp, String messageText, specialUserData)
}
