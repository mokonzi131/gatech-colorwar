package environment;

import java.util.Random;

import colorsgame.Constants;

// a grid of tiles, most tiles are valid, some are invalid,
//  some tiles contain colors, also keep track of which players (agents) are where...
public class Environment {
	public int[][] map =
			new int[Constants.WORLD_WIDTH / Constants.CELL_DISTANCE - 1]
			[Constants.WORLD_HEIGHT / Constants.CELL_DISTANCE - 1];
	
	public Environment() {
		Random random = new Random();
		for (int i = 0; i < map.length; ++i)
			for (int j = 0; j < map[0].length; ++j) {
				boolean assign = random.nextInt(100) > 10 ? true : false;
				if (assign) {
					map[i][j] = 1;
				}
				else
					map[i][j] = 0;
				map[i][j] = random.nextInt(100) > 90 ? 0 : 1;
			}
	}
	
	public int getWidth() { return map.length; }
	public int getHeight() { return map[0].length; }
}
