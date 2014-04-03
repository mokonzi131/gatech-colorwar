package colorsgame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.scene.Scene;

public class ColorsScene extends Scene {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private double m_agentTimer;
	
	private List<StarActor> m_actors;
	
	private List<StarActor> m_agents;
	private StarActor m_environment;
	
	public ColorsScene() {
		m_agentTimer = 0.0;
		m_actors = new ArrayList<>();
		m_agents = new ArrayList<>();
		m_environment = null;
	}

	@Override
	public void initialize() {
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-10, 10));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-20, 20));
		for (int i = 0; i < 33; ++i)
			m_actors.add(new StarActor(-40, 40));
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(double deltaTime) {
		for (StarActor actor : m_actors)
			actor.update(deltaTime);
		
		m_agentTimer += deltaTime;
		if (m_agentTimer > 1.0) {
			m_agentTimer = 0.0;
			LOGGER.log(Level.INFO, "MOVE AGENTS!");
		}
	}

	@Override
	public void render(Graphics2D context) {
		for (StarActor actor : m_actors)
			actor.render(context);
	}

}
