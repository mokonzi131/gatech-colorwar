package view.engine;

import java.awt.Graphics2D;

public interface IViewable {
	public void update(double deltaTime);
	public void render(Graphics2D context);
}
