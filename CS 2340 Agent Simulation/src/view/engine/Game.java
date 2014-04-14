package view.engine;

import agent.human.HumanAgent;
import agent.i.Agent;
import agent.random.RandomAgent;
import environment.ColorScene;
import environment.ColorWar;
import environment.Constants;
import view.engine.Engine;
import view.engine.system.InputMap;

// describe a set of scenes, and their transitions, and general resources
public class Game implements Runnable {
	private Engine m_engine;
	
	public Game() {
		m_engine = new Engine();
	}

	@Override
	public void run() {
		// setup input reader
		InputMap imap = null;
		if (Constants.isHumanPlayable)
			imap = new InputMap();
		
		// setup the agents
		Agent[] agents = new Agent[Constants.numAgents];
		for (int i = 0; i < Constants.numAgents; ++i) {
			if (Constants.isHumanPlayable && i == 0)
				agents[i] = new HumanAgent(imap);
			else
				agents[i] = new RandomAgent();
		}
		
		// setup the environment
		ColorWar colorWar = new ColorWar(agents);
		for (int i = 0; i < agents.length; ++i) {
			agents[i].setObserver(colorWar);
		}
		
		// setup the view and go!
		ColorScene scene = new ColorScene(colorWar, imap);
		m_engine.setScene(scene);
		m_engine.start();
		
		System.exit(0);
	}
}
