package eu.janinko.botninko;

import com.thoughtworks.xstream.XStream;
import eu.janinko.botninko.api.plugin.Plugin;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class XMLStorage {
	private static Logger logger = Logger.getLogger(XMLStorage.class);
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
		File outdir = new File(dataPath);
		if(!outdir.exists()){
			logger.info("Creating directory " + outdir.getAbsolutePath());
			outdir.mkdirs();
		}
		FileWriter out = new FileWriter(dataPath + identifier + ".xml");
		xstream.toXML(o, out);
	}

	public Object load() throws IOException{
		File in = new File(dataPath + identifier + ".xml");
		if(!in.exists()) return null;

		return xstream.fromXML(new FileReader(in));
	}
}
