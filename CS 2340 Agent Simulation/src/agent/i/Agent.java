package agent.i;

import environment.i.StructuredObserver;

/**
 * Interface to the agents or the players
 * @author John
 */
public interface Agent {
	
	/**
	 * This method returns the agent's desired move.  
	 * The agents will retrieve information about the state themselves.  
	 * @param a The agent index
	 * @return The index of the move
	 */
	public int move(int a);
	
	/**
	 * Rewards the agent.  
	 * Corresponds to the difference in score.  
	 * @param a The agent index
	 * @param r The reward
	 */
	public void reward(int a, double r);
	
	public void setObserver(StructuredObserver o);
	
	public void reset();
	
}
