package iagent;

public interface GraphicalObserver {
	
	/**
	 * @return The user's action
	 */
	public int observe();
	
	public void displayReward(double r);
	
}
