package agent.reinforcement;

import environment.i.StructuredObserver;

public interface Selector {

	int select(double[] score);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);

}
