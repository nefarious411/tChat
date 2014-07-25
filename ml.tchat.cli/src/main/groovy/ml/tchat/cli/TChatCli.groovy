package ml.tchat.cli

import ml.tchat.core.ConnectionManager
import ml.tchat.core.TwitchListener
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout




class TChatCli {

  static void main(String[] args) {
    def cli = new CliBuilder(usage: 'TChatCli [options]')
    cli.h(longOpt: 'help', 'print this message')
    cli.username(args:1, required: true, argName: 'username', "Twitch username")
    cli.oauthToken(args:1, required:true, argName:'oauthToken', "Twitch oAuth token")


    def options = cli.parse(args)
    if (options == null) {
      System.exit(1)
    }

    initLogging()

    ConnectionManager connectionManager = new ConnectionManager(new TwitchListener())
    connectionManager.connect(options.username, options.oauthToken)
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

    Logger.getLogger(TChatCli).info('Logging initialized');
  }

}
