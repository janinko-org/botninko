package eu.janinko.botninko.api.plugin;

import eu.janinko.botninko.api.PluginHelper;

/** Interface for plugins.
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @version 0.1
 * @see AbstractCommand
 */
public interface Plugin {

    /** Provides Plugin with helper.
	 * This method is invoked when plugin is loaded.
	 *
	 * @param pluginHelper Helper for this Plugin.
	 */
    void setPluginHelper(PluginHelper pluginHelper);
	
    /** This method is invoked when bot connects to MUC.
	 */
    void onConnected();

	/** This method is invoked when bot disconnects from MUC.
	 */
    void onDisconnected();
    
}
