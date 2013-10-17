/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangansimulator;

import Common.Direction;
import Common.Position;
import Common.Toolkit;
import Robot.NearRobot;
import Robot.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Bernhard Fischer
 */
public class Environment {
    
    private double maxDistance;
    private HashMap<Integer,Robot> robots = new HashMap<>();
    private HashMap<Integer,Position> realRobotPositions = new HashMap<>();
    private HashMap<Integer,Position> startRobotPositions = new HashMap<>();
    
    
    protected Environment(double maxDistance){
	this.maxDistance = maxDistance;
    }
    
    protected void addRobot(Position position){
	Robot robot = new Robot(this);
	this.robots.put(robot.getId(), robot);
	startRobotPositions.put(robot.getId(), position);
	realRobotPositions.put(robot.getId(), position);
    }
    
    
     public List<NearRobot> getNearRobots(int robotId){
	ArrayList<NearRobot> nearRobots = new ArrayList<>();
	
	for (Map.Entry<Integer, Position> entry : realRobotPositions.entrySet()) {
	    Integer id = entry.getKey();
	    Position position = entry.getValue();
	    
	    // calc distance to all other robots, and return those which are under maxDistance
	    for (Map.Entry<Integer, Position> entry1 : realRobotPositions.entrySet()) {
		Integer otherId = entry1.getKey();
		Position otherPosition = entry1.getValue();
		double distance = Toolkit.euclideanDistance(position, otherPosition);
		if (distance <= maxDistance) {
		    nearRobots.add(new NearRobot(otherId, new Direction(Toolkit.direction(position, otherPosition)), distance));
		}
	    }
	}
	
	return nearRobots;
    }

     
    /**
     * @return the robots
     */
    protected List<Robot> getRobots() {
	ArrayList<Robot> list = new ArrayList<>();
	for (Iterator<Integer> it = this.robots.keySet().iterator(); it.hasNext();) {
	    Integer robotId = it.next();
	    list.add(this.robots.get(robotId));
	}
	return list;
    }

    public List<NearRobot> getKnownRobots(int robotId){
	Robot robot = this.robots.get(robotId);
	ArrayList<NearRobot> list = new ArrayList<>();
	for (NearRobot nearRobot : robot.getKnownRobots()) {
	    list.add(new NearRobot(nearRobot.getId(), nearRobot.getDirection(), nearRobot.getDistance()));
	}
	return list;
    }
//    public Robot getRobot(int robotId){
//	return this.robots.get(robotId);
//    }
    
    protected void updateRobotPositions() {
	for (Iterator<Integer> it = this.robots.keySet().iterator(); it.hasNext();) {
	    Integer robotId = it.next();
	    Robot robot = this.robots.get(robotId);
	    Position startPosition = this.startRobotPositions.get(robot.getId());
	    Position relativePosition = robot.getRelativePosition();
	    Position resultingRealPosition = new Position(startPosition.getX() + relativePosition.getX(), startPosition.getY() + relativePosition.getY());
	    this.realRobotPositions.put(robot.getId(), resultingRealPosition);
//	    TODO: Logging?
	    
	}

    }
    
}
