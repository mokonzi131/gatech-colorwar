package colorsgame;

import java.awt.Graphics2D;
import java.util.Random;

import engine.scene.Actor;

// game entity that has a controller
public class StarActor extends Actor {
	private float x, y;
	private float vx, vy;
	
	public StarActor(float v_x, float v_y) {
		super(new StarSprite());
		
		Random random = new Random();
		x = random.nextInt(Constants.DEVELOPER_VIEW_WIDTH);
		y = random.nextInt(Constants.DEVELOPER_VIEW_HEIGHT);
		vx = v_x;
		vy = v_y;
	}
	
	public void initialize() {}
	public void teardown() {}
	
	public void update(double deltaTime) {
		x += vx * deltaTime;
		y += vy * deltaTime;
		
		if (x < 0) x = Constants.DEVELOPER_VIEW_WIDTH;
		if (y > Constants.DEVELOPER_VIEW_HEIGHT) y = 0;
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}
}
