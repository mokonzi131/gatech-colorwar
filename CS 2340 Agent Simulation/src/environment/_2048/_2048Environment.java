package environment._2048;

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
	public void turn() {
		int move = agent.move(0);
		int score = game.getScore();
		game.move(move);
		int reward = game.getScore() - score;
		agent.reward(0, reward);
	}

	@Override
	@Deprecated
	public BufferedImage total() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public BufferedImage human() {
		// TODO Auto-generated method stub
		return null;
	}

}
