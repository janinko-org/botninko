package eu.janinko.botninko;

import eu.janinko.botninko.connections.RoomImpl;
import eu.janinko.botninko.connections.Connection;
import eu.janinko.botninko.api.Room;
import eu.janinko.botninko.pluginsloaders.JarPluginsLoader;

/**
 *
 * @author jbrazdil
 */
public class Bot {
	//defaults
    private String defaultPrefix = ".";
    private String nick = "Botninko";
	private String configDir = System.getProperty("user.home") + "/.xmppmuc/";

    private Connection connection = new Connection();

    public Bot(String server, String jid, String password) {
        connection.setServer(server);
        connection.setJid(jid);
        connection.setPassword(password);
    }
    
    public Room join(String room){
		return _join(room, nick);
    }
    
    public Room join(String room, String nick){
        return _join(room, nick);
    }

	public boolean isConnected() {
		return connection.isConnected();
	}

	public void disconnect(){
		connection.disconnect();
	}

	private Room _join(String room, String nick){
		Plugins plugins = new Plugins();
		plugins.addPluginLoader(new JarPluginsLoader(configDir));

        RoomImpl r = connection.addRoom(room, nick);
		Commands commands = new Commands(plugins, r);
		commands.setPrefix(defaultPrefix);
		commands.setConfiDir(configDir);
		commands.init();

		return r;
	}

	// congiguration

	public String getDefaultPrefix() {
		return defaultPrefix;
	}

	public void setDefaultPrefix(String defaultPrefix) {
		if(defaultPrefix == null) throw new NullPointerException();
		this.defaultPrefix = defaultPrefix;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		if(nick == null) throw new NullPointerException();
		this.nick = nick;
	}

	public String getConfigDir() {
		return configDir;
	}

	public void setConfigDir(String configDir) {
		if(configDir == null) throw new NullPointerException();
		this.configDir = configDir;
	}
}