package agent.reinforcement.neural;

import java.util.Arrays;

import agent.reinforcement.Regressor;

public class NeuralRegressor implements Regressor {

	NeuralNode[][] neuralNet;
	NeuralNode bias;

	public NeuralRegressor(double a, double d, double m, int[] h) {
		int n = h.length;
		neuralNet = new NeuralNode[n][];
		NeuralNode[] layer0 = null;
		bias = new NeuralNode(a,d,m);
		bias.setOutput(1);
		
		for (int i = 0; i < n; i++) {
			int n0 = h[i];
			NeuralNode[] layer = new NeuralNode[n0];
			for (int j = 0; j < n0; j++) {
				NeuralNode node = new NeuralNode(a,d,m);
				node.addWeight(bias, false);
				if (layer0 != null)
					node.addWeight(layer0, false);
				layer[j] = node;
			}
			layer0 = layer;
			neuralNet[i] = layer;
		}
	}

	public NeuralRegressor(NeuralNode[][] net, NeuralNode b) {
		neuralNet = net;
		bias = b;
	}

	@Override
	public double[] predict(double[] obs) {
		propagate(obs);
//		System.out.println(Arrays.toString(obs));
		NeuralNode[] layer = neuralNet[neuralNet.length - 1];
		double[] score = new double[layer.length];
		for (int i = 0; i < layer.length; i++)
			score[i] = layer[i].getOutput();
//		System.out.println(Arrays.toString(score));
		return score;
	}

	private void propagate(double[] obs) {
		for (int j = 0; j < neuralNet[0].length; j++)
			neuralNet[0][j].setOutput(obs[j]);
		for (int i = 1; i < neuralNet.length; i++)
			for (NeuralNode j : neuralNet[i])
				j.propagate();
	}

	@Override
	public void fit(double[] obs, int a, double score) {
		propagate(obs);
		backpropagate(a, score);
//		System.out.println(a + " " + score);
//		predict(obs);
//		System.out.println();
//		System.out.println(this.toString());
	}

	private void backpropagate(int a, double score) {
		int n = neuralNet.length;
		NeuralNode node = neuralNet[n - 1][a];
		node.setErr(score);
		backpropagate();
	}

	private void backpropagate() {
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
		//TODO try this.serialize.unserialize
		NeuralNode[][] newNet = new NeuralNode[neuralNet.length][];
		for (int i = 0; i < neuralNet.length; i++) {
			newNet[i] = new NeuralNode[neuralNet[i].length];
			for (int j = 0; j < neuralNet[i].length; j++)
				newNet[i][j] = neuralNet[i][j].copy();
		}
		return new NeuralRegressor(newNet, bias.copy());
	}

	@Override
	public String toString() {
		String s = "Neural Net\n\n";
		for (NeuralNode[] nn0 : neuralNet) {
			for (NeuralNode nn : nn0)
				s += nn.toString() + "\n";
			s += "\n";
		}
		return s;
	}

}
