package eu.janinko.botninko.api;

/**
 *
 * @author jbrazdil
 */
public interface Presence {

	public String getNick();

	public enum Type{
		available,
		error,
		subscribe,
		subscribed,
		unavailable,
		unsubscribe,
		unsubscribed
	}

	public Type getType();
}
