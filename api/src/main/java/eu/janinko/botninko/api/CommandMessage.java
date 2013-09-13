package eu.janinko.botninko.api;

/**
 *
 * @author jbrazdil
 */
public interface CommandMessage extends Message {
    String[] getArgs();

    public String getBody();

	public String getArg(int i);

}
