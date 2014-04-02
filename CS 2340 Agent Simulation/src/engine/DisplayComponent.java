package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

public class DisplayComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	
	private Dimension m_dimension;
//	private GraphicsConfiguration m_graphicsConfiguration; // for initializing artwork TODO
	private boolean m_isInteractive;
	private BufferedImage m_image;
	
	public DisplayComponent(int width, int height, boolean interactive) {
		m_dimension = new Dimension(width, height);
		m_isInteractive = interactive;
		
		this.setFocusable(m_isInteractive);
		this.setEnabled(m_isInteractive);
		
		this.setPreferredSize(m_dimension);
		this.setMinimumSize(m_dimension);
		this.setMaximumSize(m_dimension);
	}
	
	public Graphics2D getContext() {
		// draw previous image to screen // TODO double buffer this later...
		this.getGraphics().drawImage(m_image, 0, 0, null);
		
		// get and clear graphics from the image
		Graphics2D graphics = (Graphics2D) m_image.getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, m_dimension.width, m_dimension.height);
		
		return graphics;
	}
	
	public Dimension getPreferredSize() {
		return m_dimension;
	}
	
	public void initialize() {
		m_image = new BufferedImage(m_dimension.width, m_dimension.height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void teardown() {
		// TODO
	}
}
