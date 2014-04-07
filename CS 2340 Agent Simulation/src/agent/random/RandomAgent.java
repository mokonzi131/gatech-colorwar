package agent.random;

import java.util.Random;

import environment.i.Observer;

import agent.i.Agent;

public class RandomAgent implements Agent {
	
	private Observer o;
	private Random r = new Random();
	
	public RandomAgent(Observer o0) {
		o = o0;
	}

	@Override
	public int move(int a) {
		int m0 = o.actionRange(a);
		return r.nextInt(m0);
	}

	@Override
	public void reward(int a, double r) {
		// TODO Auto-generated method stub
	}

}
