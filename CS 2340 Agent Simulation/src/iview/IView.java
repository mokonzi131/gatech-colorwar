package iview;

import java.awt.image.BufferedImage;

public interface IView {
	// takes an image from the environment describing the full game
	//  and displays it (using swing)
	public void renderEnvironment(BufferedImage image);
	
	// takes an image from the agent describing their immediate
	//  surroundings and displays it (using swing)
	public void renderAgent(BufferedImage image);
	
	// polls the input mechanism (keyboard for now) and returns an integer
	//  representing a cardinal direction where:
	// 0 = LEFT
	// 1 = UP
	// 2 = RIGHT
	// 3 = DOWN
	public int getInput();
}
