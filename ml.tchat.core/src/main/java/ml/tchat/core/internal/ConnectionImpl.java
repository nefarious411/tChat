package ml.tchat.core.internal;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ml.tchat.core.Connection;
import ml.tchat.core.event.ConnectionReadyEvent;
import org.apache.log4j.Logger;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.output.OutputCAP;
import org.pircbotx.output.OutputDCC;
import org.pircbotx.output.OutputIRC;
import org.pircbotx.output.OutputRaw;

import java.io.IOException;

public class ConnectionImpl implements Connection {
  private static final Logger logger = Logger.getLogger(ConnectionImpl.class);
  private static final int TWITCH_CLIENT_VERSION = 1;

  //public static final String HOSTNAME = "irc.twitch.tv";
  public static final String HOSTNAME = "127.0.0.1";
  public static final int PORT = 6667;
  public static final boolean IS_SSL = false;


  private final String username;
  private final String oauthToken;
  private final EventBus eventBus;

  private PircBotX bot;
  private Thread connectionThread;
  private Runnable connectionRunnable = new Runnable() {
    @Override
    public void run() {
      if (bot != null) {
        try {
          bot.startBot();
        } catch (IOException e) {
          logger.warn("Problem with connection", e);
        } catch (IrcException e) {
          logger.warn("Problem with connection", e);
        }
      }
    }
  };

  /**
   * Implementation of Connection
   * <p>
   * This class should typically be created/managed
   * by the ConnectionManager class
   * </p>
   *
   * @param eventBus
   * @param username
   * @param oauthToken
   * @see ml.tchat.core.ConnectionManager
   */
  public ConnectionImpl(EventBus eventBus, String username, String oauthToken) {
    this.eventBus = eventBus;
    this.username = username;
    this.oauthToken = oauthToken;

    eventBus.register(this);
  }

  /**
   * Starts connection in separate Thread
   */
  public void connect() {
    if (isConnected()) {
      logger.warn("Connection already established.");
      return;
    }

    Configuration configuration = new Builder()
        .setName(username)
        .setLogin(username)
        .addListener(new EventDelegationBridge(eventBus, this))
        .setServer(HOSTNAME, PORT, oauthToken)
        .buildConfiguration();
    this.bot = new PircBotX(configuration);

    connectionThread = new Thread(connectionRunnable);
    connectionThread.start();
  }

  /**
   * Disconnects from Twitch
   */
  public void disconnect() {
    if (!isConnected()) {
      logger.warn("Not connected");
      return;
    }
    bot.stopBotReconnect();
    bot.sendRaw().rawLine("QUIT");

    try {
      // give thread 5 seconds to join
      connectionThread.join(5000L);
    } catch (InterruptedException e) {
      logger.warn("Cannot join thread", e);
    }

    bot = null;
    connectionThread = null;
  }

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }

  /**
   * @return true if connected, false otherwise
   */
  public boolean isConnected() {
    return bot != null && bot.isConnected();
  }

  @Override
  public OutputIRC sendIRC() {
    return bot.sendIRC();
  }

  @Override
  public OutputRaw sendRaw() {
    return bot.sendRaw();
  }

  @Override
  public OutputCAP sendCAP() {
    return bot.sendCAP();
  }

  @Override
  public OutputDCC sendDCC() {
    return bot.sendDCC();
  }

  @Subscribe
  public void onConnectionReady(ConnectionReadyEvent event) {
    // only operation on this connection.
    if (event.getConnection() == this) {
      logger.info("Connection established");
      bot.sendRaw().rawLine("TWITCHCLIENT " + TWITCH_CLIENT_VERSION);
    }
  }
}
