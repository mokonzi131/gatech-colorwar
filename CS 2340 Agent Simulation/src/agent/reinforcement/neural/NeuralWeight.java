package agent.reinforcement.neural;

import java.io.Serializable;

public class NeuralWeight implements Serializable {

	private NeuralNode node;
	private Weight w;
	private double alpha, decay, momentum, moment = 0;
	private double input = 0;
	private boolean recurant;

	public NeuralWeight(NeuralNode n, double a, double d, double m, boolean r) {
		node = n;
		alpha = a;
		decay = d;
		momentum = m;
		w = new Weight();
		recurant = r;
	}

	public NeuralWeight(NeuralNode n, double a, double d, double m, boolean r, Weight weight) {
		node = n;
		alpha = a;
		decay = d;
		momentum = m;
		w = weight;
		recurant = r;
	}

	public double output() {
		input = node.getOutput();
		return w.weight * input;
	}

	public synchronized void backpropagate(double delta) {
		double dw = alpha * (input * delta - decay * w.weight); // weight, decay, momentum
		moment += dw;
		w.weight += dw + momentum * moment;
		
		//		System.out.printf("%.4f,%.2f ", w.weight,delta);
		node.addDelta(delta * w.weight);
	}

	public NeuralWeight copy() {
		return new NeuralWeight(node, alpha, decay, momentum, recurant, w);
	}

	@Override
	public String toString() {
		String s = String.format("%1$,.4f", w.weight);
		return s;
	}

	public class Weight implements Serializable {
		public double weight = (Math.random() - .5) / 10;
	}

}
