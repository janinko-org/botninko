package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.PluginHelper;
import eu.janinko.botninko.api.Presence;
import eu.janinko.botninko.api.plugin.AbstractCommand;
import eu.janinko.botninko.api.plugin.PresenceHandler;
import eu.janinko.botninko.api.Status;
import java.io.IOException;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class Pozdrav extends AbstractCommand implements PresenceHandler {
    private static Logger logger = Logger.getLogger(Pozdrav.class);
	HashMap<String, String> pozdravy;

	@Override
	public void setPluginHelper(PluginHelper pluginHelper) {
		super.setPluginHelper(pluginHelper);
		pozdravy = (HashMap<String, String>) pluginHelper.loadData();
		if(pozdravy == null){
			pozdravy = new HashMap<>();
		}
	}

    @Override
    public String getCommand() {
        return "pozdrav";
    }

    @Override
    public void handleCommand(CommandMessage m) {
		String[] args = m.getArgs();

        if (args.length == 1) {
            String nick = m.getNick();
            if (pozdravy.containsKey(nick)) {
                ph.sendMessage(nick + ": " + pozdravy.get(nick));
            }
        } else if (args.length < 3) {
            if (pozdravy.containsKey(args[1])) {
                ph.sendMessage(args[1] + ": " + pozdravy.get(args[1]));
            }
        } else if (args[1].equals("set")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i < args.length; i++) {
                sb.append(args[i]);
                sb.append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);

            pozdravy.put(args[2], sb.toString());
			try {
				ph.saveData(pozdravy);
			} catch (IOException ex) {
				logger.warn("Pozdravy couldn't be saved.", ex);
			}
        } else if (args[1].equals("reset")) {
            pozdravy.remove(args[2]);
			try {
				ph.saveData(pozdravy);
				ph.sendMessage("Pozdrav pro " + args[2] + " byl zrušen");
			} catch (IOException ex) {
				logger.warn("Pozdravy couldn't be saved.", ex);
			}
        } else {
            ph.sendMessage(this.help(ph.getPrefix()));
        }
    }

    @Override
    public String help(String prefix) {
        return "Syntaxe pro prikaz pozdrav je:\n"
                + prefix + "pozdrav nick\n"
                + prefix + "pozdrav set nick pozdrav\n"
                + prefix + "pozdrav reset nick";
    }

    @Override
    public void handlePresence(Presence p) {}

	@Override
	public void handleStatus(Status s) {
        if(logger.isTraceEnabled()){logger.trace("Handling status " + s);}
		if(s.getType() != Status.Type.joined) return;

        String nick = s.getNick();
		if (pozdravy.containsKey(nick)) {
			ph.sendMessage(nick + ": " + pozdravy.get(nick));
        }
	}
}
