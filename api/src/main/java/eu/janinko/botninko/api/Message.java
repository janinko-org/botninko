package eu.janinko.botninko.api;


/**
 *
 * @author jbrazdil
 */
public interface Message {

	public enum Type{
		chat,
		error,
		groupchat,
		headline,
		normal
	}
	
    public String getNick();

	public String getText();
	public Type getType();
	
}
