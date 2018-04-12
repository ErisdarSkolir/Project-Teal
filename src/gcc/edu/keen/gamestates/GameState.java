package gcc.edu.keen.gamestates;

import edu.gcc.keen.util.GameObject;
import gcc.edu.keen.graphics.Camera;
import gcc.edu.keen.graphics.MasterRenderer;

/**
 * A game state. Used to differentiate levels, the main menu, and the world map
 * from each other
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class GameState
{
	protected Camera camera;

	public GameState()
	{
		this.camera = new Camera();
	}

	public GameState(GameObject cameraObject)
	{
		this.camera = new Camera(cameraObject);
	}

	/**
	 * Update the current game state and update all objects inside this state
	 */
	public void tick()
	{
		camera.tick();
	}

	/**
	 * Render every object inside this state
	 */
	public abstract void render(MasterRenderer renderer);
}
