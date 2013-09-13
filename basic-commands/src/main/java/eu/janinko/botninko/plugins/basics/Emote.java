package eu.janinko.botninko.plugins.basics;

import eu.janinko.botninko.api.CommandMessage;
import eu.janinko.botninko.api.plugin.AbstractCommand;

public class Emote extends AbstractCommand {

    @Override
    public String getCommand() {
        return "emote";
    }

    @Override
    public void handleCommand(CommandMessage m) {
        switch (m.getArgs().length) {
            case 0:
            case 1:
                ph.sendMessage("/me zije");
                return;
            default: // >1
                ph.sendMessage("/me " + m.getBody());
        }
    }

    @Override
    public String help(String prefix) {
        return "Zaemotuje text zadaný příkazem '" + prefix + "emote text'";
    }

    @Override
    public int getPrivLevel() {
        return 1;
    }
}