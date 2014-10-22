package ml.tchat.core.db.impl

import com.google.inject.persist.Transactional
import ml.tchat.core.db.Channel
import ml.tchat.core.db.ChatDao
import ml.tchat.core.db.Message
import ml.tchat.core.db.User
import org.neo4j.graphdb.DynamicLabel
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.Node
import org.neo4j.graphdb.Relationship
import org.pircbotx.PircBotX
import org.pircbotx.hooks.events.PrivateMessageEvent

import javax.inject.Inject

class ChatDaoImpl implements ChatDao {

  @Inject
  private GraphDatabaseService database

  @Override
  @Transactional
  void storePrivateMessage(PrivateMessageEvent<PircBotX> event) {
    if (event.getUser().nick == 'jtv') {
      def parts = event.message.split(/\s+/)
      switch (parts[0]) {
        case 'USERCOLOR':
          // USERCOLOR nefarious411 #2E8A56
          User special = findOrCreateUser(parts[1])
          special.userColor = parts[2]
          break
      }
    }
  }

  @Override
  @Transactional
  public User findOrCreateUser(String nick) {
    def label = DynamicLabel.label('user')
    Node userNode = getSingleNode(label, 'nick', nick)

    if (userNode != null) {
      return new User(underlyingNode: userNode)
    }

    userNode = database.createNode(label)
    User user = new User(underlyingNode: userNode)
    user.nick = nick

    return user
  }

  @Override
  @Transactional
  public Channel findOrCreateChannel(String channelName) {
    def label = DynamicLabel.label("channel")
    Node channelNode = getSingleNode(label, 'channelName', channelName)

    if (channelNode != null) {
      return new Channel(underlyingNode: channelNode)
    }

    channelNode = database.createNode(label)
    Channel channel = new Channel(underlyingNode: channelNode)
    channel.name = channelName

    return channel
  }

  @Override
  @Transactional
  public Message createMessage(User user, Channel channel, long timestamp, String messageText, specialUserData) {
    if (user == null) {
      throw new IllegalArgumentException("null user")
    }
    if (channel == null) {
      throw new IllegalArgumentException("null channel")
    }
    Node userNode = user.underlyingNode
    Node channelNode = channel.underlyingNode
    Node messageNode = database.createNode(DynamicLabel.label("message"))
    Message message = new Message(underlyingNode: messageNode)
    message.message = messageText
    message.timestamp = timestamp

    Relationship said = userNode.createRelationshipTo(messageNode, RelTypes.SAID)
    Relationship inchan = messageNode.createRelationshipTo(channelNode, RelTypes.IN)

    specialUserData[user.nick].each {
      messageNode.setProperty(it, true)
    }

    return message
  }


  private Node getSingleNode(label, key, value) {
    def hits = database.findNodesByLabelAndProperty(label, key, value)

    for (Node node : hits) {
      return node
    }
    return null
  }
}
