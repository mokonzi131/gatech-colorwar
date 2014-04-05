package view.engine;

import java.awt.geom.Point2D;

// game entity that has a controller (human or AI)
public abstract class Actor implements Interactable {
	protected Sprite m_sprite;
	
	public Actor(Sprite sprite) {
		m_sprite = sprite;
	}
	
	public abstract Point2D location();
}
