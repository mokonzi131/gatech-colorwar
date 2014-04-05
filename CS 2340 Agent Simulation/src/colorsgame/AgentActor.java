package colorsgame;

import iview.IView;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.scene.Actor;

// game entity that has a controller
public class AgentActor extends Actor {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private static final float VELOCITY = 4.0f * Constants.CELL_DISTANCE;
	private AgentController m_controller;
	private Point2D.Float m_position;
	private Point2D.Float m_velocity;
	private Point2D.Float m_destination;
	private float m_checkDistance;
	
	public AgentActor(float x0, float y0, AgentController controller) {
		super(new AgentSprite());
		
		m_position = new Point2D.Float(x0, y0);
		m_velocity = new Point2D.Float(0f, 0f);
		m_controller = controller;
		m_destination = null;
		m_checkDistance = 0f; // TODO improve movement logic that depends on this variable
	}
	
	public void move() {
		AgentController.DIRECTION direction = m_controller.getNextMove();
		m_checkDistance = Constants.CELL_DISTANCE;
		// TODO validate move by requesting new position from the environment;
		switch(direction) {
		case NORTH:
			m_destination = new Point2D.Float(m_position.x, m_position.y - Constants.CELL_DISTANCE);
			m_velocity = new Point2D.Float(0f, -VELOCITY);
			break;
		case EAST:
			m_destination = new Point2D.Float(m_position.x + Constants.CELL_DISTANCE, m_position.y);
			m_velocity = new Point2D.Float(VELOCITY, 0f);
			break;
		case SOUTH:
			m_destination = new Point2D.Float(m_position.x, m_position.y + Constants.CELL_DISTANCE);
			m_velocity = new Point2D.Float(0f, VELOCITY);
			break;
		case WEST:
			m_destination = new Point2D.Float(m_position.x - Constants.CELL_DISTANCE, m_position.y);
			m_velocity = new Point2D.Float(-VELOCITY, 0f);
			break;
		default:
			break;
		}
	}
	
	public void initialize() {}
	public void teardown() {}
	
	public void update(double deltaTime) {
		if (m_destination == null)
			return;
		
		m_position.x = m_position.x + m_velocity.x * (float)deltaTime;
		m_position.y = m_position.y + m_velocity.y * (float)deltaTime;
		
		float currentDistance = (float) m_position.distance(m_destination);
		if (currentDistance > m_checkDistance) {
			m_position = m_destination;
			m_destination = null;
			m_velocity = new Point2D.Float(0f, 0f);
		}
		
		m_checkDistance = currentDistance;
	}
	
	public void render(Graphics2D context) {
		m_sprite.draw(context, (int)m_position.x, (int)m_position.y);
		context.setColor(Color.RED);
		Point2D.Float temp = m_destination == null ? m_position : m_destination;
		context.drawLine((int)m_position.x, (int)m_position.y, (int)temp.x, (int)temp.y);
	}

	@Override
	public Point2D location() {
		return m_position;
	}
}
