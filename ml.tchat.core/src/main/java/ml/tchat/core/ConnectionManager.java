package ml.tchat.core;


import org.apache.log4j.Logger;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;


public class ConnectionManager {

  private static final Logger logger = Logger.getLogger(ConnectionManager.class);
  public static final String HOSTNAME = "irc.twitch.tv";
  public static final int PORT = 6667;
  public static final boolean IS_SSL = false;


  private TwitchListener twitchListener;

  private PircBotX connection;
  private Thread connectionThread;
  private Runnable connectionRunnable = new Runnable() {
    @Override
    public void run() {
      if (connection != null) {
        try {
          connection.startBot();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (IrcException e) {
          e.printStackTrace();
        }
      }
    }
  };

  public void connect(String username, String oauthToken) {
    Configuration configuration = new Builder()
        .setName(username)
        .setLogin(username)
        .addListener(twitchListener)
        .setServer(HOSTNAME, PORT, oauthToken)
        .buildConfiguration();
    connection = new PircBotX(configuration);

    connectionThread = new Thread(connectionRunnable);
    connectionThread.start();
  }

  public void disconnect() {
    if (!isConnected()) {
      logger.info("Not connected");
      return;
    }
    connection.stopBotReconnect();
    connection.sendRaw().rawLine("QUIT");

    try {
      connectionThread.join(5000L);
    } catch (InterruptedException e) {
      logger.warn("Cannot join thread", e);
    }
    connection = null;
    connectionThread = null;
  }

  public boolean isConnected() {
    return connection != null && connection.isConnected();
  }
}
