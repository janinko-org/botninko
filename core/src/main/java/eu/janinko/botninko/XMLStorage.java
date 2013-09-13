package eu.janinko.botninko;

import eu.janinko.botninko.pluginsloaders.JarPluginsLoader;
import com.thoughtworks.xstream.XStream;
import eu.janinko.botninko.api.plugin.Plugin;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class XMLStorage {
	protected final String dataPath;

	protected String identifier;
	private XStream xstream;

	public XMLStorage(Class<? extends Plugin> clazz, ClassLoader cl, String configDir){
		this.identifier = clazz.getCanonicalName();
		this.xstream = new XStream();
		this.dataPath = configDir + "plugins/data/";
		xstream.setClassLoader(cl);
	}

	public void save(Object o) throws IOException{
		FileWriter out = new FileWriter(dataPath + identifier + ".xml");
		xstream.toXML(o, out);
	}

	public Object load() throws IOException{
		FileReader out = new FileReader(dataPath + identifier + ".xml");

		return xstream.fromXML(out);
	}
}
