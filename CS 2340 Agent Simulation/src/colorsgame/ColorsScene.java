package colorsgame;

import iview.IView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.scene.Actor;
import engine.scene.Scene;
import environment.Cell;
import environment.Environment;

public class ColorsScene extends Scene {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private double m_agentTimer;
	
	BufferedImage m_worldImage;
	private List<Actor> m_actors;
	private List<AgentActor> m_agentActors;
	private IView m_controller;
	
	public ColorsScene() {
		m_agentTimer = 0.0;
		m_actors = new ArrayList<>();
		m_agentActors = new ArrayList<>();
		m_controller = null;
	}
	
	public void setController(IView controller) {
		m_controller = controller;
	}

	@Override
	public void initialize() {
		m_worldImage = new BufferedImage(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		// create background color star-field
		for (int i = 0; i < 45; ++i)
			m_actors.add(new StarActor(-21, 40));
		for (int i = 0; i < 45; ++i)
			m_actors.add(new StarActor(-50, 100));
		
		// create world map (environment)
		Environment environment = new Environment();
		for (Cell cell : environment.cells()) {
			float[] coordinates = cell.coords();
			TileActor tileEnvironment = new TileActor(coordinates[0], coordinates[1], cell.containsResource()); 
			m_actors.add(tileEnvironment);
		}
		
		// create agents
		for (int i = 0; i < Constants.numAgents; ++i) {
			Point2D.Float location = environment.spawnLocation();
			// TODO create and assign controllers to the agents
			AgentActor agent = new AgentActor(location.x, location.y, new RandomAgentController());
			m_actors.add(agent);
			m_agentActors.add(agent);
		}
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(double deltaTime) {
		for (Actor actor : m_actors)
			actor.update(deltaTime);
		
		m_agentTimer += deltaTime;
		if (m_agentTimer > 1.0) {
			m_agentTimer = 0.0;
			for (AgentActor agent : m_agentActors)
				agent.move();
		}
	}

	@Override
	public void render(BufferedImage[] images) {
		// render the world
		Graphics2D worldContext = m_worldImage.createGraphics();
		background(worldContext, m_worldImage.getWidth(), m_worldImage.getHeight());
		for (Actor actor : m_actors)
			actor.render(worldContext);
		
		// render developer image from world image
		viewport(m_worldImage, images[0]);
		
		// render human image from world image
		Actor humanAgent = m_agentActors.get(0);
		Point2D location = humanAgent.location();
		snapshot(m_worldImage, images[1], location);
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
	
	private void viewport(BufferedImage from, BufferedImage to) {
		Graphics2D graphics = to.createGraphics();
		graphics.drawImage(from, 0, 0, to.getWidth(), to.getHeight(),
				0, 0, from.getWidth(), from.getHeight(), null);
	}
}
