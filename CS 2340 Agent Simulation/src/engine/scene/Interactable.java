package engine.scene;

import java.awt.Graphics2D;

public interface Interactable {
	public void initialize();
	public void teardown();
	
	public void update(double deltaTime);
	public void render(Graphics2D context);
}
