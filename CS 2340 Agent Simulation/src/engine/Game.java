package engine;

import java.awt.Graphics2D;
import java.util.logging.Logger;

import colorsgame.ColorsScene;
import engine.scene.Interactable;
import engine.scene.Scene;

// manage a set of scenes and their transitions
public class Game implements Interactable {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	
	private Scene m_currentScene;
	
	public Game() {
		m_currentScene = new ColorsScene();
	}
	
	public void initialize() {
		m_currentScene.initialize();
	}
	
	public void teardown() {
		m_currentScene.teardown();
	}
	
	public void update(double deltaTime) {
		m_currentScene.update(deltaTime);
	}
	
	public void render(Graphics2D context) {
//		m_currentScene.render(context);
	}
	
	public void render(Graphics2D[] contexts) {
		m_currentScene.render(contexts);
	}
}
