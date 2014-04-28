package agent.reinforcement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BasicRegressor implements Regressor {
	
	Map<Integer,Double> evaluation = new HashMap<Integer,Double>();
	
	private int m;
	private double alpha, decay;
	
	public BasicRegressor(int m, double a, double d) {
		this.m = m;
		this.alpha = a;
		this.decay = d;
	}
	
	@Override
	public double[] predict(double[] obs) {
		int hash = Arrays.hashCode(obs);
		double[] score = new double[m];
		for (int i = 0; i < m; i++) {
			int next = hash + i;
			if (!evaluation.containsValue(next))
				evaluation.put(next, 0.0);
			score[i] = evaluation.get(next);
		}
		return score;
	}
	
	@Override
	public void fit(double[] obs, int a, double update) {
		int hash = Arrays.hashCode(obs) + a;
		double score = evaluation.get(hash);
		score = decay * (update + alpha * (score - update));
		evaluation.put(hash, score);
	}
	
	@Override
	public void fit(double[] x, double[] y) {
		for (int a = 0; a < y.length; a++) 
			fit(x,a,y[a]);
	}
	
	@Override
	public Regressor copy() {
		return this;
	}

}
