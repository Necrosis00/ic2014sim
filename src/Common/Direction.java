/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

/**
 *
 * @author Bernhard Fischer
 */
public class Direction {
    
    private double course;

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 59 * hash + (int) (Double.doubleToLongBits(this.course) ^ (Double.doubleToLongBits(this.course) >>> 32));
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
	final Direction other = (Direction) obj;
	if (Double.doubleToLongBits(this.course) != Double.doubleToLongBits(other.course)) {
	    return false;
	}
	return true;
    }


    
    public Direction(double course){
	this.course = course % 360;
    }

    /**
     * @return the course
     */
    public double getCourse() {
	return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(double course) {
	this.course = course % 360;
    }
    
    
    
    
}
