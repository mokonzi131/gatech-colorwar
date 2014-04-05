package environment;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import colorsgame.Constants;

// a grid of tiles, most tiles are valid, some are invalid,
//  some tiles contain colors, also keep track of which players (agents) are where...
public class Environment {
	private static final Random random = new Random();
	
	private boolean[][] grid = new boolean[Constants.GRID_WIDTH][Constants.GRID_HEIGHT];
	private List<Cell> m_cells;
	
	public Environment() {
		m_cells = new ArrayList<>();
		
		// layout initial grid
		for (int i = 0; i < Constants.GRID_WIDTH; ++i)
			for (int j = 0; j < Constants.GRID_HEIGHT; ++j) {
				boolean create = random.nextInt(100) > 10 ? true : false;
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
		x = i * Constants.CELL_DISTANCE + 0.5f * Constants.CELL_DISTANCE + Constants.GRID_BUFFER;
		y = j * Constants.CELL_DISTANCE + 0.5f * Constants.CELL_DISTANCE + Constants.GRID_BUFFER;
		return new float[]{x, y};
	}
	
	public List<Cell> cells() { return m_cells; }
	
	public Point2D.Float spawnLocation() {
		float[] array = gridToWorld(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2);
		return new Point2D.Float(array[0], array[1]);
	}
	
	public int getWidth() { return Constants.GRID_WIDTH; }
	public int getHeight() { return Constants.GRID_HEIGHT; }
}
