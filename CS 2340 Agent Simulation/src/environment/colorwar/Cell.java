package environment.colorwar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import environment.colorwar.sprites.CellSprite;
import view.engine.Actor;

public class Cell extends Actor {
	private static final Color noResource = new Color(255, 255, 255, 100);
	private static final Color yesResource = new Color(0, 255, 0, 100);
	
	float m_posx, m_posy;
	boolean m_resource;
	
	public Cell(float x, float y, boolean resource) {
		super(new CellSprite());
		m_posx = x;
		m_posy = y;
		m_resource = resource;
	}
	
	public float[] coords() { return new float[]{m_posx, m_posy}; }
	public boolean containsResource() { return m_resource; }

	@Override
	public void initialize() {}

	@Override
	public void teardown() {}

	@Override
	public void update(double deltaTime) {
		// TODO nothing...
	}

	@Override
	public void render(Graphics2D context) {
		((CellSprite) m_sprite).setColor(m_resource ? yesResource : noResource);
		m_sprite.draw(context, (int)m_posx, (int)m_posy);

	}

	@Override
	public Point2D location() { return null; }
}
