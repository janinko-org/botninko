package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.plugin.AbstractCommand;

public class Say extends AbstractCommand {

    @Override
	public String getCommand() {
		return "say";
	}

    @Override
	public void handleCommand(CommandMessage m) {
		if(m.getArgs().length == 1){
			ph.sendMessage("pff");
		}else{
			ph.sendMessage(m.getBody());
		}
	}

    @Override
	public String help(String prefix) {
		return "Řekne text zadaný příkazem '" + prefix + "say text'";
	}
	
    @Override
	public int getPrivLevel(){
		return 2;
	}
}