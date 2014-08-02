package ml.tchat.core;


import com.google.common.eventbus.EventBus;
import org.pircbotx.output.OutputCAP;
import org.pircbotx.output.OutputDCC;
import org.pircbotx.output.OutputIRC;
import org.pircbotx.output.OutputRaw;

public interface Connection {
  /**
   * Starts connection in separate Thread
   */
  void connect();

  /**
   * Disconnects from Twitch
   */
  void disconnect();

  /**
   * @return true if connected, false otherwise
   */
  boolean isConnected();

  /**
   * @return returns associated EventBus to this connection
   */
  EventBus getEventBus();

  OutputIRC sendIRC();

  OutputRaw sendRaw();

  OutputCAP sendCAP();

  OutputDCC sendDCC();
}
