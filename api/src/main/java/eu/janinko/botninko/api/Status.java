package eu.janinko.botninko.api;

/**
 *
 * @author jbrazdil
 */
public interface Status {
	
	public enum Type{
		joined,
		left,
		kicked,
		voiceGranted,
		voiceRevoked,
		banned,
		membershipGranted,
		membershipRevoked,
		moderatorGranted,
		moderatorRevoked,
		ownershipGranted,
		ownershipRevoked,
		adminGranted,
		adminRevoked,
		nicknameChanged
	}

	public Type getType();
	
	public String getNick();
	
}
