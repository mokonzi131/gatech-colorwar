package agent.reinforcement;

import java.io.Serializable;

public interface Regressor extends Serializable {

	double[] predict(double[] obs);

	void fit(double[] observation, int a, double max);

	Regressor copy();

}
