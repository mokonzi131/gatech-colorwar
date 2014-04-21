package agent.reinforcement.neural.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import agent.reinforcement.neural.NeuralRegressor;

public class NeuralRegressorTest {
	
	private NeuralRegressor nr;
	private Random r;

	public NeuralRegressorTest() {
	}

	@Before
	public void setUp() throws Exception {
		r = new Random();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFit() {
		int[] h = new int[]{4,4};
		nr = new NeuralRegressor(.2,0,.1,h);
		double[] input = new double[4];
		for (int i = 0; i < 4*4*100; i++) {
			for (int j = 0; j < input.length; j++)
				input[j] = r.nextInt(2);
			int a = r.nextInt(input.length);
			nr.fit(input, a, input[a]);
		}
		System.out.println(nr.toString());
		for (int i = 0; i < 4*4*10; i++) {
			for (int j = 0; j < input.length; j++)
				input[j] = r.nextInt(2);
			double[] score = nr.predict(input);
			for (int j = 0; j < input.length; j++)
				if (input[j] > .5 ^ score[j] > .5)
					fail(Arrays.toString(input) + " : " + Arrays.toString(score));
		}
	}
	
	@Test
	public void testFitLogic() {
		int s = 3;
		int[] h = new int[]{s*s,1};
		nr = new NeuralRegressor(.2,0,.1,h);
		double[] input = new double[s*s];
		for (int i = 0; i < s*s*1000; i++) {
			boolean k = true;
			for (int j = 0; j < input.length; j++) {
				input[j] = r.nextInt(2);
				if (j%s==0)
					k &= input[j]>.5;
			}
			nr.fit(input, 0, k?1:0);
		}
		System.out.println(nr.toString());
		for (int i = 0; i < s*s*10; i++) {
			boolean k = true;
			for (int j = 0; j < input.length; j++) {
				input[j] = r.nextInt(2);
				if (j%s==0)
					k &= input[j]>.5;
			}
			double score = nr.predict(input)[0];
			if (score > .5 ^ k)
				fail(Arrays.toString(input) + " : " + score);
		}
	}
	
	@Test
	public void testFitAdvanced() {
		int[] h = new int[]{4,5,1};
		nr = new NeuralRegressor(.2,0,.1,h);
		double[] input = new double[4];
		for (int i = 0; i < 4*4*1000; i++) {
			boolean k = false;
			for (int j = 0; j < input.length; j++) {
				input[j] = r.nextInt(2);
				k ^= input[j]>.5;
			}
			nr.fit(input, 0, k?1:0);
		}
		System.out.println(nr.toString());
		int I = 4*4*10;
		int error = 0;
		for (int i = 0; i < I; i++) {
			boolean k = false;
			for (int j = 0; j < input.length; j++) {
				input[j] = r.nextInt(2);
				k ^= input[j]>.5;
			}
			double score = nr.predict(input)[0];
			if (score > .5 ^ k)
				//fail(Arrays.toString(input) + " : " + score);
				error++;
		}
		double score = ((double) error) / I;
		if (score < .8)
			fail(""+score);
	}

}
