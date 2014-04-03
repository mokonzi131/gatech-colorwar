package colorsgame;

import engine.Engine.RENDERING_TYPE;

public class Constants {
	// pre-defined constants
	public static final String GAME_NAME = "Color War";
	
	public static final int CELL_DISTANCE = 20;
	
	public static final int DEVELOPER_VIEW_WIDTH = 800;
	public static final int DEVELOPER_VIEW_HEIGHT = 600;
	
	public static final int AGENT_VIEW_WIDTH = 400;
	public static final int AGENT_VIEW_HEIGHT = 300;
	
	public static final int MINIMUM_NUM_AGENTS = 2;
	
	// settings
	public static RENDERING_TYPE renderingType = RENDERING_TYPE.NORMAL;
	public static int numAgents = 10;
	public static boolean isHumanPlayable = true;
}
