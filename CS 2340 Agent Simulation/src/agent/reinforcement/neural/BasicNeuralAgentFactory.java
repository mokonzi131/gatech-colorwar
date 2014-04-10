package agent.reinforcement.neural;

import environment.i.Observer;
import agent.i.Agent;
import agent.reinforcement.BasicLearner;
import agent.reinforcement.BasicSelector;
import agent.reinforcement.Learner;
import agent.reinforcement.Regressor;
import agent.reinforcement.ReinforcementAgent;
import agent.reinforcement.Selector;

public class BasicNeuralAgentFactory {
	
	public static Agent generateAgent(int in, int out, Observer o) {
		Regressor r = new NeuralRegressor(.05, .05, .05, new int[] {in,out});
		Learner l = new BasicLearner(r, o, .05);
		Selector s = new BasicSelector();
		return new ReinforcementAgent(l, s);
	}
	
}
