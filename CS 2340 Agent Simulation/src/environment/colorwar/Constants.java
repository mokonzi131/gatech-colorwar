package environment.colorwar;

import view.engine.Engine.RENDERING_TYPE;

public class Constants {
	// pre-defined constants
	public static final String GAME_NAME = "Color War";
	public static final int MINIMUM_NUM_AGENTS = 2;
	public static final int FPS = 24;
	
	// world definitions
	public static final int CELL_DISTANCE = 32;
	public static final int AGENT_RANGE = 5;
	public static final int GRID_WIDTH = 40;
	public static final int GRID_HEIGHT = 30;
	public static final int GRID_BUFFER = CELL_DISTANCE * 2;
	public static final int WORLD_WIDTH = (CELL_DISTANCE * GRID_WIDTH) + (2 * GRID_BUFFER) + (CELL_DISTANCE / 2);
	public static final int WORLD_HEIGHT = (CELL_DISTANCE * GRID_HEIGHT) + (2 * GRID_BUFFER) + (CELL_DISTANCE / 2);
	
	// viewport definitions
	public static final int DEV_VIEW_WIDTH = Math.min(WORLD_WIDTH, 800);
	public static final int DEV_VIEW_HEIGHT = Math.min(WORLD_HEIGHT, 600);
	
	public static final int AGENT_VIEW_WIDTH = Math.min(CELL_DISTANCE * AGENT_RANGE, 200);
	public static final int AGENT_VIEW_HEIGHT = Math.min(CELL_DISTANCE * AGENT_RANGE, 200);
	
	// settings
	public static RENDERING_TYPE renderingType = RENDERING_TYPE.NORMAL;
	public static int numAgents = 10;
	public static boolean isHumanPlayable = true;
}
