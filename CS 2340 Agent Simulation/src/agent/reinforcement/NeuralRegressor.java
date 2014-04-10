package agent.reinforcement;

public class NeuralRegressor implements Regressor {
	
	NeuralNode[][] neuralNet;
	
	public NeuralRegressor(double a, double d, double m, int[] h) {
		int n = h.length;
		neuralNet = new NeuralNode[n][];
		NeuralNode[] layer0 = null;
		for (int i = 0; i < n; i++) {
			int n0 = h[i];
			NeuralNode[] layer = new NeuralNode[n0];
			for (int j = 0; j < n0; j++) {
				NeuralNode node = new NeuralNode(a);
				if (layer0 != null)
					node.addWeight(layer0);
				layer[j] = node;
			}
			layer0 = layer;
			neuralNet[i] = layer;
		}
	}
	
	public NeuralRegressor(NeuralNode[][] net) {
		neuralNet = net;
	}

	@Override
	public double[] predict(double[] obs) {
		propagate(obs);
		NeuralNode[] layer = neuralNet[neuralNet.length-1];
		double[] score = new double[layer.length];
		for (int i = 0; i < layer.length; i++)
			score[i] = layer[i].output();
		return score;
	}

	private void propagate(double[] obs) {
		for (int i = 0; i < neuralNet.length; i++)
			for (NeuralNode j : neuralNet[i])
				j.propagate();
	}

	@Override
	public void fit(double[] obs, int a, double score) {
		propagate(obs);
		backpropagate(a, score);
	}

	private void backpropagate(int a, double score) {
		int n = neuralNet.length;
		NeuralNode node = neuralNet[n-1][a];
		node.setErr(score);
		for (int i = neuralNet.length - 1; i >= 0; i--)
			for (NeuralNode j : neuralNet[i])
				j.backpropogate();
	}
	
	public void reset() {
		for (NeuralNode[] i : neuralNet)
			for (NeuralNode j : i)
				j.reset();
	}

	@Override
	public Regressor copy() {
		NeuralNode[][] newNet = new NeuralNode[neuralNet.length][];
		for (int i = 0; i < neuralNet.length; i++) {
			newNet[i] = new NeuralNode[neuralNet[i].length];
			for (int j = 0; j < neuralNet[i].length; j++) 
				newNet[i][j] = neuralNet[i][j].copy();
		}
		return new NeuralRegressor(newNet);
	}
	
}
