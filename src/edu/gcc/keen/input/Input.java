package edu.gcc.keen.input;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

/**
 * This class handles all input and presents it in a static way so it can be
 * accessed globally
 * 
 * @author DONMOYERLR17
 *
 */
public class Input
{
	private static boolean[] keys = new boolean[600];
	private static boolean[] buttons = new boolean[10];
	private static Vector2d mousePosition = new Vector2d();

	public Input()
	{
		// Default constructor
	}

	/**
	 * Check if the given key is being pressed. Uses the standard GLFW keycodes
	 * 
	 * @param keycode
	 * @return true if key is pressed
	 */
	public static boolean isKeyDown(int keycode)
	{
		return keys[keycode];
	}

	/**
	 * Check if the given mouse button is being pressed. Uses the standard GLFW
	 * keycodes
	 * 
	 * @param keycode
	 * @return true if button is pressed
	 */
	public static boolean isButtonDown(int keycode)
	{
		return buttons[keycode];
	}

	/**
	 * Get the most recent mouse position as a 2d vector
	 * 
	 * @return the x and y position of the mouse cursor in a Vector2d
	 */
	public static Vector2d getMousePosition()
	{
		return new Vector2d(mousePosition);
	}

	/**
	 * Creates GLFW callbacks and attaches them to the specified window
	 * 
	 * @param WINDOW
	 */
	public static void createCallbacks(final long WINDOW)
	{
		GLFW.glfwSetKeyCallback(WINDOW, (long window, int key, int scancode, int action, int mods) ->
		{
			keys[key] = (action != GLFW.GLFW_RELEASE);
		});

		GLFW.glfwSetMouseButtonCallback(WINDOW, (long window, int button, int action, int mod) ->
		{
			buttons[button] = (action != GLFW.GLFW_RELEASE);

		});

		GLFW.glfwSetCursorPosCallback(WINDOW, (long window, double posx, double posy) ->
		{
			mousePosition.set(posx, posy);
		});
	}
}
