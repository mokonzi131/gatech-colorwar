package engine.scene;

import java.awt.image.BufferedImage;

public abstract class Scene {
	public abstract void initialize();
	
	public abstract void teardown();
	
	public abstract void update(double deltaTime);
	
	public abstract void render(BufferedImage[] images);
}
