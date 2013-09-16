package eu.janinko.botninko;

import eu.janinko.botninko.api.PluginHelper;
import eu.janinko.botninko.api.Room;
import eu.janinko.botninko.api.plugin.Plugin;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.Occupant;

/** Middle layer between Command and MucCommands,
 * providing helpful functionality for Command.
 * @author janinko
 *
 */
class CommandWrapper implements PluginHelper {
	private Commands commands;
	private Plugin plugin;
	private XMLStorage xmlstorage;
	private HashSet<TimerTask> tasks = new HashSet<>();

	private static Logger logger = Logger.getLogger(CommandWrapper.class);
	
	public CommandWrapper(Plugin plugin, Commands commands){
		this.commands = commands;
		this.plugin = plugin;
	}

	public Commands getCommands(){
		return commands;
	}

	/*public void sendHelp(){
		sendMessage(command.help(commands.getPrefix()));
	}*/

	@Override
	public void sendMessage(String message) {
		commands.getRoom().sendMessage(message);
	}
	
	public void sendMessage(Message message) {
		commands.getRoom().sendsMessage(message);
	}

	public void sendPrivateMessage(String nick, Message message){
		if(logger.isTraceEnabled()){logger.trace("Sending private message to " + nick + ": " + message.getBody());}
		message.setTo(commands.getRoom().getRoomName()+"/"+nick);
		message.setType(Message.Type.chat);
		sendMessage(message);
	}

	@Override
	public void sendPrivateMessage(String nick, String message){
		Message m = new Message();
		m.setBody(message);
		sendPrivateMessage(nick,m);
	}

	@Override
	public void saveData(Object o) throws IOException{
		if(xmlstorage == null){
			xmlstorage = new XMLStorage(plugin.getClass(),commands.getPlugins().getClassLoader(), commands.getConfigDir());
		}
		xmlstorage.save(o);
	}

	@Override
	public Object loadData() {
		if (xmlstorage == null) {
			xmlstorage = new XMLStorage(plugin.getClass(),commands.getPlugins().getClassLoader(), commands.getConfigDir());
		}
		try {
			return xmlstorage.load();
		} catch (IOException ex) {
			logger.info("XML storage for " + plugin.getClass() + " cant be loaded.", ex);
			return null;
		}
	}

	@Override
	public void startRepeatingTask(TimerTask task, long period){
		if(tasks.contains(task)) return;
		commands.getTimer().schedule(task, 0, period);
		tasks.add(task);
	}

	void cancelTasks(){
		Iterator<TimerTask> it = tasks.iterator();
		while(it.hasNext()){
			it.next().cancel();
			it.remove();
		}
	}

	void destroy() {
		cancelTasks();
		plugin.onDisconnected();
	}

	public Set<String> getOnlineUsers(){
		HashSet<String> online = new HashSet<>();
		try {
			for(Occupant o : commands.getRoom().getMuc().getParticipants()){
				online.add(o.getNick());
			}
			for(Occupant o : commands.getRoom().getMuc().getModerators()){
				online.add(o.getNick());
			}
		} catch (XMPPException ex) {
			logger.error("Couldn't fetch online occupants",ex);
		}
		return online;
	}


	@Override
	public String getPrefix() {
		return commands.getPrefix();
	}

	Plugin getPlugin(){
		return plugin;
	}

	@Override
	public Room getRoom() {
		return commands.getRoom();
	}
}
