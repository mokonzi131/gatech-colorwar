package iview;

import java.awt.image.BufferedImage;

public interface IView {
	/**
	 * Displays an environment image to the screen (using swing)
	 * @param image describing the full game environment
	 */
	public void renderEnvironment(BufferedImage image);
	
	/**
	 * Displays and agent image to the screen (using swing)
	 * @param image describing the immediate environment of an agent
	 */
	public void renderAgent(BufferedImage image);
	
	/**
	 * Polls the input device (keyboard for now) for a direction
	 * @return integer representing a cardinal direction where
	 *  0 = LEFT,
	 *  1 = UP,
	 *  2 = RIGHT,
	 *  3 = DOWN
	 */
	public int getInput();
}
