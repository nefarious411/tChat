package ml.tchat.core.ioc

import com.google.common.eventbus.EventBus
import com.google.inject.AbstractModule
import com.google.inject.Provides
import ml.tchat.core.Connection
import ml.tchat.core.config.ConfigurationManager
import ml.tchat.core.config.TChatConfig
import ml.tchat.core.db.Channel
import ml.tchat.core.db.ChatDao
import ml.tchat.core.db.Message
import ml.tchat.core.db.User
import ml.tchat.core.db.impl.ChatDaoImpl
import ml.tchat.core.db.impl.ChatPersistService
import ml.tchat.core.db.impl.EntityMetaClass
import ml.tchat.core.internal.ConnectionImpl
import ml.tchat.core.internal.ioc.persist.Neo4jPersistModule
import ml.tchat.core.internal.ioc.persist.Neo4jPersistService
import org.codehaus.groovy.runtime.InvokerHelper

import javax.inject.Named
import javax.inject.Singleton as ISingleton

/**
 * Core Guice Module for tChat
 */
class TChatCoreModule extends AbstractModule {


  static {
    [User, Message, Channel].each { entityClass ->
      def gemc = new EntityMetaClass(entityClass)
      gemc.initialize()
      InvokerHelper.metaRegistry.setMetaClass(entityClass, gemc)
    }
  }

  @Override
  protected void configure() {
    bind(ConfigurationManager).asEagerSingleton()
    bind(Connection).to(ConnectionImpl)
    bind(ChatDao).to(ChatDaoImpl).in(ISingleton)

    install(Neo4jPersistModule.embedded(databaseDirectory(configDirectory())))

    bind(Neo4jPersistService).to(ChatPersistService)
  }

  @Provides
  @ISingleton
  TChatConfig config(ConfigurationManager configurationManager) {
    return new TChatConfig(configurationManager.load("config.xml"));
  }

  @Provides
  @Named(InjectConstants.CONFIG_DIRECTORY)
  public String configDirectory() {
    String dir = System.getProperty("user.home") +
        "${File.separator}.tchat${File.separator}"
    new File(dir).mkdirs();
    return dir;
  }

  @Provides
  @Named(InjectConstants.DATABASE_DIRECTORY)
  public String databaseDirectory(@Named(InjectConstants.CONFIG_DIRECTORY) String configDirectory) {
    def dataDir = new File(configDirectory + File.separator + "data")
    return dataDir.canonicalPath
  }

  @Provides
  @ISingleton
  protected EventBus coreEventBus() {
    return new EventBus("tChatCore")
  }
}
