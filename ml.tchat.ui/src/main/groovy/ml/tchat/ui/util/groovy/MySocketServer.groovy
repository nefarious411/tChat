package ml.tchat.ui.util.groovy

import groovy.ui.GroovyMain
import org.apache.log4j.Logger

/**
 * majority is stolen from GroovySocketServer, but adapted to be able to stop
 * @see groovy.ui.GroovySocketServer
 */
class MySocketServer {
  private static final Logger logger = Logger.getLogger(MySocketServer)

  private URL url;
  private GroovyShell groovy;
  private boolean isScriptFile;
  private String scriptFilenameOrText;
  private boolean autoOutput;

  private Thread mainThread
  private transient boolean running = false

  public synchronized void start() {
    if (running) {
      throw new IllegalStateException("Already running")
    }
    logger.info("server starting")
    mainThread.start()
    running = true
  }

  public synchronized void stop() {
    if (running) {
      logger.info("server stopping")
      running = false
      mainThread.join()
      logger.info("server stopped")
    }
  }

  public synchronized boolean isRunning() {
    return running
  }

  /**
   * This creates and starts the socket server on a new Thread. There is no need to call run or spawn
   * a new thread yourself.
   * @param groovy
   *       The GroovyShell object that evaluates the incoming text. If you need additional classes in the
   *       classloader then configure that through this object.
   * @param isScriptFile
   *       Whether the incoming socket data String will be a script or a file path.
   * @param scriptFilenameOrText
   *       This will be a groovy script or a file location depending on the argument isScriptFile.
   * @param autoOutput
   *       whether output should be automatically echoed back to the client
   * @param port
   *       the port to listen on
   *
   */
  public MySocketServer(GroovyShell groovy, boolean isScriptFile, String scriptFilenameOrText, boolean autoOutput, int port) {
    println "constructed instance"
    this.groovy = groovy;
    this.isScriptFile = isScriptFile;
    this.scriptFilenameOrText = scriptFilenameOrText;
    this.autoOutput = autoOutput;
    try {
      url = new URL("http", InetAddress.getLocalHost().getHostAddress(), port, "/");
    } catch (IOException e) {
      e.printStackTrace();
    }
    mainThread = new Thread(new ServerRunnable())
  }

  class GroovyClientConnection implements Runnable {
    private Script script;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean autoOutputFlag;

    GroovyClientConnection(Script script, boolean autoOutput, Socket socket) throws IOException {
      this.script = script;
      this.autoOutputFlag = autoOutput;
      this.socket = socket;
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer = new PrintWriter(socket.getOutputStream());
      new Thread(this, "Groovy client connection - " + socket.getInetAddress().getHostAddress()).start();
    }

    public void run() {
      try {
        String line = null;
        script.setProperty("out", writer);
        script.setProperty("socket", socket);
        script.setProperty("init", Boolean.TRUE);
        while ((line = reader.readLine()) != null) {
          // System.out.println(line);
          script.setProperty("line", line);
          Object o = script.run();
          script.setProperty("init", Boolean.FALSE);
          if (o != null) {
            if ("success".equals(o)) {
              break; // to close sockets gracefully etc...
            } else {
              if (autoOutputFlag) {
                writer.println(o);
              }
            }
          }
          writer.flush();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          writer.flush();
          writer.close();
        } finally {
          try {
            socket.close();
          } catch (IOException e3) {
            e3.printStackTrace();
          }
        }
      }
    }
  }


  class ServerRunnable implements Runnable {
    /**
     * Runs this server. There is typically no need to call this method, as the object's constructor
     * creates a new thread and runs this object automatically.
     */
    public void run() {
      try {
        ServerSocket serverSocket = new ServerSocket(url.getPort());
        while (running) {
          // Create one script per socket connection.
          // This is purposefully not caching the Script
          // so that the script source file can be changed on the fly,
          // as each connection is made to the server.
          Script script;
          if (isScriptFile) {
            GroovyMain gm = new GroovyMain();
            script = groovy.parse(gm.getText(scriptFilenameOrText));
          } else {
            script = groovy.parse(scriptFilenameOrText);
          }
          new GroovyClientConnection(script, autoOutput, serverSocket.accept());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
