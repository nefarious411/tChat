package ml.tchat.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import ml.tchat.ui.ioc.TChatModule;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.io.OutputStreamWriter;

/**
 *
 */
public class TChat extends Application {

  public static void main(String[] args) throws Exception {
    setupLogging();
    launch(args);
  }

  private static void setupLogging() {
    ConsoleAppender appender = new ConsoleAppender();
    appender.setLayout(new PatternLayout("%-4r [%t] %-5p %c %x - %m%n"));
    appender.setName("Console");
    appender.setWriter(new OutputStreamWriter(System.out));

    Logger.getRootLogger().setLevel(Level.TRACE);
    Logger.getRootLogger().addAppender(appender);

    logger.info("Logging started");
    logger.info("JavaFX version: " + com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
  }

  @Override
  public void start(Stage stage) throws Exception {

    Injector injector = Guice.createInjector(new TChatModule());
    ApplicationController applicationController = injector.getInstance(ApplicationController.class);
    applicationController.go(stage);
  }

  private static final Logger logger = Logger.getLogger(TChat.class);
}
