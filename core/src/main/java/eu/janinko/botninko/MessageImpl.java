package eu.janinko.botninko;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import eu.janinko.botninko.api.Message.Type;

class MessageImpl implements eu.janinko.botninko.api.Message{
	protected Message message;
	protected Commands commands;

	private static Logger logger = Logger.getLogger(MessageImpl.class);

	MessageImpl(Message msg, Commands cmd ){
		message = msg;
		commands = cmd;
	}

	private Message getSmackMessage(){
		return message;
	}

	@Override
	public String getNick(){
		return message.getFrom().split("/")[1];
	}

	/*private Chat getTarget(){
		logger.trace(message.getThread());
		logger.trace(commands.getRoom().getConnection().getXMPPConnection().getChatManager().getThreadChat(message.getThread()));
		logger.trace(commands.getRoom().getConnection().getXMPPConnection().getChatManager().getThreadChat(message.getThread()).getParticipant());
		return commands.getRoom().getConnection().getXMPPConnection().getChatManager().getThreadChat(message.getThread());
	}*/

	@Override
	public String getText() {
		return message.getBody();
	}

	@Override
	public Type getType() {
		switch(message.getType()){
			case chat: return Type.chat;
			case error: return Type.error;
			case groupchat: return Type.groupchat;
			case headline: return Type.normal;
			case normal: return Type.normal;
			default: throw new RuntimeException("Unknown type of message: " + message.getType());
		}
	}
}
