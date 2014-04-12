package agent.reinforcement;

import environment.i.StructuredObserver;
import environment.i.SymmetryBasedObserver;

public interface Learner {

	double[] score(int a);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);
	
}
