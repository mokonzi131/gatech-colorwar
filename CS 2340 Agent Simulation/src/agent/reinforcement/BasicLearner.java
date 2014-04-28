package agent.reinforcement;

import java.io.Serializable;

import environment.i.Observer;
import environment.i.StructuredObserver;

public class BasicLearner implements Learner, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Regressor regressor;
	private Regressor learner;
	private transient Observer observer;
	
	private double[] o0;
	private double reward = 0;
	private double alpha;
	
	public BasicLearner(Regressor r, double a) {
		regressor = r;
		learner = r.copy();
		alpha = a;
	}
	
	@Override
	public double[] score(int a, int move) {
		double[] o1 = observer.observe(a);
		double[] score = regressor.predict(o1);
		double m = Double.NEGATIVE_INFINITY;
		for (double s : score)
			if (s > m)
				m = s;
		double delta = reward + alpha * m;
		if (o0 != null)
			learner.fit(o0, move, delta);
		o0 = o1;
		reward = 0;
		return score;
	}
	
	@Override
	public void reward(int a, double r) {
		reward += r;
	}

	@Override
	public void setObserver(StructuredObserver o) {
		observer = o;
	}
	
}
