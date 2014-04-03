package colorsgame;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
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
	
	private List<Actor> m_actors;
	private List<Actor> m_agentActors;
	
	public ColorsScene() {
		m_agentTimer = 0.0;
		m_actors = new ArrayList<>();
		m_agentActors = new ArrayList<>();
	}

	@Override
	public void initialize() {
		// create background color star-field
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-5, 5));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-10, 10));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-40, 40));
		
		// create world map (environment)
		Environment environment = new Environment();
		for (Cell cell : environment.cells()) {
			float[] coordinates = cell.coords();
			TileActor tileEnvironment = new TileActor(coordinates[0], coordinates[1], cell.containsResource()); 
			m_actors.add(tileEnvironment);
		}
		
		// create agents
		for (int i = 0; i < Constants.numAgents; ++i) {
			AgentActor agent = new AgentActor(5, 5);
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
			LOGGER.log(Level.INFO, "MOVE AGENTS!");
		}
	}

	@Override
	public void render(Graphics2D[] contexts) {
		Graphics2D developerContext = contexts[0];
		for (Actor actor : m_actors) {
			actor.render(developerContext);
		}
		
		Graphics2D humanContext = contexts[1];
		Actor humanAgent = m_agentActors.get(0);
		Point2D location = humanAgent.location();
		snapshot(developerContext, humanContext, location);
	}
	
	// render a snapshot image from one graphics to the other centered at a location
	private void snapshot(Graphics2D from, Graphics2D to, Point2D location) {
		// TODO
	}
}
