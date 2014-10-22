package ml.tchat.core.config

import ml.tchat.core.ioc.InjectConstants
import ml.tchat.core.util.FileUtil
import org.apache.commons.configuration.ConfigurationException
import org.apache.commons.configuration.FileConfiguration
import org.apache.commons.configuration.XMLConfiguration
import org.apache.log4j.Logger

import javax.inject.Inject
import javax.inject.Named

public class ConfigurationManager {
  private static final Logger logger = Logger.getLogger(ConfigurationManager.class)
  private static final String CONFIG_FILE_NAME = "config.xml"
  private final File basePath

  @Inject
  public ConfigurationManager(@Named(InjectConstants.CONFIG_DIRECTORY) String configDirectory) {
    basePath = new File(configDirectory)

    if (!basePath.exists()) {
      basePath.mkdirs()
    }
  }

  public FileConfiguration load(String file) {
    XMLConfiguration config = null;
    File configFile = FileUtil.path(basePath, file);
    try {
      config = new XMLConfiguration(configFile);
      config.setAutoSave(true);
    } catch (ConfigurationException e) {
      logger.error("Unable to load configuration", e);
    }
    return config;
  }

  public void save(FileConfiguration configuration) {
    if (configuration == null) {
      logger.error("Configuration null");
      return;
    }

    try {
      configuration.save();
    } catch (ConfigurationException e) {
      logger.error("Unable to save configuration", e);
    }
  }
}
