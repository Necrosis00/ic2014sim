/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import mangansimulator.Environment;
import mangansimulator.PositionFileReader;

import org.junit.Before;
import org.junit.Test;

import commons.Position;

/**
 * @author Andreas Rain, University of Konstanz
 */
public class SynchronizationTest {
	
	private final String positionenFilePath = "positionen.txt";
	private Environment env;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Position[] positions = PositionFileReader.readPositionFile(positionenFilePath);
		env = new Environment(200);
		
		for (int i = 0; i < positions.length; i++) {
			System.out.println("Adding robot " + positions[i]);
			env.addRobot(positions[i]);
		}
		
		
	}

	/**
	 * Tests if after a synchronization process
	 * each robot knows the other robots.
	 */
	@Test
	public void testSynchronization() {
		env.synchronizeRobots();
	}

}
