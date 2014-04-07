package agent.reinforcement;

public interface Regressor {

	double[] predict(double[] obs);

	void fit(double[] observation, int a, double max);

}
