package ml.tchat.cli

import com.google.common.eventbus.Subscribe
import ml.tchat.core.TChatPlugin
import ml.tchat.core.event.MessageReceivedEvent

/**
 *
 */
class TimeoutPatternPlugin implements TChatPlugin {

  static timeoutpatterns = [
      /\bboobs?\b/,
      /\bbewbs?\b/,
      /\bfap\b/,
      /fuck/,
      /fuq/,
      /\bshit\b/,
      /\*\*\*/,
      /\bpussy\b/,
      /vagina/,
      /\bdick\b/,
      /\bfuq\b/,
      /\bdafuq\b/,
      /\bfart/,
      /\(\s*Y\s*\)/,
      /\(\s*\.?\s*Y\s*\.?\s*\)/,
      /8=+D/,
  ]

  @Subscribe
  public void onMessage(MessageReceivedEvent event) {
    //if ((["OP"] - event.getUser().getUserLevels(event.getChannel())).isEmpty()) {
    //return;
    //}
    boolean doTimeout = timeoutpatterns.find {
      event.message.message.toLowerCase() =~ it
    }
    if (doTimeout) {
      println "${new Date().format("yyyy-dd-mm HH:mm")} - timing out ${event.message.user.nick} for message: ${event.message.message}"
      event.connection.sendIRC().message(event.message.channel.name, ".timeout " + event.message.user.nick + " 1")
    }
  }

  def addPattern(pattern) {
    timeoutpatterns.add(pattern)
  }

  def removePattern(int index) {
    timeoutpatterns.remove(index)
  }

  def printPatterns() {
    timeoutpatterns.each {
      println it
    }
  }

  def printPatternsWithIndex() {
    timeoutpatterns.eachWithIndex { pattern, index ->
      println "$index - $pattern"
    }
  }

}
