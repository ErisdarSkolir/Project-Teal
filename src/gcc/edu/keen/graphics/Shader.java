package gcc.edu.keen.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import edu.gcc.keen.util.FileUtils;

public abstract class Shader
{
	private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
	private Map<String, Integer> uniforms = new HashMap<>();

	private int id;

	private boolean enabled = false;

	protected Shader(String vertexPath, String fragementPath, String... uniformNames)
	{
		id = createShader(vertexPath, fragementPath);

		bindAttributes();
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		getUniformLocations(uniformNames);
	}

	protected abstract void bindAttributes();

	/**
	 * Find the integer ids for all given uniforms in this shader and store them in
	 * a map
	 * 
	 * @param uniformNames
	 */
	protected void getUniformLocations(String... uniformNames)
	{
		for (String name : uniformNames)
		{
			uniforms.put(name, GL20.glGetUniformLocation(id, name));
		}
	}

	/**
	 * Bind current shader attributes
	 * 
	 * @param attribute
	 * @param variableName
	 */
	protected void bindAttribute(int attribute, String variableName)
	{
		GL20.glBindAttribLocation(id, attribute, variableName);
	}

	/**
	 * Create a vertex and fragment shader program from the given file paths and
	 * return the program id
	 * 
	 * @param vertexPath
	 * @param fragmentPath
	 * @return the OpenGL program id
	 */
	private int createShader(String vertexPath, String fragmentPath)
	{
		int program = GL20.glCreateProgram();
		int vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		String vertString = FileUtils.loadAsString("res/shaders/" + vertexPath + ".vert");
		String fragString = FileUtils.loadAsString("res/shaders/" + fragmentPath + ".frag");

		GL20.glShaderSource(vertID, vertString);
		GL20.glCompileShader(vertID);
		if (GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(vertID));
		}
		GL20.glAttachShader(program, vertID);
		GL20.glDeleteShader(vertID);

		GL20.glShaderSource(fragID, fragString);
		GL20.glCompileShader(fragID);
		if (GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(fragID));
		}
		GL20.glAttachShader(program, fragID);
		GL20.glDeleteShader(fragID);

		return program;
	}

	protected void loadMatrix(String location, Matrix4f matrix)
	{
		GL20.glUniformMatrix4fv(uniforms.get(location), false, matrix.get(buffer));
	}

	/**
	 * Enable this shader
	 */
	public void enable()
	{
		enabled = true;
		GL20.glUseProgram(id);
	}

	/**
	 * Disable this shader
	 */
	public void disable()
	{
		enabled = false;
		GL20.glUseProgram(id);
	}

	/**
	 * Delete this shader to free up the resources OpenGL acquired
	 */
	public void cleanup()
	{
		if (enabled)
			disable();

		GL20.glDeleteProgram(id);
	}
}
