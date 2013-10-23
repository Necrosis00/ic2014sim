/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commons;

import java.util.Objects;

/**
 *
 * @author Bernhard Fischer
 */
public class Move {

    private Direction direction;
    private double speed;
    
    public Move(Direction direction, double speed){
	this.direction = direction;
	this.speed = speed;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 43 * hash + Objects.hashCode(this.direction);
	hash = 43 * hash + (int) (Double.doubleToLongBits(this.speed) ^ (Double.doubleToLongBits(this.speed) >>> 32));
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Move other = (Move) obj;
	if (!Objects.equals(this.direction, other.direction)) {
	    return false;
	}
	if (Double.doubleToLongBits(this.speed) != Double.doubleToLongBits(other.speed)) {
	    return false;
	}
	return true;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
	return direction;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
	return speed;
    }
    
    
    
}
