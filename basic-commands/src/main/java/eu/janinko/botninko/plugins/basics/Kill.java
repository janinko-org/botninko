package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.plugin.AbstractCommand;

public class Kill extends AbstractCommand{
    
	@Override
	public String getCommand() {
		return "kill";
	}

	@Override
	public int getPrivLevel() {
		return 20;
	}

	@Override
	public void handleCommand(CommandMessage m) {
		if(m.getArgs().length > 1){
			switch (m.getArg(1)) {
				case "9":
					Runtime.getRuntime().exit(0);
					break;
				case "0":
					ph.sendMessage("Vedle!");
					return;
				default:
					ph.sendMessage("uargh");
					break;
			}
		}
		Runtime.getRuntime().exit(0);
	}

	@Override
	public String help(String prefix) {
		return "Vypne bota";
	}

}
