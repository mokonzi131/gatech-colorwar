package colorsgame;

import iview.IView;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import engine.scene.Actor;

// game entity that has a controller
public class AgentActor extends Actor {
	private float x, y;
	private IView m_controller;
	
	public AgentActor(float x0, float y0) {
		super(new AgentSprite());
		
		x = x0;
		y = y0;
		m_controller = null;
	}
	
	public void setController(IView controller) {
		m_controller = controller;
	}
	
	public void initialize() {}
	public void teardown() {}
	
	public void update(double deltaTime) {
		if (m_controller != null) {
			int[] input = m_controller.getInput();
			if (input[IView.INPUT_EAST] == 1)
				x += Constants.CELL_DISTANCE;
			if (input[IView.INPUT_WEST] == 1)
				x -= Constants.CELL_DISTANCE;
			if (input[IView.INPUT_NORTH] == 1)
				y -= Constants.CELL_DISTANCE;
			if (input[IView.INPUT_SOUTH] == 1)
				y += Constants.CELL_DISTANCE;
		}
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)x, (int)y);
	}

	@Override
	public Point2D location() {
		return new Point2D.Double(x, y);
	}
}
