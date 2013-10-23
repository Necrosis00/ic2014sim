/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangansimulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import commons.Position;
import commons.Range;
import commons.Toolkit;

/**
 *
 * @author Bernhard Fischer
 */
public class Absetzer {
    
    private String filepath = "absetzer_positionen";
    
    public static void absetzen(String outputFilepath, int numberOfRobots, int maxDistance) throws IOException{
	writeFile(outputFilepath, randomPositions(numberOfRobots, maxDistance));
    }
    
    public static Position[] randomPositions(int numberOfPositions, int maxDistance){
	if (numberOfPositions <= 0) {
	    return new Position[0];
	}
	Position[] positions = new Position[numberOfPositions];
	// get first random
	    // get x random between 0 and 200
	    // get y random that matches the max 200 distance
	int x = (int) Toolkit.randomWithinRange(new Range(0, maxDistance));
	int y = (int) Toolkit.randomWithinRange(getYRange(new Position(0, 0), x, maxDistance));
	positions[0] = new Position(x, y);
	
	// den rest abhängig dazu zufällig erzeugen
	for (int i = 1; i < numberOfPositions; i++) {
	    // get one random of the already existing positions and create a random new position in maxdistance range
	    // get random one, existing!
	    Position existingRandomPosition = positions[Math.round((float) Toolkit.randomWithinRange(new Range(0, i-1)))];
	    // create new random from this positon
	    int newX = (int) Toolkit.randomWithinRange(new Range(existingRandomPosition.getX() - maxDistance, existingRandomPosition.getX() + maxDistance));
	    int newY = (int) Toolkit.randomWithinRange(getYRange(existingRandomPosition, newX, maxDistance));
	    positions[i] = new Position(newX, newY);
	}
	// verschieben, damit alle koordinaten positiv sind
	double minX = Double.MAX_VALUE;
	double minY = Double.MAX_VALUE;
	for (int i = 0; i < positions.length; i++) {
	    if (positions[i].getX() < minX) {
		minX = positions[i].getX();
	    }
	    if (positions[i].getY() < minY) {
		minY = positions[i].getY();
	    }
	}
	if (minX < 0) {
	    for (int i = 0; i < positions.length; i++) {
		// do the shift for x
		positions[i].setX(positions[i].getX() - minX);
	    }
	}
	if (minY < 0) {
	    for (int i = 0; i < positions.length; i++) {
		// do the shift for y
		positions[i].setY(positions[i].getY() - minY);
	    }
	}
	return positions;
    }
    
    // given a point and a x value, which are the boundries for y value to match a maximum distance constraint
    private static Range getYRange(Position reference, int chosenX, int distanceContraint){
	Range range = new Range(getMaxYwithDistanceContraintMinus(reference, chosenX, distanceContraint), 
		getMaxYwithDistanceContraintPlus(reference, chosenX, distanceContraint));
	return range;
    }
    
    private static int getMaxYwithDistanceContraintMinus(Position reference, int chosenX, int distanceContraint){
	double tempNegative = Math.sqrt(Math.pow(distanceContraint, 2) - (Math.pow((reference.getX() - chosenX), 2)));
	int maxY = (int) (reference.getY() - tempNegative);
	return maxY;
    }
    
    private static int getMaxYwithDistanceContraintPlus(Position reference, int chosenX, int distanceContraint){
	double tempPositive = Math.sqrt(Math.pow(distanceContraint, 2) - (Math.pow((reference.getX() - chosenX), 2)));
	int maxY = (int) (reference.getY() + tempPositive);
	return maxY;
    }
  
    private static void writeFile(String filepath, Position[] positions) throws IOException{
	BufferedWriter br;
	br = new BufferedWriter(new FileWriter(filepath));
	br.append(positions.length + "\n");
	for (int i = 0; i < positions.length; i++) {
	    br.append(positions[i].getX() + " " + positions[i].getY() + "\n");
	}
	br.close();
    }
    
}
