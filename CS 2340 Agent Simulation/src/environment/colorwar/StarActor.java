package environment.colorwar;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Random;

import environment.colorwar.sprites.StarSprite;
import view.engine.Actor;

// game entity that has a controller
public class StarActor extends Actor {
	private float x, y;
	private float vx, vy;
	
	public StarActor(float v_x, float v_y) {
		super(new StarSprite());
		
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
		
		if (x < 0) x = Constants.WORLD_WIDTH;
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
