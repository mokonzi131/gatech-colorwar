package agent.reinforcement.neural;

public class NeuralWeight {
	
	private NeuralNode node;
	private Weight w;
	private double alpha;
	private double input = 0;
	
	public NeuralWeight(NeuralNode n, double a) {
		node = n;
		alpha = a;
		w = new Weight();
	}
	
	public NeuralWeight(NeuralNode n, double a, Weight weight) {
		node = n;
		alpha = a;
		w = weight;
	}

	public double output() {
		input = node.output();
		return w.weight * input;
	}

	public synchronized void backpropagate(double delta) {
		w.weight += input * alpha * delta; // weight, decay, momentum
		node.addDelta(delta * w.weight);
	}

	public NeuralWeight copy() {
		return new NeuralWeight(node,alpha,w);
	}
	
	public class Weight {
		public double weight = (Math.random()-.5)/1000;
	}

}
