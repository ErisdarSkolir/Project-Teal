package gcc.edu.keen;

import gcc.edu.keen.gamestates.GameState;
import gcc.edu.keen.gamestates.MainMenu;
import gcc.edu.keen.graphics.MasterRenderer;
import gcc.edu.keen.graphics.Texture;

/**
 * The main game object. Includes the main game loop and the current game state.
 * 
 * @author DONMOYERLR17
 *
 */
public class Keen
{
	private MasterRenderer renderer = new MasterRenderer();
	private GameState currentState;

	private boolean running = true;

	/**
	 * Main game loop. Calls the tick and render methods for the current game state
	 */
	public void main()
	{
		currentState = new MainMenu();

		while (running)
		{

		}

		cleanup();
	}

	/**
	 * Runs when game is about to close to cleanup all created resources
	 */
	public void cleanup()
	{
		Texture.cleanup();
	}

	/**
	 * Entrypoint.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Keen().main();
	}
}
