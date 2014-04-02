import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import engine.Engine;
import engine.Game;
import engine.Engine.RENDERING_TYPE;

public class Main {
	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
	private static final String PROPERTIES_FILENAME = "settings.properties";
	
	public static void main(String[] args) {
		// figure out what kind of rendering stack to use (see settings.properties)
		// set the rest of the configuration properties as well
		RENDERING_TYPE renderingType = RENDERING_TYPE.NORMAL;
		int numberAgents = 5;
		boolean humanPlayer = false;
		
		Properties properties = new Properties();
		try(InputStream input = Main.class.getResourceAsStream(PROPERTIES_FILENAME)) {
			properties.load(input);
			
			String property = properties.getProperty("rendering");
			
			if (property.equals("SIMULATED"))
				renderingType = RENDERING_TYPE.SIMULATED;
			else if (property.equals("DEVELOPER"))
				renderingType = RENDERING_TYPE.DEVELOPER;
			
			numberAgents = Integer.parseInt(properties.getProperty("agents"));
			
			if (properties.getProperty("human").equals("true"))
				humanPlayer = true;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failed to read properties file: " + PROPERTIES_FILENAME);
		}
		
		// create game
		Game game = new Game(numberAgents, humanPlayer);
		
		// run engine on game
		Engine engine = new Engine(game, renderingType);
		new Thread(engine, "Game Engine").start();
	}
}
