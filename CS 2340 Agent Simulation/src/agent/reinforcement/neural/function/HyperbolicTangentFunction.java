package agent.reinforcement.neural.function;

public class HyperbolicTangentFunction implements Function {
	
	Function f = new LogisticSigmoidFunction();
	
	@Override
	public double g(double x) {
		return 2 * f.g(2 * x) - 1;
	}

	@Override
	public double dg(double x) {
		return 4 * f.dg(2 * x);
	}

}
