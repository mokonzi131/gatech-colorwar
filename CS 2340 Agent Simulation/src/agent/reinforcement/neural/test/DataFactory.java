package agent.reinforcement.neural.test;

import agent.reinforcement.Regressor;

public abstract class DataFactory {
	
	public abstract Instance generateInstance();
	
	public Instance[] generateDataset(int n) {
		Instance[] d = new Instance[n];
		for (int i = 0; i < n; i++) d[i] = generateInstance();
		return d;
	}

	public static void train(Regressor r, DataFactory d, int n) {
		for (int i = 0; i < n; i++) {
			Instance a = d.generateInstance();
			r.fit(a.x, a.y);
		}
	}

	public static double test(Regressor r, DataFactory d, int n) {
		double err = 0;
		for (int i = 0; i < n; i++) {
			Instance a = d.generateInstance();
			double[] y0 = r.predict(a.x);
			for (int j = 0; j < y0.length; j++) {
				double e = a.y[j] - y0[j];
				err += e * e / y0.length;
			}
		}
		return err / n;
	}
	
}
