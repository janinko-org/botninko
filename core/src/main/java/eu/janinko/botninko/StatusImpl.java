package eu.janinko.botninko;

import eu.janinko.botninko.api.Status;

/**
 *
 * @author jbrazdil
 */
public class StatusImpl implements Status{
	private final Type type;
	private final String participant;
	private final String newNickname;
	private final String actor;
	private final String reason;
	
	public StatusImpl(Type type, String participant) {
		this.type = type;
		this.participant = participant;
		this.newNickname = null;
		this.actor = null;
		this.reason = null;
	}

	public StatusImpl(Type type, String participant, String newNickname) {
		this.type = type;
		this.participant = participant;
		this.newNickname = newNickname;
		this.actor = null;
		this.reason = null;
	}

	public StatusImpl(Type type, String participant, String actor, String reason) {
		this.type = type;
		this.participant = participant;
		this.newNickname = null;
		this.actor = actor;
		this.reason = reason;
	}

	@Override
	public String getNick() {
		return participant.split("/")[1];
	}

	@Override
	public Type getType() {
		return type;
	}

	public String getParticipant() {
		return participant;
	}

	public String getNewNickname() {
		return newNickname;
	}

	public String getActor() {
		return actor;
	}

	public String getReason() {
		return reason;
	}
}
