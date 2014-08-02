package ml.tchat.cli

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import ml.tchat.core.ConnectionManager
import ml.tchat.core.event.ConnectionReadyEvent
import ml.tchat.core.event.MessageReceivedEvent
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

class TChatCli {

  static EventBus eventBus = new EventBus("globalEventBus")


  static timeoutpatterns = [
      /\bboob\b/,
      /fuck/,
      /\bshit\b/,
      /\*\*\*/,
      /\bpussy\b/,
      /vagina/,
      /\bdick\b/,
      /\bfuq\b/,
      /\bdafuq\b/,
      /\bfart/,
  ]

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
    //Logger.getLogger("org.pircbotx").setLevel(Level.WARN);

    Logger.getLogger(TChatCli).info('Logging initialized');
  }



  private connection;

  public TChatCli(options) {

    connection = ConnectionManager.createConnection(options.username, options.oauthToken)
    connection.getEventBus().register(this);
  }


  public void go() {
    /*
    ConnectionManager connectionManager = new ConnectionManager(new TwitchListener() {
      @Override
      void onMessage(MessageEvent<PircBotX> event) throws Exception {
        super.onMessage(event);

        //if ((["OP"] - event.getUser().getUserLevels(event.getChannel())).isEmpty()) {
        //return;
        //}
        boolean doTimeout = timeoutpatterns.find {
          event.message.toLowerCase() =~ it
        }
        if (doTimeout) {
          println "timing out ${event.user.nick} for message: ${event.message}"
          event.getBot().sendIRC().message(event.channel.name, ".timeout " + event.user.nick + " 1")
        }
      }

      @Override
      void onMotd(MotdEvent<PircBotX> event) throws Exception {
        super.onMotd(event)
        event.bot.sendRaw().rawLine("TWITCHCLIENT 1");
        event.bot.sendIRC().joinChannel('#aureylian')
      }
    });
    */

    Logger.getLogger(TChatCli).info('connecting')
    connection.connect()
    Logger.getLogger(TChatCli).info('connected')



    System.in.eachLine { line ->
      if (line == 'exit') {
        ConnectionManager.disconnectAll()
        System.exit(0)
      } else if (line =~ /^addpattern /) {
        def pattern = line - 'addpattern '
        timeoutpatterns.add(/$pattern/)
        println "Pattern added:"
        timeoutpatterns.each {
          println it
        }
      } else if (line == 'printpatterns') {
        timeoutpatterns.each {
          println it
        }
      } else if (line.startsWith('removepattern')) {
        def patternIndex = line.replaceAll('^removepattern\\s?', '')
        if (!patternIndex) {
          println "You must specify an index"
          timeoutpatterns.eachWithIndex { pattern, index ->
            println "$index - $pattern"
          }
        } else {
          timeoutpatterns.remove(Integer.valueOf(patternIndex.trim()))
          println "pattern was removed"
          timeoutpatterns.eachWithIndex { pattern, index ->
            println "$index - $pattern"
          }
        }
      } else if (line.startsWith('eval')) {
        def script = line.replaceAll('^eval\\s?', '')
        println Eval.me(script)
      } else if (line.startsWith('log')) {
        def parts = line.split(' ')
        Logger.getLogger(parts[1]).setLevel(Level.toLevel(parts[2]))
      } else {
        //connectionManager.send(line)
      }
    }
  }


  @Subscribe
  public void onConnectionReady(ConnectionReadyEvent event) {
    event.connection.sendIRC().joinChannel("aureylian")
  }

  @Subscribe
  public void onMessage(MessageReceivedEvent event) {
    //if ((["OP"] - event.getUser().getUserLevels(event.getChannel())).isEmpty()) {
    //return;
    //}
    boolean doTimeout = timeoutpatterns.find {
      event.message.toLowerCase() =~ it
    }
    if (doTimeout) {
      println "timing out ${event.user.nick} for message: ${event.message}"
      event.connection.sendIRC().message(event.channel.name, ".timeout " + event.user.nick + " 1")
    }
  }
}
