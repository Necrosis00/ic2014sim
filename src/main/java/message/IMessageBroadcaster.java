package message;

/**
 * 
 * @author Andreas Rain, University of Konstanz
 *
 */
public interface IMessageBroadcaster {

	/**
	 * Broadcast a message using the desired environment.
	 * @return the message that is to be broadcast
	 */
	public IMessage broadcastMessage();
	
}
