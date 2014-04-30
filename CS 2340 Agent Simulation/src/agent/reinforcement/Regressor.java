package agent.reinforcement;

import java.io.Serializable;

import agent.reinforcement.neural.net.NeuralNet;

public interface Regressor extends Serializable {

	double[] predict(double[] obs);

	void fit(double[] x, int a, double val);
	
	void fit(double[] x, double[] y);

	Regressor copy();

	void rootTransfer(Regressor regressor);

}
