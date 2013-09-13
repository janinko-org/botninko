package eu.janinko.botninko;

import eu.janinko.botninko.api.CommandMessage;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author jbrazdil
 */
class CommandMessageImpl extends MessageImpl implements CommandMessage {
	String body;
	String[] args;

	CommandMessageImpl(Message msg, Commands cmd, String body){
		super(msg, cmd);
		this.body = body;
		this.args = body.split(" +");
	}


	@Override
	public String[] getArgs() {
		return args.clone();
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public String getArg(int i) {
		return args[i];
	}

}
