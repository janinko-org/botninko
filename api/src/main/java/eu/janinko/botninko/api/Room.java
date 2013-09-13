package eu.janinko.botninko.api;

/**
 * This interface represents Multi User Chat room.
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Room {

	/**
	 * Sends message to room.
	 * @param message Message to be send.
	 */
	public void sendMessage(String message);

	/**
	 * Checks if bot is connected to the room.
	 * @return True if bot is connected in the room, false otherwise.
	 */
	public boolean isConnected();

	/**
	 * Returns room name.
	 * @return Room name.
	 */
	public String getRoomName();

	/**
	 * Returns bot's nick in this room.
	 * @return Bot's nick.
	 */
	public String getNick();

	/**
	 * Disconnect from this room.
	 */
	public void leave();
}
