package environment.colorwar.controllers;

import view.engine.system.InputMap;

public class HumanAgentController extends AgentController {
	private InputMap m_inputMap;
	
	public HumanAgentController(InputMap imap) {
		m_inputMap = imap;
	}

	@Override
	public DIRECTION getNextMove() {
		int key = m_inputMap.getLastKey();
		
		switch(key) {
		case InputMap.KEY_LEFT:
			return DIRECTION.WEST;
		case InputMap.KEY_RIGHT:
			return DIRECTION.EAST;
		case InputMap.KEY_DOWN:
			return DIRECTION.SOUTH;
		case InputMap.KEY_UP:
			return DIRECTION.NORTH;
		default:
			return DIRECTION.NONE;
		}
	}

}
