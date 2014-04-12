package agent.human;

import environment.i.GraphicalObserver;
import environment.i.StructuredObserver;
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
	public void reward(int a, double r) {
		observer.displayReward(r);
	}

	@Override
	public void setObserver(StructuredObserver o) {
		// TODO Auto-generated method stub
		
	}

}
