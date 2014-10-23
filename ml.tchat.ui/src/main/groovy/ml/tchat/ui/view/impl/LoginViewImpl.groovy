package ml.tchat.ui.view.impl

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Modality
import javafx.stage.Stage
import ml.tchat.ui.util.FxUtil
import ml.tchat.ui.util.TokenFetcher
import ml.tchat.ui.util.TokenReceivedEvent
import ml.tchat.ui.util.TokenReceivedHandler
import ml.tchat.ui.view.ShellView
import org.controlsfx.dialog.Dialogs

import javax.inject.Inject
import java.awt.*

class LoginViewImpl {

  private Stage stage

  @Inject
  private ShellView shellView

  @Inject
  private TokenFetcher tokenFetcher

  @FXML
  private WebView webView

  public void show() {
    if (stage == null) {
      stage = new Stage()
      stage.setTitle("Login")
      def node = FxUtil.createAndBindUi(this)
      stage.scene = new Scene(node, 640, 480)
      stage.initModality(Modality.APPLICATION_MODAL)
      stage.initOwner(shellView.asNode().scene.window)
    }
    stage.show()
  }

  @FXML
  public void connect() {
    TokenFetcher.HandlerRegistration registration
    registration = tokenFetcher.addTokenReceivedHandler(new TokenReceivedHandler() {
      @Override
      void onTokenReceived(TokenReceivedEvent event) {
        Platform.runLater {
          Dialogs.create()
              .owner(stage)
              .title("Received Token")
              .masthead("Look, We have a token!")
              .message("Token Received: ${event.token}")
              .showInformation();
        }
        registration.unregister()
      }
    });
    Desktop.desktop.browse(new URI("https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&client_id=owmac5v0oqx6ee2msmwbwf906ta6p1v&redirect_uri=http%3A%2F%2F127.0.0.1%3A61355%2Ftoken%2F&scope=chat_login+channel_commercial+channel_editor+user_read+channel_subscriptions"))
    //webView.engine.load()
    tokenFetcher.start()
  }
}
