package ml.tchat.plugins.core;

import java.io.File;


public class TChatPlugins {

  public void start() {
    try {
      System.out.println(new File("").getCanonicalPath());
    } catch (Exception e) {
    }
    /*
    PluginsConfiguration config = new PluginsConfigurationBuilder()
        .pluginDirectory(new File("plugins"))
        .bundledPluginUrl()
        .build();
    AtlassianPlugins plugins = new AtlassianPlugins(config);


    plugins.afterPropertiesSet();
    plugins.getPluginSystemLifecycle().init();
    */
  }
}
