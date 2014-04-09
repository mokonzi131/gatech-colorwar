package view.engine;

public abstract class Scene {
	public boolean finished;
	
	public abstract void initialize();
	
	public abstract void update(double deltaTime);
	
	public abstract void render();
}
