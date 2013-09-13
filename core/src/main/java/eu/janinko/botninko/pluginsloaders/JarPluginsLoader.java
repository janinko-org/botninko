package eu.janinko.botninko.pluginsloaders;

import eu.janinko.botninko.api.PluginsLoader;
import eu.janinko.botninko.api.plugin.Plugin;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.WeakHashMap;
import org.apache.log4j.Logger;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class JarPluginsLoader implements PluginsLoader {
	private final String pluginDir;
	private final String pluginJarDir;
	private static Logger logger = Logger.getLogger(JarPluginsLoader.class);
	private HashMap<String, Class<? extends Plugin>> plugins = new HashMap<>();

	private XmppmucClassLoader rootClassLoader = new XmppmucClassLoader(getClass().getClassLoader());
	private ClassLoader classLoader = JarPluginsLoader.class.getClassLoader();

	public JarPluginsLoader(String dataDir){
		pluginDir = dataDir + "plugins/";
		pluginJarDir = pluginDir + "jar/";
	}

	private void scanPluginFolder(){
		logger.trace("Scanning pluginFolder");

		File pluginDirectory = new File(pluginJarDir);

		ArrayList<URL> urls = new ArrayList<>();
		for(File f : pluginDirectory.listFiles()){
			if(logger.isTraceEnabled()){logger.trace("Checking file: " + f.getAbsolutePath());}
			try {
				urls.add(f.toURI().toURL());
			} catch (MalformedURLException e) {
				logger.warn("Failed to procces file: " + f, e);
			}
		}

		URLClassLoader cl = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
		synchronized(this){
			classLoader = cl;
			rootClassLoader.addClassLoader(cl);
		}
	}

	@Override
	synchronized public Set<Class<? extends Plugin>> getAllPlugins(){
		return new HashSet<>(plugins.values());
	}

	@Override
	synchronized public Class<? extends Plugin> getPluginByID(String canonicalName){
		if(plugins.containsKey(canonicalName)){
			return plugins.get(canonicalName);
		}
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Plugin> clazz = (Class<? extends Plugin>) classLoader.loadClass(canonicalName);
			plugins.put(clazz.getCanonicalName(), clazz);
			return clazz;
		} catch (ClassNotFoundException | ClassCastException ex) {
			return null;
		}
	}

	synchronized private void loadPlugins(){
		plugins.clear();
		for(Plugin c : ServiceLoader.load(Plugin.class, classLoader)){
			Class<? extends Plugin> clazz = c.getClass();
			plugins.put(clazz.getCanonicalName(), clazz);
		}
	}

	@Override
	public ClassLoader getClassLoader(){
		return rootClassLoader;
	}

	@Override
	public void reload() {
		scanPluginFolder();
		loadPlugins();
	}

	private static class XmppmucClassLoader extends ClassLoader{

		private Set<ClassLoader> weakHashSet;

		XmppmucClassLoader(ClassLoader parrent){
			super(parrent);
			weakHashSet = Collections.newSetFromMap(new WeakHashMap<ClassLoader, Boolean>());
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException{
			for(ClassLoader cl : weakHashSet){
				try {
					return cl.loadClass(name);
				} catch (ClassNotFoundException ex) {}
			}
			throw new ClassNotFoundException();
		}

		void addClassLoader(ClassLoader cl){
			weakHashSet.add(cl);
		}
	}
}
