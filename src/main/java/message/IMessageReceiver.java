package message;

import java.util.List;

/**
 * 
 * @author Andreas Rain, University of Konstanz
 *
 */
public interface IMessageReceiver {
	
	/**
	 * @param pMessages - Receiving a list of messages
	 * @return true if the message had an impact of any kind, false otherwise
	 */
	public boolean receiveMessages(List<IMessage> pMessages);

}
