package view.engine;

import environment.colorwar.Constants;

public class Engine {
	private static final float NANOS_PER_SECOND = 1000000000f;
	private static final float FRAME_FREQUENCY_NANOS = NANOS_PER_SECOND / Constants.FPS;
	
	private Scene m_currentScene;
	private boolean m_running;
	
	public Engine() {
		m_currentScene = null;
		m_running = false;
	}
	
	public void setScene(Scene scene) {
		m_currentScene = scene;
	}
	
	public void run() {
		// setup the engine and rendering stack
		m_currentScene.initialize();
		
		// run the game
		long lastTime = System.nanoTime();
		while (m_running) {
			// throttle update-loop timing to FPS
			long elapsedTime = System.nanoTime() - lastTime;
			while (elapsedTime < FRAME_FREQUENCY_NANOS)
				elapsedTime = System.nanoTime() - lastTime;
			lastTime += elapsedTime;
			double  deltaTime = elapsedTime / NANOS_PER_SECOND;
			
			// update game
			m_currentScene.update(deltaTime);
			
			// render the game
			m_currentScene.render();
		}
	}
	
	public void start() {
		if (!m_running)
			m_running = true;
		
		run();
	}
	
	public void stop() {
		m_running = false;
	}
}
