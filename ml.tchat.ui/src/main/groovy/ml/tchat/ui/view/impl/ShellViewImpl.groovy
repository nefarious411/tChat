package ml.tchat.ui.view.impl

import com.google.common.eventbus.EventBus
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import ml.tchat.ui.event.ShutdownEvent
import ml.tchat.ui.util.FxUtil
import ml.tchat.ui.view.ShellView

import javax.inject.Inject
import javax.inject.Provider

public class ShellViewImpl implements ShellView {

  @Inject
  private EventBus eventBus

  @Inject
  private Provider<LoginViewImpl> loginViewProvider

  private BorderPane node


  @Override
  public Node asNode() {
    if (node == null) {
      node = FxUtil.createAndBindUi(this)

      node.center = new ChatViewImpl().asNode()
    }

    return node
  }

  @FXML
  protected void onAbout() {
    println "about"
  }

  @FXML
  protected void onMenuConnect() {
    loginViewProvider.get().show()
  }

  @FXML
  protected void onMenuExit() {
    eventBus.post(new ShutdownEvent())
  }
}
