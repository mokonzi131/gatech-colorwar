package environment.colorwar;

import view.InputMap;

public class HumanAgentController extends AgentController {
	private InputMap m_inputMap;
	
	public HumanAgentController(InputMap imap) {
		m_inputMap = imap;
	}

	@Override
	public DIRECTION getNextMove() {
		return DIRECTION.NONE;
	}

}
