package ml.tchat.ui.view.impl

import ml.tchat.ui.view.ShellView

import javax.swing.*
import java.awt.*

public class ShellViewImpl implements ShellView {

  private JFrame outerFrame;

  @Override
  public Component asComponent() {
    if (outerFrame == null) {
      outerFrame = new JFrame("foo");
    }
    outerFrame.add(new JPanel());

    Integer.parseInt("")

    return outerFrame;
  }
}
