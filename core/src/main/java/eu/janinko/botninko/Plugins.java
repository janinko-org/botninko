package eu.janinko.botninko;

import eu.janinko.botninko.api.PluginsLoader;
import eu.janinko.botninko.api.plugin.Plugin;
import eu.janinko.botninko.api.plugin.Command;
import eu.janinko.botninko.api.plugin.MessageHandler;
import eu.janinko.botninko.api.plugin.PresenceHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

class Plugins {
	private static Logger logger = Logger.getLogger(Plugins.class);
	
	private Commands commands;

	private HashMap<String, CommandWrapper> pluginClasses = new HashMap<>();
	private HashMap<String, CommandWrapper> pluginCommands = new HashMap<>();
	private HashSet<CommandWrapper> plugins = new HashSet<>();
	private HashSet<CommandWrapper> commandPlugins = new HashSet<>();
	private HashSet<CommandWrapper> presencePlugins = new HashSet<>();
	private HashSet<CommandWrapper> messagePlugins = new HashSet<>();

	private final Set<PluginsLoader> pluginsLoaders = Collections.newSetFromMap(new ConcurrentHashMap<PluginsLoader, Boolean>());
	private PluginsClassLoader classLoader = new PluginsClassLoader(getClass().getClassLoader());
	
	Plugins() {}

	void addPluginLoader(PluginsLoader pl){
		pl.reload();
		pluginsLoaders.add(pl);
	}

	void setCommands(Commands commands){
		this.commands = commands;
	}

	int startPlugins(){
		StringBuilder sb = new StringBuilder("Loaded plugins: ");
		int count = 0;
		for(PluginsLoader pl : pluginsLoaders){
			for(Class<? extends Plugin> clazz : pl.getAllPlugins()){
				if(pluginClasses.containsKey(clazz.getCanonicalName())){
					continue;
				}
				if(addPlugin(clazz)){
					sb.append(clazz.getCanonicalName());
					sb.append(", ");
					count++;
				}
			}
		}
		sb.delete(sb.length()-2,sb.length());
		logger.info(sb.toString());
		return count;
	}

	boolean startPlugin(String identifier){
		if(pluginClasses.containsKey(identifier)) return false;
		Class<? extends Plugin> clazz = null;
		for(PluginsLoader pl : pluginsLoaders){
			clazz = pl.getPluginByID(identifier);
			if(clazz == null) break;
		}
		if(clazz == null) return false;

		if(!addPlugin(clazz)) return false;
		logger.info("Loaded plugin: " + clazz.getCanonicalName());
		return true;
	}

	private boolean addPlugin(Class<? extends Plugin> clazz){
		Plugin c;
		try {
			c = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			logger.error("Failed to add plugin " + clazz, ex);
			return false;
		}

		CommandWrapper cw = new CommandWrapper(c, commands);
		c.setPluginHelper(cw);
		plugins.add(cw);
		
		pluginClasses.put(clazz.getCanonicalName(), cw);
		if (c instanceof Command){
			Command command = (Command) cw.getPlugin();
			pluginCommands.put(command.getCommand(), cw);
			commandPlugins.add(cw);
		}
		if (c instanceof MessageHandler) {
			messagePlugins.add(cw);
		}
		if (c instanceof PresenceHandler) {
			presencePlugins.add(cw);
		}

		return true;
	}

	void stopPlugin(String binaryName){
		CommandWrapper cw;
		synchronized(this){
			if(!pluginClasses.containsKey(binaryName)) return;
			cw = pluginClasses.get(binaryName);
			pluginClasses.remove(binaryName);
		}

		Iterator<Entry<String, CommandWrapper>> it = pluginCommands.entrySet().iterator();
		while(it.hasNext()){
			if(it.next().getValue().equals(cw)){
				it.remove();
			}
		}

		plugins.remove(cw);
		presencePlugins.remove(cw);
		messagePlugins.remove(cw);
		cw.destroy();
	}
	
	/*public final void loadPluginsFromConfigFile(){
		String path = PLUGIN_DIR + "plugins";
		BufferedReader in=null;
		try {
			in = new BufferedReader(new FileReader(path));
			String line;
			while((line = in.readLine()) != null ){
				loadPlugin(line);
			}
		} catch (FileNotFoundException e) {
			logger.error("Failed loading file", e);
		} catch (IOException e) {
			logger.error("Failed loading file", e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error("Failed closing file", e);
				}
			}
		}
	}*/
	
	void connected(){
		for(CommandWrapper cw : plugins){
		if(logger.isTraceEnabled()){logger.trace("Connecting: " + cw.getPlugin());}
			cw.getPlugin().onConnected();
		}
	}
	
	void disconnected(){
		for(CommandWrapper cw : plugins){
		if(logger.isTraceEnabled()){logger.trace("Disconnecting: " + cw.getPlugin());}
			cw.getPlugin().onDisconnected();
		}
	}

	CommandWrapper getCommand(String command) {
		return pluginCommands.get(command);
	}

	Set<CommandWrapper> getPlugins() {
		return Collections.unmodifiableSet(plugins);
	}

	Set<CommandWrapper> getPresencePlugins() {
		return Collections.unmodifiableSet(presencePlugins);
	}

	Set<CommandWrapper> getMessagePlugins() {
		return Collections.unmodifiableSet(messagePlugins);
	}

	Iterable<CommandWrapper> getCommandPlugins() {
		return Collections.unmodifiableSet(commandPlugins);
	}

	ClassLoader getClassLoader() {
		return classLoader;
	}

	void reload() {
		throw new UnsupportedOperationException("Not yet implemented");
	}


	private class PluginsClassLoader extends ClassLoader{

		PluginsClassLoader(ClassLoader parrent){
			super(parrent);
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException{
			for(PluginsLoader pl : pluginsLoaders){
				try {
					Class<?> clazz = pl.getClassLoader().loadClass(name);
					return clazz;
				} catch (ClassNotFoundException ex) {}
			}
			throw new ClassNotFoundException();
		}
	}
}
