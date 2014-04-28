package agent.reinforcement.neural.net;

import java.io.Serializable;

public class NeuralConfig implements Serializable {

	public double a = 1;
	public double d = 0;
	public double m = 0;

	public NeuralConfig() {}
	
	public NeuralConfig(double a0, double d0, double m0) { a = a0; d = d0; m = m0; }

}
