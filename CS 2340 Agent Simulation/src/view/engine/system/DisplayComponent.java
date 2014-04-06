package view.engine.system;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class DisplayComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Dimension m_dimension;
//	private GraphicsConfiguration m_graphicsConfiguration; // for initializing artwork TODO
	private BufferedImage m_image;
	
	public DisplayComponent(int width, int height, InputMap imap) {
		m_dimension = new Dimension(width, height);
		
		if (imap != null)
			this.addKeyListener(imap);
		this.setEnabled(imap != null);
		this.setFocusable(imap != null);
		this.setPreferredSize(m_dimension);
		this.setMinimumSize(m_dimension);
		this.setMaximumSize(m_dimension);
		this.setDoubleBuffered(true);
	}
	
	public BufferedImage getContext() {
		// draw previous image to screen // TODO double buffer this later...
		this.getGraphics().drawImage(m_image, 0, 0, null);
		
		// get and clear graphics from the image
		return m_image;
	}
	
	public Dimension getPreferredSize() {
		return m_dimension;
	}
	
	public void initialize() {
		m_image = new BufferedImage(m_dimension.width, m_dimension.height, BufferedImage.TYPE_INT_ARGB);
	}
}
