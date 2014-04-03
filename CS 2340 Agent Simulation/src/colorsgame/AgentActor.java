package colorsgame;

import java.awt.Graphics2D;
import java.util.Random;

import engine.scene.Actor;

// game entity that has a controller
public class AgentActor extends Actor {
	private float x, y;
	private float vx, vy;
	
	public AgentActor(float v_x, float v_y) {
		super(new AgentSprite());
		
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
		
		if (x > Constants.DEVELOPER_VIEW_WIDTH) x = 0;
		if (y > Constants.DEVELOPER_VIEW_HEIGHT) y = 0;
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}
}
