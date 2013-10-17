/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Robot;

import Common.Direction;

/**
 *
 * @author Bernhard Fischer
 */
public class NearRobot {

    private int id;
    private Direction direction;
    private double distance;
    
    public NearRobot(int id, Direction direction, double distance){
	this. id = id;
	this.direction = direction;
	this.distance = distance;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
	return direction;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
	return distance;
    }
    
    
}
