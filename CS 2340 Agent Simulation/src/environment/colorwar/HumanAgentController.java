package environment.colorwar;

import view.engine.system.InputMap;

public class HumanAgentController extends AgentController {
	private InputMap m_inputMap;
	
	public HumanAgentController(InputMap imap) {
		m_inputMap = imap;
	}

	@Override
	public DIRECTION getNextMove() {
		// TODO use the inputMap to figure out the right move...
		return DIRECTION.NONE;
	}

}
