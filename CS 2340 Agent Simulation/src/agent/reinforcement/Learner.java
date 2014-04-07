package agent.reinforcement;

import environment.i.SymmetryBasedObserver;

public interface Learner {

	double[] score(int a);

	void reward(int a, double r);
	
}
