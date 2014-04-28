package agent.reinforcement.neural.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.reinforcement.Regressor;
import agent.reinforcement.neural.NeuralRegressor;

public class RegressorTest {
	
	Regressor r;
	DataFactory d;

	@Before
	public void setUp() throws Exception {
		int i = 4, o = 1, z = 1, k = 100000;
		int[] h = new int[] { i, i+o, o };
		r = new NeuralRegressor(h,.1,0,0);
//		d = new IdentityData(m,n,1,1,false);
		d = new XorData(i-z,z);
		DataFactory.train(r,d,k);
	}

	@Test
	public void testSquareError() {
		int k = 1000;
		double err = DataFactory.test(r,d,k);
		System.out.println("Error: "+err);
	}

}
