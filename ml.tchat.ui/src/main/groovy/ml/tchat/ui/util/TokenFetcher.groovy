package ml.tchat.ui.util

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import groovy.text.SimpleTemplateEngine as STE
import javax.inject.Singleton
import org.apache.log4j.Logger

/**
 *
 */
@Singleton
class TokenFetcher {
  private static final Logger logger = Logger.getLogger(TokenFetcher)

  public interface HandlerRegistration {
    public void unregister();
  }

  static final class SimpleServer implements Runnable {
    private int port;
    private EventBus eventBus;

    private LinkedList<Socket> openClients = new LinkedList<Socket>();
    private boolean cleaningUp = false;

    public SimpleServer(EventBus eventBus, int port) {
      this.port = port;
      this.eventBus = eventBus
    }

    @Override
    public void run() {
      ServerSocket serverSocket
      try {
        serverSocket = new ServerSocket(port);
        logger.debug("Token Server started on port: " + port);
      } catch (IOException e) {
        logger.error("Could not listen on port: " + port, e)
        return;
      }

      while (!Thread.interrupted()) {
        Socket clientSocket;
        serverSocket.setSoTimeout(1000)
        try {
          clientSocket = serverSocket.accept()
          logger.debug("Got new client connection: " + clientSocket.inetAddress.toString());
          openClients.add(clientSocket);
          (new Thread(new ConnectionWorker(eventBus, clientSocket))).start();
        } catch (IOException e) {
        }
      }
      try {
        logger.debug("Closing all open connections...");
        cleaningUp = true;
        for (Socket s : openClients) {
          s.close();
        }
        serverSocket.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        logger.error('error closing connections', e)
      }
      logger.debug("Token Server stopped on port: " + port);
    }

    protected void removeClient(Socket s) {
      // If we are cleaning up, removal will cause a ConcurrentModificationException.
      if (!cleaningUp) {
        openClients.remove(s);
      }
    }

    private class ConnectionWorker implements Runnable {
      private Socket client;
      private EventBus eventBus

      public ConnectionWorker(EventBus eventBus, Socket s) {
        this.client = s;
        this.eventBus = eventBus
      }

      @Override
      public void run() {
        try {
          PrintWriter outWriter = new PrintWriter(client.getOutputStream(), true);

          BufferedReader inReader = new BufferedReader(
              new InputStreamReader(
                  client.getInputStream()));
          String inputLine;

          inputLine = inReader.readLine()

          def token = getToken(inputLine)
          def responseText
          if (!token) {
            // no token, redirect
            responseText = getClass().getResource('TokenRedirect.html').text
          } else {
            // have token
            responseText = getClass().getResource('TokenResponse.html').text
          }

          def ste = new STE()
          def template = ste.createTemplate(responseText)
          template.make([token: token])
              .writeTo(outWriter)


          if (token != null && token != '') {
            eventBus.post(new TokenReceivedEvent(token))
          }

          outWriter.close();
          inReader.close();

          client.close();
        } catch (IOException e) {}
        SimpleServer.this.removeClient(client);
      }

      private String getToken(request) {
        // Check if token might be in there
        int start = request.indexOf("/token/");
        if (start == -1) {
          // if not, return immediately
          return "";
        }
        start += "/token/".length();
        int end = request.indexOf(" ", start);
        int end2 = request.indexOf("/", start);
        // If a / comes earlier than a space, use that
        if (end2 != -1 && end2 < end) {
          end = end2;
        }
        if (end == -1) {
          return "";
        }
        return request.substring(start, end).trim();
      }
    }
  }

  private static final int TCHAT_PORT = 61355
  private Thread tokenServer;
  private EventBus eventBus = new EventBus()
  private boolean running = false
  private String lastTokenReceived
  private List<TokenReceivedHandler> externalHandlers = []

  public synchronized void start() {
    if (running) {
      logger.error("server already running")
      return
    }
    running = true
    eventBus.register(this)
    logger.debug("Starting Token Server");
    tokenServer = new Thread(new SimpleServer(eventBus, TCHAT_PORT));
    tokenServer.start();
  }

  public synchronized void stop() {
    if (running) {
      logger.debug("Stopping Token Server");
      tokenServer.interrupt();
      tokenServer = null;
      running = false
      eventBus.unregister(this)
    }
  }

  public HandlerRegistration addTokenReceivedHandler(TokenReceivedHandler handler) {
    externalHandlers << handler
    return [
        unregister: {
          externalHandlers.remove(handler)
        }] as HandlerRegistration
  }

  public synchronized boolean isRunning() {
    return running
  }

  public String getLastTokenReceived() {
    return lastTokenReceived
  }

  /**
   * Private method.  Do not call
   */
  @Subscribe
  public void onTokenReceived(TokenReceivedEvent event) {
    stop()
    lastTokenReceived = event.token
    externalHandlers.each {
      it.onTokenReceived(event)
    }
  }
}
