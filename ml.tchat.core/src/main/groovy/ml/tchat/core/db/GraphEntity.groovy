package ml.tchat.core.db

import org.neo4j.graphdb.Node

abstract class GraphEntity {

  protected Node underlyingNode

  protected doInTransaction(closure) {
    def tx = underlyingNode.graphDatabase.beginTx()
    try {
      def val = closure()
      tx.success()
      return val
    } finally {
      tx.close()
    }
  }

  @Override
  boolean equals(Object obj) {
    return super.equals(obj)
  }

  @Override
  int hashCode() {
    return super.hashCode()
  }

  @Override
  String toString() {
    return super.toString()
  }
}
