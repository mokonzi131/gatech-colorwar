package view.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import view.IView;
import environment.colorwar.ColorsScene;

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
	
	public void render(BufferedImage[] images) {
		m_currentScene.render(images);
	}
	
	public void setController(IView controller) {
		m_currentScene.setController(controller);
	}
}
