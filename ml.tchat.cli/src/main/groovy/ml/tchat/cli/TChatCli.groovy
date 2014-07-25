package ml.tchat.cli




class TChatCli {

  static void main(String[] args) {
    def cli = new CliBuilder(usage: 'TChatCli [options]')
    cli.h(longOpt: 'help', 'print this message')


    def options = cli.parse(args)
    if (options == null) {
      System.exit(1)
    }
  }

}
