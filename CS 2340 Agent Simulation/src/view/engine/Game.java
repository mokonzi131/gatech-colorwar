package view.engine;

import environment.ColorScene;
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
		ColorScene scene = new ColorScene();
		m_engine.setScene(scene);
		m_engine.start();
		
		System.exit(0);
	}
}
