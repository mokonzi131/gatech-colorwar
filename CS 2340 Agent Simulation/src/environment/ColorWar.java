package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

import view.engine.IViewable;
import environment.Constants;
import environment.i.IEnvironment;
import agent.i.Agent;

public class ColorWar implements IEnvironment, IViewable {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private int gameSize = Constants.GRID_SIZE;
	Random r = new Random();
	Square s = new Square();
	Square[][] e;
	int n;
	Agent[] Lagents;
	Astats[] aStats;
	private double m_moveCounter;
	int fullC;
	int totalC; //total colored squares
	int turns;
	int rView = 3;
	int cView = 3;

	public ColorWar(Agent[] a) {
		Lagents = a;
		reset();
	}
	
	@Override
	public void reset() {
		turns = 0;
		for (Agent agent : Lagents)
			agent.reset();
		e = new Square[gameSize][gameSize]; //creation of environment array
		totalC = 0;
		for (int i = 0; i < e.length; ++i)
			for (int j = 0; j < e[0].length; ++j) {
				e[i][j] = new Square();
				e[i][j].Color = r.nextInt(3) > 0;
				if (e[i][j].Color == true) 
					totalC++;
			}
		aStats = new Astats[Lagents.length]; //create agent statistics at each index for each agent 
		for (int i = 0; i < aStats.length; ++i)
			aStats[i] = new Astats();
		for (int i = 0; i < Lagents.length; i++) {
			int x = r.nextInt(gameSize);
			int y = r.nextInt(gameSize);
			setAgentLocation(i, x, y);

			//make sure there is a check for initially setting agent
		}

		fullC = totalC;
		m_moveCounter = 0.0;
	}
	
	public void setAgentLocation(int a, int x, int y) {
		// don't allow movement to an invalid location
		if (x < 0 || x > gameSize - 1 || y < 0 || y > gameSize - 1)
			return;
		if (e[x][y].agent == -1) {
			e[aStats[a].x][aStats[a].y].agent = -1;
			e[aStats[a].x][aStats[a].y].agentScore = 0;
			aStats[a].x = x;
			aStats[a].y = y;
			e[x][y].agent = a;
			if (e[x][y].Color == true) {
				aStats[a].newScore++;
				e[x][y].Color = false; //no color anymore on that block 
				totalC--;
			}
			e[x][y].agentScore = aStats[a].score; //sets the agent to that agent number on the block
		}

	}

	public Point getAgentLocation(int a) {
		return new Point(aStats[a].x, aStats[a].y);
	}

	public void turn() {
		if (isEnd()) reset();
		for (int i = 0; i < Lagents.length; i++) {
			int agentMove = Lagents[i].move(i);
			//0-left, 1-up, 2-right, 3-down, 4-none
			if (agentMove == 0) {
				setAgentLocation(i, aStats[i].x - 1, aStats[i].y);
			}
			if (agentMove == 1) {
				setAgentLocation(i, aStats[i].x, aStats[i].y - 1);
			}
			if (agentMove == 2) {
				setAgentLocation(i, aStats[i].x + 1, aStats[i].y);
			}
			if (agentMove == 3) {
				setAgentLocation(i, aStats[i].x, aStats[i].y + 1);
			}
			if (agentMove == 4) {
				setAgentLocation(i, aStats[i].x, aStats[i].y);
			}
		}
		for (int i = 0; i < Lagents.length; i++) {
			Astats a = aStats[i];
			int r = a.newScore - a.score;
			a.score = a.newScore;
			Lagents[i].reward(i, r);
		}
		turns++;
	}

	//@Override

	public double[][][] observeStructure(int a) {
		double[][][] state = new double[rView][cView][3]; //[availability, color, agentScore or 0]
		int x = aStats[a].x;
		int y = aStats[a].y;
		int leftx = x - cView / 2;
		int lefty = y - rView / 2; //correct round?
		for (int i = 0; i < rView; i++) {
			for (int j = 0; j < cView; j++) {
				int xval = leftx + i;
				int yval = lefty + j;
				if (xval >= gameSize || yval >= gameSize || xval < 0 || yval < 0) {
					state[i][j][0] = 0; //0 means its unavailable 
					state[i][j][1] = 0;
					state[i][j][2] = 0;
				}
				else {
					state[i][j][0] = e[xval][yval].available ? 1 : 0;
					state[i][j][1] = e[xval][yval].Color ? 1 : 0;
					state[i][j][2] = e[xval][yval].agentScore;
				}
			}
		}
		//set corners to null
		//what is viewed based on manhattan distance
		state[0][0] = null;
		state[0][cView - 1] = null;
		state[rView - 1][0] = null;
		state[rView - 1][cView - 1] = null;
		return state;
	}

	//@Override
	public double[] observe(int a) { //take every array in observed structure and concatinate it end to end 
		int count = 0;
		double[][][] s = observeStructure(a);

		for (int i = 0; i < rView; i++) {
			for (int j = 0; j < cView; j++) {
				if (s[i][j] != null) {
					count += s[i][j].length;
				}

			}
		}
		double[] state = new double[count];
		int c = 0;
		for (int i = 0; i < rView; i++) {
			for (int j = 0; j < cView; j++) {
				if (s[i][j] != null) {
					for (int k = 0; k < s[i][j].length; k++) {
						state[c++] = s[i][j][k];
					}
				}

			}
		}
		return state;
	}

	//@Override
	public int actionRange(int a) { ///a constant # , largest # of moves that agent will have available
		return 4;
	}

	public void update(double deltaTime) {
		m_moveCounter += deltaTime;
		if (m_moveCounter >= 1.0) {
			m_moveCounter = 0.0;
			turn();
		}
	}
	
	@Override
	public void render(BufferedImage target) {
		// get the graphics object
		Graphics2D context = target.createGraphics();
		background(context, target.getWidth(), target.getHeight());
		
		// environment background
		final Color background = new Color(255, 0, 255, 50);
		context.setColor(background);
		context.fillRect(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
		
		// draw the grid
		context.setColor(Color.CYAN);
		final int TILE_BUFFER = (int) (Constants.CELL_DISTANCE * (0.1));
		for (int i = 0; i < e.length; ++i)
			for (int j = 0; j < e[0].length; ++j) {
				final Color noResource = new Color(155, 155, 155, 200);
				final Color yesResource = new Color(0, 255, 0, 200);
				context.setColor(e[i][j].Color ? yesResource : noResource);
				int x = gridToPixel(i);
				int y = gridToPixel(j);
				context.fillRect(
						x - (Constants.CELL_DISTANCE / 2) + TILE_BUFFER,
						y - (Constants.CELL_DISTANCE / 2) + TILE_BUFFER,
						Constants.CELL_DISTANCE - (2 * TILE_BUFFER),
						Constants.CELL_DISTANCE - (2 * TILE_BUFFER));
			}
		
		// draw the agents
		final int AGENT_RADIUS = Constants.CELL_DISTANCE / 2 - 4;
		final Stroke stroke = new BasicStroke(2.0f);
		final Color ringColor = new Color(255, 255, 0, 255);
		for (int i = 0; i < aStats.length; ++i) {
			int x = gridToPixel(aStats[i].x);
			int y = gridToPixel(aStats[i].y);
			
			context.setColor(aStats[i].color(fullC));
			context.fillOval(
					x - (AGENT_RADIUS) + 2,
					y - (AGENT_RADIUS) + 2,
					AGENT_RADIUS * 2 - 2,
					AGENT_RADIUS * 2 - 2);
			
			Stroke backup = context.getStroke();
			context.setStroke(stroke);
			context.setColor(ringColor);
			context.drawOval(
					x - (AGENT_RADIUS) + 2,
					y - (AGENT_RADIUS) + 2,
					AGENT_RADIUS * 2 - 2,
					AGENT_RADIUS * 2 - 2);
			context.setStroke(backup);
		}
	}

	public void renderAgentFromWorld(int i, BufferedImage world, BufferedImage target) {
		Graphics2D graphics = target.createGraphics();
		background(graphics, target.getWidth(), target.getHeight());

		int x = gridToPixel(aStats[i].x);
		int y = gridToPixel(aStats[i].y);
		int radius = (Constants.AGENT_RANGE * Constants.CELL_DISTANCE) / 2;

		graphics.drawImage(world, 0, 0, target.getWidth(), target.getHeight(),
				x - radius, y - radius, x + radius, y + radius, null);

		// draw the mask
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Constants.CELL_DISTANCE, Constants.CELL_DISTANCE);
		graphics.fillRect(0 + 2 * Constants.CELL_DISTANCE, 0, Constants.CELL_DISTANCE, Constants.CELL_DISTANCE);
		graphics.fillRect(0, 0 + 2 * Constants.CELL_DISTANCE, Constants.CELL_DISTANCE, Constants.CELL_DISTANCE);
		graphics.fillRect(0 + 2 * Constants.CELL_DISTANCE, 0 + 2 * Constants.CELL_DISTANCE,
				Constants.CELL_DISTANCE, Constants.CELL_DISTANCE);
	}

	// clear a graphics context to the background color
	private void background(Graphics2D context, int width, int height) {
		context.setColor(Color.BLACK);
		context.fillRect(0, 0, width, height);
	}

	public int gridToPixel(int i) {
		return (int) (i * Constants.CELL_DISTANCE + Constants.CELL_DISTANCE / 2 + Constants.GRID_BUFFER);
	}

	@Override
	public Dimension dim() {
		return null;
	}

	@Override
	public double[] score() {
		double[] s = new double[aStats.length];
		for (int i = 0; i < aStats.length; i++) {
			s[i] = aStats[i].score;
		}
		return s;
	}

	@Override
	public boolean isEnd() {
		return totalC == 0 || turns == gameSize*gameSize*2/Lagents.length;
	}

	@Override
	public Dimension dim(int a) {
		return null;
	}
	
	@Override
	public void render(Graphics2D context) {}
}
