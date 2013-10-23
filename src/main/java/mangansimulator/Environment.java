/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangansimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import message.IMessage;
import robot.NearRobot;
import robot.Robot;
import commons.Direction;
import commons.Position;
import commons.Toolkit;

/**
 * 
 * @author Bernhard Fischer
 */
public class Environment {

	private double maxDistance;
	private HashMap<Integer, Robot> robots = new HashMap<>();
	private HashMap<Integer, Position> realRobotPositions = new HashMap<>();
	private HashMap<Integer, Position> startRobotPositions = new HashMap<>();

	public Environment(double maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	/**
	 * Synchronize robots based on their landing points.
	 * This procedure takes less than log_2(n), where n
	 * is the amount of robots distributed.
	 * 
	 * @author Andreas Rain, University of Konstanz
	 */
	public void synchronizeRobots(){
		List<IMessage> messagesPerTick;
		boolean unchanged = false;
		
		while(!unchanged){
			messagesPerTick = new ArrayList<>();
			// Broadcast sequence
			for(Robot r : robots.values()){
				// Save this message and send it to robots in range
				messagesPerTick.add(r.broadcastMessage());
			}
			
			// Receive sequence
			for(Robot r : robots.values()){
				// Determine messages that are in range of r!
				List<IMessage> inrangeMessages = new ArrayList<>();
				for(IMessage m : messagesPerTick){
					// Iff the robots are in a 200m range.
					Position source = startRobotPositions.get(m.getSource());
					Position dest = startRobotPositions.get(r.getId());
					if(Toolkit.euclideanDistance(source, dest) <= maxDistance){
						IMessage copy = m.createCopy();
						copy.setPosition(new Position(source.getX()-dest.getX(), source.getY()-dest.getY()));
						// Add a copy of the message with the
						// relative position to the destination (this is the only position
						// the destination is able to know for now.
						inrangeMessages.add(copy);
					}
				}
				
				// Let r receive the messages, see if something has changed.
				if(!r.receiveMessages(inrangeMessages)){
					unchanged = true;
				}
			}
		}
		
		// Calculate map based on the knowledge given to them by synchronizing
		for(Robot r : robots.values()){
			r.calculateMap();
		}
		
	}

	public void addRobot(Position position) {
		Robot robot = new Robot(this);
		this.robots.put(robot.getId(), robot);
		startRobotPositions.put(robot.getId(), position);
		realRobotPositions.put(robot.getId(), position);
	}

	public List<NearRobot> getNearRobots(int robotId) {
		ArrayList<NearRobot> nearRobots = new ArrayList<>();

		for (Map.Entry<Integer, Position> entry : realRobotPositions.entrySet()) {
			Integer id = entry.getKey();
			Position position = entry.getValue();

			// calc distance to all other robots, and return those which are
			// under maxDistance
			for (Map.Entry<Integer, Position> entry1 : realRobotPositions
					.entrySet()) {
				Integer otherId = entry1.getKey();
				Position otherPosition = entry1.getValue();
				double distance = Toolkit.euclideanDistance(position,
						otherPosition);
				if (distance <= maxDistance) {
					nearRobots.add(new NearRobot(otherId, new Direction(Toolkit
							.direction(position, otherPosition)), distance));
				}
			}
		}

		return nearRobots;
	}

	/**
	 * @return the robots
	 */
	public List<Robot> getRobots() {
		ArrayList<Robot> list = new ArrayList<>();
		for (Iterator<Integer> it = this.robots.keySet().iterator(); it
				.hasNext();) {
			Integer robotId = it.next();
			list.add(this.robots.get(robotId));
		}
		return list;
	}

	public List<NearRobot> getKnownRobots(int robotId) {
		Robot robot = this.robots.get(robotId);
		ArrayList<NearRobot> list = new ArrayList<>();
		for (NearRobot nearRobot : robot.getKnownRobots()) {
			list.add(new NearRobot(nearRobot.getId(), nearRobot.getDirection(),
					nearRobot.getDistance()));
		}
		return list;
	}

	// public Robot getRobot(int robotId){
	// return this.robots.get(robotId);
	// }

	protected void updateRobotPositions() {
		for (Iterator<Integer> it = this.robots.keySet().iterator(); it
				.hasNext();) {
			Integer robotId = it.next();
			Robot robot = this.robots.get(robotId);
			Position startPosition = this.startRobotPositions
					.get(robot.getId());
			Position relativePosition = robot.getRelativePosition();
			Position resultingRealPosition = new Position(startPosition.getX()
					+ relativePosition.getX(), startPosition.getY()
					+ relativePosition.getY());
			this.realRobotPositions.put(robot.getId(), resultingRealPosition);
			// TODO: Logging?

		}

	}

}
