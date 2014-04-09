package environment.colorwar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.engine.Scene;
import view.engine.system.Display;

public class SplashScene extends Scene {
	private Display m_display;
	private double m_timer;
	
	public SplashScene() {
		m_display = null;
	}

	@Override
	public void initialize() {
		finished = false;
		m_display = new Display(Constants.DEV_VIEW_WIDTH, Constants.DEV_VIEW_HEIGHT);
		m_display.initialize(null);
	}

	@Override
	public void update(double deltaTime) {
		m_timer += deltaTime;
		if (m_timer > 10.0)
			finished = true;
	}

	@Override
	public void render() {
		BufferedImage context = m_display.getContext();
		Graphics2D graphics = context.createGraphics();
		graphics.setColor(Color.BLUE);
		graphics.fillRect(0, 0, context.getWidth(), context.getHeight());
	}
}
