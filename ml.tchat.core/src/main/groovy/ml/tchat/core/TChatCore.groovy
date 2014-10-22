package ml.tchat.core

import com.google.inject.Guice
import com.google.inject.Injector
import ml.tchat.core.config.TChatConfig
import ml.tchat.core.internal.ioc.persist.Neo4jPersistService
import ml.tchat.core.ioc.TChatCoreModule

class TChatCore {
  private static Injector injector
  private static connections = []

  static {
    injector = Guice.createInjector(new TChatCoreModule())
    Neo4jPersistService persistService = injector.getInstance(Neo4jPersistService)
    persistService.start()
    Runtime.getRuntime().addShutdownHook({
      persistService.stop()
    })
  }

  public static PluginRegistry getPluginRegistry() {
    return injector.getInstance(PluginRegistry)
  }

  public static Connection connect(String username, String oauthToken) {
    TChatConfig config = injector.getInstance(TChatConfig)
    config.setUsername(username)
    config.setOauthToken(oauthToken)

    Connection connection = injector.getInstance(Connection)
    connection.connect()
    connections << connection

    return connection
  }

  public static void disconnectAll() {
    connections.each {
      if (it.isConnected()) {
        it.disconnect();
      }
    }
  }
}
