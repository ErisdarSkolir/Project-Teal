package edu.gcc.keen.gamestates;

import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.KeenMain;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.graphics.Camera;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.input.Input;

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

		if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
			KeenMain.terminate();
	}

	/**
	 * Render every object inside this state
	 */
	public abstract void render(MasterRenderer renderer);
}
