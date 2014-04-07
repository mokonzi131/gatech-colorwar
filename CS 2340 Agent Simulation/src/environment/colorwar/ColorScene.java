package environment.colorwar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import agent.random.RandomAgent;
import environment.colorwar.Constants.RENDERING_TYPE;
import environment.colorwar.controllers.AgentController;
import environment.colorwar.controllers.HumanAgentController;
import environment.colorwar.controllers.IntelligentAgentController;
import environment.colorwar.controllers.RandomAgentController;
import view.engine.Actor;
import view.engine.Scene;
import view.engine.system.Display;
import view.engine.system.InputMap;

public class ColorScene implements Scene {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private double m_agentTimer;
	
	BufferedImage m_worldImage;
	private List<Actor> m_actors;
	private List<AgentActor> m_agentActors;
	private Display m_masterDisplay;
	private Display[] m_agentDisplays;
	
	public ColorScene() {
		m_agentTimer = 0.0;
		m_actors = new ArrayList<>();
		m_agentActors = new ArrayList<>();
		m_masterDisplay = null;
		m_agentDisplays = new Display[Constants.numAgents];
	}

	@Override
	public void initialize() {
		// setup input reader
		InputMap imap = null;
		if (Constants.isHumanPlayable)
			imap = new InputMap();
		
		// setup displays
		switch(Constants.renderingType) {
		case DEVELOPER:
			m_masterDisplay = new Display(
					Constants.DEV_VIEW_WIDTH, Constants.DEV_VIEW_HEIGHT);
			m_masterDisplay.initialize(null);
			
			for (int i = 1; i < Constants.numAgents; ++i) {
				m_agentDisplays[i] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
				m_agentDisplays[i].initialize(null);
			}
		case NORMAL:
			m_agentDisplays[0] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
			m_agentDisplays[0].initialize(imap);
		case SIMULATED:
			// TODO implement text-based resources to help observe agent-training...
			break;
		}
		
		m_worldImage = new BufferedImage(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// create background color star-field actors
		for (int i = 0; i < 45; ++i)
			m_actors.add(new StarActor(-21, 40));
		for (int i = 0; i < 45; ++i)
			m_actors.add(new StarActor(-50, 100));
		
		// create world map (environment) actor
		Environment environment = new Environment();
		m_actors.add(environment);
		
		// create agents actors
		for (int i = 0; i < Constants.numAgents; ++i) {
			Point2D.Float location = environment.spawnLocation();
			
			AgentController controller;
			if (Constants.isHumanPlayable && i == 0)
				controller = new HumanAgentController(imap);
			else if (i == 1)
				controller = new IntelligentAgentController(new RandomAgent(environment));
			else
				controller = new RandomAgentController();
			
			AgentActor agent = new AgentActor(location.x, location.y, controller, environment);
			m_actors.add(agent);
			m_agentActors.add(agent);
		}
	}

	@Override
	public void update(double deltaTime) {
		// update all actors every time
		for (Actor actor : m_actors)
			actor.update(deltaTime);
		
		// move the agents at 1-second intervals
		m_agentTimer += deltaTime;
		if (m_agentTimer > 1.0) {
			m_agentTimer = 0.0;
			for (AgentActor agent : m_agentActors)
				agent.move();
		}
	}

	@Override
	public void render() {
		if (Constants.renderingType == RENDERING_TYPE.SIMULATED)
			return;
		
		// render the world
		Graphics2D worldContext = m_worldImage.createGraphics();
		background(worldContext, m_worldImage.getWidth(), m_worldImage.getHeight());
		for (Actor actor : m_actors)
			actor.render(worldContext);
		
		switch(Constants.renderingType) {
		case DEVELOPER:
			// render the developer image from the world
			viewport(m_worldImage, m_masterDisplay.getContext());
			
			// render an image for all the agents
			for (int i = 1; i < Constants.numAgents; ++i) {
				Actor agent = m_agentActors.get(i);
				Point2D location = agent.location();
				snapshot(m_worldImage, m_agentDisplays[i].getContext(), location);
			}
		case NORMAL:
			// render the image for the first agents
			Actor agent = m_agentActors.get(0);
			Point2D location = agent.location();
			snapshot(m_worldImage, m_agentDisplays[0].getContext(), location);
		case SIMULATED:
			// TODO is there anything to do here...?
		}
	}
	
	// clear a graphics context to the background color
	private void background(Graphics2D context, int width, int height) {
		context.setColor(Color.BLACK);
		context.fillRect(0, 0, width, height);
	}
	
	// render a snapshot image from one graphics to the other centered at a location
	private void snapshot(BufferedImage from, BufferedImage to, Point2D location) {
		Graphics2D graphics = to.createGraphics();
		background(graphics, to.getWidth(), to.getHeight());
		int x = (int) location.getX();
		int y = (int) location.getY();
		int radius = (Constants.AGENT_RANGE * Constants.CELL_DISTANCE) / 2;
		
		graphics.drawImage(from, 0, 0, to.getWidth(), to.getHeight(),
				x - radius, y - radius, x + radius, y + radius, null);
	}
	
	// move the world scene to the viewport
	private void viewport(BufferedImage from, BufferedImage to) {
		Graphics2D graphics = to.createGraphics();
		graphics.drawImage(from, 0, 0, to.getWidth(), to.getHeight(),
				0, 0, from.getWidth(), from.getHeight(), null);
	}
}
