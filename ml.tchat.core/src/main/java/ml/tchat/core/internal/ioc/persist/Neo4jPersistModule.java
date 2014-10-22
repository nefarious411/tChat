package ml.tchat.core.internal.ioc.persist;

import com.google.inject.Provides;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import org.aopalliance.intercept.MethodInterceptor;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.IndexManager;

/**
 *
 */
public class Neo4jPersistModule extends PersistModule {


  public static Neo4jPersistModule embedded(String path) {
    return new Neo4jPersistModule(new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(path));
  }

  public static Neo4jPersistModule remote(String uri) {
    return null;
  }

  private Neo4jLocalTnxInterceptor transactionInterceptor;
  private final GraphDatabaseBuilder builder;

  public Neo4jPersistModule(GraphDatabaseBuilder builder) {
    this.builder = builder;
  }


  @Override
  protected void configurePersistence() {
    bind(GraphDatabaseBuilder.class).annotatedWith(Neo4j.class).toInstance(builder);

    bind(PersistService.class).to(Neo4jPersistService.class);
    bind(UnitOfWork.class).to(Neo4jPersistService.class);
    bind(GraphDatabaseService.class).toProvider(Neo4jPersistService.class);

    transactionInterceptor = new Neo4jLocalTnxInterceptor();
    requestInjection(transactionInterceptor);
  }

  @Override
  protected MethodInterceptor getTransactionInterceptor() {
    return transactionInterceptor;
  }

  @Provides
  IndexManager provideIndexManager(GraphDatabaseService graph) {
    return graph.index();
  }
}
