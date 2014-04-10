package agent.reinforcement;

import environment.i.Observer;

public class BasicLearner implements Learner {
	
	private Regressor regressor;
	private Regressor learner;
	private Observer observer;
	
	private double[] o0;
	private double reward = 0;
	private double alpha;
	
	public BasicLearner(Regressor r, Observer o, double a) {
		regressor = r;
		learner = r.copy();
		observer = o;
		alpha = a;
	}
	
	@Override
	public double[] score(int a) {
		double[] o1 = observer.observe(a);
		double[] score = regressor.predict(o1);
		double m = -Double.NEGATIVE_INFINITY;
		for (double s : score)
			if (s > m)
				m = s;
		double delta = reward + alpha * m;
		if (o0 != null)
			learner.fit(o0, a, delta);
		o0 = o1;
		reward = 0;
		return score;
	}
	
	@Override
	public void reward(int a, double r) {
		reward += r;
	}
	
}
