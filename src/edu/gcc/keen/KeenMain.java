package edu.gcc.keen;

import edu.gcc.keen.gamestates.GameState;
import edu.gcc.keen.gamestates.Level;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.graphics.Texture;

/**
 * The main game object. Includes the main game loop and the current game state.
 * 
 * @author DONMOYERLR17
 *
 */
public class KeenMain
{
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

		currentState = new Level("test");

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
				System.out.println("Updates " + updates + " \tFrames " + frames);
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
		Texture.cleanup();
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
	 * Entrypoint
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		KeenMain.run();
	}
}
