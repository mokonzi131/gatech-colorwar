package colorsgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.rendering.Sprite;

public class TileSprite extends Sprite {
	private static final int TILE_SIZE = Constants.CELL_DISTANCE;
	
	private Color m_color;
	private Rectangle m_bounds;
	
	public TileSprite() {
		m_color = Color.WHITE;
		m_bounds = new Rectangle(TILE_SIZE, TILE_SIZE);
	}
	
	public void setColor(Color color) {
		m_color = color;
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y) {
		graphics.setColor(m_color);
		graphics.fillRect(x - (m_bounds.width / 2) + 2, y - (m_bounds.height / 2) + 2,
				m_bounds.width - 2, m_bounds.height - 2);
	}

	@Override
	public Rectangle getBoundingBox() {
		return m_bounds;
	}
}
