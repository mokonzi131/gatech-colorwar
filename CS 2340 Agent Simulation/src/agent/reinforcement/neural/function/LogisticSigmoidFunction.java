package agent.reinforcement.neural.function;

public class LogisticSigmoidFunction implements Function {

	@Override
	public double g(double x) {
		if (x > 0) {
			double ex = Math.exp(-x);
			return 1 / (1 + ex);
		} else {
			double ex = Math.exp(x);
			return ex / (1 + ex);
		}
	}

	@Override
	public double dg(double x) {
		double g = g(x);
		return g * (1 - g);
	}

}
