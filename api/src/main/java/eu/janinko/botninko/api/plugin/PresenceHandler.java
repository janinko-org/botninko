package eu.janinko.botninko.api.plugin;

import eu.janinko.botninko.api.Status;
import eu.janinko.botninko.api.Presence;

/**
 *
 * @author jbrazdil
 */
public interface PresenceHandler {
	/** Handle presence.
	 * This method is invoked when presence packet is received.
	 *
	 * @param presence Received message.
	 */
	void handlePresence(Presence presence);

	/** Handle status.
	 * This method is invoked when presence packet is received.
	 *
	 * @param status Received message.
	 */
	void handleStatus(Status status);
}
