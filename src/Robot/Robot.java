/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import java.util.ArrayList;
import Common.Direction;
import Common.Move;
import Common.Position;
import Common.Toolkit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import mangansimulator.Environment;

/**
 *
 * @author Bernhard Fischer
 */
public class Robot {
    
    private static int idCounter;
    
    private int id;
    private Environment env;
    private ArrayList<Position> pathHistory = new ArrayList<>();
    private Position relativePosition;
    private List<NearRobot> robotsInRange;
    private double velocity;
    private Direction direction;
    private boolean finalState = false;
    
    private Communicator comm = new Communicator(this);
    private HashMap<Integer,NearRobot> knowRobots = new HashMap<>();
    private boolean swarmAnalyzingMode = true;
    
    
    
    public Robot(Environment env){
	idCounter++;
	this.id = idCounter;
	this.env = env;
	this.relativePosition = new Position(0, 0);
	this.velocity = 0;
	this.direction = new Direction(0);
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @return the relativePosition
     */
    public Position getRelativePosition() {
	return relativePosition;
    }

    /**
     * @param relativePosition the relativePosition to set
     */
    public void setRelativePosition(Position relativePosition) {
	this.relativePosition = relativePosition;
    }


    
    public Move decideNextMove(){
//	TODO decision next move
	// wenn der näheste der nearRobots über 75% der maxdistance entfernt ist, hänge dich voll an ihn ran, damit man nicht verloren geht?
	
	// build map - possible?
	// build graph
	
	// dont forget to set final state
	return null;
    }
    
    
    // returns true, if something changed
    // returns false, if nothing changed
    private boolean updateGraph(){
//	BIG TODO: idea so not working
	// check if new robots are found 
	
	// check if robots in range are already in knownRobots (and update)
	for (NearRobot nearRobot : robotsInRange) {
	    knowRobots.put(nearRobot.getId(), nearRobot);
	}
	// for the near robots, get all, which they know, 
	HashMap<Integer,List<NearRobot>> nearRobotsKnownRobots = new HashMap<>();
	for (NearRobot nearRobot : robotsInRange) {
	    nearRobotsKnownRobots.put(nearRobot.getId(), comm.getKnownRobots(nearRobot));
	}
	// since we know the position of the nears, we can recalculate the values for 
	
	return true;
    }
    

    
    
    
    // run all things to do within one timeslot, started by the environment
    public void iterate(double interval){
//	TODO run all the robot logic
	// update near robots
	this.robotsInRange = this.comm.getNearRobots();
	
	
	if (swarmAnalyzingMode) {
	    // try to get to know the other robots
	    boolean somethingChanged = this.updateGraph();
	    // set swamanalyzing mode
	    if (!somethingChanged) {
		swarmAnalyzingMode = false;
	    }
	} else {
	    // drive
	    
	    // decision where to go next
	    Move nextMove = this.decideNextMove();
	    // make move there
	    this.makeMove(nextMove, interval);
	    // set final state,

	}
	
    }

    
    private void makeMove(Move move, double interval){
	double moveDistance = move.getSpeed() * interval;
	Position resultingPosition = Toolkit.destination(this.relativePosition, move, interval);
	this.pathHistory.add(this.relativePosition);
	this.relativePosition = resultingPosition;
    }
    
    
    public boolean isFinalState(){
	return this.finalState;
    }
    
    
    /**
     * @return the env
     */
    protected Environment getEnv() {
	return env;
    }
    
    public List<NearRobot> getKnownRobots(){
	ArrayList<NearRobot> list = new ArrayList<>();
	for (Iterator<Integer> it = this.knowRobots.keySet().iterator(); it.hasNext();) {
	    Integer knownRobotId = it.next();
	    list.add(this.knowRobots.get(knownRobotId));
	}
	return list;
    }
    
}
