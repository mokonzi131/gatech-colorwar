package environment.colorwar.controllers;

import agent.i.Agent;

public class IntelligentAgentController extends AgentController {
	private Agent m_learningAgent;
	
	public IntelligentAgentController(Agent learningAgent) {
		m_learningAgent = learningAgent;
	}

	@Override
	public DIRECTION getNextMove() {
		int move = m_learningAgent.move(0);

		// TODO interpret according to agent's design
		switch(move) {
		case 0:
			return DIRECTION.NORTH;
		case 1:
			return DIRECTION.EAST;
		case 2:
			return DIRECTION.SOUTH;
		case 3:
			return DIRECTION.WEST;
		default:
			return DIRECTION.NONE;
		}
	}

}
