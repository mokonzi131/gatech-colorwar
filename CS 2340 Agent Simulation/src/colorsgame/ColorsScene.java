package colorsgame;

import java.awt.Graphics2D;
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
	
	public ColorsScene() {
		m_agentTimer = 0.0;
		m_actors = new ArrayList<>();
	}

	@Override
	public void initialize() {
		// create background color star-field
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-10, 10));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-20, 20));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-40, 40));
		
		// create world map (environment)
		Environment environment = new Environment();
		for (Cell cell : environment.cells()) {
			float[] coordinates = cell.coords();
			m_actors.add(new TileActor(coordinates[0], coordinates[1], cell.constainsResource()));
		}
		
		// create agents
		for (int i = 0; i < Constants.numAgents; ++i)
			m_actors.add(new AgentActor(5, 5));
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
	public void render(Graphics2D context) {
		for (Actor actor : m_actors)
			actor.render(context);
	}

}
