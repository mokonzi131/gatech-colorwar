package agent.reinforcement;

import environment.i.Observer;

public class BasicLearner implements Learner {
	
	private Regressor regressor;
	private Observer observer;
	
	private double[] o0;
	private double reward;
	
	public BasicLearner(Regressor r, Observer o) {
		regressor = r;
		observer = o;
	}
	
	@Override
	public double[] score(int a) {
		double[] o1 = observer.observe(a);
		double[] score = regressor.predict(o1);
		double m = -Double.NEGATIVE_INFINITY;
		for (double s : score)
			if (s > m)
				m = s;
		double delta = m + reward;
		regressor.fit(o0, a, delta);
		o0 = o1;
		return score;
	}

	@Override
	public void reward(int a, double r) {
		reward = r;
	}

}
