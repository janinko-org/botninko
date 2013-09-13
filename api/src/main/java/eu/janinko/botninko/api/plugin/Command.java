package eu.janinko.botninko.api.plugin;

import eu.janinko.botninko.api.CommandMessage;

/**
 *
 * @author jbrazdil
 */
public interface Command extends Plugin{

	/** Returns muc command string.
	 *
	 * @return String command that invokes this {@link Command}.
	 */
    String getCommand();

	/** Handle command.
	 * This method is invoked when message starting with command prefix followed by command.
	 *
	 * @param m Received command message.
	 * @see eu.janinko.botninko.Bot#setDefaultPrefix
	 */
    void handleCommand(CommandMessage m);

	/** Returns minimal priv level required for this plugin.
	 *
	 * @return Required priv level.
	 * @see eu.janinko.botninko.plugins.core.Privset
	 */
    int getPrivLevel();
	
	/** Returns help for this command.
	 *
	 * @param prefix Current command prefix. May be used for generating help message.
	 * @return Help for plugin.
	 */
    String help(String prefix);
}
