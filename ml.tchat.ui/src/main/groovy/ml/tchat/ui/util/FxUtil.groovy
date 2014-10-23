package ml.tchat.ui.util

import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Accordion
import javafx.scene.control.SplitPane
import javafx.scene.control.TitledPane

import javax.inject.Inject
import javax.inject.Named

public class FxUtil {
  private static final String FXML_EXT = ".fxml"

  @Inject
  @Named("i18n")
  private static ResourceBundle MESSAGES;

  /**
   * Find a {@link Node} within a {@link Parent} by it's ID.
   * <p>
   * This might not cover all possible {@link Parent} implementations but it's
   * a decent crack. {@link Control} implementations all seem to have their
   * own method of storing children along side the usual
   * {@link Parent#getChildrenUnmodifiable()} method.
   *
   * @param parent
   *            The parent of the node you're looking for.
   * @param id
   *            The ID of node you're looking for.
   * @return The {@link Node} with a matching ID or {@code null}.
   */
  @SuppressWarnings("unchecked")
  static <T> T getChildByID(Parent parent, String id) {

    String nodeId = null;

    if (parent instanceof TitledPane) {
      TitledPane titledPane = (TitledPane) parent;
      Node content = titledPane.getContent();
      nodeId = content.idProperty().get();

      if (nodeId != null && nodeId.equals(id)) {
        return (T) content;
      }

      if (content instanceof Parent) {
        T child = getChildByID((Parent) content, id);

        if (child != null) {
          return child;
        }
      }
    }

    for (Node node : parent.getChildrenUnmodifiable()) {
      nodeId = node.idProperty().get();
      if (nodeId != null && nodeId.equals(id)) {
        return (T) node;
      }

      if (node instanceof SplitPane) {
        SplitPane splitPane = (SplitPane) node;
        for (Node itemNode : splitPane.getItems()) {
          nodeId = itemNode.idProperty().get();

          if (nodeId != null && nodeId.equals(id)) {
            return (T) itemNode;
          }

          if (itemNode instanceof Parent) {
            T child = getChildByID((Parent) itemNode, id);

            if (child != null) {
              return child;
            }
          }
        }
      } else if (node instanceof Accordion) {
        Accordion accordion = (Accordion) node;
        for (TitledPane titledPane : accordion.getPanes()) {
          nodeId = titledPane.idProperty().get();

          if (nodeId != null && nodeId.equals(id)) {
            return (T) titledPane;
          }

          T child = getChildByID(titledPane, id);

          if (child != null) {
            return child;
          }
        }
      } else if (node instanceof Parent) {
        T child = getChildByID((Parent) node, id);

        if (child != null) {
          return child;
        }
      }
    }
    return null;
  }






  public static <T> T createAndBindUi(Object controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Cannot bind to a null Controller")
    }

    FXMLLoader loader = createFXMLLoader(controller.getClass())
    loader.setController(controller)
    loader.setResources(MESSAGES)

    try {
      return (T) loader.load()
    } catch (IOException e) {
      throw new RuntimeException("Unable to load FXML", e)
    }
  }

  private FxUtil() {
    throw new UnsupportedOperationException("Utility class")
  }

  private static FXMLLoader createFXMLLoader(Class<?> clazz) {
    return new FXMLLoader(clazz.getResource(clazz.getSimpleName() + FXML_EXT))
  }
}
