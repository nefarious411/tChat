package ml.tchat.ui

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.stage.WindowEvent
import ml.tchat.ui.event.ShutdownEvent
import ml.tchat.ui.ioc.InjectConstants
import ml.tchat.ui.view.ShellView
import org.apache.commons.configuration.FileConfiguration
import org.apache.log4j.Logger

import javax.inject.Inject
import javax.inject.Named

public class ApplicationController {
  private static final Logger logger = Logger.getLogger(ApplicationController.class)

  @Inject
  private ShellView shellView

  @Inject
  private EventBus eventBus

  @Inject
  @Named("i18n")
  private ResourceBundle messages

  @Inject
  @Named(InjectConstants.MAIN_CONFIG)
  private FileConfiguration mainConfiguration

  private Stage primaryStage


  public void go(Stage stage) {
    this.primaryStage = stage
    eventBus.register(this)

    primaryStage.setTitle(messages.getString("applicationTitle"));

    primaryStage.setScene(new Scene(shellView.asNode(), 800, 600));
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent windowEvent) {
        shutdown(null);
      }
    });
    //primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.show();
  }

  @Subscribe
  public void shutdown(ShutdownEvent event) {
    logger.debug("exit requested");

    // connectionManager.disconnect();

    if (primaryStage != null) {
      primaryStage.close();
    } else {
      logger.error("primaryStage is null.  Calling Platform.exit");
      Platform.exit();
    }
  }
}
