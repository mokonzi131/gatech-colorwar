package view;

import java.awt.image.BufferedImage;

public interface IView {
	public static final int INPUT_WEST = 0;
	public static final int INPUT_EAST = 1;
	public static final int INPUT_SOUTH = 2;
	public static final int INPUT_NORTH = 3;
	public static final int INPUT_ACTION = 4;

	/**
	 * @deprecated
	 * Displays an environment image to the screen (using swing)
	 * @param image describing the full game environment
	 */
	public void renderEnvironment(BufferedImage image);
	
	/**
	 * @deprecated
	 * Displays and agent image to the screen (using swing)
	 * @param image describing the immediate environment of an agent
	 */
	public void renderAgent(BufferedImage image);
	
	/**
	 * @return integer array of size 5. The current state of the input
	 *  device as an array if integers as defined by IView.INPUT_... variables.
	 *  In the array, 1 = pressed and 0 = not pressed.
	 */
	public int[] getInput();
}
