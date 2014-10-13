package ml.tchat.ui

import com.google.inject.Guice
import com.google.inject.Injector
import ml.tchat.ui.ioc.TChatModule
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

/**
 *
 */
class TChat {

  private static final Logger logger = Logger.getLogger(TChat.class);

  static void main(String[] args) {
    setupLogging()

    Injector injector = Guice.createInjector(new TChatModule())
    ApplicationController applicationController = injector.getInstance(ApplicationController.class)
    applicationController.go()
  }

  private static setupLogging() {
    ConsoleAppender appender = new ConsoleAppender()
    appender.layout = new PatternLayout("%-4r [%t] %-5p %c %x - %m%n")
    appender.name = "Console"
    appender.writer = new OutputStreamWriter(System.out)

    Logger.rootLogger.level = Level.TRACE
    Logger.rootLogger.addAppender(appender)

    logger.info("Logging started")
  }
}
