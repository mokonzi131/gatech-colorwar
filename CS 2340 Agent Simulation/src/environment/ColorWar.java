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
import environment.Constants;
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
	private double m_moveCounter;
	int totalC=0; //total colored squares
	int rView=3;
	int cView=3;
	
	public ColorWar(Agent[] a){
		Lagents=a;
		e=new Square[gameSize][gameSize]; //creation of environment array
		for (int i = 0; i < e.length; ++i)
			for (int j = 0; j < e[0].length; ++j) {
				e[i][j] = new Square();
				e[i][j].Color=r.nextInt(3) > 0;
				if(e[i][j].Color==true){
					totalC+=1;
				}
			}
		aStats=new Astats[a.length]; //create agent statistics at each index for each agent 
		for (int i = 0; i < aStats.length; ++i)
			aStats[i] = new Astats();
		for (int i=0; i< a.length; i++) {
			int x= r.nextInt(gameSize);
			int y= r.nextInt(gameSize);
			setAgentLocation(i,x,y);
		}
		
		m_moveCounter = 0.0;
	}
	
	public void setAgentLocation(int a, int x, int y){
		// don't allow movement to an invalid location
		if (x < 0 || x > gameSize - 1 || y < 0 || y > gameSize - 1)
			return;
		e[aStats[a].x][aStats[a].y].agent=-1; 
		aStats[a].x=x;
		aStats[a].y=y; 	
		if (e[x][y].agent==-1){		
			e[x][y].agent=a;
			if (e[x][y].Color==true) {
				aStats[a].score=aStats[a].score+1;
				e[x][y].agentScore=aStats[a].score; //sets the agent to that agent number on the block
				e[x][y].Color=false; //no color anymore on that block 
			}
		}

		
	}
	
	public Point getAgentLocation(int a) {
		return new Point(aStats[a].x, aStats[a].y);
	}
	
	public void turn() {
		for (int i=0; i<Lagents.length; i++){
			int agentMove=Lagents[i].move(i);
			//0-left, 1-up, 2-right, 3-down, 4-none
			if (agentMove==0){
				setAgentLocation(i, aStats[i].x-1, aStats[i].y);	
			}
			if (agentMove==1){
				setAgentLocation(i, aStats[i].x, aStats[i].y-1);
			}
			if (agentMove==2){
				setAgentLocation(i, aStats[i].x+1, aStats[i].y);
			}
			if (agentMove==3){
				setAgentLocation(i, aStats[i].x, aStats[i].y+1);
			}
			if (agentMove==4){
				setAgentLocation(i, aStats[i].x, aStats[i].y);
			}
		}
	}
	
	//@Override
	
	public double[][][] observeStructure(int a){
		double[][][] state = new double[rView][cView][4]; //[availability, color, agentScore or 0]
		int x= aStats[a].x;
		int y= aStats[a].y;
		int leftx=x+cView/2;
		int lefty=y+rView/2; //correct round?
		for (int i=0; i<rView; i++){
			for (int j=0; j<cView; j++){
				state[i][j][0]=e[leftx+i][lefty+j].available ? 1:0;
				state[i][j][1]=e[leftx+i][lefty+j].Color ? 1 : 0;
				state[i][j][2]=e[leftx+i][lefty+j].agent;
				state[i][j][3]=e[leftx+i][lefty+j].agentScore;
				
			}
		}
		//set corners to null
		state[0][0]=null;
		state[0][cView]=null;
		state[rView][0]=null;
		state[cView][rView]=null;
		return state;
	}
	


	//@Override
	public double[] observe(int a) { //take every array in observed structure and concatinate it end to end 
		double[] state = new double[rView*cView];
		double[][][] s = observeStructure(a);
		int count=0;
		for (int i = 0; i < rView; i++){
			for (int j = 0; j < cView; j++){
				if (s[i][j]!=null){
					count++;
					state[count] = s[i][j][0];
					state[count] = s[i][j][1];
					state[count] = s[i][j][2];
					state[count] = s[i][j][3];
				}
				
			}	
		}	
		return state;
	}

	//@Override
	public int actionRange(int a) { ///a constant # , largest # of moves that agent will have available 
		// TODO Auto-generated method stub
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
	
	public int gridToPixel(int i) {
		return (int) (i * Constants.CELL_DISTANCE + Constants.CELL_DISTANCE / 2 + Constants.GRID_BUFFER);
	}

	@Override
	public Dimension dim() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] score() {
		double [] s = new double[aStats.length];
		for (int i=0; i<aStats.length; i++){
			s[i]=aStats[i].score;
		}
		// TODO Auto-generated method stub
		return s;
	}

	@Override
	public boolean isEnd() {
		double [] score= score();
		double total=0;
		for (int i=0; i<score.length; i++){
			total+=score[i];
		}
		if (total==totalC){
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	
	/*public double[][][] observeStructure(int a) {
	int x= aStats[a].x;
	int y= aStats[a].y;
	
	Point p1=new Point();
	
	
	double[][][] state = new double[rView][cView][3]; //[available, color, agent]
	state[0][0]=null;
	state[0][2]=null;
	state[2][0]=null;
	state[2][2]=null;
	

	double[] s= new double[3];
	//left
	s[0]=e[x-1][y].getAvailability();
	boolean color= e[x-1][y].getColor();
	int c=0;
	if (color==true){
		 c=1;
	}
	s[1]=c;
	s[2]=e[x-1][y].getAgent();
	state[1][0]=s;
	
	//right
	s[0]=e[x+1][y].getAvailability();
	color= e[x+1][y].getColor();
	c=0;
	if (color==true){
		 c=1;
	}
	s[1]=c;
	s[2]=e[x+1][y].getAgent();
	state[2][1]=s;
	
	//up
	s[0]=e[x][y+1].getAvailability();
	color= e[x][y+1].getColor();
	c=0;
	if (color==true){
		 c=1;
	}
	s[1]=c;
	s[2]=e[x][y+1].getAgent();
	state[0][1]=s;

	//down
	s[0]=e[x][y-1].getAvailability();
	color= e[x][y-1].getColor();
	c=0;
	if (color==true){
		 c=1;
	}
	s[1]=c;
	s[2]=e[x][y-1].getAgent();
	state[0][1]=s;
	
	return state;
}*/
}
