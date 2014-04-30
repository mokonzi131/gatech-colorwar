package environment;

import java.awt.Point;

import agent.i.Agent;

public class AgentMoveSelectionThread implements Runnable {
	
	private Astats stat;
	private Agent agent;
	private int ind;
	private Point[] moves;
	AgentMoveSelectionThread(Point[] moves, int ind, Astats stat, Agent agent){
		this.stat=stat;
		this.agent=agent;
		this.ind=ind;
		this.moves=moves;
	}

	@Override
	public void run() {
		Astats a = stat;
		a.x0 = a.x;
		a.y0 = a.y;
		
		int agentMove = -1;
		if (a.alive)
			agentMove = agent.move(ind);
		
		// get the point for that desired move
		// -1 = null, 0 = left, 1 = up, 2 = right, 3 = down, 4 = none
		Point point;
		if (agentMove == -1)
			point = null;
		else if (agentMove == 0)
			point = new Point(a.x - 1, a.y);
		else if (agentMove == 1)
			point = new Point(a.x, a.y - 1);
		else if (agentMove == 2)
			point = new Point(a.x + 1, a.y);
		else if (agentMove == 3)
			point = new Point(a.x, a.y + 1);
		else
			point = new Point(a.x, a.y);
		
		// attempt to move to that desired
		moves[ind] = point;
	}
}
