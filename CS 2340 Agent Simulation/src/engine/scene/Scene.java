package engine.scene;

import iview.IView;

import java.awt.image.BufferedImage;

public abstract class Scene {
	public abstract void initialize();
	
	public abstract void teardown();
	
	public abstract void update(double deltaTime);
	
	public abstract void render(BufferedImage[] images);

	public abstract void setController(IView controller);
}
