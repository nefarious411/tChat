package ml.tchat.ui

import com.google.common.eventbus.EventBus
import ml.tchat.ui.ioc.InjectConstants
import ml.tchat.ui.presenter.ShellPresenter
import org.apache.commons.configuration.FileConfiguration
import org.apache.log4j.Logger

import javax.inject.Inject
import javax.inject.Named

public class ApplicationController {
  private static final Logger logger = Logger.getLogger(ApplicationController.class)

  @Inject
  private ShellPresenter shellPresenter

  @Inject
  private EventBus eventBus

  @Inject
  @Named("i18n")
  private ResourceBundle messages

  @Inject
  @Named(InjectConstants.MAIN_CONFIG)
  private FileConfiguration mainConfiguration


  public void go() {
    shellPresenter.show()
  }
}
