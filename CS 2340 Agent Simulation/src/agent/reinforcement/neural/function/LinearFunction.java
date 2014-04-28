package agent.reinforcement.neural.function;

import java.io.Serializable;

public class LinearFunction implements Function, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public double g(double x) {
		return x;
	}

	@Override
	public double dg(double x) {
		return 1;
	}

}
