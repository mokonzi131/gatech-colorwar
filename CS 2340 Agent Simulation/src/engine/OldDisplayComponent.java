package engine;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

//import dk.itu.mario.level.Level;
//import dk.itu.mario.scene.LevelScene;
//import dk.itu.mario.scene.LevelSceneTest;
//import dk.itu.mario.scene.LoseScene;
//import dk.itu.mario.scene.Scene;
//import dk.itu.mario.scene.WinScene;
//
//import dk.itu.mario.engine.sonar.FakeSoundEngine;
//import dk.itu.mario.engine.sonar.SonarSoundEngine;
//import dk.itu.mario.engine.sprites.Mario;

public class OldDisplayComponent extends JComponent implements Runnable,
		KeyListener, FocusListener, MouseListener {
	private static final long serialVersionUID = 739318775993206607L;

	public static final int TICKS_PER_SECOND = 24;

	public static final int EVOLVE_VERSION = 4;
	public static final int GAME_VERSION = 4;

	private boolean running = false;
	private int width, height;
	private GraphicsConfiguration graphicsConfiguration;
	// private Scene scene;
	// private SonarSoundEngine sound;
	private boolean focused = false;
	private boolean useScale2x = false;

	// private Scale2x scale2x = new Scale2x(320, 240);

	private double openTime;

	public OldDisplayComponent(int width, int height) {
		addFocusListener(this);
		addMouseListener(this);
		addKeyListener(this);

		this.setFocusable(true);
		this.setEnabled(true);
		this.width = width;
		this.height = height;

		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		// try
		// {
		// sound = new SonarSoundEngine(64);
		// }
		// catch (LineUnavailableException e)
		// {
		// e.printStackTrace();
		// sound = new FakeSoundEngine();
		// }
		this.setFocusable(true);

		// LevelScene.bothPlayed = false;

		openTime = System.nanoTime();
	}

	private void toggleKey(int keyCode, boolean isPressed) {
		// if (keyCode == KeyEvent.VK_LEFT)
		// {
		// scene.toggleKey(Mario.KEY_LEFT, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_RIGHT)
		// {
		// scene.toggleKey(Mario.KEY_RIGHT, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_DOWN)
		// {
		// scene.toggleKey(Mario.KEY_DOWN, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_UP)
		// {
		// scene.toggleKey(Mario.KEY_UP, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_A)
		// {
		// scene.toggleKey(Mario.KEY_SPEED, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_S)
		// {
		// scene.toggleKey(Mario.KEY_JUMP, isPressed);
		// }
		// if (keyCode == KeyEvent.VK_ENTER)
		// {
		// scene.toggleKey(Mario.KEY_ENTER, isPressed);
		// }
		if (isPressed && keyCode == KeyEvent.VK_F1) {
			useScale2x = !useScale2x;
		}

		if (isPressed && keyCode == KeyEvent.VK_ESCAPE) {
			try {
				System.exit(1);
			} catch (Exception e) {
				System.out.println("Unable to exit.");
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
	}

	public void update(Graphics g) {
	}

	public void stop() {
//		 Art.stopMusic();
	}

	public void run() {

		graphicsConfiguration = getGraphicsConfiguration();

		// Art.init(graphicsConfiguration, sound);
		
		toRandomGame();

		while (running) {
			// sound.clientTick(alpha);

			// scene.render(og, alpha);
		}

		// Art.stopMusic();
	}

	private void drawString(Graphics g, String text, int x, int y, int c) {
		char[] ch = text.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			// g.drawImage(Art.font[ch[i] - 32][c], x + i * 8, y, null);
		}
	}

	public void keyPressed(KeyEvent arg0) {
		toggleKey(arg0.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent arg0) {
		toggleKey(arg0.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void focusGained(FocusEvent arg0) {
		focused = true;
	}

	public void focusLost(FocusEvent arg0) {
		focused = false;
	}

	public void levelWon() {

	}

	public static final int OPTIMIZED_FIRST = 0;
	public static final int MINIMIZED_FIRST = 1;

	// private LevelScene randomLevel;

	/**
	 * Part of the fun increaser
	 */
	public void toRandomGame() {
		// randomLevel = new LevelSceneTest(graphicsConfiguration,this,new
		// Random().nextLong(),0,0,false);
		//
		// Mario.fire = false;
		// Mario.large = false;
		// Mario.coins = 0;
		// Mario.lives = 3;
		//
		// randomLevel.init();
		// randomLevel.setSound(sound);
		// scene = randomLevel;

	}

	public void toCustomGame() {

		// randomLevel = new LevelSceneTest(graphicsConfiguration,this,new
		// Random().nextLong(),0,0,true);
		//
		// Mario.fire = false;
		// Mario.large = false;
		// Mario.coins = 0;
		// Mario.lives = 3;
		//
		// randomLevel.init();
		// randomLevel.setSound(sound);
		// scene = randomLevel;

	}

	public void lose() {
		// scene = new LoseScene();
		// scene.setSound(sound);
		// scene.init();
	}

	public void win() {
		// scene = new WinScene();
		// scene.setSound(sound);
		// scene.init();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {

		while (!hasFocus()) {
			System.out.println("FORCE IT");
			requestFocus();
		}
	}
}
