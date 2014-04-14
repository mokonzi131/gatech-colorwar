package agent.human;

import view.engine.system.InputMap;
import environment.colorwar.controllers.AgentController.DIRECTION;
import environment.i.GraphicalObserver;
import environment.i.StructuredObserver;
import agent.i.Agent;

public class HumanAgent implements Agent {
	
	InputMap m_inputMap;
	
	public HumanAgent(InputMap inputMap) {
		m_inputMap = inputMap;
	}
	
	@Override
	public int move(int a) {
		int key = m_inputMap.getLastKey();
		
		switch(key) {
		case InputMap.KEY_LEFT:
			return 0;
		case InputMap.KEY_UP:
			return 1;
		case InputMap.KEY_RIGHT:
			return 2;
		case InputMap.KEY_DOWN:
			return 3;
		default:
			return 4;
		}
	}
	
	@Override
	public void reward(int a, double r) {
//		observer.displayReward(r);
	}

	@Override
	public void setObserver(StructuredObserver o) {
		// TODO Auto-generated method stub
	}

}
