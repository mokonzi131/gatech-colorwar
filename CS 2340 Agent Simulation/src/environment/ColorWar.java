package environment;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import view.engine.IViewable;
import environment.i.IEnvironment;
import agent.i.Agent;

public class ColorWar implements IEnvironment, IViewable {

	private int gameSize=10; 
	Random r= new Random();
	Square s= new Square();
	Square [][] e;
	int n;
	Agent[] Lagents;
	Astats[] aStats;
	
	public ColorWar(Agent[] a){
		Lagents=a;
		e=new Square[gameSize][gameSize]; //creation of environment array
		for (int i = 0; i < e.length; ++i)
			for (int j = 0; j < e[0].length; ++j)
				e[i][j] = new Square();
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
		if (e[x][y].getColor()==1){
			aStats[a].score=aStats[a].score+1;
			e[x][y].setColor(0); //no color anymore on that block 
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
		// TODO keep track in a counter and move agents every 1 second
		// at 1 second, call turn() or whatever method moves the agents
	}

	@Override
	public void render(Graphics2D context) {
		// TODO michael will do this part
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
