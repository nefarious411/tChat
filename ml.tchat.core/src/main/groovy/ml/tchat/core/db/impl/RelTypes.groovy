package ml.tchat.core.db.impl

import org.neo4j.graphdb.RelationshipType

/**
 *
 */
enum RelTypes implements RelationshipType {
  SAID,       // user    said  message
  IN,         // message in    channel
  SPECIAL,    // possible subscriber
  SUBSCRIBED, // subscribed to channel
}
