package ml.tchat.core.db.impl

import org.neo4j.graphdb.Node

class EntityMetaClass extends DelegatingMetaClass {
  EntityMetaClass(Class theClass) {
    super(theClass)
  }

  @Override
  Object getProperty(Object object, String property) {
    if (!isBackedProperty(object, property)) {
      return super.getProperty(object, property)
    }
    Node node = super.getProperty(object, 'underlyingNode')

    def tx = node.graphDatabase.beginTx()
    try {
      def val = node.hasProperty(property) ? node.getProperty(property) : null
      tx.success()
      return val
    } finally {
      tx.close()
    }
  }

  @Override
  void setProperty(Object object, String property, Object newValue) {
    if (!isBackedProperty(object, property)) {
      super.setProperty(object, property, newValue)
    }
    Node node = super.getProperty(object, 'underlyingNode')

    def tx = node.graphDatabase.beginTx()
    try {
      node.setProperty(property, newValue)
      tx.success()
    } finally {
      tx.close()
    }
  }

  private boolean isBackedProperty(object, propertyName) {
    object.getClass().declaredFields.grep { !it.synthetic }.find { it.name == propertyName }
  }
}
