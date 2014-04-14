package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import environment.Constants;
import environment.i.IEnvironment;
import view.engine.Scene;
import view.engine.system.Display;
import view.engine.system.InputMap;

public class ColorScene extends Scene implements WindowListener {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private Display m_masterDisplay;
//	private Display[] m_agentDisplays;
	BufferedImage m_worldImage;
	private IEnvironment m_environment;
	
	public ColorScene(IEnvironment environment, InputMap imap) {
		m_environment = environment;
		m_masterDisplay = null;
//		m_agentDisplays = new Display[Constants.numAgents];
		m_worldImage = new BufferedImage(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// setup displays
		switch(Constants.renderingType) {
		case DEVELOPER:
			m_masterDisplay = new Display(Constants.DEV_VIEW_WIDTH, Constants.DEV_VIEW_HEIGHT);
			m_masterDisplay.initialize(null, Constants.GAME_NAME);
			m_masterDisplay.setCloseListener(this);
			
//			for (int i = 1; i < Constants.numAgents; ++i) {
//				m_agentDisplays[i] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
//				m_agentDisplays[i].initialize(null, Constants.GAME_NAME);
//				m_agentDisplays[i].setCloseListener(this);
//			}
		case NORMAL:
//			m_agentDisplays[0] = new Display(Constants.AGENT_VIEW_WIDTH, Constants.AGENT_VIEW_HEIGHT);
//			m_agentDisplays[0].initialize(imap, Constants.GAME_NAME);
//			m_agentDisplays[0].setCloseListener(this);
		case SIMULATED:
			break;
		}
	}

	@Override public void initialize() {}

	@Override
	public void update(double deltaTime) {
		// update ColorWar game elements
		if (Constants.renderingType == Constants.RENDERING_TYPE.SIMULATED)
			m_environment.update(1.0);
		else
			m_environment.update(deltaTime);
		
		if (m_environment.isEnd()) {
			double[] score = m_environment.score();
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
		Graphics2D context = m_worldImage.createGraphics();
		background(context, m_worldImage.getWidth(), m_worldImage.getHeight());
		m_environment.render(context);
		
		switch(Constants.renderingType) {
		case DEVELOPER:
			// render the developer image from the complete world
			viewport(m_worldImage, m_masterDisplay.getContext());
			
			// render an image for all the agents // TODO fix this
//			for (int i = 1; i < Constants.numAgents; ++i) {
//				Point location = m_colorWar.getAgentLocation(i);
//				location = new Point(m_colorWar.gridToPixel(location.x), m_colorWar.gridToPixel(location.y));
//				snapshot(m_worldImage, m_agentDisplays[i].getContext(), location);
//			}
		case NORMAL:
			// render the image for the first agents
//			Point location = m_colorWar.getAgentLocation(0);
//			location = new Point(m_colorWar.gridToPixel(location.x), m_colorWar.gridToPixel(location.y));
//			snapshot(m_worldImage, m_agentDisplays[0].getContext(), location);
		case SIMULATED:
			// shouldn't ever render a simulation
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

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) { finished = true; }
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
}
