package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
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
				e[i][j].available = true;
//				if (i>0 && i<gameSize-1 && j>0 && j<gameSize-1){
					e[i][j].Color = r.nextInt(3) > 0;
					if (e[i][j].Color == true) 
						totalC++;
//				}	
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
			aStats[i].oldLocation = new Point2D.Float(x, y);
			aStats[i].newLocation = new Point2D.Float(x, y);
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
			aStats[a].newLocation = new Point2D.Float(x, y);
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
	
	public void removeAgentLocation(int a){
		e[aStats[a].x][aStats[a].y].agent = -1;
		e[aStats[a].x][aStats[a].y].agentScore = 0;
	}
	
	public void lose(int a){
		aStats[a].newScore=0;
		aStats[a].alive=false;	
	}

	public Point getAgentLocation(int a) {
		return new Point(aStats[a].x, aStats[a].y);
	}

	public void turn() {
		// tell all of the agents to move...
		Point[] moves = new Point[Lagents.length];
		for (int i = 0; i < Lagents.length; ++i) {
			// rendering hack
			aStats[i].oldLocation = aStats[i].newLocation;
			
			// get a desired move (for alive agents only)
			int agentMove = -1;
			if (aStats[i].alive)
				agentMove = Lagents[i].move(i);
			
			// get the point for that desired move
			// -1 = null, 0 = left, 1 = up, 2 = right, 3 = down, 4 = none
			Point point;
			if (agentMove == -1)
				point = null;
			else if (agentMove == 0)
				point = new Point(aStats[i].x - 1, aStats[i].y);
			else if (agentMove == 1)
				point = new Point(aStats[i].x, aStats[i].y - 1);
			else if (agentMove == 2)
				point = new Point(aStats[i].x + 1, aStats[i].y);
			else if (agentMove == 3)
				point = new Point(aStats[i].x, aStats[i].y + 1);
			else
				point = new Point(aStats[i].x, aStats[i].y);
			
			// attempt to move to that desired
			moves[i] = point;
			if (point != null)
				setAgentLocation(i, point.x, point.y); //take out when implement same square check
		}
//		sameSquareCheck(moves);
		for (int i = 0; i < Lagents.length; i++) {
			Astats stats = aStats[i];
			int reward = stats.newScore - stats.score;
			stats.score = stats.newScore;
			Lagents[i].reward(i, reward);
		}
		turns++;
	}
	
	public void sameSquareCheck(Point[] move){
		for (int j=0; j<move.length; j++){
			if(move[j]!=null){
//				if (move[j].x < 0 ||move[j].x > gameSize - 1 || move[j].y < 0 || move[j].y > gameSize - 1)
//					return;
				Square s= e[move[j].x][move[j].y];
				s.amove.add(j);
			}

		}
		
		for (int j=0; j<move.length; j++){
			if(move[j]!=null){
				Square s= e[move[j].x][move[j].y];
				if (s.amove.size()==1){
					setAgentLocation(j, move[j].x, move[j].y);
				}
				
				else{
					int highest=Integer.MIN_VALUE;
					int win=0;
					for (int i=0; i<s.amove.size();i++){
						int agent=s.amove.get(i);
						if (aStats[agent].score>highest){
							highest=aStats[agent].score;
							win=agent;
						}
						else if (aStats[agent].score==highest){
							win=-1;
						}
						//if same score both destroyed 
					}
					if (win!=-1){
						setAgentLocation(win, move[win].x, move[win].y);
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
						if (agent!=win){
							lose(agent);
						}
					}
	
				}
			}
		}
		
		//reset to 0 
		for (int j=0; j<move.length; j++){
			if(move[j]!=null){
				e[move[j].x][move[j].y].amove.clear();
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
	
	// Precise method which guarantees v = v1 when t = 1.
	static float lerp(float v0, float v1, float t) {
	  return (1-t)*v0 + t*v1;
	}
	
	@Override
	public void render(BufferedImage target) {
		// get the graphics object
		Graphics2D context = target.createGraphics();
		background(context, target.getWidth(), target.getHeight());
		
		// environment background
		int alpha = (int) lerp(40.f, 70.f, (float) m_moveCounter * 3);
		int beta = (int) lerp(70.f, 40.f, (float)m_moveCounter);
		int value = Math.min(alpha, beta);
		final Color background = new Color(255, 0, 255, value);
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
			
			int x = gridToPixel(lerp(aStats[i].oldLocation.x, aStats[i].newLocation.x, Math.min((float) m_moveCounter * 3, 1f)));
			int y = gridToPixel(lerp(aStats[i].oldLocation.y, aStats[i].newLocation.y, Math.min((float) m_moveCounter * 3, 1f)));
			
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

		int x = gridToPixel(lerp(aStats[i].oldLocation.x, aStats[i].newLocation.x, Math.min((float) m_moveCounter * 3, 1f)));
		int y = gridToPixel(lerp(aStats[i].oldLocation.y, aStats[i].newLocation.y, Math.min((float) m_moveCounter * 3, 1f)));
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

	public int gridToPixel(float i) {
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
