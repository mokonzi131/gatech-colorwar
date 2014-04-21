package environment;

import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import agent.human.HumanAgent;
import agent.i.Agent;
import agent.random.RandomAgent;
import agent.reinforcement.ReinforcementAgent;
import agent.reinforcement.neural.BasicNeuralAgentFactory;
import environment.ColorWar;
import environment.Constants;
import environment.i.IEnvironment;
import view.engine.Scene;
import view.engine.system.Display;
import view.engine.system.InputMap;

public class ColorScene extends Scene implements WindowListener {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private Display m_masterDisplay;
	private Display[] m_agentDisplays;
	BufferedImage m_worldImage;

	private ColorWar m_colorWar;

	private String filename = "BasicNeuralAgent.dat";
	private ReinforcementAgent neuralAgent;
	private int count = 0;
	
	public ColorScene(IEnvironment environment, InputMap imap) {
		m_colorWar = (ColorWar)environment;
		m_masterDisplay = null;
		m_agentDisplays = new Display[Constants.numAgents];
		m_worldImage = new BufferedImage(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// setup displays
		switch(Constants.renderingType) {
		case DEVELOPER:
			m_masterDisplay = new Display(Constants.DEV_VIEW_WIDTH, Constants.DEV_VIEW_HEIGHT);
			m_masterDisplay.initialize(null, Constants.GAME_NAME);
			m_masterDisplay.setCloseListener(this);
			
			for (int i = 1; i < Constants.numAgents; ++i) {
				m_agentDisplays[i] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
				m_agentDisplays[i].initialize(null, Constants.GAME_NAME);
				m_agentDisplays[i].setCloseListener(this);
			}
		case NORMAL:
			m_agentDisplays[0] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
			m_agentDisplays[0].initialize(imap, Constants.GAME_NAME);
			m_agentDisplays[0].setCloseListener(this);
			neuralAgent = BasicNeuralAgentFactory.loadAgent(filename);
		case SIMULATED:
			// TODO implement text-based resources to help observe agent-training...
			neuralAgent = BasicNeuralAgentFactory.generateAgent(15, 4);
			break;
		}
		
		// setup ColorWar game components
		Agent[] agents = new Agent[Constants.numAgents];
		for (int i = 0; i < Constants.numAgents; ++i) {
			if (Constants.isHumanPlayable && i == 0 && Constants.renderingType != Constants.renderingType.SIMULATED)
				agents[i] = new HumanAgent(imap);
			else
				agents[i] = neuralAgent;
		}
		
		ColorWar colorWarEnvironment = new ColorWar(agents);
		for (int i = 0; i < agents.length; ++i) {
			agents[i].setObserver(colorWarEnvironment);
		}
		
		m_colorWar = colorWarEnvironment;
	}

	@Override public void initialize() {}

	@Override
	public void update(double deltaTime) {
		// update ColorWar game elements
		if (Constants.renderingType == Constants.RENDERING_TYPE.SIMULATED) {
			m_colorWar.update(1.0);
			if (++count%1000==0)
				BasicNeuralAgentFactory.saveAgent(neuralAgent, "BasicNeuralAgent.dat");
		} else
			m_colorWar.update(deltaTime);
		
		if (m_colorWar.isEnd()) {
			double[] score = m_colorWar.score();
			String scoreString = "";
			for (int i = 0; i < score.length; ++i)
				scoreString += score[i] + ", ";
			LOGGER.info(scoreString);
			finished = true;
		}
	}

	@Override
	public void render() {
		if (Constants.renderingType == Constants.RENDERING_TYPE.SIMULATED)
			return;
		
		// render the complete world
		m_colorWar.render(m_worldImage);
		
		switch(Constants.renderingType) {
		case DEVELOPER:
			// render the developer image from the complete world
			viewport(m_worldImage, m_masterDisplay.getContext());
			
			// render an image for all the agents
			for (int i = 1; i < Constants.numAgents; ++i)
				m_colorWar.renderAgentFromWorld(i, m_worldImage, m_agentDisplays[i].getContext());
		case NORMAL:
			// render the image for the first agents
			m_colorWar.renderAgentFromWorld(0, m_worldImage, m_agentDisplays[0].getContext());
		case SIMULATED:
			// shouldn't ever render a simulation
		}
	}
	
	// move the world scene to the viewport
	private void viewport(BufferedImage from, BufferedImage to) {
		Graphics2D graphics = to.createGraphics();
		graphics.drawImage(from, 0, 0, to.getWidth(), to.getHeight(),
				0, 0, from.getWidth(), from.getHeight(), null);
	}

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) { finished = true; }
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
}
