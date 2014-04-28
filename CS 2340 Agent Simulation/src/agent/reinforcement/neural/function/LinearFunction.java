package agent.reinforcement.neural.function;

public class LinearFunction implements Function {

	@Override
	public double g(double x) {
		return x;
	}

	@Override
	public double dg(double x) {
		return 1;
	}

}
