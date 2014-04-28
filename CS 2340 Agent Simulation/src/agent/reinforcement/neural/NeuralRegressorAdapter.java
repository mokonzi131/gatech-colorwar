package agent.reinforcement.neural;

import java.io.Serializable;

import util.linalg.DenseVector;
import util.linalg.Vector;
import func.nn.activation.LogisticSigmoid;
import func.nn.backprop.BackPropagationNetwork;
import func.nn.backprop.BackPropagationNetworkFactory;
import agent.reinforcement.Regressor;

public class NeuralRegressorAdapter implements Regressor, Serializable {
	private static final long serialVersionUID = 1L;
	
	// TODO figure out how to serialize these objects...
	BackPropagationNetworkFactory bnf = new BackPropagationNetworkFactory();
	BackPropagationNetwork bn;

	public NeuralRegressorAdapter(int[] nodeCounts) {
		bn = bnf.createRegressionNetwork(nodeCounts, new LogisticSigmoid());
	}

	@Override
	public double[] predict(double[] obs) {
		bn.setInputValues(new DenseVector(obs));
		bn.run();
		Vector v = bn.getOutputValues();
		double[] r = new double[v.size()];
		for (int i = 0; i < v.size(); i++) r[i] = v.get(i);
		return r;
	}

	@Override
	public void fit(double[] obs, int a, double score) {
		double[] r = predict(obs);
		double[] e = new double[r.length];
		e[a] = r[a] - score;
		bn.setOutputErrors(e);
		bn.backpropagate();
	}

	public void reset() {
	}

	@Override
	public Regressor copy() {
		return this;
	}

	@Override
	public String toString() {
		return bn.toString();
	}

}
