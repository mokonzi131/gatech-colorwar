package agent.reinforcement.neural.test;

import java.util.Random;

public class XorData extends DataFactory {
	
	int m = 4, n = 4;
	
	Random r = new Random();
	
	public XorData(int m0, int n0) {
		m = m0;
		n = n0;
	}
	
	public XorData() {  }
	
	@Override
	public Instance generateInstance() {
		double[] x = new double[m+n];
		double[] y = new double[1];
		boolean c = false;
		for (int i = 0; i < m+n; i++) {
			boolean v = r.nextBoolean();
			if (i < m)
				c ^= v;
			x[i] = v?1:0;
		}
		y[0] = c?1:0;
		return new Instance(x,y);
	}

}
