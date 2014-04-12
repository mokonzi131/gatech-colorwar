import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import agent.i.Agent;

import environment._2048._2048Environment;
import environment.colorwar.Constants;
import environment.colorwar.GenericGame;
import environment.i.IEnvironment;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Thread
			.currentThread().getStackTrace()[0].getClassName());
	private static final String PROPERTIES_FILENAME = "settings.properties";
	private static final String PROPERTY_RENDERING = "rendering";
	private static final String PROPERTY_AGENTS = "agents";
	private static final String PROPERTY_PLAYABLE = "human";

	public static void main(String[] args) {
		// figure out what kind of rendering stack to use (see
		// settings.properties)
		// set the rest of the configuration properties as well
		Properties properties = new Properties();
		try (InputStream input = Main.class.getResourceAsStream(PROPERTIES_FILENAME)) {
			properties.load(input);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to read properties file: " + PROPERTIES_FILENAME);
			return;
		}

		// set game type
		String property = properties.getProperty(PROPERTY_RENDERING);
		if (property.equals("SIMULATED"))
			Constants.renderingType = Constants.RENDERING_TYPE.SIMULATED;
		else if (property.equals("DEVELOPER"))
			Constants.renderingType = Constants.RENDERING_TYPE.DEVELOPER;

		// set num agents
		// TODO hide behind environment interface
		Constants.numAgents =
				Math.max(Integer.parseInt(properties.getProperty(PROPERTY_AGENTS)), Constants.MINIMUM_NUM_AGENTS);
		Constants.numAgents = Math.min(Constants.numAgents, Constants.MAXIMUM_NUM_AGENTS);

		// set human playable
		property = properties.getProperty(PROPERTY_PLAYABLE);
		if (property.equals("true"))
			Constants.isHumanPlayable = true;
		else
			Constants.isHumanPlayable = false;

		// create game
		GenericGame game = new GenericGame();
		new Thread(game, "Generic Game Thread").start();
	}
	
	public void simulate2048(Agent a) {
		_2048Environment e = new _2048Environment(a,4,4,20);
		System.out.println(simulate(e)[0]);
	}
	
	public double[] simulate(IEnvironment e) {
		e.reset();
		while (!e.isEnd())
			e.update(1.0);
		return e.score();
	}
}
