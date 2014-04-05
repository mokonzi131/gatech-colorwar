package agent.i;

/**
 * The interface for retrieving graphical information about the users location
 * and showing it to the user
 * @author John
 *
 */
public interface GraphicalObserver extends Observer {
	
	/**
	 * Shows the user the state of the game and requests a move.  
	 * @param a The agent index.  
	 * @return The users move.  
	 */
	public int queueUserMove(int a);
	
	/**
	 * Shows the player the reward.  
	 * @param r The reward
	 */
	public void displayReward(double r);
	
}
