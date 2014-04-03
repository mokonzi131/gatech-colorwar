package engine.scene;

import java.awt.Color;
import java.awt.Graphics2D;

import colorsgame.SimpleRectangleSprite;

// game entity that moves (has a controller)
public class Actor implements Interactable {
	private float x,y;
	private float vx, vy;
	
	private SimpleRectangleSprite m_sprite;
	
	public Actor() {
		x = 0f;
		y = 0f;
		vx = 20f;
		vy = 10f;
		
		m_sprite = new SimpleRectangleSprite();
	}
	
	public void initialize() {
//		m_sprite.initialize();
	}
	
	public void teardown() {
//		m_sprite.teardown();
	}
	
	public void update(double deltaTime) {
		x += vx * deltaTime;
		y += vy * deltaTime;
		
//		m_sprite.update(deltaTime);
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}
}
