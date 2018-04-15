package edu.gcc.keen.gamestates;

import edu.gcc.keen.graphics.Camera;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.util.GameObject;

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

	/**
	 * Default constructor
	 */
	public GameState()
	{
		this.camera = new Camera();
	}

	/**
	 * Constructor
	 * 
	 * @param cameraObject
	 */
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
