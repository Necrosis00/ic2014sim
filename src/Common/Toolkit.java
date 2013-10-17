/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

/**
 *
 * @author Bernhard Fischer
 */
public class Toolkit {

    // returns a random number in a given range
    public static double randomWithinRange(Range outputRange){
	Range randomRange = new Range(0, 0.99999999999999999);
	double value = Math.random();
	double result = Toolkit.mapValueToOtherRange(randomRange, outputRange, value);
	return result;
    }
    
    
    // maps the input value from one range to the proportional value in another range
    public static double mapValueToOtherRange(Range inputRange, Range outputRange, double value){
	return (value - inputRange.getMin()) * (outputRange.getMax() - outputRange.getMin()) / (inputRange.getMax() - inputRange.getMin()) + outputRange.getMin();
    }
    
    // returns the euclidean distance between two points
    public static double euclideanDistance(Position pos1, Position pos2){
	double result = Math.sqrt(Math.pow(pos1.getX()-pos2.getX(),2) + Math.pow(pos1.getY()-pos2.getY(),2));
	return result;
    }
    
    
    public static double direction(Position pos1, Position pos2){
//	int direction = Math.tan
	
	// 0 - matching position
	if (pos1.equals(pos2)) {
	    return -1.0;
	}
	// 1 - bottom left -> top right
	if (pos1.getX() < pos2.getX() && pos1.getY() < pos2.getY()) {
	    // dy / dx, 90 - winkel
	   double angle = Math.toDegrees(Math.atan(Math.abs(pos1.getY() - pos2.getY()) / Math.abs(pos1.getX() - pos2.getX())));
	   return 90.0 - angle;
	}
	// 2 - top left -> bottom right
	if (pos1.getX() < pos2.getX() && pos1.getY() > pos2.getY()) {
	    // dx / dy, 180 - winkel
	    double angle = Math.toDegrees(Math.atan(Math.abs(pos1.getX() - pos2.getX()) / Math.abs(pos1.getY() - pos2.getY())));
	    return 180.0 - angle;
	}
	// 3 - top right -> bottom left
	if (pos1.getX() > pos2.getX() && pos1.getY() > pos2.getY()) {
	    // dx / dy, 180 + winkel
	    double angle = Math.toDegrees(Math.atan(Math.abs(pos1.getX() - pos2.getX()) / Math.abs(pos1.getY() - pos2.getY())));
	    return 180.0 + angle;
	}
	// 4 - bottom right -> top left
	if (pos1.getX() > pos2.getX() && pos1.getY() < pos2.getY()) {
	    // dy / dx, 270 + winkel
	    double angle = Math.toDegrees(Math.atan(Math.abs(pos1.getY() - pos2.getY()) / Math.abs(pos1.getX() - pos2.getX())));
	    return 270.0 + angle;
	}
	// 5 - horizontal, left -> right
	if (pos1.getY() == pos2.getY() && pos1.getX() < pos2.getX()) {
	    return 90.0;
	}
	// 6 - horizontal, right -> left
	if (pos1.getY() == pos2.getY() && pos1.getX() > pos2.getX()) {
	    return 270.0;
	}
	// 7 - vertical, top -> bottom
	if (pos1.getX() == pos2.getX() && pos1.getY() > pos2.getY()) {
	    return 180.0;
	}
	// 8 - vertical, bottom -> top
	if (pos1.getX() == pos2.getX() && pos1.getY() < pos2.getY()) {
	    return 0.0;
	}
	return -1.0;
    }
    
    public static Position destination(Position pos, Move move, double interval){
//	int direction = Math.tan
	double course = move.getDirection().getCourse();
	double distance = move.getSpeed() * interval;
	
	// 0 - matching position
	if (move.getSpeed() == 0) {
	    return pos;
	}
	
	// 5 - horizontal, left -> right
	if (course == 90) {
	    return new Position(pos.getX() + distance, pos.getY());
	}
	// 6 - horizontal, right -> left
	if (course == 270) {
	    return new Position(pos.getX() - distance, pos.getY());
	}
	// 7 - vertical, top -> bottom
	if (course == 180) {
	    return new Position(pos.getX(), pos.getY() - distance);
	}
	// 8 - vertical, bottom -> top
	if (course == 0) {
	    return new Position(pos.getX(), pos.getY() + distance);
	}
	
	// 1 - bottom left -> top right
	if (course > 0 && course < 90) {
	    double tempCourse = 90 - course;
	    double dx = Math.cos(Math.toRadians(tempCourse)) * distance;
	    double dy = Math.sin(Math.toRadians(tempCourse)) * distance;
	    Position newPos = new Position(pos.getX() + dx, pos.getY() + dy);
	    return newPos;
	}
	// 2 - top left -> bottom right
	if (course > 90 && course < 180) {
	    double tempCourse = 180 - course;
	    double dx = Math.sin(Math.toRadians(tempCourse)) * distance;
	    double dy = Math.cos(Math.toRadians(tempCourse)) * distance;
	    Position newPos = new Position(pos.getX() + dx, pos.getY() - dy);
	    return newPos;
	}
	// 3 - top right -> bottom left
	if (course > 180 && course < 270) {
	    double tempCourse = 270 - course;
	    double dx = Math.sin(Math.toRadians(tempCourse)) * distance;
	    double dy = Math.cos(Math.toRadians(tempCourse)) * distance;
	    Position newPos = new Position(pos.getX() - dx, pos.getY() - dy);
	    return newPos;
	}
	// 4 - bottom right -> top left
	if (course > 270 && course < 360) {
	    double tempCourse = 360 - course;
	    double dx = Math.cos(Math.toRadians(tempCourse)) * distance;
	    double dy = Math.sin(Math.toRadians(tempCourse)) * distance;
	    Position newPos = new Position(pos.getX() - dx, pos.getY() + dy);
	    return newPos;
	}
	
	
	return null;
    }
    
    
}
