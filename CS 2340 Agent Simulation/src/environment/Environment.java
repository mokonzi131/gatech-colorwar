package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import colorsgame.Constants;

// a grid of tiles, most tiles are valid, some are invalid,
//  some tiles contain colors, also keep track of which players (agents) are where...
public class Environment {
	private static final Random random = new Random();
	private static final int WORLD_WIDTH = Constants.DEVELOPER_VIEW_WIDTH;
	private static final int WORLD_HEIGHT = Constants.DEVELOPER_VIEW_HEIGHT;
	private static final int MAP_CELLS_X = WORLD_WIDTH / Constants.CELL_DISTANCE - 2;
	private static final int MAP_CELLS_Y = WORLD_HEIGHT / Constants.CELL_DISTANCE - 2;
	
	private boolean[][] grid = new boolean[MAP_CELLS_X][MAP_CELLS_Y];
	private List<Cell> m_cells;
	
	public Environment() {
		m_cells = new ArrayList<>();
		
		// layout initial grid
		for (int i = 0; i < MAP_CELLS_X; ++i)
			for (int j = 0; j < MAP_CELLS_Y; ++j) {
				boolean create = random.nextInt(100) > 12 ? true : false;
				grid[i][j] = create;
				if (create)
					m_cells.add(createCell(i, j));
			}
	}
	
	private Cell createCell(int i, int j) {
		float[] worldLoc = gridToWorld(i, j);
		boolean resource = random.nextInt(100) > 33 ? true : false;
		return new Cell(worldLoc[0], worldLoc[1], resource);
	}
	
	private float[] gridToWorld(int i, int j) {
		float x, y;
		x = i * Constants.CELL_DISTANCE + 1.5f * Constants.CELL_DISTANCE;
		y = j * Constants.CELL_DISTANCE + 1.5f * Constants.CELL_DISTANCE;
		return new float[]{x, y};
	}
	
	public List<Cell> cells() { return m_cells; }
	
	public int getWidth() { return MAP_CELLS_X; }
	public int getHeight() { return MAP_CELLS_Y; }
}
