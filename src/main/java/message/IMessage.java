package message;

import robot.Robot;
import commons.Position;

/**
 * 
 * @author Andreas Rain, University of Konstanz
 *
 */
public interface IMessage {
	
	/**
	 * Source if of the robot
	 * @return
	 */
	public int getSource();
	
	/**
	 * A copy of the message since different destinations require different settings.
	 * @return 
	 */
	public IMessage createCopy();
	
	/**
	 * The destination robot receives only a position that
	 * is relative to it's own position as the center.
	 * @return position
	 */
	public Position getPosition();
	
	/**
	 * The destination robot receives only a position that
	 * is relative to it's own position as the center.
	 * @param pPosition
	 */
	public void setPosition(Position pPosition);
	
	/**
	 * The source robot sending this message
	 * @return robot
	 */
	public Robot getRobot();

}