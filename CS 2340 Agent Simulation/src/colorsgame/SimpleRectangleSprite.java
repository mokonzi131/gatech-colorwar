package colorsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.Sprite;

public class SimpleRectangleSprite extends Sprite {
	private static int width = 20;
	private static int height = 20;
	
	private Color m_color;
	private Rectangle m_bounds;
	
	public SimpleRectangleSprite() {
		m_color = Color.WHITE;
		m_color = new Color(255, 255, 255, 100);
		m_bounds = new Rectangle(width, height);
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y) {
		graphics.setColor(m_color);
		graphics.fillRect(x - (m_bounds.width / 2), y - (m_bounds.height / 2), m_bounds.width, m_bounds.height);
	}

	@Override
	public Rectangle getBoundingBox() {
		return m_bounds;
	}
}
