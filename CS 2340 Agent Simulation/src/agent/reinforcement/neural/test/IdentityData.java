package agent.reinforcement.neural.test;

import java.util.Random;

public class IdentityData extends DataFactory {
	
	int m = 4, n = 4;
	double xr = 1, yr = 1;
	boolean p = false;
	
	Random r = new Random();
	
	public IdentityData(int m0, int n0, double xr0, double yr0, boolean p0) {
		m = m0;
		n = n0;
		xr = xr0;
		yr = yr0;
		p = p0;
	}
	
	public IdentityData() {  }
	
	@Override
	public Instance generateInstance() {
		double[] x = new double[m+n];
		double[] y = new double[m];
		for (int i = 0; i < m+n; i++) {
			double v = r.nextDouble();
			if (p && r.nextBoolean())
				v = -v;
			if (i < m)
				x[i] = y[i] = yr * v;
			else
				x[i] = xr * v;
		}
		return new Instance(x,y);
	}

}
