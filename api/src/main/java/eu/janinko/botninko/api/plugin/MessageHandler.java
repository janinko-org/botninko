package eu.janinko.botninko.api.plugin;

import eu.janinko.botninko.api.Message;

/**
 *
 * @author jbrazdil
 */
public interface MessageHandler extends Plugin {

	/**
	 * Handle message. This method is invoked when message is sent to MUC.
	 *
	 * @param m Received message.
	 */
	void handleMessage(Message m);
}
