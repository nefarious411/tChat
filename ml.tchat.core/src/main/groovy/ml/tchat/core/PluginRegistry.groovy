package ml.tchat.core

import com.google.common.eventbus.EventBus

import javax.inject.Inject

class PluginRegistry {

  public interface PluginRegistration {
    void removeRegistration();
  }

  @Inject
  EventBus eventBus

  public PluginRegistration register(TChatPlugin plugin) {
    eventBus.register(plugin)

    return new PluginRegistration() {
      @Override
      void removeRegistration() {
        eventBus.unregister(plugin)
      }
    };
  }
}
