package environment.colorwar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import view.engine.Sprite;

public class StarSprite extends Sprite {
	private Color m_color;
	private Rectangle m_bounds;
	
	public StarSprite() {
		Random random = new Random();
		int diameter = random.nextInt(5) + 3;
		m_color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 255);
		m_bounds = new Rectangle(diameter, diameter);
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y) {
		graphics.setColor(m_color);
		graphics.fillOval(x - (m_bounds.width / 2), y - (m_bounds.height / 2), m_bounds.width, m_bounds.height);
	}

	@Override
	public Rectangle getBoundingBox() {
		return m_bounds;
	}
}
