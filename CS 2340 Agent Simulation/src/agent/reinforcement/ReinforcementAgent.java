package agent.reinforcement;

import java.io.Serializable;

import environment.i.StructuredObserver;
import agent.i.Agent;

public class ReinforcementAgent implements Agent, Serializable {
	
	private Learner learner;
	private Selector selector;
	
	private int move = 0;
	
	public ReinforcementAgent(Learner l, Selector s) {
		this.learner = l;
		this.selector = s;
	}

	@Override
	public int move(int a) {
		double[] score = learner.score(a, move);
		move = selector.select(score);
//		System.out.println("Move: "+move);
		return move;
	}

	@Override
	public void reward(int a, double r) {
//		System.out.println("Reward: "+r);
		learner.reward(a, r);
		selector.reward(a, r);
	}

	@Override
	public void setObserver(StructuredObserver o) {
		learner.setObserver(o);
		selector.setObserver(o);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
}
