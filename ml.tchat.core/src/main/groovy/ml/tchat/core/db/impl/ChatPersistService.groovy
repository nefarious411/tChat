package ml.tchat.core.db.impl

import com.google.inject.persist.Transactional
import ml.tchat.core.internal.ioc.persist.Neo4j
import ml.tchat.core.internal.ioc.persist.Neo4jPersistService
import org.neo4j.graphdb.DynamicLabel
import org.neo4j.graphdb.factory.GraphDatabaseBuilder

import javax.inject.Inject
import java.util.concurrent.TimeUnit

@javax.inject.Singleton
class ChatPersistService extends Neo4jPersistService {

  @Inject
  public ChatPersistService(@Neo4j GraphDatabaseBuilder builder) {
    super(builder)
  }

  @Override
  void start() {
    super.start()
    applyConstraints()
    awaitIndexes()
  }

  @Override
  void stop() {
    println "Stopping database"
    super.stop()
  }

  @Transactional
  public applyConstraints() {
    def schema = get().schema()

    schema.with {
      if (!getConstraints(DynamicLabel.label('user')).iterator().hasNext()) {
        constraintFor(DynamicLabel.label('user'))
            .assertPropertyIsUnique('nick')
            .create()
      }
      if (!getConstraints(DynamicLabel.label('channel')).iterator().hasNext()) {
        constraintFor(DynamicLabel.label('channel'))
            .assertPropertyIsUnique('name')
            .create()
      }
    }
  }

  @Transactional
  public awaitIndexes() {
    def schema = get().schema()
    try {
      schema.awaitIndexesOnline(2, TimeUnit.MINUTES)
    } catch (IllegalStateException e) {
      // attempted to wait
    }
  }
}
