package environment.colorwar;

public abstract class AgentController {
	public static enum DIRECTION {
		NORTH,
		SOUTH,
		EAST,
		WEST,
		NONE
	}
	
	public abstract DIRECTION getNextMove();
}
