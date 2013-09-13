package eu.janinko.botninko.connections;

import eu.janinko.botninko.Commands;
import eu.janinko.botninko.api.Room;
import eu.janinko.botninko.listeners.InvitationRejectionListenerLogger;
import eu.janinko.botninko.listeners.PacketListenerLogger;
import eu.janinko.botninko.listeners.ParticipantStatusListenerLogger;
import eu.janinko.botninko.listeners.PluginsPacketListener;
import eu.janinko.botninko.listeners.PluginsParticipantStatusListener;
import eu.janinko.botninko.listeners.SubjectUpdatedListenerLogger;
import eu.janinko.botninko.listeners.UserStatusListenerLogger;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.FromMatchesFilter;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * @author Honza Brázdil <jbrazdil@redhat.com>
 */
public class RoomImpl implements Room{
	private static Logger logger = Logger.getLogger(RoomImpl.class);
	private String room;
	private String nick;
	private Connection connection;
	private MultiUserChat muc;
	private Commands commands;

	private FromMatchesFilter messageFilter;

	private static final DiscussionHistory dh = new DiscussionHistory();
	static{ dh.setMaxChars(0);}

	RoomImpl(Connection connection, String room, String nick) {
		this.connection = connection;
		this.room = room;
		this.nick = nick;
		this.messageFilter = new FromMatchesFilter(room);
	}

	boolean connect(){
		muc = new MultiUserChat(connection.getXMPPConnection(), room);
		try {
			muc.join(nick, null, dh, 5000);
		} catch (XMPPException e) {
			logger.error("Connection to room failed", e);
			return false;
		}
		
		// FOR DEBUG AND DEVEL ->
		muc.addUserStatusListener(new UserStatusListenerLogger());
		muc.addInvitationRejectionListener(new InvitationRejectionListenerLogger());
		muc.addMessageListener(new PacketListenerLogger());
		muc.addParticipantListener(new PacketListenerLogger());
		muc.addParticipantStatusListener(new ParticipantStatusListenerLogger());
		muc.addSubjectUpdatedListener(new SubjectUpdatedListenerLogger());
		// <- FOR DEBUG AND DEVEL

		connection.getXMPPConnection().addPacketListener(new PluginsPacketListener(commands), messageFilter);
		muc.addParticipantStatusListener(new PluginsParticipantStatusListener(commands));
		return true;
	}

	public void setCommands(Commands commands){
		this.commands = commands;
	}

	/**
	 * Sets bot nickname.
	 *
	 * @param nick Bot nickname.
	 * @return This instance of XmppConnection.
	 */
	RoomImpl setNick(String nick) {
		this.nick = nick;
		return this;
	}

	/**
	 * Returns current bot nickname.
	 *
	 * @return Bot nickname.
	 */
	public String getNick() {
		return nick;
	}

	@Override
	public String getRoomName(){
		return room;
	}

	@Override
	public void sendMessage(String message) {
		if (!connection.isConnected()) {
			return;
		}
		try {
			muc.sendMessage(message);
		} catch (XMPPException e) {
			logger.error("Failed to send message", e);
		}
	}

	public void sendsMessage(org.jivesoftware.smack.packet.Message message) {
		if (!connection.isConnected()) {
			return;
		}
		try {
			muc.sendMessage(message);
		} catch (XMPPException e) {
			logger.error("Failed to send message", e);
		}
	}

	/**
	 * Returns current {@link MultiUserChat} we are connected into.
	 *
	 * @return MUC object to which we ar connected or null.
	 */
	public MultiUserChat getMuc() {
		if (!connection.isConnected()) {
			return null;
		}
		return muc;
	}

	@Override
	public boolean isConnected() {
		return connection.isConnected();
	}

	Connection getConnection() {
		return connection;
	}

	@Override
	public void leave() {
		commands.disconnected();
		muc.leave();
		connection.removeRoom(this);
	}
}
