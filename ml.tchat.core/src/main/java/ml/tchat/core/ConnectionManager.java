package ml.tchat.core;


import com.google.common.eventbus.EventBus;
import ml.tchat.core.internal.ConnectionImpl;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class ConnectionManager {

  private static final Logger logger = Logger.getLogger(ConnectionManager.class);
  private static Set<Connection> connections = new HashSet<Connection>();

  /**
   * Creates and returns a new Connection instance.
   * <p/>
   * Connection instance does not start in "connected" state.
   *
   * @param username
   * @param oauthToken
   * @return Connection
   */
  public static Connection createConnection(String username, String oauthToken) {
    return createConnection(new EventBus(), username, oauthToken);
  }

  /**
   * Creates and returns a new Connection instance
   * <p/>
   * Connection instance does not start in a "connected" state.
   *
   * @param eventBus
   * @param username
   * @param oauthToken
   * @return
   */
  public static Connection createConnection(EventBus eventBus, String username, String oauthToken) {
    Connection connection = new ConnectionImpl(eventBus, username, oauthToken);
    connections.add(connection);
    return connection;
  }

  /**
   * Disconnects all managed connections
   */
  public static void disconnectAll() {
    for (Connection connection : connections) {
      if (connection.isConnected()) {
        connection.disconnect();
      }
    }
  }
}
