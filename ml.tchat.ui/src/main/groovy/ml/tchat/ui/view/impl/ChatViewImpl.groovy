package ml.tchat.ui.view.impl

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import ml.tchat.ui.util.FxUtil
import ml.tchat.ui.view.View

/**
 *
 */
class ChatViewImpl implements View {

  private Node node

  @FXML
  protected ListView chatList
  @FXML
  protected TextField inputField


  @Override
  javafx.scene.Node asNode() {
    if (node == null) {
      node = FxUtil.createAndBindUi(this)
    }
    return node
  }

  @FXML
  protected void handleText() {
    def text = inputField.getText()
    println "text handled: ${text}"
    inputField.clear()
  }
}
