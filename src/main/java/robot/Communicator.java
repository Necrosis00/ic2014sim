/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robot;


import java.util.List;

/**
 *
 * @author Bernhard Fischer
 */
public class Communicator {

    private Robot robot;
    
    protected Communicator(Robot robot){
	this.robot = robot;
    }
    
    
    protected List<NearRobot> getNearRobots() {
	return this.robot.getEnv().getNearRobots(this.robot.getId());
    }

    protected List<NearRobot> getNearRobots(NearRobot nearRobot){
	return this.robot.getEnv().getNearRobots(nearRobot.getId());
    }
    
    protected List<NearRobot> getKnownRobots(NearRobot nearRobot){
	return this.robot.getEnv().getKnownRobots(nearRobot.getId());
    }
    
}
