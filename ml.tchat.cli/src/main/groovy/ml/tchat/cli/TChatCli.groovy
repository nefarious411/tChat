package ml.tchat.cli

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import ml.tchat.core.Connection
import ml.tchat.core.TChatCore
import ml.tchat.core.TChatPlugin
import ml.tchat.core.event.ConnectionReadyEvent
import ml.tchat.core.event.LoginUnsuccessfulEvent
import ml.tchat.core.event.MessageReceivedEvent
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

class TChatCli {

  static EventBus eventBus = new EventBus("globalEventBus")

  def timeoutPatternPlugin

  static void main(String[] args) {
    def cli = new CliBuilder(usage: 'TChatCli [options]')
    cli.h(longOpt: 'help', 'print this message')
    cli.username(args: 1, required: true, argName: 'username', "Twitch username")
    cli.oauthToken(args: 1, required: true, argName: 'oauthToken', "Twitch oAuth token")


    OptionAccessor options = cli.parse(args)
    if (options == null) {
      System.exit(1)
    }

    initLogging()

    new TChatCli(options).go();
  }


  static initLogging() {
    ConsoleAppender appender = new ConsoleAppender();
    PatternLayout layout = new PatternLayout("%-4r [%t] %-5p %c %x - %m%n");
    appender.setLayout(layout);
    appender.setName("Console");
    OutputStreamWriter writer = new OutputStreamWriter(System.out);
    appender.setWriter(writer);

    Logger.getRootLogger().setLevel(Level.TRACE);
    Logger.getRootLogger().addAppender(appender);
    //Logger.getLogger("org.pircbotx").setLevel(Level.FATAL);

    Logger.getLogger(TChatCli).info('Logging initialized');
  }



  private Connection connection;

  public TChatCli(options) {

    // random stuff
    TChatCore.pluginRegistry.register(new TChatPlugin() {
      @Subscribe
      public void onUnsuccessfulLogin(LoginUnsuccessfulEvent event) {
        println "Unable to login"
        TChatCore.disconnectAll()
      }

      @Subscribe
      public void onConnectionReady(ConnectionReadyEvent event) {
        event.connection.sendIRC().joinChannel("#nefarious411")
        //event.connection.sendIRC().joinChannel("#aureylian")
      }

      @Subscribe
      public void onMessage(MessageReceivedEvent event) {
        println "${event.message.channel.name} : ${event.message.user.nick} : ${event.message.message}"
      }
    })

    timeoutPatternPlugin = new TimeoutPatternPlugin()
    //TChatCore.pluginRegistry.register(timeoutPatternPlugin)

    Logger.getLogger(TChatCli).info('connecting')
    connection = TChatCore.connect(options.username, options.oauthToken)
  }


  public void go() {
    System.in.eachLine { line ->
      if (line == 'exit') {
        TChatCore.disconnectAll()
        System.exit(0)
      } else if (line =~ /^addpattern /) {
        def pattern = line - 'addpattern '
        timeoutPatternPlugin.addPattern(/$pattern/)
        println "Pattern added:"
        timeoutPatternPlugin.printPatterns()
      } else if (line == 'printpatterns') {
        timeoutPatternPlugin.printPatterns()
      } else if (line.startsWith('removepattern')) {
        def patternIndex = line.replaceAll('^removepattern\\s?', '')
        if (!patternIndex) {
          println "You must specify an index"
          timeoutPatternPlugin.printPatternsWithIndex()
        } else {
          timeoutPatternPlugin.removePattern(Integer.valueOf(patternIndex.trim()))
          println "pattern was removed"
          timeoutPatternPlugin.printPatternsWithIndex()
        }
      } else if (line.startsWith('eval')) {
        def script = line.replaceAll('^eval\\s?', '')
        println Eval.me(script)
      } else if (line.startsWith('log')) {
        def parts = line.split(' ')
        Logger.getLogger(parts[1]).setLevel(Level.toLevel(parts[2]))
      } else {
        connection.sendRaw().rawLine(line)
      }
    }
  }

}
