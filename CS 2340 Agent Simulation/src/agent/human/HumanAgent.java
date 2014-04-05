package agent.human;

import environment.i.GraphicalObserver;
import agent.i.Agent;

public class HumanAgent implements Agent {
	
	private GraphicalObserver observer;
	
	public HumanAgent(GraphicalObserver o) {
		observer = o;
	}
	
	@Override
	public int move(int a) {
		return observer.queueUserMove(a);
	}
	
	@Override
	public void reward(double r) {
		observer.displayReward(r);
	}

}
