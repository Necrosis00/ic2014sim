/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package zrubbish;

import commons.Position;

/**
 *
 * @author Bernhard Fischer
 */
public class EnvironmentRobotRepresentation {

    private Position realPosition;
    private Position startPosition;
    private int id;
    
    public EnvironmentRobotRepresentation(int id, Position startPosition){
	this.id = id;
	this.startPosition = startPosition;
    }

    /**
     * @return the realPosition
     */
    public Position getRealPosition() {
	return realPosition;
    }

    /**
     * @param realPosition the realPosition to set
     */
    public void setRealPosition(Position realPosition) {
	this.realPosition = realPosition;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @return the startPosition
     */
    public Position getStartPosition() {
	return startPosition;
    }
    
}
