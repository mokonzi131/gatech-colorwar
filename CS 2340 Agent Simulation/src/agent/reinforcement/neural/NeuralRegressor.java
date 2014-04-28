package agent.reinforcement.neural;

import java.io.Serializable;
import java.util.Arrays;

import agent.reinforcement.Regressor;
import agent.reinforcement.neural.function.LinearFunction;
import agent.reinforcement.neural.net.NeuralConfig;
import agent.reinforcement.neural.net.NeuralNet;
import agent.reinforcement.neural.net.NeuralNode;

public class NeuralRegressor implements Regressor, Serializable {
	private static final long serialVersionUID = 1L;
	
	NeuralNet n;
	NeuralNode bias;
	
	double en = 0, u = .99;
	
	boolean verbose = false;
	
	public NeuralRegressor(int[] h) {
		n = new NeuralNet(h);
		n.setOutputFunction(new LinearFunction());
		n.linkLayers();
	}

	public NeuralRegressor(int[] h, double a, double d, double m) {
		n = new NeuralNet(h);
		n.setOutputFunction(new LinearFunction());
		n.linkLayers();
		n.setConfig(new NeuralConfig(a, d, m));
	}

	@Override
	public double[] predict(double[] x) {
		n.setInput(x);
		n.propagate();
		return n.getOutput();
	}
	
	@Override
	public void fit(double[] x, double[] y) {
		double[] y0 = predict(x);
		if (verbose)
			System.out.println("Initial: \n" + n.toString());
		double[] e = new double[y0.length];
		for (int i = 0; i < y0.length; i++)
			e[i] = y[i] - y0[i];
		if (verbose)
			System.out.println("Goal : " + Arrays.toString(y));
		n.addError(e);
		n.backpropagate();
		if (verbose) {
			predict(x);
			System.out.println("Final: \n" + n.toString());
		}
	}
	
	@Override
	public void fit(double[] x, int k, double yk) {
		double[] y0 = predict(x);
		double[] e = new double[y0.length];
		e[k] = yk - y0[k];
//		System.out.println("Output : " + Arrays.toString(y0));
//		System.out.println("Goal : " + k + " " + yk);
		en = u * en + (1 - u) * e[k] * e[k];
		System.out.println("Error : " + (e[k] * e[k]) + " : " + en);
		n.addError(e);
		n.backpropagate();
	}
	
	public void reset() {
		n.reset();
	}

	@Override
	public Regressor copy() {
		//TODO try this.serialize.unserialize
		return this;
	}

	@Override
	public String toString() {
		return "Neural Net\n\n" + n.toString();
	}

}
