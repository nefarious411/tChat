package ml.tchat.core.internal.ioc.persist;

import com.google.common.base.Preconditions;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Neo4jPersistService implements Provider<GraphDatabaseService>, PersistService, UnitOfWork {

  private final GraphDatabaseBuilder builder;
  private GraphDatabaseService graph;

  @Inject
  public Neo4jPersistService(@Neo4j GraphDatabaseBuilder builder) {
    this.builder = builder;
  }

  public GraphDatabaseService get() {
    return graph;
  }

  public void begin() {
    // Neo4j has no notion of unit-of-work
  }

  public void end() {
    // Neo4j has no notion of unit-of-work
  }

  public void start() {
    Preconditions.checkState(graph == null, "Persistence service was already initialized.");
    graph = builder.newGraphDatabase();
  }

  public void stop() {
    if (graph != null) {
      graph.shutdown();
    }
  }
}
