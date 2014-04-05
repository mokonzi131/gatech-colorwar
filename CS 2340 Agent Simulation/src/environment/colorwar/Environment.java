package environment.colorwar;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import environment.i.IEnvironment;

// a grid of tiles, most tiles are valid, some are invalid,
//  some tiles contain colors, also keep track of which players (agents) are where...
public class Environment implements IEnvironment {
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
	
	private int[] worldToGrid(float x, float y) {
		float fi, fj;
		fi = (x - Constants.GRID_BUFFER - 0.5f * Constants.CELL_DISTANCE) / Constants.CELL_DISTANCE;
		fj = (y - Constants.GRID_BUFFER - 0.5f * Constants.CELL_DISTANCE) / Constants.CELL_DISTANCE;
		int i = Math.round(fi);
		int j = Math.round(fj);
		return new int[]{i, j};
	}
	
	private boolean isValidLocation(Point2D.Float location) {
		int[] pos = worldToGrid(location.x, location.y);
		int i = pos[0];
		int j = pos[1];
		if (i < 0 || j < 0 || i >= Constants.GRID_WIDTH || j >= Constants.GRID_HEIGHT)
			return false;
		return grid[i][j];
	}
	
	public List<Cell> cells() { return m_cells; }
	
	public Point2D.Float spawnLocation() {
		// TODO return a valid location on the grid
		float[] array = gridToWorld(Constants.GRID_WIDTH / 2, Constants.GRID_HEIGHT / 2);
		return new Point2D.Float(array[0], array[1]);
	}
	
	public Point2D.Float getLocation(Point2D.Float currentLocation, AgentController.DIRECTION direction) {
		Point2D.Float location;
		
		switch(direction) {
		case NORTH:
			location = new Point2D.Float(currentLocation.x, currentLocation.y - Constants.CELL_DISTANCE);
			break;
		case EAST:
			location = new Point2D.Float(currentLocation.x + Constants.CELL_DISTANCE, currentLocation.y);
			break;
		case SOUTH:
			location = new Point2D.Float(currentLocation.x, currentLocation.y + Constants.CELL_DISTANCE);
			break;
		case WEST:
			location = new Point2D.Float(currentLocation.x - Constants.CELL_DISTANCE, currentLocation.y);
			break;
		default:
			location = currentLocation;
			break;
		}
		
		if (!isValidLocation(location))
			location = currentLocation;
		
		return location;
	}
	
	public int getWidth() { return Constants.GRID_WIDTH; }
	public int getHeight() { return Constants.GRID_HEIGHT; }

	@Override
	public double[][][] observeStructure(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] observe(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actionRange(int a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void turn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage total() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage human() {
		// TODO Auto-generated method stub
		return null;
	}
}
