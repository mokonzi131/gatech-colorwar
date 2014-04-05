package environment.i;

/**
 * The interface for returning structured information about the agent's perspective. 
 * @author John
 */
public interface StructuredObserver extends Observer {
	
	/**
	 * Returns structure about the game
	 * @param a The agent index
	 * @return The observation.  The coordinates are x, y, and index, in that order.  
	 */
	public double[][][] observeStructure(int a);
	
}
