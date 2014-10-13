package ml.tchat.ui.presenter

import ml.tchat.ui.view.ShellView

import javax.inject.Inject

public class ShellPresenter {

  @Inject
  private ShellView view;


  public void show() {
    view.asComponent().visible = true
  }
}
