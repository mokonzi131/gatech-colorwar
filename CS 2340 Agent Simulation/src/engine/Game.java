package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private static final int MINIMUM_NUM_AGENTS = 1;
	
	private int m_numAgents;
	private boolean m_isHumanPlayable;
	private double m_agentTimer;
	
	public Game(int numAgents, boolean human) {
		m_numAgents = Math.max(numAgents, MINIMUM_NUM_AGENTS);
		m_isHumanPlayable = human;
		m_agentTimer = 0.0;
	}
	
	public void initialize() {
		// TODO
		LOGGER.log(Level.INFO, "initializing game");
	}
	
	public void teardown() {
		// TODO
		LOGGER.log(Level.INFO, "tearing down game");
	}
	
	public void update(double deltaTime) {
		m_agentTimer += deltaTime;
		if (m_agentTimer > 1.0) {
			m_agentTimer = 0.0;
			LOGGER.log(Level.INFO, "MOVE AGENTS!");
		}
	}
	
	public void render(Graphics2D context) {
		// TODO
		context.setColor(Color.RED);
		context.fillRect(10, 10, 30, 50);
	}
	
	public int numPlayers() { return m_numAgents; }
}
