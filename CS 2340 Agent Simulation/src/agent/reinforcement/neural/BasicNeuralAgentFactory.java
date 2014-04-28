package agent.reinforcement.neural;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import agent.reinforcement.BasicLearner;
import agent.reinforcement.BasicSelector;
import agent.reinforcement.Learner;
import agent.reinforcement.Regressor;
import agent.reinforcement.ReinforcementAgent;
import agent.reinforcement.Selector;

public class BasicNeuralAgentFactory {
	private static final Logger LOGGER = Logger.getLogger(BasicNeuralAgentFactory.class.getName());

	public static ReinforcementAgent generateAgent(int in, int out) {
		Regressor r = new NeuralRegressor(new int[] { in, out }, .01/(in+out), 0, 0);
		// Regressor r = new NeuralRegressorAdapter(new int[] { in, out });
		Learner l = new BasicLearner(r, .95);
		Selector s = new BasicSelector();
		return new ReinforcementAgent(l, s);
	}

	public static ReinforcementAgent loadAgent(String f) throws Exception {
		ReinforcementAgent e = null;
		
		FileInputStream fileIn = new FileInputStream(f);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		e = (ReinforcementAgent) in.readObject();
		in.close();
		fileIn.close();
		
		return e;
	}

	public static void saveAgent(ReinforcementAgent e, String f) {
		try
		{
			FileOutputStream fileOut = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
			out.close();
			fileOut.close();
			LOGGER.info("Serialized data is saved in " + f);
		} catch (IOException i)
		{
			i.printStackTrace();
		}
	}

}
