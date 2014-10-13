package ml.tchat.ui.ioc

import com.google.common.eventbus.EventBus
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import ml.tchat.ui.config.ConfigurationManager
import ml.tchat.ui.view.ShellView
import ml.tchat.ui.view.impl.ShellViewImpl
import org.apache.commons.configuration.FileConfiguration

import javax.inject.Named

public class TChatModule extends AbstractModule {

  private static final String MESSAGES_BASE_PATH = "ml/tchat/ui/i18n/Messages";
  private static final String GLOBAL_EVENT_BUS_ID = "GlobalEventBus";

  @Override
  protected void configure() {
    bind(ShellView.class).to(ShellViewImpl.class);
  }

  @Provides
  @Singleton
  @Named("i18n")
  public ResourceBundle createMessages() {
    return ResourceBundle.getBundle(MESSAGES_BASE_PATH);
  }


  @Provides
  @Singleton
  public EventBus createEventBus() {
    return new EventBus(GLOBAL_EVENT_BUS_ID);
  }

  @Provides
  @Named(InjectConstants.CONFIG_DIRECTORY)
  public String configDirectory() {
    String dir = System.getProperty("user.home") +
        "${File.separator}.tchat${File.separator}"
    new File(dir).mkdirs();
    return dir;
  }


  @Provides
  @Singleton
  @Named(InjectConstants.MAIN_CONFIG)
  public FileConfiguration mainConfiguration(ConfigurationManager manager) {
    return manager.load("config.xml");
  }
}
