package environment.colorwar;

import view.engine.Engine;

// describe a set of scenes, and their transitions
public class GenericGame implements Runnable {
	private Engine m_engine;
	
	public GenericGame() {
		m_engine = new Engine();
	}

	@Override
	public void run() {
		// there is only one scene in this game
		m_engine.setScene(new ColorScene());
		m_engine.start();
	}
}
