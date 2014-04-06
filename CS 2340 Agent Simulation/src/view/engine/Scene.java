package view.engine;

public interface Scene {
	public abstract void initialize();
	
	public abstract void update(double deltaTime);
	
	public abstract void render();
}
