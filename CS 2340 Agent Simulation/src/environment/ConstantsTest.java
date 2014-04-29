package environment;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantsTest {

	@Test
	public void testInBounds() {
		Constants.AGENT_RANGE = 3;
		assertEquals(5, Constants.getInBounds());
		
		Constants.AGENT_RANGE = 5;
		assertEquals(13, Constants.getInBounds());
		
		Constants.AGENT_RANGE = 7;
		assertEquals(25, Constants.getInBounds());
		
		Constants.AGENT_RANGE = 9;
		assertEquals(41, Constants.getInBounds());
	}

}
