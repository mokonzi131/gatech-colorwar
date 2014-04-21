package agent.reinforcement.neural;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class NeuralNode implements Serializable {

	private List<NeuralWeight> weights;

	private double input = 0, output = 0, delta = 0;

	private double alpha, decay, momentum;

	public NeuralNode(double a, double d, double m) {
		alpha = a;
		decay = d;
		momentum = m;
		weights = new ArrayList<NeuralWeight>();
	}

	public NeuralNode(List<NeuralWeight> w, double a, double d, double m) {
		weights = w;
		alpha = a;
		decay = d;
		momentum = m;
	}

	public void addWeight(NeuralNode[] layer, boolean recurant) {
		for (NeuralNode node : layer)
			addWeight(node, recurant);
	}

	public void addWeight(NeuralNode node, boolean recurant) {
		weights.add(new NeuralWeight(node, alpha, decay, momentum, recurant));
	}

	public double getOutput() {
		return output;
	}
	
	public void setOutput(double d) {
		output = d;
	}

	public void propagate() {
		alpha = 1;
		input = 0;
		for (NeuralWeight w : weights)
			input += w.output();
		output = g(input);
		delta = 0;
	}

	public void addDelta(double d) {
		delta += d;
	}

	public void setErr(double expected) {
		delta += (expected - output) * dg(input);
	}

	public void backpropogate() {
		double d = delta * dg(input);
		for (NeuralWeight w : weights)
			w.backpropagate(d);
		delta = 0;
	}

	private double g(double s) {
		return 1 / (1 + Math.exp(-s));
	}

	private double dg(double s) {
		double g = g(s);
		return g * (1 - g);
	}

	public void reset() {
		input = 0;
		output = 0;
		delta = 0;
	}

	public NeuralNode copy() {
		List<NeuralWeight> w = new ArrayList<NeuralWeight>(weights.size());
		for (NeuralWeight w0 : weights)
			w.add(w0.copy());
		return new NeuralNode(w, alpha, decay, momentum);
	}

	@Override
	public String toString() {
		String s = "";
		for (NeuralWeight w : weights)
			s += w.toString() + ", ";
		return s;
	}

}
