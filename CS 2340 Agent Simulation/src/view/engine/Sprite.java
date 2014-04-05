package view.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Sprite {
	public abstract void draw(Graphics2D graphics, int x, int y);
	public abstract Rectangle getBoundingBox();
}
