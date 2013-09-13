package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.plugin.AbstractCommand;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Roulette extends AbstractCommand {

	private Set<String> deadNicks;
	private int chamber;
	private Random random;
	private String lastNick;

	public Roulette() {
		deadNicks = new HashSet<>();
		chamber = 1;
		random = new Random();
		lastNick = "";
	}

	@Override
	public String getCommand() {
		return "roulette";
	}

	@Override
	public void handleCommand(CommandMessage m) {
		String nick = m.getNick();
		String[] args = m.getArgs();

		if (args.length == 2 && args[1].equals("restart")) {
			deadNicks.clear();
			chamber = 1;
			lastNick = "";
		}

		if (deadNicks.contains(nick)) {
			ph.sendMessage(nick + ": Jseš mrtvej!");
		} else if (lastNick.equals(nick)) {
			ph.sendMessage(nick + ": Nemůžeš mačkat kohoutek dvakrat po sobě!");
		} else {
			if (random.nextInt(8 - chamber) == 0) {
				ph.sendMessage(nick + ": komora #" + chamber + " z 6 => *BANG*");
				ph.sendMessage("/me přebil");
				deadNicks.add(nick);
				chamber = 1;
			} else {
				ph.sendMessage(nick + ": komora #" + chamber + " z 6 => +klik+");
				if (chamber == 6) {
					ph.sendMessage("WTF?");
					ph.sendMessage("/me přebil");
					chamber = 1;
				} else {
					chamber++;
				}
			}
			lastNick = nick;
		}

	}

	@Override
	public String help(String prefix) {
		return "Příkazem '" + prefix + "roulette' si zahraješ ruskou ruletu.\n"
		     + "Příkazem '" + prefix + "roulette restart' restartuješ ruletu a vrátíš mrtvé mezi živé.";
	}
}
