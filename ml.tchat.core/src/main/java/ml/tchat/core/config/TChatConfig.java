package ml.tchat.core.config;


import org.apache.commons.configuration.FileConfiguration;

public class TChatConfig {
  private FileConfiguration fileConfiguration;

  public TChatConfig(FileConfiguration fileConfiguration) {
    this.fileConfiguration = fileConfiguration;

  }

  public String getUsername() {
    return fileConfiguration.getString("username");
  }

  public void setUsername(String username) {
    fileConfiguration.setProperty("username", username);
  }

  public String getOauthToken() {
    return fileConfiguration.getString("oauth_token");
  }

  public void setOauthToken(String oAuthToken) {
    fileConfiguration.setProperty("oauth_token", oAuthToken);
  }
}
