package eu.janinko.botninko.plugins.core;

import eu.janinko.botninko.Commands;
import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.PluginHelper;
import eu.janinko.botninko.api.plugin.AbstractCommand;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class Privset extends AbstractCommand {
    Map<String, Integer> privs;
    private static Logger logger = Logger.getLogger(Privset.class);
	private Commands commands;

	public void setCommands(Commands c){
		this.commands = c;
	}

    @Override
    public void setPluginHelper(PluginHelper pluginHelper) {
		super.setPluginHelper(pluginHelper);

		privs = (Map<String, Integer>) ph.loadData();
		if(privs == null){
			logger.warn("Privs file not found, creating empty privset.");
			privs = new HashMap<>();
		}

        for (Map.Entry<String, Integer> e : privs.entrySet()) {
            if (logger.isTraceEnabled()) {
                logger.trace("Priv for " + e.getKey() + " set to " + e.getValue());
            }
            commands.privSet(e.getKey(), e.getValue());
        }
    }

    @Override
    public String getCommand() {
        return "privset";
    }

    @Override
    public int getPrivLevel() {
        return 0;
    }

    @Override
    public void handleCommand(CommandMessage m) {
		String[] args = m.getArgs();
        if (args.length != 3) {
            return;
        }

        if (!args[1].matches("[A-Za-z.-]+@[A-Za-z.-]+.[a-z]+")) {
            return;
        }

        if (!args[2].matches("-?[0-9]+")) {
            return;
        }

		privs.put(args[1], Integer.decode(args[2]));
        commands.privSet(args[1], Integer.decode(args[2]));
		String message = "Práva pro " + args[1] + " byla ";
		try {
			ph.saveData(privs);
		} catch (IOException ex) {
			logger.warn("Privs couldn't be changed.",ex);
			message += "dočasně ";
		}
        message += "nastaven na: " + Integer.decode(args[2]);
        logger.info(message);
        ph.sendMessage(message);
    }

    @Override
    public String help(String prefix) {
        return prefix + getCommand() + " jid prvlevel";
    }
}
