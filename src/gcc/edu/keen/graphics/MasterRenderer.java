package gcc.edu.keen.graphics;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import edu.gcc.keen.item.Item;
import edu.gcc.keen.util.BufferUtils;
import gcc.edu.keen.Keen;
import gcc.edu.keen.entities.Entity;
import gcc.edu.keen.input.Input;
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

	private TwoDimensionalShader shader;
	private Mesh quad;
	private Texture texture;

	private long window;

	/**
	 * Render the given set of game objects to the screen
	 * 
	 * @param entities
	 * @param tiles
	 * @param items
	 */
	public void render(List<Entity> entities, List<Tile> tiles, List<Item> items, Camera camera)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		shader.enable();
		GL11.glEnable(GL11.GL_BLEND);
		GL20.glEnableVertexAttribArray(0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		shader.loadTransformationMatrix(createTransformationMatrix(new Vector2f(0.0f, 0.0f), new Vector2f(10.0f, 10.0f)));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL20.glDisableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		shader.disable();

		// TODO write rendering

		if (GLFW.glfwWindowShouldClose(window))
			Keen.terminate();

		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
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

		window = createGlfwWindow(1920, 1080, true, "Keen", GLFW.glfwGetPrimaryMonitor());

		Input.createCallbacks(window);

		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		GLFW.glfwSwapInterval(1);

		GL.createCapabilities();
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

		shader = new TwoDimensionalShader();
		texture = new Texture("tilesheet");

		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = loadToVAO(positions);

		return true;
	}

	/**
	 * Creates a GLFW window with the given parameters
	 * 
	 * @param width
	 * @param height
	 * @param title
	 * @param monitor
	 * @return the address of the new GLFW window or NULL if error ocurred
	 */
	public long createGlfwWindow(int width, int height, boolean windowed, String title, long monitor)
	{
		if (windowed)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GL11.GL_TRUE);
			return GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		}
		else
			return GLFW.glfwCreateWindow(width, height, title, monitor, MemoryUtil.NULL);
	}

	/**
	 * Create vertex array object
	 * 
	 * @return id of newly created vao
	 */
	private int createVao()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	/**
	 * Create vertex buffer object
	 * 
	 * @return id of newly created vbo
	 */
	private int createVbo(int floatCount)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_STREAM_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vboID;
	}

	/**
	 * Load to VAO.
	 *
	 * @param positions
	 *            the positions
	 * @return the mesh
	 */
	public Mesh loadToVAO(float[] positions)
	{
		int vaoID = createVao();
		storeDataInAttributeList(0, 2, positions);
		GL30.glBindVertexArray(0);
		return new Mesh(vaoID, positions.length / 2);
	}

	/**
	 * Store data in attribute list.
	 *
	 * @param attributeNumber
	 *            the attribute number
	 * @param coordinateSize
	 *            the coordinate size
	 * @param data
	 *            the data
	 */
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * returns an orthographic projection matrix with the given widht and height of
	 * the screen
	 * 
	 * @param width
	 * @param height
	 * @return the matrix4f orthographic matrix
	 */
	private Matrix4f getOrthographicMatrix(int width, int height)
	{
		return new Matrix4f().ortho(0, width, 0, height, -1, 1);
	}

	public Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(new Vector3f(translation, 0));
		matrix.scale(new Vector3f(scale.x, scale.y, 1f));
		return matrix;
	}

	/**
	 * Releases GLFW window and cleanup used resources
	 */
	public void cleanup()
	{
		for (int i : vaos)
		{
			GL30.glDeleteVertexArrays(i);
		}

		for (int i : vbos)
		{
			GL15.glDeleteBuffers(i);
		}

		GLFW.glfwTerminate();
	}
}
