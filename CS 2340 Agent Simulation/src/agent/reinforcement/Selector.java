package agent.reinforcement;

public interface Selector {

	int select(double[] score);

	void reward(int a, double r);

}
