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
		while (dt0 > 1) 
			move();
	}
	
	public void move() {
		int move = agent.move(0);
		int score = game.getScore();
//		int[][] grid0 = game.getGrid();
		game.move(move);
//		int[][] grid1 = game.getGrid();
//		boolean change = false;
//		for (int i = 0; i < m; i++)
//			for (int j = 0; j < n; j++)
//				change |= grid0[i][j] != grid1[i][j];
		agent.reward(0, game.getScore() - score);
		dt0--;
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
	public void render(BufferedImage target) {
		Graphics2D g = target.createGraphics();
		
		int[][] grid = game.getGrid();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				int k = grid[i][j];
				if (k > 0) {
					char c = (char) ('0' + k);
					if (k >= 10)
						c = (char) ('A' + k - 10);
					g.drawString(""+c, gridSize*i, gridSize*(j+1));
				}
			}
	}

	@Override
	public Dimension dim() {
		return new Dimension(m*gridSize, n*gridSize);
	}

	@Override
	public Dimension dim(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renderAgentFromWorld(int i, BufferedImage world, BufferedImage target) {
		// TODO Auto-generated method stub
	}

}
