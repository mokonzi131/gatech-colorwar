package view.engine.system;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputMap implements KeyListener {
	private static final int KEY_LEFT = 0;
	private static final int KEY_RIGHT = 1;
	private static final int KEY_UP = 2;
	private static final int KEY_DOWN = 3;
	private static final int KEY_ACTION = 4;
	private static final int NUM_KEYS = 5;

	private boolean[] m_keys;
    
    public InputMap() {
    	m_keys = new boolean[NUM_KEYS];
    	for (int i = 0; i < NUM_KEYS; ++i)
    		m_keys[i] = false;
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
			m_keys[KEY_LEFT] = isPressed;
			break;
		case KeyEvent.VK_RIGHT:
			m_keys[KEY_RIGHT] = isPressed;
			break;
		case KeyEvent.VK_UP:
			m_keys[KEY_UP] = isPressed;
			break;
		case KeyEvent.VK_DOWN:
			m_keys[KEY_DOWN] = isPressed;
			break;
		case KeyEvent.VK_SPACE:
			m_keys[KEY_ACTION] = isPressed;
			break;
		}
	}
}
