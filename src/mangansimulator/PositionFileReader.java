/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangansimulator;

import Common.Position;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bernhard Fischer
 */
public class PositionFileReader {

    
    public static Position[] readPositionFile(String filepath) throws FileNotFoundException, IOException {
	ArrayList<Position> tempPositions = new ArrayList<>();
	BufferedReader br = new BufferedReader(new FileReader(filepath));
	// line 1
	int numberOfEntries = Integer.parseInt(br.readLine());
	// positions
	String line = null;
	while ((line = br.readLine()) != null) {	    
	    String[] strArray = line.split(" ");
	    int x = Integer.parseInt(strArray[0]);
	    int y = Integer.parseInt(strArray[1]);
	    tempPositions.add(new Position(x, y));
	}
	br.close();
	
	// since also just the number at the beginning can be there, easily return the array and let the simulation check, if there are positions included
	Position[] positions = new Position[numberOfEntries];
	for (int i = 0; i < tempPositions.size(); i++) {
		positions[i] = tempPositions.get(i);
	    }
	return positions;
	

    }
    
}
