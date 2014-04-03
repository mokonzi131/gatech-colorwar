package engine.scene;

import java.awt.geom.Point2D;

import engine.rendering.Sprite;

// game entity that has a controller (human or AI)
public abstract class Actor implements Interactable {
	protected Sprite m_sprite;
	
	public Actor(Sprite sprite) {
		m_sprite = sprite;
	}
	
	public abstract Point2D location();
}
