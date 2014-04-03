package colorsgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Random;

import engine.Sprite;

public class TileSprite extends Sprite {
	private static final int TILE_SIZE = Constants.CELL_DISTANCE;
//	private static final Stroke stroke = new BasicStroke(2.0f);
//	private static final Color borderColor = new Color(255, 255, 255, 100);
	
	private Color m_color;
	private Rectangle m_bounds;
	
	public TileSprite() {
		Random random = new Random();
		m_color = new Color(255, 255, 255, 100);
		if (random.nextInt(10) > 3)
			m_color = new Color(0, 255, 0, 100);
		m_bounds = new Rectangle(TILE_SIZE, TILE_SIZE);
	}
	
	@Override
	public void draw(Graphics2D graphics, int x, int y) {
//		Stroke backup = graphics.getStroke();
//		graphics.setStroke(stroke);
//		graphics.setColor(Color.WHITE);
//		graphics.drawRect(x - (m_bounds.width / 2), y - (m_bounds.height / 2), m_bounds.width, m_bounds.height);
//		graphics.setStroke(backup);
		
		graphics.setColor(m_color);
		graphics.fillRect(x - (m_bounds.width / 2) + 2, y - (m_bounds.height / 2) + 2,
				m_bounds.width - 2, m_bounds.height - 2);
	}

	@Override
	public Rectangle getBoundingBox() {
		return m_bounds;
	}
}
