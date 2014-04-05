import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.engine.Engine;
import view.engine.Game;
import view.engine.Engine.RENDERING_TYPE;
import environment.colorwar.Constants;

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
		try (InputStream input = Main.class
				.getResourceAsStream(PROPERTIES_FILENAME)) {
			properties.load(input);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to read properties file: "
					+ PROPERTIES_FILENAME);
			return;
		}

		// set game type
		String property = properties.getProperty(PROPERTY_RENDERING);
		if (property.equals("SIMULATED"))
			Constants.renderingType = RENDERING_TYPE.SIMULATED;
		else if (property.equals("DEVELOPER"))
			Constants.renderingType = RENDERING_TYPE.DEVELOPER;

		// set num agents
		Constants.numAgents =
				Math.max(Integer.parseInt(properties.getProperty(PROPERTY_AGENTS)), Constants.MINIMUM_NUM_AGENTS);

		// set human playable
		property = properties.getProperty(PROPERTY_PLAYABLE);
		if (property.equals("true"))
			Constants.isHumanPlayable = true;
		else
			Constants.isHumanPlayable = false;

		// create game
		Game game = new Game();

		// run engine on game
		Engine engine = new Engine(game);
		new Thread(engine, "Game Engine").start();
	}
}
