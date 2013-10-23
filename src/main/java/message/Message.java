package message;

import robot.Robot;

import commons.Position;
/**
 * 
 * @author Andreas Rain, University of Konstanz
 *
 */
public class Message implements IMessage {
	
	private Position mPosition;
	private final Robot mRobot;
	
	public Message(Robot pRobot){
		mRobot = pRobot;
	}

	@Override
	public int getSource() {
		return mRobot.getId();
	}

	@Override
	public IMessage createCopy() {
		Message m = new Message(mRobot);
		m.setPosition(mPosition);
		return m;
	}

	@Override
	public Position getPosition() {
		return mPosition;
	}

	@Override
	public void setPosition(Position pPosition) {
		mPosition = pPosition;
	}

	@Override
	public Robot getRobot() {
		return mRobot;
	}

}
