package agent.reinforcement.neural;

import java.util.ArrayList;
import java.util.List;

public class NeuralNode {
	
	private List<NeuralWeight> weights;
	
	private double input = 0, output = 0, delta = 0;
	
	private double alpha;
	
	public NeuralNode(double a) {
		alpha = a;
		weights = new ArrayList<NeuralWeight>();
	}

	public NeuralNode(List<NeuralWeight> w, double a) {
		weights = w;
		alpha = a;
	}

	public void addWeight(NeuralNode[] layer) {
		for (NeuralNode node : layer)
			addWeight(node);
	}
	
	public void addWeight(NeuralNode node) {
		weights.add(new NeuralWeight(node, alpha));
	}

	public double output() {
		return output;
	}

	public void propagate() {
		double sum = 0;
		for (NeuralWeight w : weights)
			sum += w.output();
		output = g(sum);
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
		return 1/(1+Math.exp(-s));
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
		return new NeuralNode(weights, alpha);
	}

}
