package agent.reinforcement.neural.function;

import java.io.Serializable;

public interface Function extends Serializable {
	
	double g(double x);
	double dg(double x);
	
}
