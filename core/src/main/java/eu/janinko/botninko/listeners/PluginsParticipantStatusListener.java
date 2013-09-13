/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.janinko.botninko.listeners;

import eu.janinko.botninko.Commands;
import eu.janinko.botninko.StatusImpl;
import org.apache.log4j.Logger;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;

/**
 *
 * @author jbrazdil
 */
public class PluginsParticipantStatusListener implements ParticipantStatusListener{
	private Commands commands;
	
	private Logger logger = Logger.getLogger(PluginsParticipantStatusListener.class);
	
	public PluginsParticipantStatusListener(Commands commands){
		this.commands = commands;
	}

	@Override
	public void joined(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.joined, participant));
	}

	@Override
	public void left(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.left, participant));
	}

	@Override
	public void kicked(String participant, String actor, String reason) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.kicked, participant, actor, reason));
	}

	@Override
	public void voiceGranted(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.voiceGranted, participant));
	}

	@Override
	public void voiceRevoked(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.voiceRevoked, participant));
	}

	@Override
	public void banned(String participant, String actor, String reason) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.banned, participant, actor, reason));
	}

	@Override
	public void membershipGranted(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.membershipGranted, participant));
	}

	@Override
	public void membershipRevoked(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.membershipRevoked, participant));
	}

	@Override
	public void moderatorGranted(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.moderatorGranted, participant));
	}

	@Override
	public void moderatorRevoked(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.moderatorRevoked, participant));
	}

	@Override
	public void ownershipGranted(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.ownershipGranted, participant));
	}

	@Override
	public void ownershipRevoked(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.ownershipRevoked, participant));
	}

	@Override
	public void adminGranted(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.adminGranted, participant));
	}

	@Override
	public void adminRevoked(String participant) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.adminRevoked, participant));
	}

	@Override
	public void nicknameChanged(String participant, String newNickname) {
		commands.handleStatus(new StatusImpl(StatusImpl.Type.nicknameChanged, participant, newNickname));
	}
	
}
