package agent.reinforcement;

import java.io.Serializable;
import java.util.Arrays;

import util.ObjectCloner;

import environment.i.StructuredObserver;
import agent.i.Agent;
import agent.reinforcement.neural.NeuralRegressor;
import agent.reinforcement.neural.net.NeuralNet;

public class ReinforcementAgent implements Agent, Serializable {
	private static final long serialVersionUID = 1L;
	
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
		learner.reset(move);
	}
	
	public ReinforcementAgent copy() {
		try {
			ReinforcementAgent ra = (ReinforcementAgent) ObjectCloner.deepCopy(this);
			ra.rootTransfer(this);
			return ra;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	private void rootTransfer(ReinforcementAgent ra) {
		learner.rootTransfer(ra.getLearner());
	}

	private Learner getLearner() {
		return learner;
	}
	
}
