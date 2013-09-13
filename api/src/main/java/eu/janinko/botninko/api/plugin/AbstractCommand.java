package eu.janinko.botninko.api.plugin;

import eu.janinko.botninko.api.PluginHelper;

/** Abstract class helping with implementing {@link Command} interface. 
 * 
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @see Command
 */
public abstract class AbstractCommand implements Command {
	protected PluginHelper ph;
	protected boolean connected = false;
	
	@Override
	public void setPluginHelper(PluginHelper pluginHelper){
		ph = pluginHelper;
	}

	@Override
	public String help(String prefix) {
		return null;
	}

	@Override
	public int getPrivLevel() {
		return 0;
	}

	@Override
	public void onConnected() {
		connected = true;
	}

	@Override
	public void onDisconnected() {
		connected = false;
	}
}
