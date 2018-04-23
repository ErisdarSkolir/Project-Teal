package edu.gcc.keen.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import edu.gcc.keen.KeenMain;
import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.entities.Keen;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.Tile;
import edu.gcc.keen.tiles.TileCreator;
import edu.gcc.keen.util.BufferUtils;
import edu.gcc.keen.util.GameObject;

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

	private Map<String, Texture> textures = new HashMap<>();

	private TwoDimensionalShader shader;
	private Mesh quad;

	private long window;

	/**
	 * Render the given set of game objects to the screen
	 * 
	 * @param entities
	 * @param tiles
	 * @param items
	 */
	public void render(Keen keen, List<Entity> entities, List<Tile> tiles, List<Item> items, Camera camera)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		shader.enable();
		shader.loadMatrix("viewMatrix", createViewMatrix(camera));

		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);

		drawObjects(textures.get("tiles").getID(), tiles);
		drawObjects(textures.get("enemies").getID(), entities);
		drawObject(textures.get("keen").getID(), keen);

		GL20.glDisableVertexAttribArray(0);

		shader.disable();

		if (GLFW.glfwWindowShouldClose(window))
			KeenMain.terminate();

		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}

	public void drawObjects(int texture, List<? extends GameObject> objects)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		for (GameObject object : objects)
		{
			shader.loadTransformationMatrix(createTransformationMatrix(object.getPosition(), object.getScale()));
			shader.loadTextureAtlasInformation(object.getTexture().getTextureRowsAndColumns(), object.getTexture().getTextureOffset());

			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void drawObject(int texture, GameObject object)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

		shader.loadTransformationMatrix(createTransformationMatrix(object.getPosition(), object.getScale()));
		shader.loadTextureAtlasInformation(object.getTexture().getTextureRowsAndColumns(), object.getTexture().getTextureOffset());

		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
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
		GLFW.glfwSwapInterval(0);

		GL.createCapabilities();
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		shader = new TwoDimensionalShader();

		shader.enable();
		shader.loadMatrix("orthographicMatrix", getOrthographicMatrix(-16.0f, 16.0f, -9.0f, 9.0f, -1.0f, 1.0f));
		shader.disable();

		textures.put("tiles", new Texture("tilesheet"));
		textures.put("keen", new Texture("keen_spritesheet"));
		textures.put("enemies", new Texture("enemy"));
		textures.put("background", new Texture("background"));

		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = loadToVAO(positions);

		TileCreator.loadData("src/edu/gcc/keen/tiles/tiledata.dat");

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
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4L, GL15.GL_STREAM_DRAW);
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
	private Matrix4f getOrthographicMatrix(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4f matrix = new Matrix4f().identity();

		matrix._m00(2.0f / (right - left));
		matrix._m11(2.0f / (top - bottom));
		matrix._m22(-2.0f / (far - near));
		matrix._m03((right + left) / (right - left));
		matrix._m13((top + bottom) / (top - bottom));
		matrix._m23((far + near) / (far - near));

		return matrix;
	}

	/**
	 * Create a transformation matrix depending on the given objects position,
	 * scale, and the camera's position
	 * 
	 * @param translation
	 * @param scale
	 * @return
	 */
	public Matrix4f createTransformationMatrix(Vector3f translation, Vector2f scale)
	{
		Matrix4f matrix = new Matrix4f().identity();
		matrix.translate(translation);
		matrix.scale(new Vector3f(scale, 0));
		return matrix;
	}

	/**
	 * Create view matrix from camera position
	 * 
	 * @param camera
	 * @return the camera view matrix
	 */
	public Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f matrix = new Matrix4f().identity();
		matrix.translate(-camera.getPosition().x, -camera.getPosition().y, 0.0f);
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
