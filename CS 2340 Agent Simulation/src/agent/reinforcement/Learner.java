package agent.reinforcement;

import environment.i.StructuredObserver;

public interface Learner {

	/**
	 * 
	 * @param a The agent index
	 * @param move The last move chosen
	 * @return The estimated score of each move available
	 */
	double[] score(int a, int move);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);
	
	/**
	 * 
	 * @param m The last move made
	 */
	void reset(int m);
	
}
