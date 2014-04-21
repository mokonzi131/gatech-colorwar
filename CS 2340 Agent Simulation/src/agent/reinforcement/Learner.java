package agent.reinforcement;

import java.io.Serializable;

import environment.i.StructuredObserver;
import environment.i.SymmetryBasedObserver;

public interface Learner extends Serializable {

	double[] score(int a, int move);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);
	
}
