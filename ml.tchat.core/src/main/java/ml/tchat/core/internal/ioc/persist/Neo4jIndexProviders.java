package ml.tchat.core.internal.ioc.persist;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.inject.persist.Transactional;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.RelationshipIndex;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

public class Neo4jIndexProviders {

  @Inject
  private Provider<IndexManager> indexManagerProvider;


  @Transactional
  public Index<Node> indexForNodes(final String name) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    return indexManagerProvider.get().forNodes(name);
  }


  /*
  public static Provider<Index<Node>> indexForNodes(final String name) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    return new Provider<Index<Node>>() {
      @Inject
      private IndexManager indexManager;

      @Override
      public Index<Node> get() {
        return indexManager.forNodes(name);
      }
    };
  }
  */


  public static Provider<Index<Node>> indexForNodes(final String name, Map<String, String> config) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    Preconditions.checkNotNull(config);

    final Map<String, String> configToUse = ImmutableMap.copyOf(config);

    return new Provider<Index<Node>>() {

      @Inject
      IndexManager indexManager;

      @Override
      public Index<Node> get() {
        return indexManager.forNodes(name, configToUse);
      }
    };
  }

  public static Provider<RelationshipIndex> indexForRelationships(final String name) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    return new Provider<RelationshipIndex>() {

      @Inject
      IndexManager indexManager;

      @Override
      public RelationshipIndex get() {
        return indexManager.forRelationships(name);
      }
    };
  }

  public static Provider<RelationshipIndex> indexForRelationships(final String name, Map<String, String> config) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
    Preconditions.checkNotNull(config);

    final Map<String, String> configToUse = ImmutableMap.copyOf(config);

    return new Provider<RelationshipIndex>() {

      @Inject
      IndexManager indexManager;

      @Override
      public RelationshipIndex get() {
        return indexManager.forRelationships(name, configToUse);
      }
    };
  }

  //private Neo4jIndexProviders() {
  //}
}
