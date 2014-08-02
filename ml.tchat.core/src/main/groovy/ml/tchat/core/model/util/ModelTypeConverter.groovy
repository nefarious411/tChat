package ml.tchat.core.model.util

import ml.tchat.core.model.Channel
import ml.tchat.core.model.User




class ModelTypeConverter {

  static Channel convert(org.pircbotx.Channel channel) {
    if (channel == null) {
      return null
    }
    return new Channel(propertyArgs(Channel, channel))
  }

  static User convert(org.pircbotx.User user) {
    if (user == null) {
      return null
    }
    new User(propertyArgs(User, user))
  }

  private static copyProperties(def source, def target) {
    target.metaClass.properties.each {
      if (source.metaClass.hasProperty(source, it.name) && it.name != 'metaClass' && it.name != 'class')
        it.setProperty(target, source.metaClass.getProperty(source, it.name))
    }
  }

  private static HashMap propertyArgs(def targetClass, source) {
    def targetMetaClass = targetClass.metaClass
    def map = [:]
    targetMetaClass.properties.each {
      if (source.metaClass.hasProperty(source, it.name) && it.name != 'metaClass' && it.name != 'class') {
        map[it.name] = source.metaClass.getProperty(source, it.name)
      }
    }
    return map
  }
}
