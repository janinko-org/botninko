package eu.janinko.botninko;

import eu.janinko.botninko.api.Presence;

/**
 *
 * @author jbrazdil
 */
public class PresenceImpl implements Presence{
	org.jivesoftware.smack.packet.Presence presence;

	public PresenceImpl(org.jivesoftware.smack.packet.Presence presence) {
		this.presence = presence;
	}

	@Override
	public String getNick() {
		return presence.getFrom();
	}

	@Override
	public Type getType() {
		switch(presence.getType()){
			case available: return Type.available;
			case error: return Type.error;
			case subscribe: return Type.subscribe;
			case subscribed: return Type.subscribed;
			case unavailable: return Type.unavailable;
			case unsubscribe: return Type.unsubscribe;
			case unsubscribed: return Type.unsubscribed;
			default: throw new RuntimeException("Unknown type of presence: " + presence.getType());
		}
	}

}
