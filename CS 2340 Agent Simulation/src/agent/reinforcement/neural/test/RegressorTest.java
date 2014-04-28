package agent.reinforcement.neural.test;

import org.junit.Before;
import org.junit.Test;

import agent.reinforcement.Regressor;
import agent.reinforcement.neural.NeuralRegressor;

public class RegressorTest {
	
	Regressor r;
	DataFactory d;

	@Before
	public void setUp() throws Exception {
		int i = (4*1+1)*3, o = 4, n = i+o, k = 100;
		int[] h = new int[] { i, i+o, o };
		r = new NeuralRegressor(h,1.0/n,0,0);
		d = new IdentityData(o,i-o,1,1,false);
//		d = new XorData(i-z,z);
		DataFactory.train(r,d,k);
	}

	@Test
	public void testSquareError() {
		int k = 1000;
		double err = DataFactory.test(r,d,k);
		System.out.println("Error: "+err);
	}

}
