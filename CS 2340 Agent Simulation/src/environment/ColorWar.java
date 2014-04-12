package environment;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import environment.i.IEnvironment;

import agent.i.Agent;

public class ColorWar implements IEnvironment {

	private int gameSize=10; 
	Random r= new Random();
	Point[] location;
	int n;
	ColorWar(Agent[] a){
		n=a.length;
		location= new Point [n];
		for (int i=0; i< n; i++){
			int x= r.nextInt(gameSize);
			int y= r.nextInt(gameSize);
			Point p = new Point(x,y);
			setAgentLocation(i, p);

		}
	}
	
	public void setAgentLocation(int a, Point p){
		location[a] = p;  
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public double[][][] observeStructure(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] observe(int a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actionRange(int a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage total() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage human() {
		// TODO Auto-generated method stub
		return null;
	}

}
