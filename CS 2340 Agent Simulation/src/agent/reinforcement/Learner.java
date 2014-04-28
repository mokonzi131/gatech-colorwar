package agent.reinforcement;

import environment.i.StructuredObserver;

public interface Learner {

	double[] score(int a, int move);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);
	
}
