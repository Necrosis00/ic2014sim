/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import commons.Direction;
import commons.Move;
import commons.Position;
import commons.Toolkit;
import mangansimulator.Environment;
import message.IMessage;
import message.IMessageBroadcaster;
import message.IMessageReceiver;
import message.Message;

/**
 * 
 * @author Bernhard Fischer
 */
public class Robot implements IMessageBroadcaster, IMessageReceiver {

	private static int idCounter;

	private int id;
	private Environment env;
	private Position relativePosition;
	private double velocity;
	private Direction direction;
	private boolean finalState = false;

	private ArrayList<Position> pathHistory = new ArrayList<>();

	// Calculate map
	private List<NearRobot> robotsInRange;
	private List<Position> calculatedMap;
	private Position centerOfCalculatedMap;

	private HashMap<Integer, NearRobot> knownRobots = new HashMap<>();
	private boolean swarmAnalyzingMode = true;

	public Robot(Environment env) {
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
	 * @param relativePosition
	 *            the relativePosition to set
	 */
	public void setRelativePosition(Position relativePosition) {
		this.relativePosition = relativePosition;
	}

	// public Move decideNextMove() {
	// // TODO decision next move
	// // wenn der näheste der nearRobots über 75% der maxdistance entfernt
	// // ist, hänge dich voll an ihn ran, damit man nicht verloren geht?
	//
	// // build map - possible?
	// // build graph
	//
	// // dont forget to set final state
	// return null;
	// }

	// returns true, if something changed
	// returns false, if nothing changed
	// private boolean updateGraph() {
	// // BIG TODO: idea so not working
	// // check if new robots are found
	//
	// // check if robots in range are already in knownRobots (and update)
	// for (NearRobot nearRobot : robotsInRange) {
	// knowRobots.put(nearRobot.getId(), nearRobot);
	// }
	// // for the near robots, get all, which they know,
	// HashMap<Integer, List<NearRobot>> nearRobotsKnownRobots = new
	// HashMap<>();
	// for (NearRobot nearRobot : robotsInRange) {
	// nearRobotsKnownRobots.put(nearRobot.getId(),
	// comm.getKnownRobots(nearRobot));
	// }
	// // since we know the position of the nears, we can recalculate the
	// // values for
	//
	// return true;
	// }

	// run all things to do within one timeslot, started by the environment
	// public void iterate(double interval) {
	// // TODO run all the robot logic
	// // update near robots
	// this.robotsInRange = this.comm.getNearRobots();
	//
	// if (swarmAnalyzingMode) {
	// // try to get to know the other robots
	// boolean somethingChanged = this.updateGraph();
	// // set swamanalyzing mode
	// if (!somethingChanged) {
	// swarmAnalyzingMode = false;
	// }
	// } else {
	// // drive
	//
	// // decision where to go next
	// Move nextMove = this.decideNextMove();
	// // make move there
	// this.makeMove(nextMove, interval);
	// // set final state,
	//
	// }
	//
	// }

	// private void makeMove(Move move, double interval) {
	// double moveDistance = move.getSpeed() * interval;
	// Position resultingPosition = Toolkit.destination(this.relativePosition,
	// move, interval);
	// this.pathHistory.add(this.relativePosition);
	// this.relativePosition = resultingPosition;
	// }

	public boolean isFinalState() {
		return this.finalState;
	}

	/**
	 * @return the env
	 */
	protected Environment getEnv() {
		return env;
	}

	public List<NearRobot> getKnownRobots() {
		ArrayList<NearRobot> list = new ArrayList<>();
		for (Iterator<Integer> it = this.knownRobots.keySet().iterator(); it
				.hasNext();) {
			Integer knownRobotId = it.next();
			list.add(this.knownRobots.get(knownRobotId));
		}
		return list;
	}

	/**
	 * Apply this method when knowledge has been shared across the swarm.
	 */
	public void calculateMap() {
		System.out.println(id + " knows " + knownRobots.values().size()
				+ " robots.");

		Position[] positions = new Position[knownRobots.values().size() + 1];

		// calc all relative positions first.
		int i = 0;
		for (NearRobot r : knownRobots.values()) {
			positions[i] = new Position(r.getDistance()
					* Math.cos(r.getDirection().getCourse()
							* Math.toRadians(r.getDirection().getCourse())),
					r.getDistance()
							* Math.sin(r.getDirection().getCourse()
									* Math.toRadians(r.getDirection()
											.getCourse())));
			i++;
		}
		
		positions[positions.length-1] = this.relativePosition;
		
		// Determine the center of all positions
		
		double lowestX = 0, lowestY = 0, highestX = 0, highestY = 0.0;
		for (int j = 0; j < positions.length; j++) {
			if(positions[j].getX() < lowestX){
				lowestX = positions[j].getX();
			}
			if(positions[j].getX() > highestX){
				highestX = positions[j].getX();
			}
			if(positions[j].getY() < lowestY){
				lowestY = positions[j].getY();
			}
			if(positions[j].getY() > highestY){
				highestY = positions[j].getY();
			}
		}
		
		double centerX = highestX -((highestX - lowestX) * 0.5);
		double centerY = highestY -((highestY - lowestY) * 0.5);
		
		centerOfCalculatedMap = new Position(centerX, centerY);
		System.out.println("Centerposition is " + centerOfCalculatedMap);
		// Recalculate own position, relative to the center

	}

	/**
	 * Compare near robots for similarities.
	 * 
	 * @param pRobot
	 * @return true if there was new knowledge gained
	 */
	private boolean compareKnownRobots(Robot pRobot) {
		boolean impact = false;
		for (NearRobot r : pRobot.getKnownRobots()) {
			if (!knownRobots.containsKey(r.getId())) {
				impact = true;
				
				// Recalculated distance and direction to the near robot.
				//NearRobot recalculatedRobot = new NearRobot(idCounter, direction, distance);
				
				//knownRobots.put(r.getId(), recalculatedRobot);
				knownRobots.put(r.getId(), r);
			}
		}
		return impact;
	}

	@Override
	public boolean receiveMessages(List<IMessage> pMessages) {
		boolean impact = false;

		// if no message changes anything then the impact is false.
		for (IMessage m : pMessages) {
			if (!knownRobots.containsKey(m.getRobot().getId())) {
				knownRobots.put(
						m.getRobot().getId(),
						new NearRobot(m.getRobot().getId(), new Direction(
								Toolkit.direction(this.relativePosition,
										m.getPosition())), Toolkit
								.euclideanDistance(this.relativePosition,
										m.getPosition())));
				impact = true;
			}

			if (compareKnownRobots(m.getRobot())) {
				impact = true;
			}
		}

		return impact;
	}

	@Override
	public IMessage broadcastMessage() {
		Message m = new Message(this);
		return m;
	}

}
