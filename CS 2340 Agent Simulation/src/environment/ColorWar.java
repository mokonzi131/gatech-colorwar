package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Random;
import java.util.logging.Logger;

import view.engine.IViewable;
import environment.colorwar.Constants;
import environment.i.IEnvironment;
import agent.i.Agent;

public class ColorWar implements IEnvironment, IViewable {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private int gameSize = Constants.GRID_SIZE;
	Random r = new Random();
	Square s = new Square();
	Square [][] e;
	int n;
	Agent[] Lagents;
	Astats[] aStats;
	
	public ColorWar(Agent[] a){
		Lagents=a;
		e=new Square[gameSize][gameSize]; //creation of environment array
		for (int i = 0; i < e.length; ++i)
			for (int j = 0; j < e[0].length; ++j) {
				e[i][j] = new Square();
				e[i][j].setColor(r.nextInt(100) > 33 ? true : false);
			}
		aStats=new Astats[a.length]; //create agent statistics at each index for each agent 
		for (int i = 0; i < aStats.length; ++i)
			aStats[i] = new Astats();
		for (int i=0; i< a.length; i++) {
			int x= r.nextInt(gameSize);
			int y= r.nextInt(gameSize);
			setAgentLocation(i,x,y);
		}
	}
	
	public void setAgentLocation(int a, int x, int y){
		aStats[a].x=x;
		aStats[a].y=y; 	
		e[x][y].setAgent(a); //sets the agent to that agent number on the block
		if (e[x][y].getColor()==true){
			aStats[a].score=aStats[a].score+1;
			e[x][y].setColor(false); //no color anymore on that block 
		}
	}
	
	public void turn(int a, Point p){
		for (int i=0; i<Lagents.length; i++){
			int agentMove=Lagents[i].move(i);
			//0-left, 1-up, 2-right, 3-down, 4-none
			if (agentMove==0){
				setAgentLocation(i, aStats[i].x-1, aStats[i].y);	
			}
			if (agentMove==1){
				setAgentLocation(i, aStats[i].x, aStats[i].y+1);
			}
			if (agentMove==2){
				setAgentLocation(i, aStats[i].x+1, aStats[i].y);
			}
			if (agentMove==3){
				setAgentLocation(i, aStats[i].x, aStats[i].y-1);
			}
			if (agentMove==4){
				setAgentLocation(i, aStats[i].x, aStats[i].y);
			}
		}
	}
	
	//@Override
	public double[][][] observeStructure(int a) {
		
		// TODO Auto-generated method stub
		//x,y location with information about that specific block (color, available, other agent on it?)
		return null;
	}

	//@Override
	public double[] observe(int a) { //take every array in observed structure and concatinate it end to end 
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public int actionRange(int a) { ///a constant # , largest # of moves that agent will have available 
		// TODO Auto-generated method stub
		return 0;
	}

	public void update(double deltaTime) {
		LOGGER.info("dt=" + deltaTime);
		// TODO keep track in a counter and move agents every 1 second
		// at 1 second, call turn() or whatever method moves the agents
	}

	@Override
	public void render(Graphics2D context) {
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
				context.setColor(e[i][j].getColor() ? yesResource : noResource);
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
		final Color agentColor = new Color(0, 0, 255, 50);
		for (int i = 0; i < aStats.length; ++i) {
			int x = gridToPixel(aStats[i].x);
			int y = gridToPixel(aStats[i].y);
			
			context.setColor(agentColor);
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
	
	private int gridToPixel(int i) {
		return (int) (i * Constants.CELL_DISTANCE + Constants.CELL_DISTANCE / 2 + Constants.GRID_BUFFER);
	}

	@Override
	public Dimension dim() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] score() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
}
