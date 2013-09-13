package eu.janinko.botninko.api;

import java.io.IOException;
import java.util.TimerTask;

/**
 *
 * @author jbrazdil
 */
public interface PluginHelper {
    void sendMessage(String message);
    public String getPrefix();
	public Object loadData();
	public void saveData(Object o) throws IOException;
	public void startRepeatingTask(TimerTask task, long period);

	public void sendPrivateMessage(String nick, String msg);
}
