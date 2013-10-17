/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

/**
 *
 * @author Bernhard Fischer
 */
public class Range {

    private double min;
    private double max;

    public Range(double min, double max){
	this.min = min;
	this.max = max;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 13 * hash + (int) (Double.doubleToLongBits(this.min) ^ (Double.doubleToLongBits(this.min) >>> 32));
	hash = 13 * hash + (int) (Double.doubleToLongBits(this.max) ^ (Double.doubleToLongBits(this.max) >>> 32));
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
	final Range other = (Range) obj;
	if (Double.doubleToLongBits(this.min) != Double.doubleToLongBits(other.min)) {
	    return false;
	}
	if (Double.doubleToLongBits(this.max) != Double.doubleToLongBits(other.max)) {
	    return false;
	}
	return true;
    }

    /**
     * @return the min
     */
    public double getMin() {
	return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(double min) {
	this.min = min;
    }

    /**
     * @return the max
     */
    public double getMax() {
	return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
	this.max = max;
    }

    @Override
    public String toString() {
	return "Range{" + "min=" + min + ", max=" + max + '}';
    }
    
    
}
