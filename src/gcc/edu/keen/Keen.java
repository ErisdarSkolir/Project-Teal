package gcc.edu.keen;

/**
 * The main game object. Includes the main game loop and the current game state.
 * 
 * @author DONMOYERLR17
 *
 */
public class Keen
{
	private boolean running = true;

	/**
	 * Entrypoint.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Keen keen = new Keen();
		keen.main();
	}

	/**
	 * Main game loop. Calls the tick and render methods for the current game state
	 */
	public void main()
	{
		while (running)
		{

		}
	}
}
