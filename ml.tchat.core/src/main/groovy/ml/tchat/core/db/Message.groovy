package ml.tchat.core.db

import ml.tchat.core.db.impl.RelTypes
import org.neo4j.graphdb.Direction

@Labeled('message')
class Message extends GraphEntity {

  String message
  long timestamp
  boolean subscriber
  boolean turbo

  public User getUser() {
    doInTransaction {
      return new User(underlyingNode: underlyingNode.getSingleRelationship(RelTypes.SAID, Direction.INCOMING).startNode)
    }
  }

  public Channel getChannel() {
    doInTransaction {
      return new Channel(underlyingNode: underlyingNode.getSingleRelationship(RelTypes.IN, Direction.OUTGOING).endNode)
    }
  }
}