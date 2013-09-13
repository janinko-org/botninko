package eu.janinko.botninko.plugins.core;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.plugin.AbstractCommand;
import org.apache.log4j.Logger;

public class PluginManagerCommand extends AbstractCommand {
	private static Logger logger = Logger.getLogger(PluginManagerCommand.class);

	@Override
	public String getCommand() {
		return "pm";
	}

	@Override
	public void handleCommand(CommandMessage m) {
		String args[] = m.getArgs();
		switch(args.length){
			case 0:
			case 1:
				ph.sendHelp();
				return;
			default: // >=3
				switch (args[1]) {
					case "stop":
						logger.info("request stop command: " + args[2]);
						ph.getCommands().getPlugins().stopPlugin(args[2]);
						ph.sendMessage("Plugin " + args[2] + " byl zastaven.");
						return;
					case "start":
						logger.info("request start command: " + args[2]);
						if(ph.getCommands().getPlugins().startPlugin(args[2])){
							ph.sendMessage("Plugin " + args[2] + " byl spuštěn.");
						}
						return;
				}
			case 2:
				switch (args[1]) {
					case "reload":
						ph.getCommands().getPlugins().reload();
						ph.sendMessage("Plugin " + args[2] + " byl zastaven.");
					return;
				}
				break;
		}
		ph.sendHelp();
	}

	@Override
	public String help(String prefix) {
		return prefix + getCommand() + " ((stop|start) command|reload)";
	}

	@Override
	public int getPrivLevel() {
		return 100;
	}
}
