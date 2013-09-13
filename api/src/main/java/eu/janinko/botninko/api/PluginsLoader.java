package eu.janinko.botninko.api;

import eu.janinko.botninko.api.plugin.Plugin;
import java.util.Set;

/**
 *
 * @author jbrazdil
 */
public interface PluginsLoader {

	public Class<? extends Plugin> getPluginByID(String identifier);
	public Set<Class<? extends Plugin>> getAllPlugins();
	public ClassLoader getClassLoader();
	public void reload();
}
