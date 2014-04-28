package agent.reinforcement;

import java.io.Serializable;
import java.util.Random;

import environment.i.StructuredObserver;

public class BasicSelector implements Selector, Serializable {
	private static final long serialVersionUID = 1L;
	
	Random r = new Random();

	@Override
	public int select(double[] score) {
		double m = 0;
		double a = 1;
		for (int i = 0; i < score.length; i++)
			m += Math.exp(a*score[i]);
		double m0 = m * r.nextDouble();
		for (int i = 0; i < score.length; i++) {
			m -= Math.exp(a*score[i]);
			if (m <= m0)
				return i;
		}
		return 0;
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
