package environment._2048;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import agent.i.Agent;
import model.Model;
import environment.i.IEnvironment;

public class _2048Environment implements IEnvironment {
	
	private Model game;
	private Agent agent;
	private int m, n;
	
	public _2048Environment(Agent a, int m, int n) {
		agent = a;
		game = new Model(m, "");
		this.m = m;
		this.n = m;
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
	public void update() {
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
		//TODO
	}

}
