package environment.i;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
//import IAgent;
/**
 * IEnvironment keeps track of what is happening on the board during the game
 * It returns information to the agent about what is around it
 * It updates the agent location and reward based on the move it decides to make 
 * @author Lior
 *
 */
public interface IEnvironment extends StructuredObserver {
	
	
	
	//IEnvironment(IAgent[] agentArray); create a constructor taking in an array of agents and placing them on board (assigning x,y position), keep track of agent interface-- returns move they would like to make
		
	//public double[][][] agentView(int a); //what they cannot see goes to null ....what they can see and information about each point (color, wall, agents, stats)
	
	//public double[] information(int a); //take output for agent view and concatinate to form new array
	
	public void update(); //trigger environment to increment it, control how quickly it asks for a move 
	
	public Dimension dim();
	
	public double[] score();
	
	//public double[] agentLocation(int a); for myself reinforcement agents dont need to know this 
	
	public boolean isEnd();
	
	public void reset();
	
	public void render(Graphics2D g);

	//public int reward(int a); calculate each agents "score" 

}


//0-left, 1-up, 2-right, 3-down -->need method to interpret what it means--ask what agents want to do, construct next state of game and appropriately reward each agent depending on new position (1 reward for each color pickup, -all pixels if eaten, +opponent pixels if eat them)