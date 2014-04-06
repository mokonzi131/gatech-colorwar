package environment.colorwar;

import java.util.Random;

public class RandomAgentController extends AgentController {
	private final Random m_random;
	
	public RandomAgentController() {
		m_random = new Random();
	}
	
	@Override
	public DIRECTION getNextMove() {
		int variable = m_random.nextInt(5);
		switch(variable) {
		case 0:
			return DIRECTION.NORTH;
		case 1:
			return DIRECTION.EAST;
		case 2:
			return DIRECTION.SOUTH;
		case 3:
			return DIRECTION.WEST;
		default:
			return DIRECTION.NONE;
		}
	}
}
