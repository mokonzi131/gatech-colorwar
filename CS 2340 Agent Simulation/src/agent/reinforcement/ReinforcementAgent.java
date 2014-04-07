package agent.reinforcement;

import agent.i.Agent;

public class ReinforcementAgent implements Agent {
	
	private Learner learner;
	private Selector selector;
	
	public ReinforcementAgent(Learner l, Selector s) {
		this.learner = l;
		this.selector = s;
	}

	@Override
	public int move(int a) {
		double[] score = learner.score(a);
		return selector.select(score);
	}

	@Override
	public void reward(int a, double r) {
		learner.reward(a, r);
		selector.reward(a, r);
	}
	
}
