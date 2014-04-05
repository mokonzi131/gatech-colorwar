package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class KeyboardView implements IView, KeyListener {
    private boolean keys[] = new boolean[5];
    
    public KeyboardView() {
    	for (int i = 0; i < keys.length; ++i)
    		keys[i] = false;
    }

	@Override
	public void renderEnvironment(BufferedImage image) {
		// deprecated...
	}

	@Override
	public void renderAgent(BufferedImage image) {
		// deprecated...
	}

	@Override
	public int[] getInput() {
		int[] inputArray = new int[5];
		for (int i = 0; i < keys.length; ++i)
			inputArray[i] = keys[i] ? 1 : 0;
		return inputArray;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignore...
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}
	
	private void toggleKey(int code, boolean isPressed) {
		switch(code) {
		case KeyEvent.VK_LEFT:
			keys[INPUT_WEST] = isPressed;
			break;
		case KeyEvent.VK_RIGHT:
			keys[INPUT_EAST] = isPressed;
			break;
		case KeyEvent.VK_UP:
			keys[INPUT_NORTH] = isPressed;
			break;
		case KeyEvent.VK_DOWN:
			keys[INPUT_SOUTH] = isPressed;
			break;
		case KeyEvent.VK_SPACE:
			keys[INPUT_ACTION] = isPressed;
			break;
		}
	}
}
