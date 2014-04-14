package view.engine;

import environment.colorwar.ColorScene;
import view.engine.Engine;

// describe a set of scenes, and their transitions, and general resources
public class Game implements Runnable {
	private Engine m_engine;
	
	public Game() {
		m_engine = new Engine();
	}

	@Override
	public void run() {
		// the main game scene
		m_engine.setScene(new ColorScene());
		m_engine.start();
		
		System.exit(0);
	}
}
