package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.Message;
import eu.janinko.botninko.api.plugin.MessageHandler;
import eu.janinko.botninko.api.plugin.AbstractCommand;
import org.apache.log4j.Logger;

public class MessageLogger extends AbstractCommand implements MessageHandler{
	private boolean logging = true;
    private static Logger logger = Logger.getLogger(MessageLogger.class);
	
    @Override
	public String getCommand() {
		return "lg";
	}

    @Override
	public void handleCommand(CommandMessage m) {
		if(m.getArgs().length == 2){
			switch (m.getArg(1)) {
				case "start":
					logging = true;
					break;
				case "stop":
					logging = false;
					break;
			}
		}
	}

    @Override
	public String help(String prefix) {
		return prefix + getCommand() + " (start|stop)";
	}
	
    @Override
	public int getPrivLevel(){
		return 80;
	}

	@Override
	public void handleMessage(Message m) {
		if(!logging) return;
		logger.info(m.getNick()+ ": " + m.getText());
	}
}