package view.engine;

import java.util.logging.Logger;

import agent.human.HumanAgent;
import agent.i.Agent;
import agent.random.RandomAgent;
import agent.reinforcement.ReinforcementAgent;
import agent.reinforcement.neural.BasicNeuralAgentFactory;
import environment.ColorScene;
import environment.ColorWar;
import environment.Constants;
import view.engine.Engine;
import view.engine.system.InputMap;

// describe a set of scenes, and their transitions, and general resources
public class Game implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());
	
	private Engine m_engine;
	
	public Game() {
		m_engine = new Engine();
	}

	@Override
	public void run() {
		// acquire the neural agent resources
		final String filename = "BasicNeuralAgent.dat";
		ReinforcementAgent neuralAgent;
		try {
			neuralAgent = BasicNeuralAgentFactory.loadAgent(filename);
		} catch (Exception e) {
			LOGGER.info("Generating a new Neural Agent");
			neuralAgent = BasicNeuralAgentFactory.generateAgent(15, 4);
		}
		
		// setup input reader, and the scene
		InputMap imap = null;
		if (Constants.isHumanPlayable)
			imap = new InputMap();
		
		// setup the agents
		Agent[] agents = new Agent[Constants.numAgents];
		for (int i = 0; i < Constants.numAgents; ++i) {
//			if (i == 0)
//				if (Constants.isHumanPlayable)
//					agents[i] = new HumanAgent(imap);
//				else
//					agents[i] = new RandomAgent();
//			else
				agents[i] = neuralAgent;
		}
		
		// setup the environment
		ColorWar colorWar = new ColorWar(agents);
		for (int i = 0; i < agents.length; ++i)
			agents[i].setObserver(colorWar);
		
		// run the iterations
		ColorScene scene = new ColorScene(colorWar, imap);
		for (int i = 0; i < 100; ++i) {
			colorWar.reset();
			scene.reset();
			m_engine.setScene(scene);
			m_engine.start();
		}
		
		// finish game - save agent, etc.
		BasicNeuralAgentFactory.saveAgent(neuralAgent, filename);
		
		System.exit(0);
	}
}
