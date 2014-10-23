package ml.tchat.ui.util

import com.google.common.eventbus.Subscribe

public interface TokenReceivedHandler {
  @Subscribe
  public void onTokenReceived(TokenReceivedEvent event);
}