package colorsgame;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.Actor;

// game entity that has a controller
public class TileActor extends Actor {
	private static final Color noResource = new Color(255, 255, 255, 100);
	private static final Color yesResource = new Color(0, 255, 0, 100);
	private float x, y;
	
	public TileActor(float posx, float posy, boolean hasResource) {
		super(new TileSprite());
		((TileSprite)m_sprite).setColor(hasResource ? yesResource : noResource);
		
		x = posx;
		y = posy;
	}
	
	public void initialize() {}
	public void teardown() {}
	
	public void update(double deltaTime) {}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}
}
