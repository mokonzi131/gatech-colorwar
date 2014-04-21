package agent.reinforcement;

import java.io.Serializable;

import environment.i.StructuredObserver;

public interface Selector extends Serializable {

	int select(double[] score);

	void reward(int a, double r);

	void setObserver(StructuredObserver o);

}
