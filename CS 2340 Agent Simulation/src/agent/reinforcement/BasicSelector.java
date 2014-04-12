package agent.reinforcement;

import java.util.Random;

import environment.i.StructuredObserver;

public class BasicSelector implements Selector {
	
	Random r = new Random();
	
	@Override
	public int select(double[] score) {
		if (r.nextInt(10) == 0) 
			return r.nextInt(score.length);
		else {
			double m = Double.NEGATIVE_INFINITY;
			int m0 = -1;
			for (int i = 0; i < score.length; i++) {
				double s = score[i];
				if (s > m) {
					m = s;
					m0 = i;
				}
			}
			return m0;
		}
	}

	@Override
	public void reward(int a, double r) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setObserver(StructuredObserver o) {
		// TODO Auto-generated method stub
		
	}

}
