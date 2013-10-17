/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangansimulator;

import Common.Position;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Bernhard Fischer
 */
public class ScenarioSimulation {

    private Environment env;
    
    
    
    public ScenarioSimulation(){
    }
    
    // number of robots will be ignored, if a file already exists
    public void start(String positionsInputFilepath, int maxDistance, int numberOfRobots) throws FileNotFoundException, IOException{
	this.initialize(positionsInputFilepath, maxDistance, numberOfRobots);
	
	boolean endCondition = false;
	double simIntervalLength = 1.0; // precision of the simulation (sample rate)
	double simTime = 0;
	// run loop
	while (!endCondition) {
	    
	    // run for all robots the "iterate" method
	    for (int i = 0; i < this.env.getRobots().size(); i++) {
		// update robot position (let move do by robot and save value into the robot, 
		this.env.getRobots().get(i).iterate(simIntervalLength);
	    }
	    
	    // then let the environment check the positions and log them) and log it
		// TODO logging, but robots save their path themselves
	    this.env.updateRobotPositions();
	    // calculate what distance was driven and add to counter???

	    // calculate how many mangan collected
	    
	    // check if all robots have got into a final state. if so, end the while loop
	    boolean aRobotHasNotFinished = true;
	    for (int i = 0; i < this.env.getRobots().size(); i++) {
		if (this.env.getRobots().get(i).isFinalState() == false) {
		    aRobotHasNotFinished = true;
		    break;
		}
	    }
	    if (!aRobotHasNotFinished) {
		endCondition = true;
	    }
	}
	   
	// to the after processing, like calculating the amount of gathered material and distance driven altogether
	
	// output the results
    }
	    
	
    
    
    // number of robots will be ignored, of a file already exists
    public void initialize(String positionsInputFilepath, int maxDistance, int numberOfRobots) throws FileNotFoundException, IOException{
	// create environment
	this.env = new Environment(maxDistance);
	
	// ensure, that a file with position exists
	
	// if positions file exists, try to read from it. else do run the "absetzer"
	if (new File(positionsInputFilepath).exists()) {
	    // read from file
	    Position[] positions = PositionFileReader.readPositionFile(positionsInputFilepath);
	    // check if only the length is important, or if positions are included
	    if (positions[1] == null) {
		Absetzer.absetzen(positionsInputFilepath, positions.length, maxDistance);
	    }
	} else {
	    Absetzer.absetzen(positionsInputFilepath, numberOfRobots, maxDistance);
	}
	
	
	// get initial robot positions and number
	Position[] initialRobotPositions = PositionFileReader.readPositionFile(positionsInputFilepath);
	// create robots
	for (int i = 0; i < initialRobotPositions.length; i++) {
	    this.env.addRobot(initialRobotPositions[i]);
	}
	
	
    }
    


//    /**
//     * @return the env
//     */
//    public Environment getEnv() {
//	return env;
//    }
    
    
}
