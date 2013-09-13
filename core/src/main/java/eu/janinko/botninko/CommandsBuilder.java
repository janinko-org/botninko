package eu.janinko.botninko;

import eu.janinko.botninko.connections.Connection;
import eu.janinko.botninko.pluginsloaders.JarPluginsLoader;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class CommandsBuilder {
	private Connection conn;
	private JarPluginsLoader pm;

	private String prefix = null;
	private String nick = null;


	public CommandsBuilder(){
		conn = new Connection();
		pm = new JarPluginsLoader(System.getProperty("user.home") + "/.xmppmuc/");
	}

	public CommandsBuilder setJID(String jid){
		conn.setJid(jid);
		return this;
	}

	public CommandsBuilder setPassword(String password){
		conn.setPassword(password);
		return this;
	}

	public CommandsBuilder setServer(String server){
		conn.setServer(server);
		return this;
	}

	public CommandsBuilder setPrefix(String prefix){
		this.prefix = prefix;
		return this;
	}

	public CommandsBuilder setNick(String nick){
		this.nick = nick;
		return this;
	}

	public Commands newCommands(String room) {
		if(nick == null) throw new IllegalStateException("Default nick is not set.");
		return newCommands(room, nick);
	}

	public Commands newCommands(String room, String nick) {
		Plugins plugins = new Plugins();
		plugins.addPluginLoader(pm);
		Commands ret =  new Commands(plugins, conn.addRoom(room, nick));
		if(prefix != null){
			ret.setPrefix(prefix);
		}
		ret.init();
		return ret;
	}

	public Connection getConnection() {
		return conn;
	}

}
