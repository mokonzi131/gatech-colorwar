package environment;

public class Constants extends environment.i.Constants {
	// pre-defined constants
	public static final String GAME_NAME = "Color War";
	public static final int MINIMUM_NUM_AGENTS = 1;
	public static final int MAXIMUM_NUM_AGENTS = 1200;
	public static final int FPS = 24;
	
	// world definitions
	public static final int CELL_DISTANCE = 50;
	public static final int AGENT_RANGE = 5;
	public static final int GRID_SIZE = 10;
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_BUFFER = CELL_DISTANCE * 2;
	public static final int WORLD_WIDTH = (CELL_DISTANCE * GRID_WIDTH) + (2 * GRID_BUFFER);
	public static final int WORLD_HEIGHT = (CELL_DISTANCE * GRID_HEIGHT) + (2 * GRID_BUFFER);
	
	// viewport definitions
	public static final int DEV_VIEW_WIDTH = Math.min(WORLD_WIDTH, 600);
	public static final int DEV_VIEW_HEIGHT = Math.min(WORLD_HEIGHT, 600);
	
	public static final int AGENT_VIEW_WIDTH = Math.min(CELL_DISTANCE * AGENT_RANGE, 200);
	public static final int AGENT_VIEW_HEIGHT = Math.min(CELL_DISTANCE * AGENT_RANGE, 200);
	
	// settings
	public enum RENDERING_TYPE {
		SIMULATED,
		DEVELOPER,
		NORMAL
	}
	
	public enum SPEED {
		HIGH,
		LOW
	}
	
	public static RENDERING_TYPE renderingType = RENDERING_TYPE.NORMAL;
	public static int numAgents = 10;
	public static boolean isHumanPlayable = true;
	public static SPEED speed = SPEED.LOW;	
	
	public static int getInBounds() {
		int seed = AGENT_RANGE;
		int total = seed;
		for (seed -= 2; seed > 0; seed -= 2)
			total += 2 * seed;
		return total;
	}
}
