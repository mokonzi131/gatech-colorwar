package environment._2048;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import agent.i.Agent;
import model.Model;
import environment.i.IEnvironment;

public class _2048Environment implements IEnvironment {
	
	private Model game;
	private Agent agent;
	private int m, n;
	private int gridSize;
	private double dt0 = 0;
	
	public _2048Environment(Agent a, int m, int n, int g) {
		agent = a;
		agent.setObserver(this);
		game = new Model(m, "");
		this.m = m;
		this.n = m;
		gridSize = g;
	}
	
	@Override
	public double[][][] observeStructure(int a) {
		double[][][] state = new double[m][n][1];
		int[][] grid = game.getGrid();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				state[i][j][0] = grid[i][j];
		return state;
	}

	@Override
	public double[] observe(int a) {
		double[] state = new double[m*n];
		int[][] grid = game.getGrid();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				state[n*i+j] = grid[i][j];
		return state;
	}

	@Override
	public int actionRange(int a) {
		return 4;
	}

	@Override
	public void update(double dt) {
		dt0 += dt;
		while (--dt0 < 1)
			move();
	}
	
	public void move() {
		int move = agent.move(0);
		int score = game.getScore();
		game.move(move);
		int reward = game.getScore() - score;
		agent.reward(0, reward);
	}

	@Override
	public boolean isEnd() {
		int[][] grid = game.getGrid();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				int k = grid[i][j];
				if (k == 0 || i < m-1 && k == grid[i+1][j] || j < n-1 && k == grid[i][j+1])
					return true;
			}
		return false;
	}

	@Override
	public void reset() {
		game.reset();
	}
	
	@Override
	public double[] score() {
		return new double[] { game.getScore() };
	}

	@Override
	public void render(Graphics2D g) {
		int[][] grid = game.getGrid();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				int k = grid[i][j];
				if (k > 0) {
					char c = (char) ('0' + k);
					if (k >= 10)
						c = (char) ('A' + k - 10);
					g.drawString(""+c, gridSize*i, gridSize*j);
				}
			}
	}

	@Override
	public Dimension dim() {
		return new Dimension(m*gridSize, n*gridSize);
	}

}
