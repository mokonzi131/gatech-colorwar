package environment.colorwar.sprites;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import environment.colorwar.Constants;
import view.engine.Sprite;

public class AgentSprite extends Sprite {
	private static final int AGENT_RADIUS = Constants.CELL_DISTANCE / 2 - 4;
	private static final Stroke stroke = new BasicStroke(2.0f);
	private static final Color ringColor = new Color(255, 255, 0, 255);
	
	private Color m_color;
	private Rectangle m_bounds;
	
	public AgentSprite() {
		m_color = new Color(0, 0, 255, 50);
		m_bounds = new Rectangle(AGENT_RADIUS * 2, AGENT_RADIUS * 2);
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y) {
		graphics.setColor(m_color);
		graphics.fillOval(x - (m_bounds.width / 2) + 2, y - (m_bounds.height / 2) + 2,
				m_bounds.width - 2, m_bounds.height - 2);
		
		Stroke backup = graphics.getStroke();
		graphics.setStroke(stroke);
		graphics.setColor(ringColor);
		graphics.drawOval(x - (m_bounds.width / 2) + 2, y - (m_bounds.height / 2) + 2,
				m_bounds.width - 2, m_bounds.height - 2);
		graphics.setStroke(backup);
	}

	@Override
	public Rectangle getBoundingBox() {
		return m_bounds;
	}
}
