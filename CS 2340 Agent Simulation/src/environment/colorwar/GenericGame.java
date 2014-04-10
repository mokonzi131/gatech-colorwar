package environment.colorwar;

import view.engine.Engine;

// describe a set of scenes, and their transitions, and general resources
public class GenericGame implements Runnable {
	private Engine m_engine;
	
	public GenericGame() {
		m_engine = new Engine();
	}

	@Override
	public void run() {
//		// intro scene
//		m_engine.setScene(new SplashScene());
//		m_engine.start();
		
		// the main game scene
		m_engine.setScene(new ColorScene());
		m_engine.start();
		
		System.exit(0);
	}
}
