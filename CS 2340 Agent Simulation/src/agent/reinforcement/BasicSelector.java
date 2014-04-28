package agent.reinforcement;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import environment.i.StructuredObserver;

public class BasicSelector implements Selector, Serializable {
	private static final long serialVersionUID = 1L;
	
	Random r = new Random();
	
	double a = 1.e-1, g = 1.0005;

	@Override
	public int select(double[] score) {
		double m = 0;
		a *= g;
//		System.out.println(a);
//		System.out.println(Arrays.toString(score));
		double max = Double.NEGATIVE_INFINITY;
		for (double s : score)
			if (s > max)
				max = s;
		for (double s : score)
			m += Math.exp(a*(s-max));
		double m0 = m * r.nextDouble();
		for (int i = 0; i < score.length; i++) {
			m -= Math.exp(a*(score[i]-max));
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
