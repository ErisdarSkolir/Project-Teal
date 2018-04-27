package edu.gcc.keen;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.gcc.keen.gamestates.GameState;
import edu.gcc.keen.gamestates.Level;
import edu.gcc.keen.graphics.MasterRenderer;

/**
 * The main game object. Includes the main game loop and the current game state.
 * 
 * @author DONMOYERLR17
 *
 */
public class KeenMain
{
	private static final Logger LOGGER = Logger.getLogger("Logger");
	private static MasterRenderer renderer = new MasterRenderer();
	private static GameState currentState;

	private static boolean running = true;

	/**
	 * Main game loop. Calls the tick and render methods for the current game state
	 */
	public static void run()
	{
		if (!renderer.init())
			terminate();

		currentState = new Level("csvtest");

		long lastTime = System.nanoTime();
		long updateCounter = System.currentTimeMillis();
		double tps = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		while (KeenMain.running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / tps;
			lastTime = now;

			if (delta >= 1.0)
			{
				KeenMain.currentState.tick();
				updates++;
				delta--;
			}

			KeenMain.currentState.render(renderer);
			frames++;

			if (System.currentTimeMillis() - updateCounter >= 1000)
			{
				LOGGER.log(java.util.logging.Level.FINE, "Updates {0}\tFrames {1}", new Object[] { updates, frames });
				updateCounter = System.currentTimeMillis();
				updates = 0;
				frames = 0;
			}
		}

		cleanup();
	}

	/**
	 * Runs when game is about to close to cleanup all created resources
	 */
	public static void cleanup()
	{
		renderer.cleanup();
	}

	/**
	 * Sets the current game state to be rendered and updated. Can be called
	 * from anywhere
	 * 
	 * @param state
	 */
	public static void setState(GameState state)
	{
		KeenMain.currentState = state;
	}

	/**
	 * Sets running to false to tell the main game loop to exit next cycle. Can be
	 * called from anywhere
	 */
	public static void terminate()
	{
		KeenMain.running = false;
	}

	/**
	 * Entrypoint. Sets up the logger and starts the main game.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		LOGGER.setLevel(java.util.logging.Level.FINE);
		try
		{
			FileHandler fileHandler = new FileHandler("log.log");
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
		}
		catch (Exception e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		KeenMain.run();
	}
}
