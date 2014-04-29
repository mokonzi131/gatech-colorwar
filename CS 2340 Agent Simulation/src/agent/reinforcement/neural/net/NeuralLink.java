package agent.reinforcement.neural.net;

import java.io.Serializable;

public class NeuralLink implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private NeuralNode n;
	private Weight w = new Weight();
	
	private double m = 0;
	
	private NeuralConfig c = new NeuralConfig();

	public NeuralLink(NeuralNode node) {
		n = node;
	}

	public double output() {
		return w.w * n.getOutput();
	}

	public synchronized void backpropagate(double delta) {
		double dw = c.a * (n.getOutput() * delta - c.d * w.w); // weight, decay, momentum
		m += dw;
		w.w += dw + c.m * m;
		n.addError(delta * w.w);
	}
	
	public void setConfig(NeuralConfig c0) {
		c = c0;
	}

	@Override
	public String toString() {
//		String s = String.format("%1$,.4f", w.w);
		String s = "" + w.w;
		return s;
	}

	public class Weight implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public double w = (Math.random() - .5) / 2;
	}

	public void rootTransfer(NeuralLink l0) {
		w = l0.getWeight();
	}

	private Weight getWeight() {
		return w;
	}

}
