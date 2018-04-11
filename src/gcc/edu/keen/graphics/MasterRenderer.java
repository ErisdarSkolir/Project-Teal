package gcc.edu.keen.graphics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import edu.gcc.keen.item.Item;
import gcc.edu.keen.entities.Entity;
import gcc.edu.keen.tiles.Tile;

/**
 * The master renderer handles window creation and listeners.
 * 
 * @author DONMOYERLR17
 *
 */
public class MasterRenderer
{
	private Set<Integer> vaos = new HashSet<>();
	private Set<Integer> vbos = new HashSet<>();

	/**
	 * Render the given set of game objects to the screen
	 * 
	 * @param entities
	 * @param tiles
	 * @param items
	 */
	public void render(List<Entity> entities, List<Tile> tiles, List<Item> items)
	{

	}

	/**
	 * Create a GLFW window, set callbacks and create and attach an OpenGL context
	 * 
	 * @return true if initialization was successful, false otherwise.
	 */
	public boolean init()
	{
		if (!GLFW.glfwInit())
			return false;

		return true;
	}

	/**
	 * Creates a given GLFW window with the given parameters
	 * 
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @return the address of the new GLFW window or NULL if error ocurred
	 */
	public long createGlfwWindow(int width, int height, String title, long monitor)
	{
		return GLFW.glfwCreateWindow(width, height, title, monitor, MemoryUtil.NULL);
	}

	/**
	 * Releases GLFW window and cleanup used resources
	 */
	public void cleanup()
	{
		GLFW.glfwTerminate();
	}
}
