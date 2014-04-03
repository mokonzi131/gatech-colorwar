package colorsgame;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

import engine.scene.Actor;

// game entity that has a controller
public class AgentActor extends Actor {
	private float x, y;
	private float vx, vy;
	
	public AgentActor(float v_x, float v_y) {
		super(new AgentSprite());
		
		Random random = new Random();
		x = random.nextInt(Constants.WORLD_WIDTH);
		y = random.nextInt(Constants.WORLD_HEIGHT);
		vx = v_x;
		vy = v_y;
	}
	
	public void initialize() {}
	public void teardown() {}
	
	public void update(double deltaTime) {
		x += vx * deltaTime;
		y += vy * deltaTime;
		
		if (x > Constants.WORLD_WIDTH) x = 0;
		if (y > Constants.WORLD_HEIGHT) y = 0;
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}

	@Override
	public Point2D location() {
		return new Point2D.Double(x, y);
	}
}
