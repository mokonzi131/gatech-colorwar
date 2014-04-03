package colorsgame;

import java.awt.Graphics2D;

import engine.scene.Actor;

// game entity that has a controller
public class TileActor extends Actor {
	private float x, y;
	
	public TileActor(float posx, float posy) {
		super(new TileSprite());
		
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
