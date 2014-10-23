package ml.tchat.ui.util

import ml.tchat.ui.util.groovy.MySocketServer

import javax.inject.Singleton

/**
 * Small HTTP Server to capture OAuth token
 */
@Singleton
class OAuthHttpServer {

  private MySocketServer server = new MySocketServer(
      new GroovyShell(),
      false,
      "println line.reverse()",
      true,
      61355)

  public OAuthHttpServer() {
    Runtime.getRuntime().addShutdownHook({
      stop()
    })
  }


  public void start() {
    if (!server.isRunning()) {
      server.start()
    }
  }

  public void stop() {
    if (server.isRunning()) {
      server.stop()
    }
  }

}
