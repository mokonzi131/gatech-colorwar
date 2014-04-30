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
	int rView = Constants.AGENT_RANGE;
	int cView = Constants.AGENT_RANGE;

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
				if (i>0 && i<gameSize-1 && j>0 && j<gameSize-1){
						e[i][j].available = true;
						e[i][j].Color = r.nextInt(3) > 0;
						if (e[i][j].Color == true) 
							totalC++;
					}	
			}
		aStats = new Astats[Lagents.length]; //create agent statistics at each index for each agent 
		for (int i = 0; i < aStats.length; ++i)
			aStats[i] = new Astats();
		for (int i = 0; i < Lagents.length; i++) {
			int x = r.nextInt(gameSize);
			int y = r.nextInt(gameSize);
			while(e[x][y].agent!=-1 || !e[x][y].available ||(x+y)%2==0) {
				x = r.nextInt(gameSize);
				y = r.nextInt(gameSize);
			}
			setAgentLocation(i, x, y);
		}
		fullC = totalC;
		m_moveCounter = 0.0;
	}
	
	public void setAgentLocation(int a, int x, int y) {
		// don't allow movement to an invalid location
		if (x < 0 || x > gameSize - 1 || y < 0 || y > gameSize - 1)
			return;
		
		// don't allow movement to already taken location
//		if (e[x][y].agent != -1)
//			return;
//			System.out.printf("agent %d on square already\n", e[x][y].agent);
			
		if (e[x][y].agent==-1) {// && e[x][y].available) {
			removeAgentLocation(a);
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
//		else{
//			lose(a);
//		}

	}
	
	public void removeAgentLocation(int i){
		Astats a = aStats[i];
		Square s = e[a.x][a.y];
		s.agent = -1;
		s.agentScore = 0;
	}
	
	public void lose(int i){
		Astats a = aStats[i];
		a.newScore=0;
		a.alive=false;
		reward(i);
	}

	private void reward(int i) {
		Astats a = aStats[i];
		int reward = a.newScore - a.score;
		a.score = a.newScore;
		Lagents[i].reward(i, reward);
	}

	public Point getAgentLocation(int i) {
		Astats a = aStats[i];
		return new Point(a.x, a.y);
	}

	public void turn() {
		// tell all of the agents to move...
		Point[] moves = new Point[Lagents.length];
		int[] count= new int[1];
		for (int i = 0; i < Lagents.length; ++i) {
			// get a desired move (for alive agents only)
			AgentMoveSelectionThread runnable = new AgentMoveSelectionThread(count, moves ,i ,aStats[i], Lagents[i]);
			Thread thread = new Thread(runnable);
			thread.start();
		}
		while(count[0]!=Lagents.length){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < aStats.length; i++) {
			removeAgentLocation(i);
			aStats[i].x = moves[i].x;
			aStats[i].y = moves[i].y;
		}
		for (int i = 0; i < aStats.length; i++)
			if (!available(moves[i]) && aStats[i].alive) {
				lose(i);
				moves[i] = null;
			}
		sameSquareCheck(moves);
		for (int i = 0; i < Lagents.length; i++) {
			Astats a = aStats[i];
			if (a.alive) {
				reward(i);
			}
		}
		turns++;
	}
	
	private boolean available(Point p) {
		if (p != null) return available(p.x,p.y);
		else return false;
	}

	private boolean available(int x, int y) {
		if (x < 0 || x >= gameSize || y < 0 || y >= gameSize) return false;
		else return e[x][y].available;
	}

	public void sameSquareCheck(Point[] move){
		for (int j=0; j<move.length; j++) {
			Point m = move[j];
			if(m!=null)
				e[m.x][m.y].amove.add(j);
		}
		
		for (int j=0; j<move.length; j++){
			Point m = move[j];
			if(m!=null){
				Square s= e[m.x][m.y];
				
				int highest=Integer.MIN_VALUE;
				int win=-1;
				for (int i=0; i<s.amove.size();i++){
					int agent=s.amove.get(i);
					Astats a = aStats[agent];
					if (a.score>highest){
						highest=a.score;
						win=agent;
					}
					else if (a.score==highest){
						win=-1;
					}
					//if same score both destroyed 
				}
				if (win!=-1){
					Point m0 = move[win];
					setAgentLocation(win, m0.x, m0.y);
					for (int agent : s.amove){
						if (agent!=win){
							aStats[win].newScore+=aStats[agent].score;
						}
					}
				}
				else{
					s.available=false;
					s.Color=false;
				}
				for (int i=0; i<s.amove.size() ;i++){
					int agent=s.amove.get(i);
					if (agent!=win)
						lose(agent);
				}
				for (int m0 : s.amove)
					move[m0] = null;
				
				s.amove.clear();
			}
		}
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
					state[i][j][2] = e[xval][yval].agentScore/(gameSize*gameSize);
				}
				int distance = Math.abs(x-xval) + Math.abs(y-yval);
				if(distance > cView / 2)
					state[i][j] = null;
			}
		}
		//set corners to null
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
				// don't draw un-available cells
				if (!e[i][j].available)
					continue;
				
				// draw cell according to it's color
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
			if (!aStats[i].alive)
				continue;
			
			int x = gridToPixel(lerp(aStats[i].x0, aStats[i].x, Math.min((float) m_moveCounter * 3, 1f)));
			int y = gridToPixel(lerp(aStats[i].y0, aStats[i].y, Math.min((float) m_moveCounter * 3, 1f)));
			
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

	private float lerp(int x0, int x1, float z) {
		return (1-z)*x0-z*x1;
	}

	public void renderAgentFromWorld(int i, BufferedImage world, BufferedImage target) {
		Graphics2D graphics = target.createGraphics();
		background(graphics, target.getWidth(), target.getHeight());

		int x = gridToPixel(lerp(aStats[i].x0, aStats[i].x, Math.min((float) m_moveCounter * 3, 1f)));
		int y = gridToPixel(lerp(aStats[i].y0, aStats[i].y, Math.min((float) m_moveCounter * 3, 1f)));
		int radius = (Constants.AGENT_RANGE * Constants.CELL_DISTANCE) / 2;

		graphics.drawImage(world, 0, 0, target.getWidth(), target.getHeight(),
				x - radius, y - radius, x + radius, y + radius, null);

		// draw the mask
		graphics.setColor(Color.BLACK);
		float adjustedCellDistance = (int) Math.round((float) target.getWidth() / Constants.AGENT_RANGE);
		float gridDistance = Constants.AGENT_RANGE / 2;
		for (int a = 0; a < Constants.AGENT_RANGE; ++a) {
			for (int b = 0; b < Constants.AGENT_RANGE; ++b) {
				float distance =  Math.abs(gridDistance - a) + Math.abs(gridDistance - b);
				if (distance > gridDistance) {
					graphics.fillRect(a * (int) adjustedCellDistance, b * (int) adjustedCellDistance, (int) adjustedCellDistance, (int) adjustedCellDistance);
				}
			}
		}
	}

	// clear a graphics context to the background color
	private void background(Graphics2D context, int width, int height) {
		context.setColor(Color.BLACK);
		context.fillRect(0, 0, width, height);
	}

	public int gridToPixel(float f) {
		return (int) (f * Constants.CELL_DISTANCE + Constants.CELL_DISTANCE / 2 + Constants.GRID_BUFFER);
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
		return turns == gameSize*gameSize*2/Lagents.length; // totalC == 0 || 
	}

	@Override
	public Dimension dim(int a) {
		return null;
	}
	
	@Override
	public void render(Graphics2D context) {}
}
